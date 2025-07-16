provider "aws" {
  region                      = "us-east-1"
  access_key                  = "test"
  secret_key                  = "test"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    apigateway = "http://localhost:4566"
    dynamodb   = "http://localhost:4566"
    sns        = "http://localhost:4566"
  }
}

module "apigateway" {
  source = "./modules/apigateway"
}

output "post_endpoint_url" {
  value = module.apigateway.post_endpoint_url
}

module "dynamodb" {
  source = "./modules/dynamodb"
}

module "sns" {
  source = "./modules/sns"
}

