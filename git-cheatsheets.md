# Git Cheatsheet — Projeto E-commerce MVP

Este arquivo resume **o fluxo de branches e comandos Git** usados até agora no projeto.

---

## 🌳 Estrutura de Branches

```
main        → produção / estável
  ↑
develop     → integração / base do trabalho
  ↑
argos       → branch pessoal de desenvolvimento
```

---

## 🚀 Criação de Branch Pessoal a partir da `develop`

```bash
git checkout develop
git pull origin develop
git checkout -b argos
```

Push inicial da branch:

```bash
git push -u origin argos
```

---

## 🧱 Commit de Alterações Comuns (ex: build.gradle)

### Commit na branch base (`develop`)

```bash
git checkout develop
git add build.gradle
git commit -m "chore(build): ajusta configuração do Gradle"
```

---

## 🔀 Trazer **apenas um arquivo** de outra branch

### Caso: trazer `build.gradle` da `develop` para a `main`

```bash
git checkout main
git checkout develop -- build.gradle
git add build.gradle
git commit -m "chore(build): sincroniza build.gradle com develop"
git push origin main
```

> ⚠️ Isso **não faz merge completo** — apenas copia o arquivo escolhido.

---

## 🍒 Cherry-pick (quando o commit é isolado)

Usar apenas se o commit mexeu **somente** no que você quer levar.

```bash
git log --oneline develop
git checkout main
git cherry-pick <hash-do-commit>
```

---

## 🔁 Merge normal entre branches

### De `argos` → `develop`

```bash
git checkout develop
git merge argos
git push origin develop
```

### De `develop` → `main`

```bash
git checkout main
git merge develop
git push origin main
```

---

## ❌ O que NÃO existe no Git

```bash
git commit --all-branches   # ❌ não existe
git push develop, main      # ❌ sintaxe inválida
```

> Commits sempre pertencem à **branch atual**.

---

## 🧠 Regras de Ouro

* Infra compartilhada (Gradle, Docker, CI): **commit uma vez**, propague com merge ou cherry-pick
* Arquivo isolado: `git checkout <branch> -- <arquivo>`
* Feature pronta: `merge`
* Correção pontual: `cherry-pick`

---

## ✅ Fluxo recomendado (MVP)

1. Trabalhar em `argos`
2. Merge em `develop`
3. Testar / estabilizar
4. Merge controlado em `main`

---

📌 Este cheatsheet reflete o fluxo atual do projeto e deve ser mantido atualizado conforme o time evoluir.
