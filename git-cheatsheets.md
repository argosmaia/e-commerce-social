# Git Cheatsheet — Projeto E-commerce MVP

Este arquivo resume **o fluxo de branches e comandos Git** usados no projeto até agora.

---

## 🌳 Estrutura de Branches

```
main        → produção / estável (remota)
develop     → integração
argos       → branch pessoal
marcos      → branch pessoal
```

---

## 📦 Conceito Fundamental

O Git separa internamente:

```
refs/heads/*              → branches locais
refs/remotes/origin/*     → referências remotas
```

Quando alguém faz:

```bash
git clone <repo>
```

O Git:

- Baixa todas as referências remotas
- Faz checkout apenas da branch default (normalmente `main`)
- Mantém as demais como `origin/<branch>`

---

## 🚀 Criar Branch Pessoal a partir da `develop`

```bash
git checkout develop
git pull origin develop
git checkout -b argos
```

Push inicial da branch:

```bash
git push -u origin argos
```

O `-u` cria o vínculo de rastreamento (upstream).

---

## 🔎 Fetch vs Pull

### `git fetch`

- Atualiza referências remotas
- Não altera sua branch atual
- Traz novas branches do servidor

Exemplo de saída:

```
[new branch] marcos -> origin/marcos
```

Isso cria apenas:

```
origin/marcos
```

Ainda não cria branch local.

---

### `git pull`

Equivale a:

```bash
git fetch
git merge <upstream>
```

Ele **não cria novas branches automaticamente**.

---

## 🔁 Transformar Branch Remota em Branch Local

Após:

```bash
git fetch
```

Verifique as remotas:

```bash
git branch -r
```

Se aparecer:

```
origin/marcos
```

Crie a branch local:

```bash
git switch marcos
```

ou explicitamente:

```bash
git checkout -b marcos origin/marcos
```

O Git:

- Cria `refs/heads/marcos`
- Configura tracking para `origin/marcos`

Verifique com:

```bash
git branch -vv
```

---

## 🔀 Trazer Apenas Um Arquivo de Outra Branch

Exemplo: trazer `build.gradle` da `develop` para `main`

```bash
git checkout main
git checkout develop -- build.gradle
git add build.gradle
git commit -m "chore(build): sincroniza build.gradle com develop"
git push origin main
```

Isso **não faz merge completo** — apenas copia o arquivo escolhido.

---

## 🍒 Cherry-pick

Trazer um commit específico:

```bash
git log --oneline develop
git checkout main
git cherry-pick <hash>
```

---

## 🏆 Merge Squash ("Merge Fake")

Aplica mudanças sem manter histórico da branch:

```bash
git checkout develop
git merge --squash argos
```

Remover arquivos indesejados:

```bash
git restore --staged .metadata/
git restore .metadata/
git commit -m "feat: traz funcionalidades da branch argos"
```

---

## 🔁 Merge Normal

### `argos` → `develop`

```bash
git checkout develop
git merge argos
git push origin develop
```

### `develop` → `main`

```bash
git checkout main
git merge develop
git push origin main
```

---

## 🗑 Remover Branch do Remoto

Apagar do servidor:

```bash
git push origin --delete develop
```

Isso remove apenas do remoto.

---

## 🔧 Remover Vínculo com Remoto (Unset Upstream)

Se quiser que a branch seja apenas local:

```bash
git branch --unset-upstream develop
```

Verificar tracking:

```bash
git branch -vv
```

---

## 🧹 Limpar Referências Remotas Mortas

Após apagar branch do remoto:

```bash
git fetch --prune
```

Remove `origin/<branch>` inexistentes.

---

## 📡 Comportamento Após Clone

Quando alguém clona:

```bash
git clone <repo>
```

Ele recebe:

- Todas as referências remotas
- Apenas a branch default ativa (`main`)

Para acessar outra branch:

```bash
git fetch
git switch develop
```

---

## ❌ O que NÃO existe no Git

```bash
git commit --all-branches
git push develop, main
```

Commits pertencem sempre à **branch atual**.

---

## 🎯 Fluxo Atual do Projeto

1. Trabalhar em `argos`, `marcos` ou branch pessoal
2. Push para remoto quando precisar compartilhar
3. Integrar via `develop`
4. Promover para `main` quando estabilizado

---

## 📌 Resumo Técnico

| Comando | Função |
|----------|------------|
| `git fetch` | Atualiza referências remotas |
| `git pull` | Fetch + merge da branch atual |
| `git switch <branch>` | Cria branch local se existir no remoto |
| `git push -u origin <branch>` | Publica branch e cria tracking |
| `git branch -vv` | Mostra upstream |
| `git fetch --prune` | Remove refs remotas mortas |

---

Este documento reflete o estado atual do workflow do projeto.