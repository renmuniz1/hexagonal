output "post_endpoint_url" {
  value = "http://localhost:4566/restapis/${aws_api_gateway_rest_api.debit_api.id}/local/_user_request_/api/v1/debit"
}