📦 CupomAPI

API REST desenvolvida como teste técnico, com foco em boas práticas de arquitetura, separação de responsabilidades, testes unitários e conteinerização com Docker.

🎯 Objetivo do Projeto

Demonstrar domínio em:

Arquitetura em camadas

Desenvolvimento de APIs REST com Spring Boot

Organização de código

Testes unitários

Uso de MapStruct para mapeamento de DTOs

Documentação de API com Swagger

Execução da aplicação via Docker

🏗️ Arquitetura e Estrutura do Projeto

O projeto segue uma arquitetura em camadas, facilitando manutenção, testabilidade e escalabilidade.

📂 Estrutura das Camadas

Controller
Responsável por expor os endpoints REST e orquestrar as requisições HTTP.

DTOs (Data Transfer Objects)
Utilizados para padronizar a comunicação entre as camadas, evitando exposição direta das entidades.

Entities
Contém as entidades de domínio e enums da aplicação, representando o modelo de dados.

Mappers
Interfaces responsáveis pelo mapeamento entre DTOs e Entities, utilizando MapStruct para reduzir código boilerplate.

Repositories
Interfaces de acesso a dados, utilizando Spring Data para comunicação com o banco de dados.

Service
Camada onde estão concentradas todas as regras de negócio da aplicação.

🧪 Testes

Foram implementados testes unitários na camada de Service

O objetivo é validar regras de negócio e garantir o correto funcionamento da aplicação

A separação clara das camadas facilita a escrita e manutenção dos testes

🛠️ Tecnologias Utilizadas

Java 21

Spring Boot 3.3.5

Spring Data JPA

MapStruct

H2 Database

Swagger (OpenAPI)

Docker

Docker Compose

📄 Documentação da API (Swagger)

A API é documentada automaticamente com Swagger.

Após iniciar a aplicação, acesse:

http://localhost:8080/swagger-ui.html

🐳 Executando o Projeto com Docker

O projeto já possui toda a configuração necessária para execução via Docker.

Pré-requisitos

Docker

Docker Compose

Passos para execução

Na raiz do projeto, execute:

docker compose up --build


Após a inicialização, a aplicação estará disponível em:

http://localhost:8080

✅ Considerações Finais

O banco de dados H2 foi utilizado para simplificar a execução e os testes

O projeto foi estruturado visando clareza, organização e boas práticas

A aplicação está pronta para avaliação técnica e evolução futura
