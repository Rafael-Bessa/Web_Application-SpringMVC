# 🏦 **SISTEMA FINANCEIRO - ANÁLISE DE TRANSAÇÕES SUSPEITAS**

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.5-brightgreen?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-5.7-green?style=for-the-badge&logo=spring-security&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.0-blue?style=for-the-badge&logo=thymeleaf&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Sistema web completo para análise e detecção de transações financeiras suspeitas**

[🚀 Demonstração](#-demonstração) • [📋 Funcionalidades](#-funcionalidades) • [⚙️ Instalação](#%EF%B8%8F-instalação) • [🤝 Contato](#-contato)

![mvc](https://user-images.githubusercontent.com/104053775/204969363-fd6d0a47-2a55-425f-9ea4-f21b55ae496b.jpg)

</div>

---

## 📖 **Sobre o Projeto**

Sistema web desenvolvido com **Spring MVC** para análise automatizada de milhares de transações financeiras, identificando operações e contas bancárias suspeitas através de algoritmos de validação e persistência de dados.

A aplicação implementa **autenticação segura**, **upload de arquivos CSV**, **validação de dados**, **análise preditiva** e **geração de relatórios**, seguindo as melhores práticas de desenvolvimento com **SOLID**, **Design Patterns** e **Clean Code**.

### 🎯 **Problema Resolvido**

Instituições financeiras precisam analisar milhares de transações diárias para identificar operações suspeitas de lavagem de dinheiro ou fraudes. Este sistema automatiza:

- ✅ Importação massiva de transações via CSV
- ✅ Validação automática de dados segundo regras de negócio
- ✅ Detecção de transações individuais acima de R$ 100.000,00
- ✅ Identificação de contas com movimentação mensal superior a R$ 1.000.000,00
- ✅ Auditoria completa com registro de usuário e timestamp
- ✅ Interface intuitiva para análise de dados

---

## 🚀 **Demonstração**

### 1️⃣ **Login & Autenticação**
<img width="1736" height="854" alt="image" src="https://github.com/user-attachments/assets/a61de24f-2ace-4180-a794-44d0303a7d75" />

### 2️⃣ **Dashboard & Importação**
<img width="1824" height="863" alt="image" src="https://github.com/user-attachments/assets/c588a7f9-b7dc-4b0f-87b4-d3c4045d8a05" />

### 3️⃣ **Detalhe de uma Importação**
<img width="1793" height="853" alt="image" src="https://github.com/user-attachments/assets/9913131d-b896-49ba-b015-1a816e800750" />

### 4️⃣ **Análise de Transações Suspeitas**
<img width="1798" height="856" alt="image" src="https://github.com/user-attachments/assets/c84e1780-ed8f-4b5d-99dd-c7ba71db958a" />

---
## 📋 **Funcionalidades**

### 🔐 **Autenticação & Segurança**
- ✅ Sistema de login com Spring Security
- ✅ Proteção de rotas (apenas `/login`, `/cadastro` e `/h2` são públicos)
- ✅ Senha criptografada com BCrypt
- ✅ Sessão persistente até logout explícito
- ✅ Cadastro de novos usuários com validação de email único

### 📁 **Importação de Transações**
- ✅ Upload de arquivos CSV com validação rigorosa
- ✅ Detecção automática da data de referência
- ✅ Validação de campos obrigatórios
- ✅ Prevenção de importações duplicadas
- ✅ Filtro automático de transações de outras datas
- ✅ Registro de usuário e timestamp da importação

### 📊 **Análise & Relatórios**
- ✅ Detecção de transações ≥ R$ 100.000,00
- ✅ Identificação de contas com movimentação mensal > R$ 1.000.000,00
- ✅ Filtro por período (mês/ano)
- ✅ Visualização detalhada de transações importadas
- ✅ Listagem ordenada por data (decrescente)

### 🎨 **Interface & UX**
- ✅ Design responsivo com Bootstrap 5
- ✅ Templates modernos com Thymeleaf
- ✅ Modais interativos para feedback
- ✅ Páginas de erro customizadas
- ✅ Badges e ícones informativos

---

## 🛠️ **Tecnologias Utilizadas**

| Categoria | Tecnologias |
|-----------|-------------|
| **Backend** | Java 17 • Spring Boot 2.7.5 • Spring Security 5.7 • Spring Data JPA • Bean Validation • Lombok |
| **Frontend** | Thymeleaf • Bootstrap 5 • HTML5 • CSS3 • JavaScript |
| **Banco de Dados** | H2 Database (in-memory) • SQL |
| **Testes** | Selenium • JUnit |
| **Ferramentas** | IntelliJ IDEA • Maven • Git |

---

## ⚙️ **Instalação**

### **Pré-requisitos**
- Java 17+
- Maven 3.6+
- IDE (IntelliJ IDEA recomendado)

### **Passo a Passo**

**1. Clone o repositório**

```bash
git clone https://github.com/Rafael-Bessa/financial-fraud-detector.git
cd financial-fraud-detector
```

**2. Configure o banco de dados**

O projeto usa H2 in-memory. As configurações estão em `application.properties`:

```bash
spring.datasource.url=jdbc:h2:mem:financeiro
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

**3. Execute a aplicação**

```bash
mvn spring-boot:run
```

**4. Acesse no navegador**

```bash
http://localhost:8080
```

### **Usuários Padrão** *(já cadastrados via data.sql)*

| Email | Senha | Role |
|-------|-------|------|
| rafael@teste.com | abc | USER |
| bessa@email.com | abc | USER |

---

## 📝 **Regras de Negócio**

### **Importação de CSV**

#### ✅ **Formato Esperado**

BANCO_ORIGEM, AGENCIA_ORIGEM, CONTA_ORIGEM, BANCO_DESTINO, AGENCIA_DESTINO, CONTA_DESTINO, VALOR, DATA_HORA

#### 📋 **Exemplo**
BANCO SANTANDER,0001,00002-1,BANCO BRADESCO,0001,00001-1,79800.22,2022-01-01T08:44:00

#### ⚠️ **Validações Aplicadas**
1. Arquivo não pode estar vazio
2. Primeira linha define a data de referência do lote
3. Transações de outras datas são ignoradas
4. Todos os campos são obrigatórios
5. Não permite importações duplicadas (mesma data)
6. Registra usuário e timestamp da operação

### **Análise de Suspeitas**

| Tipo | Critério |
|------|----------|
| 🚨 **Transação Suspeita** | Valor ≥ **R$ 100.000,00** |
| 🏦 **Conta Suspeita** | Movimentação mensal (entrada OU saída) > **R$ 1.000.000,00** |

---

## 🎓 **Conceitos Aplicados**

- ✅ **MVC Pattern** (Model-View-Controller)
- ✅ **DTO Pattern** (Data Transfer Object)
- ✅ **Repository Pattern** (Spring Data JPA)
- ✅ **Service Layer** (Lógica de Negócio)
- ✅ **Dependency Injection** (IoC Container)
- ✅ **Bean Validation** (Validação de Dados)
- ✅ **EntityGraph** (Performance JPA)
- ✅ **Exception Handling** (Páginas customizadas)
- ✅ **Security** (Spring Security)
- ✅ **SOLID Principles**

---

**Cobertura:**
- ✅ Login de usuários válidos
- ✅ Rejeição de credenciais inválidas
- ✅ Proteção de rotas autenticadas

---

## 🚀 **Melhorias Futuras**

- [ ] Dashboard com gráficos (Chart.js)
- [ ] Exportação de relatórios em PDF/Excel
- [ ] API REST para integração externa
- [ ] Notificações por email de transações suspeitas
- [ ] Auditoria completa (log de ações)
- [ ] Filtros avançados de busca
- [ ] Paginação de resultados
- [ ] Migração para PostgreSQL em produção
- [ ] Docker + Docker Compose
- [ ] CI/CD (GitHub Actions)

---

## 🤝 **Contato**

<div align="center">

### **Rafael Morangon Bessa**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/rafaelmbessa/)
[![YouTube](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/@rafaelmbessa)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/rafaelmbessa)

**📧 Entre em contato para projetos, dúvidas ou colaborações!**

</div>

---

## 📄 **Licença**

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## ⭐ **Agradecimentos**

Se este projeto foi útil para você, considere dar uma ⭐ no repositório!

---

<div align="center">

**Desenvolvido com 💜 por [Rafael Bessa](https://www.linkedin.com/in/rafaelmbessa/)**

![Spring](https://img.shields.io/badge/Made%20with-Spring-6DB33F?style=flat&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Powered%20by-Java-007396?style=flat&logo=java&logoColor=white)

</div>
