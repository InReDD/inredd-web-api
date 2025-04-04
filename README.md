# Inredd-Webservices-API

## 🚀 Requisitos
Antes de iniciar, instale as seguintes dependências:

✅ Java 11 (ou superior)  
✅ Maven  
✅ Extensões do Java para o VS Code *(se for utilizar VS Code, mas pode testar em outra IDE)*  

## 🔧 Instalação do Maven
Se o Maven não estiver instalado, siga os passos:

```sh
wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz

tar -xzvf apache-maven-3.9.6-bin.tar.gz -C /opt/

sudo mv /opt/apache-maven-3.9.6 /opt/maven

nano ~/.bashrc
```
Adicione as linhas no final do arquivo:
```sh
export MAVEN_HOME=/opt/maven
export PATH=$PATH:$MAVEN_HOME/bin
```
Depois, execute:
```sh
source ~/.bashrc
mvn -v  # Verifique a instalação
```

## ▶️ Rodando a Aplicação

Para iniciar a aplicação, execute:
```sh
mvn spring-boot:run
```
*(Por enquanto, podemos rodar dessa forma pois havera muitos testes. A migração para Docker pode ser feita rapidamente depois.)*

## ✅ Testando a API
Após subir o serviço, acesse:  
📌 `http://localhost:8881/`

Se precisar modificar a porta ou algum dado do banco, edite o arquivo **application.properties**:
```properties
server.port=8881
```

## Ajuste no Banco de Dados
Se o ID do usuário não estiver sendo autoincrementado, execute o seguinte comando no banco (ta anotado para eu ver isso se continuar dando problema quando eu subir a aplicacao dnv, provavel BO de sql):
```sql
SELECT setval('user_id_user_seq', (SELECT MAX(id_user) FROM "user"));
```

## Criando um Usuário
**Endpoint:**
```
POST: http://localhost:8881/users
```

**Corpo da requisição:**
```json
{
  "firstName": "Thiago",
  "lastName": "Teste API",
  "email": "thiago.vmatos5@gmail.com",
  "password": "t123456",
  "active": true,
  "contact": "0001"
}
```

## Como Fazer Login
**Endpoint:**
```
POST: http://localhost:8881/oauth/token
```

**Autenticação:**
- Tipo: Basic Auth
- Usuário: `client`
- Senha: `client`

**Body (x-www-form-urlencoded):**
```
grant_type=password
username=thiago.vmatos5@gmail.com
password=t123456
```

### Resposta
O retorno incluirá o `refresh_token`, que possuem uma duração específica.


