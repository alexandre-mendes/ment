# Projeto Base - Mentoria Prática do Cassundé

Este é um projeto Spring Boot com Kotlin, desenvolvido como ponto de partida para as atividades práticas da mentoria.

## Visão Geral

O projeto está configurado com:
- Kotlin
- Gradle
- Spring Boot
- Spring Web
- Spring Data JPA
- Conexão com PostgreSQL

## Mock de Contratos com `json-server`

Para agilizar o desenvolvimento e permitir o trabalho desacoplado entre frontend e backend, utilizaremos o `json-server` para mockar os contratos da API.

A pasta `mock` no projeto contém os arquivos JSON que servem como base para o `json-server`.

### Como usar o `json-server`

1.  **Instale o `json-server` globalmente (caso não tenha):**
    ```bash
    npm install -g json-server
    ```

2.  **Inicie o servidor de mock:**
    A partir da raiz do projeto, execute o comando:
    ```bash
    json-server --watch mock/db.json
    ```

3.  O `json-server` estará rodando e servindo os dados do arquivo `db.json`.
