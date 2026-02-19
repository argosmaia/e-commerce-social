# Git Cheatsheet — Projeto E-commerce MVP

## Feito por: Argos A. Maia  
## Você tem liberdade de editar esse documento mas mantenha meus créditos no arquivo

---

# 📚 Sumário

- [🧱 Inicialização de Repositório](#-inicialização-de-repositório)
- [📥 Clonar Repositório](#-clonar-repositório)
- [🔗 Conectar a um Remoto](#-conectar-a-um-remoto)
- [📤 Primeiro Push](#-primeiro-push)
- [🌳 Estrutura de Branches](#-estrutura-de-branches)
- [📦 Conceito Fundamental](#-conceito-fundamental)
- [🚀 Criar Branch Pessoal](#-criar-branch-pessoal-a-partir-da-develop)
- [🔎 Fetch vs Pull](#-fetch-vs-pull)
- [🔁 Transformar Branch Remota em Local](#-transformar-branch-remota-em-branch-local)
- [🔀 Trazer Apenas Um Arquivo](#-trazer-apenas-um-arquivo-de-outra-branch)
- [🍒 Cherry-pick](#-cherry-pick)
- [🏆 Merge Squash](#-merge-squash-merge-fake)
- [🔁 Merge Normal](#-merge-normal)
- [🗑 Remover Branch do Remoto](#-remover-branch-do-remoto)
- [🔧 Unset Upstream](#-remover-vínculo-com-remoto-unset-upstream)
- [🧹 Limpeza com Prune](#-limpar-referências-remotas-mortas)
- [📡 Comportamento Após Clone](#-comportamento-após-clone)
- [🌊 GitFlow](#-gitflow)
- [🔥 Git salva snapshots, não diferenças](#-git-salva-snapshots-não-diferenças)
- [🧭 Branch é só um ponteiro](#-branch-é-só-um-ponteiro)
- [🎯 HEAD é só um ponteiro simbólico](#-head-é-só-um-ponteiro-simbólico)
- [💾 Quase nada se perde no Git](#-quase-nada-se-perde-no-git)
- [🧬 Merge une históricos, não arquivos](#-merge-une-históricos-não-arquivos)
- [🧯 Rebase reescreve história](#-rebase-reescreve-história)
- [🗂 Commits parciais com git add -p](#-commits-parciais-com-git-add--p)
- [🧪 Encontrar bug com git bisect](#-encontrar-bug-com-git-bisect)
- [🗄 Git é um banco de dados de objetos](#-git-é-um-banco-de-dados-de-objetos)
- [⚠️ Forçar push com segurança](#-forçar-push-com-segurança)
- [❌ O que NÃO existe no Git](#-o-que-não-existe-no-git)
- [🎯 Fluxo Atual do Projeto](#-fluxo-atual-do-projeto)
- [📌 Resumo Técnico](#-resumo-técnico)

---

Este arquivo resume **o fluxo de branches e comandos Git** usados no projeto até agora.

---

## 🧱 Inicialização de Repositório

Criar um repositório do zero:

```bash
git init
```

Isso cria a pasta `.git/` com toda a estrutura interna do versionamento.

Adicionar arquivos e criar o primeiro commit:

```bash
git add .
git commit -m "chore: commit inicial"
```

---

## 📥 Clonar Repositório

Clonar um repositório remoto existente:

```bash
git clone <url-do-repositorio>
```

Exemplo:

```bash
git clone git@github.com:usuario/repositorio.git
```

O Git:

- Cria uma pasta com o nome do projeto
- Configura automaticamente o remoto `origin`
- Faz checkout da branch default (`main` normalmente)

---

## 🔗 Conectar a um Remoto

Caso o repositório já exista localmente:

```bash
git remote add origin <url-do-repositorio>
```

Verificar remotos:

```bash
git remote -v
```

Alterar URL:

```bash
git remote set-url origin <nova-url>
```

Remover remoto:

```bash
git remote remove origin
```

---

## 📤 Primeiro Push

Publicar a branch principal:

```bash
git push -u origin main
```

O `-u` cria o vínculo de upstream.

Depois disso, basta usar:

```bash
git push
git pull
```

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

Push inicial:

```bash
git push -u origin argos
```

---

## 🔎 Fetch vs Pull

### `git fetch`

- Atualiza referências remotas
- Não altera sua branch atual
- Traz novas branches do servidor

Exemplo:
```
[new branch] marcos -> origin/marcos
```

---

### `git pull`

Equivale a:

```bash
git fetch
git merge <upstream>
```

Não cria novas branches automaticamente.

---

## 🔁 Transformar Branch Remota em Branch Local

```bash
git fetch
git branch -r
git switch marcos
```

Ou:

```bash
git checkout -b marcos origin/marcos
```

---

## 🔀 Trazer Apenas Um Arquivo de Outra Branch

```bash
git checkout main
git diff --name-only main develop
git diff --name-only --cached # verifica o que tá staged
git checkout develop -- build.gradle
git add build.gradle
git commit -m "chore(build): sincroniza build.gradle com develop"
git push origin main
```

---

## 🍒 Cherry-pick

```bash
git log --oneline develop
git checkout main
git cherry-pick <hash>
```

---

## 🏆 Merge Squash (Merge Fake)

```bash
git checkout develop
git merge --squash argos
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

```bash
git push origin --delete develop
```

---

## 🔧 Remover Vínculo com Remoto (Unset Upstream)

```bash
git branch --unset-upstream develop
git branch -vv
```

---

## 🧹 Limpar Referências Remotas Mortas

```bash
git fetch --prune
```

---

## 📡 Comportamento Após Clone

```bash
git clone <repo>
git fetch
git switch develop
```
---

## 🌊 GitFlow

Modelo clássico de versionamento:

```
main        → produção
develop     → integração contínua
feature/*   → novas funcionalidades
release/*   → preparação de versão
hotfix/*    → correções urgentes em produção
```

Exemplo de fluxo:

Criar feature:

```bash
git checkout develop
git checkout -b feature/login
```

Finalizar feature:

```bash
git checkout develop
git merge feature/login
git branch -d feature/login
```

Criar release:

```bash
git checkout develop
git checkout -b release/1.0.0
```

Hotfix:

```bash
git checkout main
git checkout -b hotfix/correcao-critica
```

---

## 💾 Quase nada se perde no Git

Mesmo após:

```bash
git reset --hard
```

Você pode recuperar via:

```bash
git reflog
```
O reflog registra movimentos locais de HEAD.

---

## 🧯 Rebase reescreve história
```bash
git rebase develop
```
Cria novos commits com novos hashes.
Rebase altera a linha do tempo do histórico.

---

## 🗂 Commits parciais com git add -p

```bash
git add -p
```
Permite selecionar trechos específicos (hunks) para commit.
---

## 🧪 Encontrar bug com git bisect
```bash
git bisect start
git bisect bad
git bisect good <hash>
```

Ele faz busca binária no histórico para encontrar o commit que introduziu o erro.

---

## ⚠️ --force-with-lease é mais seguro que --force
```bash
git push --force-with-lease
```
Força o push apenas se ninguém alterou o remoto desde seu último fetch.
É mais seguro que --force.

---

## ❌ O que NÃO existe no Git

```bash
git commit --all-branches
git push develop, main
```

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
| `git init` | Inicializa repositório |
| `git clone` | Clona repositório remoto |
| `git remote add` | Conecta remoto |
| `git fetch` | Atualiza referências remotas |
| `git pull` | Fetch + merge da branch atual |
| `git switch <branch>` | Cria branch local se existir no remoto |
| `git push -u origin <branch>` | Publica branch e cria tracking |
| `git branch -vv` | Mostra upstream |
| `git fetch --prune` | Remove refs remotas mortas |

---

Este documento reflete o estado atual do workflow do projeto.