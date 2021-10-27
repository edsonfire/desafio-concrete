# Desafio Java Concrete Solutions
Api Rest para cria��o de usu�rios e login

jornada de usu�rio:
1. O usu�rio dever� se cadastrar
2. Uma vez cadastrado, o usu�rio dever� conseguir fazer login
3. Ao fazer login, o usu�rio dever� ter um token e poder� utilizar esse token para acessar o seu perfil

## Requisitos

* Utilizar JSON como media type em todos os endpoints, inclusive os de erro.
* Banco de dados em mem�ria, pode ser HSQLDB, H2 etc...
* Persist�ncia com Hibernate.
* Framework Spring Boot.
* Prazo de 4 dias corridos.
* Entregar um repo p�blico (github ou bitbucket) com o c�digo fonte.
* Java 7+.


Todas as mensagens de erro devem ter o formato:
>
>```json
>    {"mensagem": "mensagem de erro"}
>```


## Cadastro

>[POST] /desafio/api/v1/users/cadastro

>Este endpoint dever� ser utilizado para cadastrar um novo usu�rio na base, seguindo as especifica��es abaixo:

* Esse endpoint dever� receber no body um usu�rio com os campos "nome", "email", "senha", mais uma lista de objetos "telefone", seguindo o formato abaixo:

Exemplo:

>```json
>   {
>        "name": "Jo�o da Silva",
>        "email": "joao@silva.org",
>        "password": "hunter2",
>        "phones": [
>            {
>                "number": "987654321",
>                "ddd": "21"
>            }
>        ]
>    }
>```


* Em caso de sucesso, retorne no body de resposta, o usu�rio, mais os campos:
    * `id`: id do usu�rio (pode ser o pr�prio gerado pelo banco, por�m seria interessante se fosse um UUID)
    * `created`: data da cria��o do usu�rio
    * `modified`: data da �ltima atualiza��o do usu�rio
    * `last_login`: data do �ltimo login (no caso da cria��o, ser� a mesma data que a  data de cria��o)
    * `token`: token de acesso para o endpoint de perfil (pode ser um UUID ou um JWT)

Exemplo:

>```json
>    {   
>        "id": " 00c6de58-6582-11eb-ae93-0242ac130002",
>         "name": "Jo�o da Silva",
>         "email": "joao@silva.org",
>         "password": "hunter2",
>         "phones": [
>             {
>                 "number": "987654321",
>                 "ddd": "21"
>             }
>         ],
>         "created": "2020-10-03T19:30:00",
>         "modified": "2020-10-03T19:30:00",
>         "last_login": "2020-10-03T19:30:00",
>         "token": "eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA"
>     }
> ```

* Caso o e-mail j� exista, dever� retornar erro com a mensagem "E-mail j� existente".
  
* O token dever� ser persistido junto com o usu�rio.

## Login 
> [POST] /login

Este endpoint dever� ser utilizado para que o usu�rio, utilizando um e-mail e senha cadastrados, realize um login, ao fazer login o token dever� ser atualizado.

* Utilizar o exemplo abaixo para o body:

> ```json
>     {
>         "email": "joao@silva.org",
>         "password": "hunter2"
>     }
> ```

* Caso o e-mail e a senha correspondam a um usu�rio existente, retornar com o status apropriado e conforme o exemplo abaixo:
  
>```json
>    {   
>        "id": " 00c6de58-6582-11eb-ae93-0242ac130002",
>         "name": "Jo�o da Silva",
>         "email": "joao@silva.org",
>         "password": "hunter2",
>         "phones": [
>             {
>                 "number": "987654321",
>                 "ddd": "21"
>             }
>         ],
>         "created": "2020-10-03T19:30:00",
>         "modified": "2020-10-03T19:30:00",
>         "last_login": "2020-10-03T19:30:00",
>         "token": "eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA"
>     }
> ```

* Caso o e-mail n�o exista, retornar status apropriado e utilizar o formato de mensagem de erro com a mensagem "Usu�rio e/ou senha inv�lidos".

* Caso o e-mail exista mas a senha n�o bata, retornar o status 401 e utilizar o formato de mensagem de erro com a mensagem "Usu�rio e/ou senha inv�lidos".

## Perfil do Usu�rio

>[GET] desafio/api/v1/users/perfil/{id}

>header ---> key: Autorization value: Bearer token

Este endpoint dever� receber no header um token (jwt ou uuid), e um id de usu�rio no path, considerar os cen�rios a seguir:

* Caso o token n�o seja passado no header, dever� retornar erro com status apropriado e com a mensagem "N�o autorizado".

* Caso o token seja diferente do persistido, retornar erro com status apropriado e com a mensagem "N�o autorizado".
  
* Caso o token exista, e seja o mesmo persistido, buscar o usu�rio pelo `id` passado no path.

* Caso o usu�rio n�o seja encontrado pelo id, retornar com status e mensagem de erro apropriados.

* Verificar se o �ltimo login foi h� MENOS de 30 minutos atr�s. 
     * Caso n�o seja h� MENOS de 30 minutos atr�s, retornar erro com status apropriado e com a mensagem "Sess�o inv�lida".
  
* Caso tudo esteja ok, retornar conforme o exemplo abaixo:
  
>```json
>    {   
>        "id": " 00c6de58-6582-11eb-ae93-0242ac130002",
>        "name": "Jo�o da Silva",
>        "email": "joao@silva.org",
>        "password": "hunter2",
>        "phones": [
>            {
>                "number": "987654321",
>                "ddd": "21"
>            }
>        ],
>        "created": "2020-10-03T19:30:00",
>        "modified": "2020-10-03T19:30:00",
>        "last_login": "2020-10-03T19:30:00",
>        "token": "eyJhbGciOiJIUzUxMiJ9.eyJwcm9ncmFtQ29kZSI6ImRhMjhiNjk4MDM0M2I3ZjE3ODUwMDgyNzlmNzI0MGJiNWNmZDAyNjYiLCJ1c2VySWQiOiI1ZjkyZGI3Y2M3MDgxYjliOTZmNGNlNDkiLCJwZXJzb25JZCI6IjVmOTJkYjdjYzcwODFiOWI5NmY0Y2U0OSIsInVzZXJUeXBlIjoiQUNDT1VOVCIsInNlc3Npb25JZCI6Ijc1NWM0MTcyLWYyYjgtNDRiYS1hMzgzLTBlZGI2NzdlYTZiYyIsInJvbGVzIjoiIiwic3ViIjoiNjk0MjA2NjMwMzUiLCJhdWQiOiJ1bmtub3duIiwiaWF0IjoxNjA3NTM0MzU1LCJleHAiOjE2MDc1MzQ1MzV9.3GNRIE4ND_NSbe7cDYoVRUMMXj-_sZmwE_oX-u6Ju7xnUYipEjKz1A2m7mUfPa08BY3USe5zau220u0Zij3LEA"
>    }
>```

