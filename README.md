# Sistema de Criação e Manutenção de Usuários

Aplicação desktop (Java Swing) para **criação e manutenção de usuários** com um sistema de
**notificações internas**. Desenvolvida como trabalho final da disciplina de
*Projeto de Sistemas de Software* (2022-2).

**Autores:** Marcelo Bento e Caio Cordeiro

---

## 📋 Índice

- [Funcionalidades](#-funcionalidades)
- [Tecnologias e Dependências](#-tecnologias-e-dependências)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar](#-como-executar)
- [Testes](#-testes)
- [Banco de Dados](#-banco-de-dados)
- [Arquitetura e Padrões de Projeto](#-arquitetura-e-padrões-de-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Fluxo de Uso](#-fluxo-de-uso)

---

## ✅ Funcionalidades

**Autenticação e usuários**
- Cadastro de usuários com validação de campos, e-mail e senha
- Senhas armazenadas com **hash BCrypt** (nunca em texto puro)
- Login com autenticação de credenciais
- Fluxo de autorização: o **primeiro usuário cadastrado vira admin automaticamente**;
  os demais só conseguem entrar depois de o admin autorizá-los
- Listagem, busca, visualização, edição e exclusão de usuários (painel do admin)
- Preferência de formato de log por usuário (`JSON` / `CSV`)

**Notificações**
- Envio de notificações entre usuários
- Listagem de notificações **não lidas** e **lidas**
- Marcar notificação como lida
- Contadores de notificações não lidas / lidas / enviadas exibidos nas telas

**Interface**
- Telas em Swing: Login, Cadastro, Admin, Usuário Comum, Visualização, Edição,
  Notificações e Envio de Notificações
- Navegação entre telas gerenciada por um serviço central de telas
- Tratamento de erros com mensagens amigáveis (enums de mensagens de erro/sucesso)

---

## 🛠 Tecnologias e Dependências

| Tecnologia | Uso |
|---|---|
| Java 11+ | Linguagem (compilador configurado para 11) |
| Maven | Build e gerência de dependências |
| Swing | Interface gráfica (telas `.form` do IntelliJ + código gerado) |
| SQLite (`sqlite-jdbc`) | Banco de dados embarcado |
| JBCrypt | Hash de senhas |
| [validadorsenha](https://github.com/claytonfraga/validadorsenha) | Regras de validação de senha |
| [LogAdapter](https://github.com/Marcelozoo/LogAdapter) | Formato de log JSON/CSV (via JitPack) |
| JUnit 4 + Mockito | Testes |

---

## 💻 Pré-requisitos

- **JDK 11 ou superior** (`java -version`)
- **Maven 3.6+** (`mvn -v`)
- (Opcional) **IntelliJ IDEA** — recomendado para editar as telas `.form`

> As dependências são baixadas do Maven Central e do [JitPack](https://jitpack.io)
> na primeira execução, portanto é necessário ter acesso à internet.

---

## ▶ Como Executar

Os comandos devem ser executados **na raiz do projeto** (é de lá que o caminho
`banco/meuBanco.db` é resolvido).

### Opção 1 — IntelliJ IDEA (recomendada)

1. Abra o projeto em IntelliJ IDEA (o arquivo `pom.xml` é detectado automaticamente).
2. Aguarde a sincronização do Maven.
3. Execute a classe `src/main/java/Main.java` (botão verde ao lado de `main`).

### Opção 2 — Linha de comando (Maven)

```bash
mvn compile
mvn org.codehaus.mojo:exec-maven-plugin:3.1.0:java -Dexec.mainClass=Main
```

A janela de **Login** deve abrir.

### Opção 3 — Java "puro" (sem Maven exec)

```bash
mvn -q compile dependency:build-classpath -Dmdep.outputFile=target/cp.txt
java -cp "target/classes:$(cat target/cp.txt)" Main
```

### Usando outro arquivo de banco

A conexão é criada em `factory.ConexaoFactory` e pode ser sobrescrita pela
propriedade de sistema `database.url`:

```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.1.0:java \
    -Dexec.mainClass=Main \
    -Ddatabase.url=jdbc:sqlite:banco/meuBanco.db
```

---

## 🧪 Testes

```bash
mvn test
```

**55 testes**, todos passando, distribuídos em:

| Classe de teste | O que cobre |
|---|---|
| `dao.UsuariosDAOTest` | CRUD de usuários no SQLite |
| `dao.NotificacoesDAOTest` | CRUD e consultas de notificações |
| `services.UsuarioServiceTest` | Cadastro, login, autorização, validações |
| `state.StateTest` | Transições de estado da máquina de estados |

Os testes usam o banco isolado `banco/meuBancoTest.db` (definido via propriedade
`database.url` dentro dos próprios testes), separado do banco de produção.

---

## 🗄 Banco de Dados

Bancos SQLite versionados na pasta [`banco/`](banco/):

- `banco/meuBanco.db` — banco de **produção** (usado pela aplicação)
- `banco/meuBancoTest.db` — banco de **testes**

**Esquema:**

```sql
CREATE TABLE usuarios (
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    email             TEXT NOT NULL UNIQUE,
    nome              VARCHAR(100) NOT NULL,
    senha_hash        TEXT NOT NULL,
    is_admin          BOOLEAN NOT NULL DEFAULT 0,
    criado_em         TEXT NOT NULL DEFAULT CURRENT_DATE,
    preferencia_log   TEXT NOT NULL DEFAULT 'JSON'
                      CHECK (preferencia_log IN ('JSON', 'CSV')),
    foi_autenticado   BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE notificacoes (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    destinatario_id INTEGER NOT NULL REFERENCES usuarios(id),
    remetente_id    INTEGER NOT NULL REFERENCES usuarios(id),
    criada_em        TEXT NOT NULL DEFAULT CURRENT_DATE,
    foi_lida        BOOLEAN NOT NULL DEFAULT 0,
    conteudo        TEXT NOT NULL
);
```

---

## 🏗 Arquitetura e Padrões de Projeto

O projeto segue **MVP (Model-View-Presenter)** com separação clara entre camadas:

| Padrão | Onde | Papel |
|---|---|---|
| **MVP** | `views/` + `presenters/` + `models/` | A View só expõe componentes; o Presenter escuta os botões e coordena a lógica |
| **Command** | `command/` | Cada operação (autenticar, cadastrar, excluir, enviar notificação...) é um objeto `Command` executado pelo `Invoke` |
| **State** | `state/` | Máquina de estados da tela (`EstadoTela` + estados como `LogandoState`, `LogadoState`, `EditandoUsuarioState`...). Transições inválidas lançam `TransicaoEstadoInvalidaException` |
| **Factory Method** | `factory/` | `FabricaTela*` criam cada tela/presenter; `TelaFactory` é a fachada |
| **Singleton** | `GerenciadorEventosSingleton` | Central de eventos das telas |
| **Observer** | `observer/` + `eventosTela/` | Telas se inscrevem em eventos (`USUARIO_AUTENTICADO_ADMIN`, `USUARIO_EXCLUIDO_COM_SUCESSO`...) e são notificadas |
| **DAO** | `dao/` | Interfaces `UsuariosDAO`/`NotificacaoDAO` com implementação SQLite, permitindo mock nos testes |
| **Adapter / Façade** | `ProvedorService` | Agrupa os services e os injeta nas telas |

**Fluxo de uma ação:** `View (evento do usuário)` → `Presenter` → `State` → `Command`
→ `Service` → `DAO` → `SQLite`, retornando um `ResultadoOperacao<T>` que acumula
resultado **ou** erros (sem exceções de fluxo normal).

---

## 📁 Estrutura do Projeto

```
.
├── banco/                  # Bancos SQLite (produção e testes)
├── diagrama/               # Diagrama do projeto (Arquivo .asta - Astah)
├── requisitosTrabalho/     # PDF com os requisitos da atividade
├── src/
│   ├── main/java/
│   │   ├── Main.java       # Ponto de entrada
│   │   ├── command/        # Padrão Command
│   │   ├── dao/            # Acesso a dados (SQLite)
│   │   ├── eventosTela/    # Eventos das telas (Observer)
│   │   ├── excecoes/       # Exceções + enums de mensagens
│   │   ├── factory/        # Fábricas de tela e de conexão
│   │   ├── mensagens/      # Mensagens de sucesso
│   │   ├── models/         # Usuario, Notificacao, ResultadoOperacao
│   │   ├── navegacao/      # TipoTela (identidade das telas)
│   │   ├── observer/       # Interface Observer
│   │   ├── presenters/     # Presenters (MVP)
│   │   ├── services/       # Regras de negócio e navegação
│   │   ├── state/          # Padrão State
│   │   ├── utilidades/     # Formatação de erros
│   │   └── views/          # Telas Swing (.form + .java)
│   └── test/java/          # Testes JUnit + Mockito
└── pom.xml
```

---

## 🚦 Fluxo de Uso

1. **Primeira execução:** a tela de *Cadastro* está disponível direto do Login.
2. O **primeiro usuário cadastrado** é criado como **admin** e já sai autenticado.
3. Usuários seguintes são criados **não autorizados** (`foi_autenticado = 0`) e
   recebem a mensagem de que aguardam autorização.
4. O admin entra no **painel admin**, onde pode buscar, visualizar, editar e excluir
   usuários e **enviar notificações**. A **autorização** de um cadastro pendente é
   feita na tela de **visualização** do usuário selecionado.
5. O usuário comum entra na **tela do usuário**, vê suas notificações (lidas/não
   lidas), marca como lidas e edita o próprio perfil.
6. Ao sair, o sistema retorna para a tela de Login.

---

## 📄 Documentação Relacionada

- [`requisitosTrabalho/`](requisitosTrabalho/) — enunciado e requisitos da atividade (PDF)
- [`diagrama/Trabalho Final.asta`](diagrama/) — diagrama do sistema (abrir com [Astah](https://astah.net/))
