  # Desafio 3 Compass UOL
  
  Este projeto tem como objetivo desenvolver uma API para o registro de usuários e atualização de senhas utilizando **Spring Boot**. A API inclui segurança com JWT e um sistema de mensageria para comunicação com outro microserviço.
  
  ## Tecnologias Utilizadas
  
  - **Java Development Kit (JDK)**: 21
  - **Spring Framework**: 3.3.5
  - **MongoDB**: latest
  - **MySQL**: 8.0.39
  - **Apache Kafka**: Para a mensageria
  - **Docker**: Para os containers da aplicação
  - **Swagger**: Para documentação da API
  
  ## Requisitos
  
  Certifique-se de ter as seguintes ferramentas instaladas:
  
  - **Java Development Kit (JDK) 21 ou superior**
  - **Docker e Docker Compose**
  - **Ferramenta para requisições HTTP** (Ex. Postman, Insomnia)
  
  - **OBSERVAÇÃO:** Execute o docker-compose para garantir o pleno funcionanmento dos micro-serviços
  
  ## Configuração do Projeto e Banco de Dados
  Siga os passos abaixo para configurar o projeto em seu ambiente de testes:

1. **Clone o repositório**

   ```bash
   git clone https://github.com/LucasGeneroso15/challenge-3-compass.git
   cd nome-do-diretorio
   ```

2. **Executar o projeto**

   Acesse o diretório raiz do projeto (local que está o docker-compose.yml):
   ```bash
   docker-compose up -d
   ```
   Certifique-se que os serviços estão funcionando corretamente

3. **Documentação Swagger**

   Acesse as urls para verificar o formato das requisições:
    - **Users Endpoints**: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#)
    - **Messages Endpoint**: [http://localhost:8081/swagger-ui/index.html#](http://localhost:8081/swagger-ui/index.html#)

4. **Ferramenta de Requisições HTTP**

  Caso prefira requisitar os endpoints utilizando uma ferramenta diferente (como POSTMAN ou INSOMNIA), verifique a documentação para as requisições.

  ### Contato
  Em caso de dúvidas ou problemas, entre em contato com:
  
  * Nome: Lucas Generoso da Silva
  * Email: lucas.generoso.pb@compasso.com.br
  * GitHub: https://github.com/LucasGeneroso15

