#  LocaFácil

Sistema de gerenciamento de locação de veículos desenvolvido como projeto da disciplina de **Programação Orientada a Objetos** da **Universidade Federal do Agreste de Pernambuco (UFAPE)**.

##  Informações Acadêmicas

* **Universidade:** Universidade Federal do Agreste de Pernambuco (UFAPE)
* **Curso:** Ciência da Computação
* **Disciplina:** Programação Orientada a Objetos
* **Professor:** Igor Medeiros Vanderlei
* **Período:** II
* **Turno:** Noite

##  Equipe

* João Gabriel
* João Vitor
* José Erasmo Barros
* Lucas Fraga

---

#  Sobre o Projeto

O **LocaFácil** é uma aplicação cliente-servidor desenvolvida para gerenciar o processo de locação de veículos, permitindo o cadastro de clientes, funcionários, veículos e categorias, além do controle de locações, pagamentos e disponibilidade dos automóveis.

O projeto foi desenvolvido seguindo os princípios da Programação Orientada a Objetos e a arquitetura proposta pela disciplina.

---

#  Arquitetura

## Back-end

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL (ou MySQL)
* API REST
* JSON

Arquitetura em camadas:

```
Controller (REST)

↓

Fachada

↓

Service

↓

Repository (JPA)

↓

Model
```

## Front-end

Tecnologia escolhida pela equipe:

* React.js *(ou Vue.js / Flutter / React Native, conforme a implementação)*

---

#  Modelo de Domínio

O sistema é composto pelas seguintes entidades:

* Pessoa (abstrata)
* Cliente
* Funcionário
* Veículo
* Categoria
* Locação
* ItemLocação
* Pagamento
* StatusVeiculo (Enum)

O modelo utiliza:

* Herança
* Encapsulamento
* Associação entre classes
* Enumerações
* Polimorfismo

---

#  Funcionalidades

* Cadastro de clientes
* Cadastro de funcionários
* Cadastro de veículos
* Cadastro de categorias
* Registro de locações
* Controle de disponibilidade
* Registro de pagamentos
* Cálculo de diárias
* Cálculo de multas
* Finalização de locações
* Verificação de inadimplência

---

#  Tecnologias

### Back-end

* Java 21
* Spring Boot
* Spring Data JPA
* Maven
* PostgreSQL

### Front-end

* React.js

### Ferramentas

* Git
* GitHub
* Postman
* IntelliJ IDEA
* VS Code

---

#  Testes

O projeto contempla:

* Testes Unitários
* Testes de Integração
* Testes de API
* Testes de Interface

---

#  Organização do Projeto

```
src
├── controller
├── fachada
├── service
├── repository
├── model
├── dto
├── exception
├── config
└── tests
```

---

#  Requisitos Atendidos

* ✔ Herança
* ✔ Polimorfismo
* ✔ Encapsulamento
* ✔ Associação entre classes
* ✔ Interfaces entre camadas
* ✔ Arquitetura Cliente-Servidor
* ✔ Comunicação JSON
* ✔ Git e GitHub
* ✔ Documentação
* ✔ Organização em pacotes

---

#  Diagrama de Classes

O modelo de domínio foi elaborado utilizando UML, contemplando as principais entidades do sistema e seus relacionamentos.

---

# 🚀 Possíveis Melhorias

* Autenticação JWT
* Upload de documentos
* Geração de relatórios em PDF
* Internacionalização
* Integração com mapas
