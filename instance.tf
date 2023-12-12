resource "ovh_cloud_project" "my_project" {
  name        = "my-project"  # Replace with your project name
  description = "My Terraform Project"
}

resource "ovh_vrack" "my_vrack" {
  cloud_project_id = ovh_cloud_project.my_project.id
  name             = "my-vrack"  # Replace with your vRack name
}

resource "ovh_private_network" "my_network" {
  cloud_project_id = ovh_cloud_project.my_project.id
  name             = "my-network"  # Replace with your network name
  subnet {
    cidr = "192.0.0.0/24"  # Replace with your desired CIDR block
  }
}

resource "ovh_public_ssh_key" "my_ssh_key" {
  cloud_project_id = ovh_cloud_project.my_project.id
  name             = "my-ssh-key"  # Replace with your SSH key name
  public_key       = file("~/.ssh/id_rsa.pub")  # Replace with the path to your public SSH key
}

resource "ovh_instance" "my_instance" {
  cloud_project_id    = ovh_cloud_project.my_project.id
  provider            = openstack.ovh
  name                = "my-instance"  # Replace with your instance name
  flavor_id           = "b2-7"  # Replace with your desired flavor ID
  image_id            = "d2d21517-f68d-45c7-953f-fb8e1e6c80e6"  # Replace with your desired image ID
  region              = "GRA7"  # Replace with your desired region code
  private_network_ids = [ovh_private_network.my_network.id]
  public_key_ids      = [ovh_public_ssh_key.my_ssh_key.id]
}
