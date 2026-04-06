Endpoints

# PRODUTOS (/produtos)

GET    /produtos
GET    /produtos/{id}
POST   /produtos
PUT    /produtos/{id}
DELETE /produtos/{id}


# USUÁRIOS (/usuarios)

GET    /usuarios
GET    /usuarios/{id}
POST   /usuarios
PUT    /usuarios/{id}



=== PADRÃO RECOMENDADO DE ENDPOINTS ===

1. UTILIZAR SUBSTANTIVOS NO PLURAL
   Exemplo:
   /produtos
   /usuarios
   /pedidos

2. UTILIZAR MÉTODOS HTTP CORRETOS
   GET    -> Buscar recurso(s)
   POST   -> Criar recurso
   PUT    -> Atualizar recurso completo
   PATCH  -> Atualização parcial (opcional)
   DELETE -> Remover recurso

3. ESTRUTURA PADRÃO DE CRUD

   LISTAR:
   GET /recurso

   BUSCAR POR ID:
   GET /recurso/{id}

   CRIAR:
   POST /recurso

   ATUALIZAR:
   PUT /recurso/{id}

   DELETAR:
   DELETE /recurso/{id}

4. UTILIZAR SUB-RECURSOS QUANDO NECESSÁRIO

   Exemplo:
   /usuarios/{id}/enderecos
   /produtos/{id}/avaliacoes
   /carrinho/itens

Obs.: Pergunta crucial para definir recursos ou subrecursos: A entidade faz sentido existir sozinha, ou só dentro de outra?
Por exemplo, avaliações só fazem sentido quando avaliam algo; assim avaliações só pode ser um subrecurso, e não um recurso estrito em modelagem REST.

5. EVITAR VERBOS NA URL

   ERRADO:
   /criarProduto
   /deletarUsuario

   CORRETO:
   POST   /produtos
   DELETE /usuarios/{id}

6. PADRONIZAR RESPOSTAS DA API

   Exemplo:
   {
     "sucesso": true,
     "dados": {...},
     "erros": []
   }

7. VERSIONAMENTO DA API

   Recomenda-se usar v<nº atual da versão>:
   /api/v1/produtos
   /api/v1/usuarios

8. SUPORTE A PAGINAÇÃO

   GET /produtos?page=0&size=10&sort=nome,asc

9. NOMES CONSISTENTES

   - IDs sempre como {id}
   - Evitar abreviações
   - Manter padrão em toda API (System Design)

10. UTILIZAR CÓDIGOS HTTP CORRETOS

200 OK        -> Sucesso em GET/PUT
201 Created   -> Recurso criado com sucesso (POST)
204 No Content-> Sucesso sem retorno (DELETE)

400 Bad Request   -> Erro de validação
401 Unauthorized  -> Não autenticado
403 Forbidden     -> Sem permissão
404 Not Found     -> Recurso não encontrado
500 Internal Error-> Erro interno

11. FILTROS VIA QUERY PARAMS

GET /produtos?nome=iphone
GET /produtos?precoMin=1000&precoMax=5000
GET /produtos?categoria=eletrônicos

Obs.: Nunca usar query params para ações

12. IDEMPOTÊNCIA

PUT e DELETE devem ser idempotentes:
Executar a mesma requisição múltiplas vezes deve gerar o mesmo resultado.

13. PADRÃO DE NOMES NO JSON

- Usar camelCase:
  nomeProduto, precoTotal, dataCriacao

- Evitar:
  nome_produto, NomeProduto

