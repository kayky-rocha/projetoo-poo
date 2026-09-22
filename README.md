# Sistema de Gerenciamento de Academia

Trabalho da disciplina de Programação Orientada a Objetos — UFVJM (DECOM/FACET).

## Integrantes
- Kayky Francisco Rodrigues da Rocha
- Aaron Araujo Agapito Guedes

**Professor:** Eduardo Pelli

## Sobre o projeto
Sistema para gerenciamento de uma academia fictícia em Milho Verde-MG, com 4 salas de atividades, lanchonete e loja de materiais. Contempla:
- Cadastro de clientes
- Agendamentos (preliminar/confirmado/cancelado)
- Controle de diárias e mensalidades
- Gestão de funcionários e administradores
- Lançamento de despesas e geração de balanço mensal

## Tecnologias
- Java 17
- Maven

## Como rodar

**Via terminal:**

mvn compile
mvn exec:java -Dexec.mainClass="poo.projeto.academia.Main"

**Via NetBeans:**
Abrir o projeto (File → Open Project) e rodar `Main.java` diretamente.

## Estrutura

src/main/java/poo/projeto/academia/
├── model/ # Classes de domínio (Pessoa, Cliente, Funcionario, Administrador...)


## Status
Em desenvolvimento — fase de modelagem das classes de domínio.

## Documentação
Casos de uso, diagramas de sequência, classes e estados disponíveis na pasta `docs/` (ou onde vocês guardarem os PDFs/LaTeX).