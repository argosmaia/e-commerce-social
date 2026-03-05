# PostgreSQL Cheatsheet – Projeto EcommerceSocial

## 📌 Contexto

- **Owner / Superuser:** argosdgti
- **Banco:** ecomercesocial
- **Usuário restrito:** marcosvvitor
- **Permissões:** SELECT, INSERT, UPDATE
- **Bloqueado:** DELETE, DROP, DDL estrutural

---

## 🔹 1. Criar Banco

```sql
CREATE DATABASE ecomercesocial OWNER argosdgti;
```

---

## 🔹 2. Conectar no Banco

```sql
\c ecomercesocial
```

---

## 🔹 3. Criar Usuário Restrito

```sql
CREATE ROLE marcosvvitor
WITH
    LOGIN
    PASSWORD 'algumasenha'
    NOSUPERUSER
    NOCREATEDB
    NOCREATEROLE
    NOINHERIT;
```

---

## 🔹 4. Permissões Básicas

### Permitir conexão ao banco

```sql
GRANT CONNECT ON DATABASE ecomercesocial TO marcosvvitor;
```

### Permitir uso do schema

```sql
GRANT USAGE ON SCHEMA public TO marcosvvitor;
```

### Permitir SELECT, INSERT e UPDATE

```sql
GRANT SELECT, INSERT, UPDATE
ON ALL TABLES IN SCHEMA public
TO marcosvvitor;
```

### Remover DELETE explicitamente

```sql
REVOKE DELETE
ON ALL TABLES IN SCHEMA public
FROM marcosvvitor;
```

### Aplicar regra para tabelas futuras

```sql
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE ON TABLES TO marcosvvitor;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
REVOKE DELETE ON TABLES FROM marcosvvitor;
```

---

## 🔹 5. Listar Usuários

```sql
\du
```

---

## 🔹 6. Alterar Senha

```sql
ALTER USER marcosvvitor WITH PASSWORD 'NovaSenha';
```

Modo interativo:

```sql
\password marcosvvitor
```

---

## 🔹 7. Como o Owner Pode Reverter Permissões

### ✅ Conceder DELETE

```sql
GRANT DELETE
ON ALL TABLES IN SCHEMA public
TO marcosvvitor;
```

Permitir DELETE para tabelas futuras:

```sql
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT DELETE ON TABLES TO marcosvvitor;
```

---

### ✅ Permitir DROP

DROP exige ser owner da tabela ou superuser.

#### Opção 1 – Tornar owner de tabela específica

```sql
ALTER TABLE nome_da_tabela OWNER TO marcosvvitor;
```

⚠️ Concede controle total da tabela.

#### Opção 2 – Tornar SUPERUSER (não recomendado)

```sql
ALTER ROLE marcosvvitor WITH SUPERUSER;
```

Reverter:

```sql
ALTER ROLE marcosvvitor WITH NOSUPERUSER;
```

---

## 🔹 8. Remover Todas as Permissões

```sql
REVOKE ALL PRIVILEGES
ON ALL TABLES IN SCHEMA public
FROM marcosvvitor;
```

---

## 🔹 9. Remover Usuário

```sql
DROP ROLE marcosvvitor;
```

⚠️ Só funciona se ele não for owner de objetos.

---

## 🔹 10. Testar Permissões

Entrar como usuário restrito:

```bash
psql -U marcosvvitor -h localhost -d ecomercesocial
```

Testes:

```sql
DELETE FROM tabela;             -- Deve falhar
DROP TABLE tabela;              -- Deve falhar
UPDATE tabela SET campo = 'x';  -- Deve funcionar
```
