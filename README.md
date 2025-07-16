# Projeto Api Hexagonal
![Image](https://github.com/user-attachments/assets/69c4d428-f37a-48ad-81a5-281fa1887566)

<img width="480" height="240" alt="Image" src="https://github.com/user-attachments/assets/101632bd-3389-475d-8cf5-565e5407afdb" />

Projeto utilizando java 21, spring boot e infra estruturada via terraform, utilizando localstack para simular serviços AWS


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
<img width="488" height="104" alt="Image" src="https://github.com/user-attachments/assets/5c22971b-49aa-4841-87d1-1b93e03ccdb4" />

* Dentro do projeto na pasta infra executar o comando **terraform init** e depois o comando **terraform apply** para gerar a infra aws simulada pelo localstack **(API GATEWAY, DYNAMODB, SNS)**. Ao final do comando uma mensagem output vai mostar a url para realizar a chamada do serviço.
<img width="901" height="124" alt="Image" src="https://github.com/user-attachments/assets/caadd322-15ef-4737-9439-0a8ba3270dcd" />

* Executar uma chamada POST para criar um debito com o seguinte exemplo de JSON
``` shell
{
  "reference": "REF-CREATE",
  "amount": 180.75
}
```
Onde amount é o montante do débito e reference um identificador fornecido pelo cliente da API

<img width="1919" height="648" alt="Image" src="https://github.com/user-attachments/assets/2a21ce77-665f-4f4d-9fd6-e18230bf140f" />

* Executar uma chamada PUT para cancelar o debito. Com o mesmo parâmtero reference utilizado na criação do debito, com o seguinte exemplo de JSON. Incluindo o id retornado na chamada post como parametro na url.
``` shell
{
  "reference": "REF-CREATE"
}
```

<img width="1919" height="835" alt="Image" src="https://github.com/user-attachments/assets/a586599d-f71c-4630-8c66-1c1c925fea30" />

* No site do [localstack](https://www.localstack.cloud/pricing) é possível verificar o informação inserida no dynamodb.

<img width="1505" height="648" alt="Image" src="https://github.com/user-attachments/assets/f5e62879-fb08-4ac6-b1d1-6a98c31ea1f7" />



