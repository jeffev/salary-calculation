# salary-calculation

Este projeto é um **Sistema de Cálculo de Salários** desenvolvido com **Spring Boot** e **Hibernate**. O sistema importa dados de uma planilha Excel, cria uma tabela com os dados consolidados e calcula os salários.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para simplificação da configuração e desenvolvimento de aplicações Java.
- **Hibernate**: Implementação de JPA para gerenciamento de persistência.
- **PostgreSQL**: Banco de dados relacional para armazenamento dos dados.
- **JoinFaces**: Biblioteca para integração de JSF com Spring Boot.
- **Mockito**: Framework para testes de unidade em Java.
- **JUnit**: Framework para testes automatizados em Java.

## Funcionalidades Implementadas

- **Cálculo/Recalculo de Salários**: Calcula ou recalcula os salários dos funcionários com base nos dados importados.
- **Listagem de Funcionários e Salários**: Exibe uma lista de todos os funcionários e seus salários calculados.

## Configuração do Projeto

### 1. **Clonando o Repositório**

Clone o repositório e navegue até a pasta do projeto:

```bash
git clone https://github.com/jeffev/salary-calculation.git
cd salary-calculation
```

### 2. **Configuração do Banco de Dados**

Certifique-se de ter o PostgreSQL instalado e um banco de dados criado para o projeto. Atualize as configurações do banco de dados no arquivo `src/main/resources/application.properties`:

```properties
# Configurações do Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/salary_calculation_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Configuração do Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. **Construindo o Projeto**

Certifique-se de ter o Maven instalado. Execute o seguinte comando para construir o projeto:

```bash
mvn clean package
```

### 4. **Executando os Testes**

Execute os testes automatizados para garantir que tudo esteja funcionando corretamente:

```bash
mvn test
```

### 5. **Executando a Aplicação**

Inicie a aplicação Spring Boot usando o comando Maven:

```bash
mvn spring-boot:run
```

### 6. **Acessando a Aplicação**

Abra um navegador e acesse:

```
http://localhost:8080/index.xhtml
```

Aqui, você verá a interface da aplicação para gerenciamento dos funcionários e cálculo dos salários.

## Estrutura do Projeto

- **`src/main/java/com/example`**: Contém o código fonte do projeto.
  - **`controller`**: Controladores para gerenciar as solicitações HTTP.
  - **`model`**: Classes de modelo de dados.
  - **`repository`**: Repositórios para acesso aos dados.
- **`src/main/resources`**: Contém recursos da aplicação.
  - **`application.properties`**: Configurações do Spring Boot e Hibernate.
  - **`META-INF/resources`**: Arquivos XHTML para renderização de páginas JSF.

## Contribuindo

Se você deseja contribuir para o projeto, siga estas etapas:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/feature-name`).
3. Commit suas alterações (`git commit -am 'Add new feature'`).
4. Envie para o repositório remoto (`git push origin feature/feature-name`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## Contato

Para mais informações, entre em contato com:

- **Nome**: Jefferson Valandro 
- **E-mail**: jeffev123@gmail.com
