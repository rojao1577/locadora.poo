# 🚗 LocaFácil

Sistema de gerenciamento de locação de veículos desenvolvido como projeto acadêmico. O objetivo é oferecer uma solução para cadastro de clientes, funcionários, veículos, categorias, locações e pagamentos, permitindo o controle completo do processo de aluguel de automóveis.

## 📋 Funcionalidades

* Cadastro de clientes
* Cadastro de funcionários
* Cadastro de veículos
* Gerenciamento de categorias de veículos
* Registro de locações
* Controle de disponibilidade dos veículos
* Registro de pagamentos
* Cálculo de diária
* Cálculo de multas por atraso
* Finalização de locações
* Verificação de inadimplência de clientes

## 🏗️ Estrutura do Projeto

O sistema foi modelado utilizando Programação Orientada a Objetos (POO), contendo as seguintes classes principais:

### Pessoa (Classe Abstrata)

Classe base responsável pelas informações comuns de clientes e funcionários.

**Atributos**

* id
* nome
* cpf
* telefone
* endereço

**Método**

* validarCpf()

---

### Cliente

Herda de **Pessoa**.

**Atributos**

* email
* scoreCredito

**Métodos**

* cadastrar()
* verificarInadimplencia()

---

### Funcionário

Herda de **Pessoa**.

**Atributos**

* cargo
* salário
* data de contratação

---

### Veículo

Representa os automóveis disponíveis para locação.

**Atributos**

* placa
* modelo
* marca
* ano de fabricação
* status

**Método**

* verificarDisponibilidade()

---

### Categoria

Define a categoria dos veículos e o valor base da diária.

**Métodos**

* calcularValorDiaria()

---

### Locação

Representa uma locação realizada por um cliente.

**Atributos**

* data da locação
* data prevista para devolução
* data real da devolução
* valor total

**Métodos**

* registrar()
* calcularMulta()
* finalizarLocacao()

---

### ItemLocacao

Armazena os veículos envolvidos na locação e o valor da diária aplicada.

---

### Pagamento

Representa o pagamento referente à locação.

**Atributos**

* valor
* data do pagamento
* forma de pagamento

---

### StatusVeiculo

Enumeração responsável pelo estado do veículo.

* DISPONIVEL
* ALUGADO
* MANUTENCAO

## 🔗 Relacionamentos

* Um cliente pode realizar diversas locações.
* Um funcionário pode atender diversas locações.
* Uma locação pode conter um ou mais itens.
* Cada veículo pertence a uma categoria.
* Cada pagamento está vinculado a uma locação.
* Cada veículo possui um status que indica sua disponibilidade.

## 💻 Tecnologias

* Java
* Programação Orientada a Objetos
* UML
* Git
* GitHub

## 👥 Equipe

* João Gabriel
* João Vitor
* José Erasmo Barros
* Lucas Fraga

## 📄 Diagrama de Classes

O projeto foi modelado utilizando UML para representar as entidades do sistema, seus atributos, métodos e relacionamentos, servindo como base para a implementação da aplicação.
