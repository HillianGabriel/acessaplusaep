# Acessa+ — AEP 4º Semestre (Entrega 2)

Canal único e rastreável para estudantes com deficiência relatarem barreiras de acessibilidade (ODS 4, 10 e 16).
Back-end em **Java 17 + Spring Boot 3 + MySQL/MariaDB (XAMPP)** (API REST). Sem interface gráfica (foco Engenharia de Software).

## Estrutura
- `/src` — código-fonte (`model`, `repository`, `service`, `controller`)
- `/database/schema.sql` — script de criação das tabelas
- `/docs` — diagramas e documentação

## Como rodar
Pré-requisitos: **JDK 17**, **Maven 3.9+** e **XAMPP** (MySQL/MariaDB).

1. Abra o **XAMPP Control Panel** e inicie o módulo **MySQL** (porta 3306).
2. Crie o banco e as tabelas, de um destes jeitos:
   - **phpMyAdmin** (`http://localhost/phpmyadmin`) → aba **Importar** → escolher `database/schema.sql` → **Executar**. O script já cria o banco `acessamais`.
   - Ou pelo terminal: `mysql -u root < database/schema.sql` (o `mysql.exe` fica em `C:\xampp\mysql\bin`).
3. Rodar a aplicação (padrão do XAMPP: usuário `root`, sem senha):
   ```bash
   mvn spring-boot:run
   ```
   Se o seu MySQL tiver senha ou outra porta, defina `DB_URL`, `DB_USER` e `DB_PASSWORD`. Exemplo (Windows PowerShell):
   ```powershell
   $env:DB_PASSWORD="minhasenha"; mvn spring-boot:run
   ```
   Para um **MySQL puro** (fora do XAMPP), use `DB_URL=jdbc:mysql://localhost:3306/acessamais`.
4. A API sobe em `http://localhost:8080`.

> O XAMPP usa o MariaDB, compatível com MySQL; por isso o projeto traz os dois drivers e o padrão é `jdbc:mariadb://`.

## Pilares de POO
- **Herança:** `Usuario` (abstrata) → `Estudante`, `MembroNucleo`, `EquipeManutencao`.
- **Interface / classe abstrata:** `CalculaPrazoSla` e `Encaminhamento` (abstrata).
- **Polimorfismo (`@Override`):** `calcularPrazoSLA()` em `EncaminhamentoEstrutural` (3 dias), `EncaminhamentoPedagogico` (7) e `EncaminhamentoAtitudinal` (15).
- **Associação/Composição:** `Estudante` 1—N `Manifestacao`; `Manifestacao` 1—1 `Encaminhamento` (cascade + orphanRemoval).

## CRUD da entidade principal (`Manifestacao`)
```bash
# criar estudante
curl -X POST localhost:8080/usuarios -H "Content-Type: application/json" \
  -d '{"tipo":"ESTUDANTE","nome":"Lucas Ferreira","email":"lucas@aluno.edu","extra":"25000001-1"}'

# Create
curl -X POST localhost:8080/manifestacoes -H "Content-Type: application/json" \
  -d '{"estudanteId":1,"tipoBarreira":"ARQUITETONICA","local":"Bloco B - Elevador","descricao":"Elevador quebrado"}'

# Read
curl localhost:8080/manifestacoes
curl localhost:8080/manifestacoes/1

# Update (dados e status)
curl -X PUT localhost:8080/manifestacoes/1 -H "Content-Type: application/json" -d '{"local":"Bloco C - Elevador"}'
curl -X PATCH localhost:8080/manifestacoes/1/status -H "Content-Type: application/json" -d '{"status":"RESOLVIDA"}'

# Delete
curl -X DELETE localhost:8080/manifestacoes/1
```
Tipos de barreira: `ARQUITETONICA`, `PEDAGOGICA`, `ATITUDINAL`. Status: `RECEBIDA`, `EM_ANALISE`, `EM_ANDAMENTO`, `RESOLVIDA`.

Erros comuns: `404` (id inexistente), `400` (campos obrigatórios ausentes) e `409` (e-mail de usuário já cadastrado).

## O que foi implementado
- Cadastro de usuários por perfil (`Estudante`, `MembroNucleo`, `EquipeManutencao`) — `POST/GET /usuarios`.
- Registro de manifestação de barreira de acessibilidade (RF02) com classificação por tipo.
- Geração automática do encaminhamento, com setor responsável e prazo de SLA definidos por polimorfismo.
- Acompanhamento da manifestação: consulta, atualização de dados e de status (a conclusão registra `data_conclusao`).
- Exclusão da manifestação (o encaminhamento é removido junto, por composição).

## Alterações de escopo em relação à Entrega 1
- O banco passou de **PostgreSQL para MySQL/MariaDB (XAMPP)**, para facilitar a execução no ambiente do avaliador. O modelo de dados não mudou; só os tipos do `schema.sql` foram adaptados (`INT AUTO_INCREMENT`, `DATETIME`).
- `Encaminhamento` passou de interface pura para **classe abstrata que implementa a interface `CalculaPrazoSla`** (necessário para persistir a entidade).
- Adicionado `EncaminhamentoAtitudinal` (3º tipo de barreira do RF02) e a coluna `tipo_encaminhamento`.
- A associação `MembroNucleo` → `Manifestacao` ("realiza triagem") foi retirada do diagrama de classes desta entrega por não estar implementada; fica como evolução futura.
- Autenticação/perfis (RF01), notificações (RF06), indicadores (RF05) e upload de anexos ficam como evolução futura; a tabela `anexos` já existe no script.

## Documentação
Diagramas (classes e DER) e o guia em PDF para o avaliador ficam em [`/docs`](docs/).
