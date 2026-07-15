terraform {
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.32"
    }
  }
}

provider "kubernetes" {
  config_path = "~/.kube/config"
}

resource "kubernetes_secret" "postgres_credentials" {
  metadata {
    name = "postgres-credentials"
  }
  data = {
    POSTGRES_DB       = "inv-api-db"
    POSTGRES_USER     = "inv-api-user"
    POSTGRES_PASSWORD = "inv-api-secret"
  }
  type = "Opaque"
}

resource "kubernetes_deployment" "postgres" {
  metadata {
    name = "postgres"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "postgres"
      }
    }
    template {
      metadata {
        labels = {
          app = "postgres"
        }
      }
      spec {
        container {
          name  = "postgres"
          image = "postgres:latest"
          env_from {
            secret_ref {
              name = kubernetes_secret.postgres_credentials.metadata[0].name
            }
          }
          port {
            container_port = 5432
          }
          volume_mount {
            name       = "postgres-data"
            mount_path = "/var/lib/postgresql/data"
          }
          env {
            name  = "PGDATA"
            value = "/var/lib/postgresql/data/pgdata"
          }
        }
        volume {
          name = "postgres-data"
          persistent_volume_claim {
            claim_name = kubernetes_persistent_volume_claim.postgres_data.metadata[0].name
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "postgres" {
  metadata {
    name = "postgres"
  }
  spec {
    selector = {
      app = "postgres"
    }
    port {
      port        = 5432
      target_port = 5432
    }
  }
}

resource "kubernetes_persistent_volume_claim" "postgres_data" {
  metadata {
    name = "postgres-data"
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}

resource "kubernetes_deployment" "inventory_api" {
  metadata {
    name = "inventory-api"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "inventory-api"
      }
    }
    template {
      metadata {
        labels = {
          app = "inventory-api"
        }
      }
      spec {
        container {
          name = "inventory-api"
          image = "inventory-api-kotlin-app:latest"
          image_pull_policy = "Never"
          env {
            name = "SPRING_DATASOURCE_URL"
            value = "jdbc:postgresql://postgres:5432/inv-api-db"
          }
          env {
            name = "SPRING_DATASOURCE_USERNAME"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.postgres_credentials.metadata[0].name
                key  = "POSTGRES_USER"
              }
            }
          }
          env {
            name = "SPRING_DATASOURCE_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.postgres_credentials.metadata[0].name
                key  = "POSTGRES_PASSWORD"
              }
            }
          }
          port {
            container_port = 8080
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "inventory-api" {
  metadata {
    name = "inventory-api"
  }
  spec {
    type = "NodePort"
    selector = {
      app = "inventory-api"
    }
    port {
      port = 8080
      target_port = 8080
    }
  }
}