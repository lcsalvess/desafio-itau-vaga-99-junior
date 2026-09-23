# API de Transações e Estatísticas

API REST desenvolvida em Java e Spring Boot como exercício prático de desenvolvimento de APIs, testes automatizados, tratamento de erros, observabilidade e documentação.

O projeto foi baseado no [desafio técnico do Itaú Unibanco](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior), mas esta implementação foi desenvolvida **exclusivamente como projeto de estudo**, com foco no aprendizado e na aplicação de boas práticas de desenvolvimento backend.

## 1. Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Validation
- Spring Boot Actuator
- Springdoc OpenAPI
- Swagger UI
- Maven
- JUnit
- Mockito

## 2. Características

- API REST
- Armazenamento das transações **em memória**
- Cálculo de estatísticas por intervalo de tempo
- Intervalo padrão configurável
- Testes automatizados
- Logs da aplicação
- Healthcheck e métricas com Spring Boot Actuator
- Documentação da API com OpenAPI/Swagger
- Tratamento global de erros

## 3. Requisitos

Para executar o projeto, é necessário ter instalado:

- Java 21
- Git

O projeto utiliza o Maven Wrapper, portanto não é necessário instalar o Maven separadamente.

## 4. Como executar

Clone o repositório:

```bash
git clone https://github.com/lcsalvess/transaction-statistics-api.git
```

Entre no diretório:

```bash
cd transaction-statistics-api
```

Execute a aplicação:

**Windows**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/macOS**
```bash
./mvnw spring-boot:run
```

A aplicação será iniciada por padrão em:

```
http://localhost:8080
```

## 5. Testes

Para executar todos os testes:

**Windows**
```bash
.\mvnw.cmd test
```

**Linux/macOS**
```bash
./mvnw test
```

Os testes abrangem diferentes camadas da aplicação, incluindo:

- Regras de negócio
- Validação das transações
- Cálculo das estatísticas
- Controle do intervalo de tempo
- Controllers
- Tratamento de requisições inválidas
- Comportamentos de sucesso e erro

## 6. Endpoints

### Criar uma transação

`POST /transacao`

Cria uma nova transação e a armazena em memória.

Exemplo de requisição:

```json
{
  "valor": 150.50,
  "dataHora": "2026-09-22T19:30:00-03:00"
}
```

Respostas:

| Status | Descrição |
|---|---|
| 201 Created | Transação criada com sucesso |
| 422 Unprocessable Entity | Dados da transação inválidos |
| 400 Bad Request | JSON inválido |

A transação deve:

- possuir `valor` e `dataHora`;
- possuir `valor` maior ou igual a 0;
- possuir uma data/hora que não esteja no futuro.

### Excluir todas as transações

`DELETE /transacao`

Remove todas as transações armazenadas em memória.

Resposta:

| Status | Descrição |
|---|---|
| 200 OK | Transações excluídas com sucesso |

### Consultar estatísticas

`GET /estatistica`

Retorna estatísticas das transações realizadas dentro do intervalo informado.

Sem informar o parâmetro `intervalo`, é utilizado o valor configurado como padrão.

Exemplo:

```
GET /estatistica
```

Também é possível informar um intervalo personalizado:

```
GET /estatistica?intervalo=120
```

Resposta:

```json
{
  "count": 5,
  "sum": 750.0,
  "avg": 150.0,
  "min": 50.0,
  "max": 300.0
}
```

Quando não existem transações dentro do intervalo:

```json
{
  "count": 0,
  "sum": 0.0,
  "avg": 0.0,
  "min": 0.0,
  "max": 0.0
}
```

## 7. Documentação da API

A API possui documentação interativa utilizando OpenAPI e Swagger UI.

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI também pode ser consultada em:

```
http://localhost:8080/v3/api-docs
```

A documentação apresenta:

- endpoints disponíveis;
- parâmetros;
- códigos de resposta;
- exemplos de requisição;
- exemplos de resposta;
- descrição dos campos dos DTOs;
- tipos e regras de validação dos dados.

## 8. Observabilidade

A aplicação utiliza Spring Boot Actuator para disponibilizar informações de saúde e métricas.

### Healthcheck

```
GET /actuator/health
```

Exemplo de resposta:

```json
{
  "status": "UP"
}
```

### Métricas disponíveis

```
GET /actuator/metrics
```

A aplicação também disponibiliza métricas relacionadas às requisições HTTP, JVM, sistema e aplicação.

Por exemplo:

```
GET /actuator/metrics/http.server.requests
```

É possível filtrar as métricas por endpoint e método HTTP utilizando as tags disponibilizadas pelo Actuator.

## 9. Configuração

O intervalo padrão utilizado para calcular as estatísticas pode ser configurado no arquivo:

```
src/main/resources/application.properties
```

Configuração padrão:

```properties
estatistica.intervalo-padrao-segundos=60
```

Por exemplo, para utilizar 120 segundos como intervalo padrão:

```properties
estatistica.intervalo-padrao-segundos=120
```

Também é possível sobrescrever o intervalo diretamente na requisição através do parâmetro `intervalo`.

## 10. Logs

A aplicação utiliza o sistema de logging do Spring Boot para registrar eventos relevantes da aplicação.

Entre os eventos registrados estão:

- criação de transações;
- exclusão de transações;
- cálculo de estatísticas.

O nível de log da aplicação pode ser configurado em:

```properties
logging.level.com.lucas.transactionstatistics=INFO
```

## 11. Arquitetura

O projeto utiliza uma separação simples entre controllers, services e DTOs.

```
src
└── main
    ├── java
    │   └── com.lucas.transactionstatistics
    │       ├── controller
    │       ├── dto
    │       ├── exception
    │       ├── model
    │       ├── service
    │       └── config
    │
    └── resources
        └── application.properties
```

**Controller**
Responsável por receber as requisições HTTP e retornar as respostas da API.

**Service**
Responsável pelas regras de negócio, como armazenamento das transações e cálculo das estatísticas.

**DTO**
Define o formato dos dados recebidos e retornados pela API.

**Exception**
Centraliza o tratamento das exceções relacionadas às requisições.

**Config**
Contém configurações utilizadas pela aplicação, incluindo a fonte de tempo utilizada pelo sistema.

## 12. Controle de tempo

A aplicação utiliza `java.time` e `Clock` para controlar a obtenção do horário atual.

Essa abordagem permite que a aplicação utilize o horário real em produção e um horário controlado durante os testes.

Isso torna os testes relacionados ao intervalo de tempo mais previsíveis e determinísticos.

## 13. Armazenamento

As transações são armazenadas exclusivamente em memória, conforme a proposta original do desafio.

Não é utilizado banco de dados ou sistema de cache.

As transações são perdidas quando a aplicação é encerrada.

## 14. Melhorias implementadas

Além dos requisitos básicos da API, o projeto possui algumas funcionalidades adicionais:

- testes automatizados;
- logs dos principais eventos da aplicação;
- healthcheck;
- métricas com Actuator;
- documentação OpenAPI;
- Swagger UI;
- intervalo de estatísticas configurável;
- tratamento global de erros;
- utilização de `Clock` para facilitar testes de tempo;
- validação dos dados recebidos pela API.

## 15. Status do projeto

Projeto desenvolvido como exercício prático de backend com Java e Spring Boot.

O objetivo principal é praticar conceitos de:

- desenvolvimento de APIs REST;
- Spring Boot;
- validação;
- testes automatizados;
- tratamento de exceções;
- observabilidade;
- documentação de APIs;
- configuração de aplicações;
- manipulação de data e hora;
- organização de código backend.
