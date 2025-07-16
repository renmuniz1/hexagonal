# Projeto Api Hexagonal
![Image](https://github.com/user-attachments/assets/69c4d428-f37a-48ad-81a5-281fa1887566)

<img width="480" height="240" alt="Image" src="https://github.com/user-attachments/assets/101632bd-3389-475d-8cf5-565e5407afdb" />

Projeto com utilizando java 21, spring boot e infra estruturada via terraform utilizando localstack para simular serviços AWS


## Recursos
- [x] aws resources
- [x] java 21
- [x] Spring Boot
- [x] Localstack
- [x] Docker

## O que preciso para rodar o projeto
* Ter o docker instalado localmente.
* Ter Terraform instalado.
* (Opcional) Criar uma [conta localstack](https://www.localstack.cloud/pricing) para visualizar os recursos aws utilizados. Utilizar o free tier

## Como executar o projeto
* Baixar o projeto do github e fazer checkout para branch master.
* Dentro do projeto na pasta app, executar o comando **docker-compose up --build -d**. Com esse comando o docker vai baixar as imagens necessarias e fazer o build da aplicação e subir o projeto localmente.
* Dentro do projeto na pasta infra executar o comando **terraform init** e depois o comando **terraform apply** para gerar a infra aws simulada pelo localstack.


