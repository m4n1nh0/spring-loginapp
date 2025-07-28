# Projeto Base de Autenticação com Spring Boot e DDD (Web MVC)

## Descrição do Projeto

Este é um projeto base de autenticação desenvolvido com **Spring Boot**, aplicando os princípios do **Domain-Driven Design (DDD)**. Ele demonstra como construir um sistema de login e registro de usuários, utilizando o **Spring Security** para gerenciar a segurança e **Thymeleaf** para a renderização das páginas web (MVC tradicional).

O principal objetivo deste projeto é servir como um modelo para entender a aplicação do DDD em um contexto Spring Boot, separando claramente as preocupações em camadas (Domínio, Aplicação, Infraestrutura).

## Funcionalidades

* **Registro de Usuários:** Permite que novos usuários criem uma conta com nome de usuário e senha.
* **Login de Usuários:** Autentica usuários existentes.
* **Proteção de Rotas:** Utiliza Spring Security para proteger páginas, garantindo que apenas usuários autenticados possam acessá-las.
* **Criptografia de Senhas:** Armazena senhas de forma segura usando BCrypt.
* **Validação de Entrada:** Validação de dados de formulário com Jakarta Validation.
* **Banco de Dados em Memória:** Utiliza H2 Database para persistência em desenvolvimento, facilitando o setup.

## Tecnologias Utilizadas

* **Spring Boot `3.3.1`**: Framework base para o desenvolvimento rápido de aplicações Java.
* **Java `22`**: Linguagem de programação.
* **Maven**: Ferramenta de automação de construção e gerenciamento de dependências.
* **Spring Security**: Framework robusto para segurança de aplicações.
* **Spring Data JPA**: Facilita a interação com bancos de dados.
* **Hibernate**: Implementação ORM padrão do JPA.
* **H2 Database**: Banco de dados relacional em memória para desenvolvimento.
* **Thymeleaf**: Motor de template para rendering de HTML no lado do servidor.
* **Lombok**: Ferramenta para reduzir código boilerplate (getters, setters, construtores).
* **Jakarta Validation (Bean Validation)**: Para validação declarativa de objetos.

## Estrutura do Projeto (DDD)

A aplicação segue uma arquitetura baseada em camadas inspiradas no DDD:

```
src/main/java/com/exemplo/loginapp
├── application           <-- Orquestra casos de uso (comandos, queries, serviços de aplicação)
│   ├── command           (Objetos que representam intenções de ação)
│   ├── handler           (Componentes que executam a lógica para um comando)
│   ├── query             (Objetos para critérios de consulta)
│   └── service           (Serviços que coordenam o fluxo de aplicação)
├── config                <-- Configurações gerais (Spring Security, JPA)
├── domain                <-- O Coração do Negócio (Entidades, Objetos de Valor, Repositórios, Serviços de Domínio)
│   ├── model             (Entidades, Agregados, Objetos de Valor - e.g., User, UserId, Password)
│   ├── repository        (Interfaces que definem o contrato de persistência)
│   └── service           (Lógica de negócio complexa que não pertence a uma única Entidade)
├── infrastructure        <-- Detalhes Técnicos (Implementações de interfaces, Web, Persistência)
│   ├── adapter           (Adaptações para frameworks externos, e.g., Spring Security)
│   ├── persistence       (Implementações concretas dos repositórios, entidades JPA)
│   │   └── entity        (Entidades JPA, separadas do modelo de domínio)
│   └── web               (Controladores web, DTOs, filtros)
│       └── dto           (Objetos de Transferência de Dados para comunicação web)
└── shared                <-- Utilitários e Exceções Comuns
    ├── exception         (Exceções de negócio customizadas)
    └── util              (Classes utilitárias diversas)

```

## Como Rodar o Projeto

### Pré-requisitos

* **Java 22** ou superior instalado.
* **Maven** instalado.

### Configuração e Execução

1.  **Clone o repositório** (ou crie a estrutura de pastas e arquivos conforme o projeto fornecido):

    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd login-app
    ```

    *Se você está montando o projeto manualmente, certifique-se de que todos os arquivos `.java`, `pom.xml`, `application.properties` e os templates `.html` estão nas pastas corretas conforme a estrutura.*

2.  **Verifique o `pom.xml`**:
    Certifique-se de que a versão do Java e as dependências estão corretas, especialmente a adição de `spring-boot-starter-validation`.

3.  **Construa o Projeto**:
    Navegue até a pasta raiz do projeto (`login-app`) no seu terminal e execute:

    ```bash
    mvn clean install
    ```

    Isso baixará as dependências e compilará o projeto.

4.  **Execute a Aplicação**:
    Após a compilação, você pode iniciar a aplicação Spring Boot com:

    ```bash
    mvn spring-boot:run
    ```

    A aplicação será iniciada no `http://localhost:8080`.

## Endpoints da Aplicação (Web MVC)

Uma vez que a aplicação esteja rodando, você pode acessá-la via navegador:

* **Página de Login:** `http://localhost:8080/login`
* **Página de Registro:** `http://localhost:8080/register`
* **Página Protegida (Home):** `http://localhost:8080/home` (Acessível apenas após o login)
* **Console do H2 Database:** `http://localhost:8080/h2-console`
    * **JDBC URL:** `jdbc:h2:mem:loginappdb`
    * **User Name:** `sa`
    * **Password:** (deixe em branco)
    * **Query para verificar usuários:** `SELECT * FROM USERS;`

## Como Usar (Registro e Login)

1.  Acesse `http://localhost:8080/register`.
2.  Preencha o formulário com um **Nome de Usuário** e uma **Senha** (mínimo de 6 caracteres).
3.  Clique em "Registrar". Você será redirecionado para a página de login com uma mensagem de sucesso.
4.  Na página de login (`http://localhost:8080/login`), insira as credenciais que você acabou de criar.
5.  Clique em "Entrar". Se as credenciais estiverem corretas, você será redirecionado para a página `http://localhost:8080/home`.

## Observações de Desenvolvimento

* **Open Session In View (OSIV)**: O `application.properties` está configurado com `spring.jpa.open-in-view=false`, o que é uma boa prática para evitar problemas de N+1 e garantir que as transações de banco de dados ocorram na camada de serviço. Certifique-se de que todas as entidades e coleções lazy-loaded necessárias para as views são buscadas antes de retornar ao controlador.
* **Spring Security Warnings**: No log de inicialização, você pode ver um aviso sobre `AuthenticationProvider` e `UserDetailsService`. Este é esperado, pois estamos configurando explicitamente o `DaoAuthenticationProvider` que utiliza nosso `CustomUserDetailsService`, o que é o comportamento desejado.

## Contribuições

Sinta-se à vontade para explorar, modificar e aprimorar este projeto. Sugestões e pull requests são bem-vindos\!

## Licença

Este projeto está licenciado sob a licença MIT.
