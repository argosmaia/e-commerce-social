# 📦 Módulo `Usuario` — Documentação Técnica

> **Projeto:** `ammm_tech_labs / e-commerce-mvp`  
> **Camada:** Backend — Spring Boot 3 + Java 21  
> **Responsabilidade:** Cadastro, autenticação básica, leitura, atualização e exclusão de usuários.

---

## 📋 Sumário

1. [Visão Geral da Arquitetura](#1-visão-geral-da-arquitetura)
2. [Fluxo Request → Response](#2-fluxo-request--response)
3. [Camadas do Módulo](#3-camadas-do-módulo)
   - 3.1 [Model — `Usuario`](#31-model--usuario)
   - 3.2 [DTOs](#32-dtos)
   - 3.3 [Repository — `UsuarioRepository`](#33-repository--usuariorepository)
   - 3.4 [Mapper — `UsuarioMapper`](#34-mapper--usuariomapper)
   - 3.5 [Service — `UsuarioService`](#35-service--usuarioservice)
   - 3.6 [Controller — `UsuarioController`](#36-controller--usuariocontroller)
   - 3.7 [Factory — `UsuarioFactory`](#37-factory--usuariofactory)
4. [Diagrama UML — Classes](#4-diagrama-uml--classes)
5. [Diagrama UML — Sequência (Cadastro)](#5-diagrama-uml--sequência-cadastro)
6. [Endpoints da API](#6-endpoints-da-api)
7. [Decisões de Design](#7-decisões-de-design)
8. [Melhorias Futuras](#8-melhorias-futuras)

---

## 1. Visão Geral da Arquitetura

O módulo segue a **arquitetura em camadas** clássica do Spring Boot, com separação clara de responsabilidades:

```
┌─────────────────────────────────────────────────────────┐
│                    HTTP Request                         │
└────────────────────────┬────────────────────────────────┘
                         │
              ┌──────────▼──────────┐
              │   UsuarioController │  ◄── recebe e valida requisições
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │   UsuarioService    │  ◄── regras de negócio
              └───┬─────────────────┘
                  │            │
     ┌────────────▼──┐   ┌─────▼──────────┐
     │UsuarioMapper  │   │UsuarioRepository│  ◄── acesso ao banco
     │  (MapStruct)  │   │  (Spring Data)  │
     └───────────────┘   └────────┬────────┘
                                  │
                         ┌────────▼────────┐
                         │  Banco de Dados  │
                         │  (PostgreSQL /   │
                         │   H2 em testes)  │
                         └─────────────────┘
```

---

## 2. Fluxo Request → Response

Abaixo o fluxo completo de uma requisição de **criação de usuário** (`POST /usuarios`):

```
Cliente
  │
  │  POST /usuarios  {CriarUsuarioDTO}
  ▼
UsuarioController.cadastrarUsuario()
  │  @Valid valida os campos do DTO
  │
  ▼
UsuarioService.cadastrarUsuario()
  │  1. Verifica se email já existe → retorna 409 se sim
  │  2. Faz hash da senha com PasswordEncoder
  │  3. Instancia new Usuario(...)
  │  4. Salva via UsuarioRepository.save()
  │  5. Mapeia para UsuarioDTO via UsuarioMapper
  │
  ▼
APIResponse<UsuarioDTO>
  │
  ▼
ResponseEntity (201 Created + Location header)
  │
  ▼
Cliente recebe resposta
```

---

## 3. Camadas do Módulo

### 3.1 Model — `Usuario`

**Pacote:** `models`  
**Arquivo:** `Usuario.java`

A entidade JPA que representa a tabela `usuarios` no banco de dados.

```java
@Entity(name="Usuario")
@Table(name="usuarios")
public class Usuario { ... }
```

#### Campos

| Campo            | Tipo            | Restrição               | Descrição                              |
|------------------|-----------------|-------------------------|----------------------------------------|
| `id`             | `UUID`          | PK, gerado automaticamente | Identificador único do usuário      |
| `primeiroNome`   | `String`        | —                       | Primeiro nome                          |
| `ultimoNome`     | `String`        | —                       | Sobrenome                              |
| `username`       | `String`        | —                       | Gerado automaticamente pelo sistema    |
| `senha`          | `String`        | —                       | Hash BCrypt da senha                   |
| `telefone`       | `String`        | —                       | Telefone com DDD e código do país      |
| `email`          | `String`        | `UNIQUE`, `NOT NULL`    | Email único para login/contato         |
| `dataNascimento` | `LocalDateTime` | —                       | Usado futuramente para calcular idade  |
| `idade`          | `int`           | —                       | Idade informada pelo usuário (manual)  |
| `cpf`            | `String`        | `UNIQUE`, `NOT NULL`    | CPF do usuário (11 dígitos)            |

#### Construtores

Há **três construtores** distintos por propósito:

```java
// 1. Gerado pelo Lombok (@AllArgsConstructor) — JPA e testes internos
Usuario(UUID id, String primeiroNome, ...)

// 2. Construtor de negócio — cria um usuário com geração automática de username
Usuario(String primeiroNome, String ultimoNome, ...) {
    this.username = gerarUsername(primeiroNome, ultimoNome);
}

// 3. Construtor via DTO — usado quando não há lógica extra de username
Usuario(CriarUsuarioDTO dados) { ... }
```

> ⚠️ **Atenção:** o construtor 2 chama `gerarUsername()` internamente, sobrepondo o `username` passado. O construtor 3 usa o `username` diretamente do DTO, sem geração automática. Há uma inconsistência entre os dois que pode ser resolvida — veja [Melhorias Futuras](#8-melhorias-futuras).

#### Método `gerarUsername`

```java
private String gerarUsername(String nome, String sobrenome) {
    return (sobrenome + nome).toLowerCase().replaceAll("\s+", "");
}
```

Concatena sobrenome + nome, tudo em minúsculas e sem espaços. Exemplo: `João Silva` → `silvajoão`.

#### Método `atualizar`

```java
public void atualizar(String primeiroNome, String ultimoNome, ...) {
    this.primeiroNome = primeiroNome != null ? primeiroNome : this.primeiroNome;
    // ...
    this.username = gerarUsername(this.primeiroNome, this.ultimoNome);
}
```

Aplica atualizações parciais: só modifica campos não-nulos. O `username` é **sempre recalculado** com base no nome atual, ignorando o valor passado diretamente.

---

### 3.2 DTOs

**Pacote:** `dto`  
Os DTOs (**Data Transfer Objects**) protegem a entidade de domínio da API externa, controlando exatamente o que entra e sai em cada operação.

#### `CriarUsuarioDTO` — Entrada de criação

```java
public record CriarUsuarioDTO(
    @NotBlank String primeiroNome,
    @NotBlank String ultimoNome,
    @NotBlank String username,
    @NotBlank @Size(min = 8, max = 16) String senha,
    @NotBlank @Size(min = 12, max = 13) String telefone,
    @NotNull int idade,
    @NotBlank @Email String email,
    @NotNull @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataNascimento,
    @NotBlank @Pattern(regexp = "\\d{11}") String cpf
) {}
```

| Anotação | Por que é necessária |
|----------|---------------------|
| `@NotBlank` | Impede strings vazias ou só com espaços |
| `@Size(min=8, max=16)` | Garante senha com comprimento seguro |
| `@Email` | Valida formato de e-mail |
| `@Pattern(regexp="\\d{11}")` | Aceita apenas CPF com exatamente 11 dígitos numéricos |
| `@DateTimeFormat` | Instrui o Spring a parsear datas no formato brasileiro |

#### `AtualizarUsuarioDTO` — Entrada de atualização

```java
public record AtualizarUsuarioDTO(
    @NotNull UUID id,
    @NotBlank String primeiroNome,
    // ...
) {}
```

Carrega o `id` no corpo para que o service possa identificar o recurso. O `id` também vem na URL (`@PathVariable`), garantindo consistência dupla.

#### `ListarUsuarioDTO` — Saída paginada

```java
public record ListarUsuarioDTO(UUID id, String primeiroNome, String ultimoNome, String username, int idade) {}
```

Expõe apenas dados não-sensíveis na listagem. **Senha, CPF e telefone são omitidos intencionalmente.**

#### `VerUsuarioDTO` — Saída de detalhe público

```java
public record VerUsuarioDTO(UUID id, String username, String email) {}
```

Retorna o mínimo de informações necessárias para identificar um usuário publicamente.

#### `UsuarioDTO` — Saída completa (uso interno/admin)

```java
public record UsuarioDTO(UUID id, String primeiroNome, ..., String cpf) {}
```

DTO completo, retornado em operações como criação e atualização onde o chamador precisa ver o estado final do recurso. Inclui campos sensíveis — **não deve ser exposto em endpoints públicos.**

---

### 3.3 Repository — `UsuarioRepository`

**Pacote:** `repository`

```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Page<ListarUsuarioDTO> findAll(Pageable paginacao);
}
```

Estende `JpaRepository`, ganhando automaticamente todos os métodos CRUD (`save`, `findById`, `deleteById`, etc.).

#### Métodos customizados

| Método | Por que existe |
|--------|---------------|
| `existsByEmail(String email)` | Verificação rápida de duplicidade antes de salvar. Mais eficiente que `findByEmail()` pois não traz o objeto inteiro. |
| `findByUsername(String username)` | Necessário para autenticação via Spring Security (futuro). |
| `findAll(Pageable)` | Listagem paginada com projeção direta para DTO, evitando trazer campos desnecessários. |

> ⚠️ **Nota:** O import `org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable` no repositório está incorreto — deve ser `org.springframework.data.domain.Pageable`. Isso causará erro de compilação.

---

### 3.4 Mapper — `UsuarioMapper`

**Pacote:** `maps`

```java
@Mapper(componentModel = "spring")
public interface UsuarioMapper { ... }
```

Usa **MapStruct** para gerar automaticamente em tempo de compilação o código de conversão entre entidade e DTOs. Elimina código boilerplate de mapeamento manual.

#### Mapeamentos declarados

| Método | Direção | Observação |
|--------|---------|-----------|
| `toDTO(Usuario)` | entidade → UsuarioDTO | Mapeamento completo |
| `toListarDTO(Usuario)` | entidade → ListarUsuarioDTO | Apenas campos públicos |
| `toVerDTO(Usuario)` | entidade → VerUsuarioDTO | Campos mínimos |
| `toEntity(CriarUsuarioDTO)` | DTO → entidade | `id`, `username` e `senha` ignorados (gerados pelo sistema) |
| `atualizar(Usuario, AtualizarUsuarioDTO)` | DTO → entidade existente | `cpf`, `idade` e `senha` imutáveis via update |

A estratégia `NullValuePropertyMappingStrategy.IGNORE` no método `atualizar` garante que campos nulos no DTO **não sobrescrevem** os valores existentes na entidade — essencial para atualizações parciais (PATCH-like sobre PUT).

---

### 3.5 Service — `UsuarioService`

**Pacote:** `service`

A camada de **regras de negócio**. Não conhece HTTP — apenas recebe e retorna objetos de domínio.

```java
@Service
@RequiredArgsConstructor
public class UsuarioService { ... }
```

#### Dependências injetadas

| Dependência | Por que é necessária |
|-------------|---------------------|
| `UsuarioRepository` | Acesso ao banco de dados |
| `UsuarioMapper` | Conversão entre entidade e DTOs |
| `PasswordEncoder` | Hash seguro de senhas (BCrypt) |

#### Método `cadastrarUsuario`

```java
@Transactional
public APIResponse<UsuarioDTO> cadastrarUsuario(CriarUsuarioDTO dados) {
    if (usuarios.existsByEmail(dados.email())) {
        return APIResponse.conflito("Email já cadastrado"); // 409
    }
    var senhaHash = passwordEncoder.encode(dados.senha());
    var usuario = new Usuario(dados.primeiroNome(), ..., senhaHash, ...);
    usuarios.save(usuario);
    return APIResponse.criado("Usuário criado com sucesso", mapper.toDTO(usuario));
}
```

**Fluxo:** valida unicidade → hash da senha → persiste → retorna DTO.

> A senha **nunca é salva em texto puro**. O `passwordEncoder.encode()` aplica BCrypt antes de qualquer persistência.

#### Método `listarUsuarios`

```java
public APIResponse<Page<ListarUsuarioDTO>> listarUsuarios(Pageable paginacao) {
    var paginas = usuarios.findAll(paginacao).map(mapper::toListarDTO);
    return APIResponse.sucesso("Lista de usuários", paginas);
}
```

Retorna resultado paginado. O `.map()` converte cada `Usuario` para `ListarUsuarioDTO` via mapper.

#### Método `atualizarUsuario`

```java
@Transactional
public APIResponse<UsuarioDTO> atualizarUsuario(UUID id, AtualizarUsuarioDTO dados) {
    var usuario = usuarios.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    usuario.atualizar(...);
    return APIResponse.sucesso("Usuário atualizado", mapper.toDTO(usuario));
}
```

Busca a entidade existente e chama o método `atualizar()` nela. Por estar em um contexto `@Transactional`, o Spring detecta as mudanças e gera o `UPDATE` automaticamente — **não é necessário chamar `save()` explicitamente**.

#### Método `deletarUsuario`

```java
@Transactional
public APIResponse<?> deletarUsuario(UUID id) {
    if (!usuarios.existsById(id)) throw new EntityNotFoundException("...");
    usuarios.deleteById(id);
    return APIResponse.sucesso("Usuário deletado com sucesso");
}
```

Verifica existência antes de deletar para retornar `404` com mensagem clara, em vez do comportamento padrão silencioso do JPA.

---

### 3.6 Controller — `UsuarioController`

**Pacote:** `controller`

A porta de entrada HTTP. Responsável apenas por **receber, delegar e responder** — sem lógica de negócio.

```java
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController { ... }
```

#### Mapeamento de endpoints

| Método | Path | DTO de entrada | DTO de saída | Status |
|--------|------|---------------|-------------|--------|
| `POST` | `/usuarios` | `CriarUsuarioDTO` | `UsuarioDTO` | 201 Created |
| `GET` | `/usuarios` | — (Pageable) | `Page<ListarUsuarioDTO>` | 200 OK |
| `GET` | `/usuarios/{id}` | — | `VerUsuarioDTO` | 200 OK |
| `PUT` | `/usuarios/{id}` | `AtualizarUsuarioDTO` | `UsuarioDTO` | 200 OK |

O endpoint `POST` usa `UriComponentsBuilder` para construir o header `Location` da resposta `201 Created`, seguindo o padrão REST:

```
Location: http://localhost:8080/usuarios/{id-do-usuario-criado}
```

---

### 3.7 Factory — `UsuarioFactory`

**Pacote:** `factory`

Componente utilitário que gera instâncias de `CriarUsuarioDTO` com **dados realistas e aleatórios**, usando a biblioteca [JavaFaker](https://github.com/DiUS/java-faker) com locale `pt-BR`.

```java
@Component
public class UsuarioFactory {
    private final Faker faker = new Faker(Locale.of("pt", "BR"));

    public CriarUsuarioDTO criarUsuarioDTOAleatorio() { ... }
}
```

**Para que serve:**
- Testes de integração e carga
- Seed do banco em ambiente de desenvolvimento
- Demonstração da API sem precisar montar payloads manualmente

#### Gerador de CPF

```java
private String gerarCPFValido() {
    // Gera 9 dígitos aleatórios + 2 dígitos verificadores zerados
    return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d", n1..n9, 0, 0);
}
```

> ⚠️ O método não calcula os dígitos verificadores reais do CPF. CPFs gerados passarão na validação de formato (`\\d{11}`) mas **falharão** em validações de CPF verdadeiro. Adequado apenas para desenvolvimento.

---

## 4. Diagrama UML — Classes

```
┌─────────────────────────────────────┐
│              <<record>>             │
│           CriarUsuarioDTO           │
├─────────────────────────────────────┤
│ + primeiroNome: String              │
│ + ultimoNome: String                │
│ + username: String                  │
│ + senha: String                     │
│ + telefone: String                  │
│ + idade: int                        │
│ + email: String                     │
│ + dataNascimento: LocalDateTime     │
│ + cpf: String                       │
└──────────────────┬──────────────────┘
                   │ usado para criar
                   ▼
┌─────────────────────────────────────┐
│           <<@Entity>>               │
│               Usuario               │
├─────────────────────────────────────┤
│ - id: UUID                          │
│ - primeiroNome: String              │
│ - ultimoNome: String                │
│ - username: String                  │
│ - senha: String                     │
│ - telefone: String                  │
│ - email: String [UNIQUE]            │
│ - dataNascimento: LocalDateTime     │
│ - idade: int                        │
│ - cpf: String [UNIQUE]              │
├─────────────────────────────────────┤
│ + gerarUsername(): String           │
│ + atualizar(...): void              │
└──────────────────┬──────────────────┘
                   │ persistido por
                   ▼
┌─────────────────────────────────────┐
│        <<interface>>                │
│       UsuarioRepository             │
│    extends JpaRepository            │
├─────────────────────────────────────┤
│ + existsByEmail(String): boolean    │
│ + findByUsername(String): Optional  │
│ + findAll(Pageable): Page<ListarDTO>│
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│         <<record>> outputs          │
├──────────────┬──────────────────────┤
│UsuarioDTO    │ Completo (admin)      │
│ListarDTO     │ Listagem paginada     │
│VerUsuarioDTO │ Detalhe público       │
└──────────────┴──────────────────────┘
```

---

## 5. Diagrama UML — Sequência (Cadastro)

```
Cliente          Controller          Service          Repository         DB
  │                  │                  │                  │              │
  │  POST /usuarios  │                  │                  │              │
  │─────────────────►│                  │                  │              │
  │                  │  @Valid valida   │                  │              │
  │                  │  CriarUsuarioDTO │                  │              │
  │                  │─────────────────►│                  │              │
  │                  │                  │ existsByEmail()  │              │
  │                  │                  │─────────────────►│              │
  │                  │                  │                  │  SELECT      │
  │                  │                  │                  │─────────────►│
  │                  │                  │                  │◄─────────────│
  │                  │                  │◄─────────────────│              │
  │                  │                  │                  │              │
  │                  │                  │ encode(senha)    │              │
  │                  │                  │──► BCrypt Hash   │              │
  │                  │                  │                  │              │
  │                  │                  │ new Usuario(...) │              │
  │                  │                  │                  │              │
  │                  │                  │ save(usuario)    │              │
  │                  │                  │─────────────────►│              │
  │                  │                  │                  │  INSERT       │
  │                  │                  │                  │─────────────►│
  │                  │                  │                  │◄─────────────│
  │                  │                  │◄─────────────────│              │
  │                  │                  │                  │              │
  │                  │                  │ mapper.toDTO()   │              │
  │                  │                  │──► UsuarioDTO    │              │
  │                  │◄─────────────────│                  │              │
  │                  │ 201 Created      │                  │              │
  │◄─────────────────│ + Location header│                  │              │
  │                  │ + body(UsuarioDTO)│                 │              │
```

---

## 6. Endpoints da API

### `POST /usuarios` — Criar usuário

**Request:**
```json
{
  "primeiroNome": "João",
  "ultimoNome": "Silva",
  "username": "joaosilva",
  "senha": "Senha@123",
  "telefone": "+55 (11) 99999-9999",
  "idade": 25,
  "email": "joao@email.com",
  "dataNascimento": "01/01/2000",
  "cpf": "12345678901"
}
```

**Response `201 Created`:**
```json
{
  "mensagem": "Usuário criado com sucesso",
  "dados": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "primeiroNome": "João",
    "email": "joao@email.com",
    ...
  }
}
```

---

### `GET /usuarios` — Listar (paginado)

**Query params:** `?page=0&size=10&sort=primeiroNome,asc`

**Response `200 OK`:**
```json
{
  "mensagem": "Lista de usuários",
  "dados": {
    "content": [ { "id": "...", "username": "...", "idade": 25 } ],
    "totalElements": 100,
    "totalPages": 10
  }
}
```

---

### `GET /usuarios/{id}` — Detalhar usuário

**Response `200 OK`:**
```json
{
  "dados": { "id": "...", "username": "joaosilva", "email": "joao@email.com" }
}
```

---

### `PUT /usuarios/{id}` — Atualizar usuário

**Request:** campos opcionais (nulos são ignorados)
```json
{
  "id": "550e8400-...",
  "primeiroNome": "João Carlos",
  "email": "novoemail@email.com"
}
```

---

## 7. Decisões de Design

| Decisão | Justificativa |
|---------|--------------|
| **UUID como PK** | Evita enumeração de IDs sequenciais em endpoints públicos, mais seguro que `Long`. |
| **Records para DTOs** | Imutabilidade nativa, `equals/hashCode/toString` automáticos, sintaxe enxuta. |
| **MapStruct** | Mapeamento gerado em compile-time, sem reflection — performance superior a ModelMapper. |
| **`@Transactional` no Service** | Garante atomicidade das operações. Mudanças detectadas automaticamente pelo contexto JPA, sem `save()` manual. |
| **Hash no Service (não no Model)** | O `Model` não deve conhecer `PasswordEncoder` — isso é regra de negócio. |
| **DTOs diferentes por operação** | Controle explícito do que entra/sai em cada endpoint. Evita expor `senha` ou `cpf` onde não necessário. |
| **`APIResponse<T>` como wrapper** | Padroniza todas as respostas da API com mensagem + dados, facilitando consumo no frontend. |

---

## 8. Melhorias Futuras

- [ ] **Calcular `idade` automaticamente** a partir de `dataNascimento` — eliminar o campo manual que pode ficar desatualizado.
- [ ] **Validar CPF real** com cálculo dos dígitos verificadores.
- [ ] **Soft delete** — adicionar campo `ativo: boolean` e filtrar nas queries, em vez de deletar fisicamente.
- [ ] **Implementar `DELETE /usuarios/{id}`** — o método `deletarUsuario` no Service existe mas não há `@DeleteMapping` no Controller.
- [ ] **Consistência de `username`** — unificar os construtores de `Usuario` para que a geração automática seja sempre aplicada, removendo a inconsistência entre o construtor de negócio e o construtor via DTO.
- [ ] **Corrigir import** de `Pageable` no `UsuarioRepository` (trocar `spring.boot.data.autoconfigure` por `spring.data.domain`).
- [ ] **Spring Security** — proteger os endpoints com autenticação JWT, usando `findByUsername` que já existe no repositório.
- [ ] **Auditoria** — adicionar `createdAt` e `updatedAt` com `@CreatedDate` / `@LastModifiedDate`.