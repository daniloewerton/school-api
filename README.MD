<h1>SchoolClass API</h1>

<p>Projeto pessoal de uma escola, criada para estudos e boas práticas de programação.
Na API  é possível cadastrar alunos, professores, cursos, turmas, e disciplinas. onde cada operação 
é validada antes de ser efetuada, a fim de impedir a persistência de informações inconsistentes no banco de dados.</p>

<h1>Estrutura de diretórios e classes</h1>

* <p>Config - Classes de configurações de bibliotecas externas.</p>
* <p>Controller - Endpoints disponíveis para utilização das funcionalidades disponíveis.</p>
* <p>Enums - Arquivo de enumeração referente aos status de atributos das entidades do domínio.</p>
* <p>Exception Handler - Endpoints de tratamento de exceções personalizados.</p>
* <p>Model - Classes de domínio que representam os objetos da aplicação e as tabelas e atributos no banco de dados.</p>
* <p>Model/DTO - Classes que representam o corpo da resposta das requisições.</p>
* <p>Repositories - Classes que conectam a aplicação ao banco de dados.</p>
* <p>Services - Interfaces com assinaturas de métodos que representam as regras de negócio da aplicação.</p>
* <p>Services/Exception - Classes que representam as exceções personalizadas da aplicação.</p>
* <p>Services/Impl - Classes que representam as implementações das interfaces de serviços, e fornecem respostas para as requisições dos controladores.</p>

<h3>Pasta Resources</h3>

* <p>application.properties: Arquivo base de propriedades do projeto.</p>

* <p>application-dev.properties: Arquivo com propriedades do ambiente de desenvolvimento, que é executado localmente com o SGBD Postgresql.</p>

* <p>application-prod.properties: Arquivo com propriedades do ambiente produção que seta variáveis ambientes de conexão com o servidor de hospedagem da API.</p>

<h1>Endpoints disponíveis</h1>

<h3>Courses</h3>

<h4>Endereço raíz: /course</h4>

* <code>/course</code> - Método POST - Disponibiliza um novo curso usando os campos abaixo:

~~~
{
    "name": "Analise de sistemas",
    "workload": 2200,
    "courseValue": 2.785,
    "minimumGrade": 7.0,
    "maximumGrade": 10.0
}
~~~

* <code>/course</code> - Método GET - Retorna todos os cursos disponíveis.


* <code>/course/{id}</code> - Método PUT - Realiza alterações nos cursos já existentes. Deverá ser informado o número de ID na URL.

~~~
{
    "name": "System analysis and Development",
    "workload": 2200,
    "courseValue": 2.785,
    "minimumGrade": 6.0,
    "maximumGrade": 10.0
}
~~~

* <code>/course/{id}</code> - Método DELETE - Remove um curso através do ID.


* <code>/course</code> - Método PUT - Remove uma disciplina da grade de uma turma passando o ID da turma e da disciplina como parâmetro na URL.

Ex: <code>/course?idClass=1&idCourse=1</code>

<h1>Students</h1>

<h4>Endereço raíz: /student</h4>

* <code>/student</code> - Método POST - Matricula um estudante em um curso já existente informando os campos abaixo.

~~~
{
    "name": "Danilo Ewerton Pereira Freire",
    "cpf": "12345678983",
    "birthdate": "2010-04-09",
    "email": "danilo@email.com",
    "courseId": 2
}
~~~

* <code>/student</code> - Método PUT - Atualiza os dados de um estudante informando os campos já atualizados.

~~~
{
    "id": 5,
    "name": "Danilo Ewerton",
    "cpf": "12345678918",
    "birthdate": "2010-04-09",
    "email": "danilo@gemail.com",
    "enrollmentStatus": "LOCKED"
}
~~~

* <code>/student/{id}</code> - Método GET - Retorna todos os estudantes matriculados em um curso. Deverá ser informado o ID do curso na URL.


* <code>/student/{id}</code> - Método DELETE - Deleta um estudante informando seu ID na URL.


<h1>School Class</h1>

<h4>Endereço raíz: /class</h4>

* <code>/class</code> - Método POST - Abre uma nova turma informando os campos abaixo.

~~~
{
    "beginDate": "2022-07-10",
    "vacancy": 40,
    "courseId": 1,
    "teacherId": 1,
    "subjectId": 2
}
~~~


* <code>/class</code> - Método PUT - Matricula um aluno em uma turma já existente através dos parâmetros idSubject, idSchoolClass e idStudent.

<p>Ex: <code>/class/?idSubject=2&idStudent=2&idSchoolClass=1</code></p>


* <code>/class/update</code> - Método PUT - Atualiza informações de uma turma passando os atributos já atualizados.

~~~
{
    "id": 1,
    "beginDate": "2022-10-10",
    "vacancy": 3,
    "courseId": 1,
    "teacherId": 3,
    "subjectId": 2
}
~~~

* <code>/class/filter</code> - Método GET - Filtra turmas existentes através da data de início.

<p>Ex: <code>/class/?date=2020-08-07</code></p>

* <code>/class/{id}</code> - Método DELETE - Finaliza uma turma passando o ID na URL. 

<p>OBS: A turma não será removida, apenas desabilitada para matrícula. Ainda será possível encontra-la nos endpoints de pesquisa.</p>

<h1>Teacher</h1>

<h4>Endereço raíz: /teacher</h4>

* <code>/teacher</code> - Método POST - Realiza a matrícula de um novo professor informando os campos abaixo.

~~~
{
    "name": "Rogério Vieira",
    "cpf": "12365847902"
}
~~~

* <code>/teacher</code> - Método PUT - Realiza alteração nos dados de um professor informando os campos atualizados.

~~~
{
    "id": 6,
    "name": "Rogério Vieira de Alencar",
    "cpf": "92365847948"
}
~~~

* <code>/teacher</code> - Método GET - Retorna todos os professores matriculados.


* <code>/teacher</code> - Método DELETE - Remove um professor passando o ID como parâmetro.

Ex: <code>/teacher?id=6</code>

<h1>Subject</h1>

<h4>Endereço raíz: /subject</h4>

* <code>/subject</code> - Método POST - Insere uma nova disciplina informando o campo abaixo.

~~~
{
    "name": "Metodologia Científica"
}
~~~

* <code>/student</code> - Método PUT - Atualiza dados de um disciplina informando o ID com os campos atualizados.

~~~
{
    "id": 1,
    "name": "Introdução a Informática"
}
~~~

* <code>/subject/?id={id}</code> - Método GET - Lista todas as disciplinas de um determinado curso informando o ID do curso.

Ex: <code>/subject?id=2</code>


<h1>Tecnologias utilizadas</h1>

* Java 17;

* Spring Boot 2.7.1;

* Spring Data JPA 2.6.4;

* Postgresql 14;

* Model Mapper 3.1.0;

* Lombok 1.18.24;

* H2 2.1.214;

* JUnit 5;

* Mockito.

<h1>Autoria do projeto</h1>

Projeto desenvolvido por Danilo Ewerton - Linkedin (https://www.linkedin.com/in/daniloewerton/)