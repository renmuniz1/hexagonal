resource "aws_api_gateway_rest_api" "debit_api" {
  name        = "debit-api"
  description = "API for creating and canceling debits"
}

resource "aws_api_gateway_resource" "api" {
  rest_api_id = aws_api_gateway_rest_api.debit_api.id
  parent_id   = aws_api_gateway_rest_api.debit_api.root_resource_id
  path_part   = "api"
}

resource "aws_api_gateway_resource" "v1" {
  rest_api_id = aws_api_gateway_rest_api.debit_api.id
  parent_id   = aws_api_gateway_resource.api.id
  path_part   = "v1"
}

resource "aws_api_gateway_resource" "debit" {
  rest_api_id = aws_api_gateway_rest_api.debit_api.id
  parent_id   = aws_api_gateway_resource.v1.id
  path_part   = "debit"
}

resource "aws_api_gateway_method" "post_debit" {
  rest_api_id   = aws_api_gateway_rest_api.debit_api.id
  resource_id   = aws_api_gateway_resource.debit.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "post_debit_integration" {
  rest_api_id             = aws_api_gateway_rest_api.debit_api.id
  resource_id             = aws_api_gateway_resource.debit.id
  http_method             = aws_api_gateway_method.post_debit.http_method
  integration_http_method = "POST"
  type                    = "HTTP_PROXY"
  uri                     = "http://app:8080/api/v1/debit"
}

resource "aws_api_gateway_resource" "debit_id" {
  rest_api_id = aws_api_gateway_rest_api.debit_api.id
  parent_id   = aws_api_gateway_resource.debit.id
  path_part   = "{id}"
}

resource "aws_api_gateway_method" "put_debit" {
  rest_api_id   = aws_api_gateway_rest_api.debit_api.id
  resource_id   = aws_api_gateway_resource.debit_id.id
  http_method   = "PUT"
  authorization = "NONE"

  request_parameters = {
    "method.request.path.id" = true
  }
}

resource "aws_api_gateway_integration" "put_debit_integration" {
  rest_api_id             = aws_api_gateway_rest_api.debit_api.id
  resource_id             = aws_api_gateway_resource.debit_id.id
  http_method             = aws_api_gateway_method.put_debit.http_method
  integration_http_method = "PUT"
  type                    = "HTTP_PROXY"
  uri                     = "http://app:8080/api/v1/debit/{id}"

  request_parameters = {
    "integration.request.path.id" = "method.request.path.id"
  }
}

resource "aws_api_gateway_deployment" "debit_api_deployment" {
  depends_on = [
    aws_api_gateway_integration.post_debit_integration,
    aws_api_gateway_integration.put_debit_integration,
  ]
  rest_api_id = aws_api_gateway_rest_api.debit_api.id
}

resource "aws_api_gateway_stage" "local" {
  stage_name    = "local"
  rest_api_id   = aws_api_gateway_rest_api.debit_api.id
  deployment_id = aws_api_gateway_deployment.debit_api_deployment.id
}
