# Microserviços Bancários

## Visão Geral

Este projeto implementa uma plataforma de processamento de transações em tempo real para um banco digital como parte da disciplina de Arquitetura de Software do PPGCC UNESP.

Demonstra uma arquitetura de microsserviços que utiliza Spring Boot, mensageria Kafka e PostgreSQL para atender a requisitos de alta disponibilidade, consistência de dados, baixa latência e escalabilidade elástica.

## Arquitetura

O sistema é baseado em mensageria assíncrona para desacoplar os serviços e garantir confiabilidade.

Componentes Principais:

- **Transaction Service**: Recebe requisições de transação, valida e processa via tópicos Kafka.
- **Account Service**: Gerencia cadastro de clientes e atualiza saldos das contas após conclusão da transação.
- **Notification Service**: Envia notificações por SMS/email sobre o status das transações.
- **Audit Service**: Persiste todos os eventos de mensagens e tópicos para auditoria.
- **bank-commons**: Biblioteca compartilhada com modelos e utilitários comuns.

## Diagrama UML

Um diagrama PlantUML mostrando as interações entre os serviços está disponível em:

![](/docs/services_architecture.png)

## Pré-requisitos

- Docker & Docker Compose
- Java 17+ (para builds locais)
- Maven (opcional, para compilar serviços individualmente)

## Inicialização do Banco de Dados

O banco de dados PostgreSQL usado por todos os serviços pode ser inicializado com o script SQL fornecido:

```powershell
psql -h localhost -U postgres -d banking_db -f scripts/postgres-init.sql
```

Alternativamente, o Docker Compose monta este script automaticamente no início do container.

## Executando a Aplicação

A partir da raiz do projeto:

```powershell
docker-compose up -d --build
```

Este comando iniciará:

- Zookeeper (2181)
- Kafka broker (9092)
- PostgreSQL (5432)
- Transaction Service (8080)
- Account Service (8081)
- Audit Service (8082)
- Notification Service (8083)

Para parar e remover todos os containers:

```powershell
docker-compose down
```

## Endpoints da API

Um arquivo HAR representando todos os endpoints dos serviços e exemplos de requisições está disponível em:

```
/docs/banking-microservices-api.har
```

Importe este arquivo no Postman, Insomnia ou qualquer cliente HTTP para explorar e testar as APIs.

## Descrição dos Serviços

### Serviço de Transação (8080)

- **POST /solicitar**: Envia uma nova transação (`PIX`, `TED`, etc.) com origem, destino e valor. Retorna o ID da transação.

- **Listener: TransacaoSolicitadaListener**: Valida contas de origem/destino e saldo usando Chain of Responsibility, processa via fábrica TransactionHandler e publica um `TransacaoConcluidaModel` no tópico Kafka `TransacaoConcluida`.

### Serviço de Conta (8081)

- **POST /registro/cliente**: Registra um novo cliente com CPF, email e telefone únicos. Cria uma conta com saldo zero e retorna o ID da conta.

- **Listener: TransacaoConcluidaListener**: Inscreve-se no tópico `TransacaoConcluida`. Em status `CONFIRMADA`, debita a conta de origem e credita a conta de destino; caso contrário, apenas registra o evento no log.

### Serviço de Notificação (8083)

- **Listener: TransacaoConcluidaListener**: Busca detalhes da transação e das contas e envia notificações por SMS ou Email conforme preferências do cliente. Utiliza padrão Factory para selecionar implementações de `Notificator`.

### Serviço de Auditoria (8082)

- **AuditListener**: Listener genérico que se inscreve em todos os tópicos Kafka e persiste cada mensagem, junto ao nome do tópico, no banco de auditoria.

### bank-commons

- Módulo Maven compartilhado que fornece modelos de domínio comuns (`TransacaoSolicitadaModel`, `TransacaoConcluidaModel`, etc.) e utilitários usados pelos serviços.

## Licença

Este projeto é para fins acadêmicos.
