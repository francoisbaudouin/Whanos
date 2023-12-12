terraform {
  required_version = ">= 0.14.0" # Takes into account Terraform versions from 0.14.0
  required_providers {
    openstack = {
      source  = "terraform-provider-openstack/openstack"
      version = "~> 1.42.0"
    }

    ovh = {
      source  = "ovh/ovh"
      version = ">= 0.13.0"
    }
  }
}

provider "ovh" {
  endpoint    = "ovh-eu"  # Replace with the appropriate OVH endpoint for your region
  application_key    = "your_application_key"
  application_secret = "your_application_secret"
  consumer_key       = "your_consumer_key"
}
