CREATE DATABASE banking_db;

\c banking_db;

CREATE TABLE Cliente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    notificacao VARCHAR[]
);

CREATE TABLE Conta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    numero VARCHAR(20) UNIQUE NOT NULL,
    agencia VARCHAR(10) NOT NULL,
    tipo VARCHAR NOT NULL,
    saldo DECIMAL(15,2) DEFAULT 0.00,
    cliente_id UUID REFERENCES Cliente(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Transacao (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   tipo VARCHAR NOT NULL,
   valor DECIMAL(15,2) NOT NULL,
   data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   status VARCHAR NOT NULL,
   origem UUID REFERENCES Conta(id),
   destino UUID REFERENCES Conta(id)
);

CREATE TABLE Pagamento (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   descricao VARCHAR(200),
   valor DECIMAL(15,2) NOT NULL,
   data_vencimento DATE NOT NULL,
   status VARCHAR DEFAULT 'PENDENTE',
   transacao_id UUID REFERENCES Transacao(id)
);

CREATE TABLE Notificacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    mensagem TEXT NOT NULL,
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo VARCHAR NOT NULL,
    transacao_id UUID REFERENCES Transacao(id),
    conta_id UUID REFERENCES Conta(id)
);

CREATE TABLE Auditoria (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   operacao VARCHAR(50) NOT NULL,
   data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   detalhes TEXT,
   transacao_id UUID REFERENCES Transacao(id)
);