resource "aws_dynamodb_table" "debit_table" {
  name         = "debit_entity"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"
  range_key    = "reference"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "reference"
    type = "S"
  }

  tags = {
    Environment = "local"
    Application = "hexagonal-debit"
  }
}