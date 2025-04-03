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


