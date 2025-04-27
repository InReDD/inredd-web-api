# Inredd-Webservices-API
## 🚀 Requisitos

Antes de iniciar, instale as seguintes dependências:

✅ Java 11 (ou superior)
✅ Maven
✅ Extensões do Java para o VS Code (se for utilizar VS Code, mas pode usar qualquer IDE também)

## 🔧 Instalação do Maven
```sh
wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz tar -xzvf apache-maven-3.9.6-bin.tar.gz -C /opt/ sudo mv /opt/apache-maven-3.9.6 /opt/maven

nano ~/.bashrc
```

Adicione no final do arquivo:
```sh
export MAVEN_HOME=/opt/maven
export PATH=$PATH:$MAVEN_HOME/bin
```

Depois:
```sh
source ~/.bashrc
mvn -v # Para verificar a instalação
```

## ▶️ Como Rodar a Aplicação com Docker

    Primeiro, entre na pasta inredd-web-api/ e gere o build do projeto:

```sh
cd inredd-web-api mvn clean package -DskipTests
```
    Em seguida, volte para a pasta onde está o docker-compose.yml (nível superior) e execute:

```sh
docker compose up -d --build inredd-web-api
```
Atenção:

    O docker-compose.yml precisa ser executado fora da pasta inredd-web-api/, pois o contexto de build é configurado para lá.

## 📚 Documentação da API

Depois que os containers estiverem rodando, acesse:
```sh
http://localhost:8881/swagger-ui/index.html
```
A documentação completa dos endpoints estará disponível via OpenAPI/Swagger.

## 🧑‍💻 Criando um Usuário

Endpoint:

POST: http://localhost:8881/users

Corpo da requisição:
```sh
{
  "firstName": "Thiago", 
  "lastName": "Teste API", 
  "email": "thiago.vmatos5@gmail.com", 
  "password": "t123456", 
  "active": true, 
  "contact": "0001"
}
```

## 🔐 Como Fazer Login

Endpoint:

POST: http://localhost:8881/oauth/token

Autenticação:

    Tipo: Basic Auth

    Usuário: client

    Senha: client

Body (x-www-form-urlencoded):

    grant_type=password
    username=thiago.vmatos5@gmail.com
    password=t123456

### 🔑 Resposta

O retorno incluirá um access_token e um refresh_token, que podem ser utilizados para autenticação nas próximas requisições.