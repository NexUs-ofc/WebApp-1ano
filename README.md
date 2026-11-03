# Ceris

**Sua despensa inteligente: a cereja do bolo da sua cozinha.**

O **Ceris** é uma plataforma desenvolvida para facilitar o gerenciamento de alimentos e o controle de despensas, ajudando a reduzir o desperdício e incentivar o consumo consciente.

A proposta é transformar a maneira como pessoas e empresas organizam seus alimentos, oferecendo ferramentas para acompanhar estoques, controlar validades e tomar decisões mais conscientes no dia a dia.

> O que você já tem pode ir mais longe.

---

## Sobre o projeto

O Ceris surgiu com o propósito de combater o desperdício de alimentos por meio da tecnologia, promovendo hábitos de consumo mais sustentáveis e contribuindo para o **Objetivo de Desenvolvimento Sustentável 12 (ODS 12) – Consumo e Produção Responsáveis**, estabelecido pela ONU.

A plataforma reúne recursos de gerenciamento e acompanhamento de alimentos em um único ambiente, proporcionando mais organização, praticidade e consciência no consumo.

## Funcionalidades

- **Gerenciamento de alimentos:** cadastro, consulta, atualização e remoção de alimentos.
- **Controle de despensa:** acompanhamento dos itens armazenados e suas respectivas quantidades.
- **Controle de validade:** organização dos alimentos de acordo com suas datas de vencimento.
- **Categorias:** classificação dos alimentos para facilitar sua organização.
- **Gerenciamento de usuários e empresas:** administração dos cadastros e informações.
- **Painel administrativo:** ambiente para gerenciamento das informações da plataforma.
- **Análises e indicadores:** visualização de dados e informações sobre os registros do sistema.

## Tecnologias utilizadas

O projeto utiliza tecnologias voltadas ao desenvolvimento de aplicações web:

| Tecnologia | Utilização |
|---|---|
| Java | Desenvolvimento do sistema |
| Jakarta EE | Gerenciamento das requisições e funcionalidades web |
| JSP | Construção das páginas dinâmicas |
| HTML5 | Estrutura das páginas |
| CSS3 | Estilização e responsividade |
| JavaScript | Interatividade |
| PostgreSQL | Armazenamento dos dados |
| JDBC | Comunicação com o banco de dados |
| Cloudinary | Armazenamento de imagens |
| BCrypt | Proteção das senhas |
| Git e GitHub | Versionamento e colaboração |

## Organização do projeto

O projeto está organizado para separar as responsabilidades e facilitar a manutenção do código.

```text
src/
└── main/
    ├── java/
    │   └── org/example/interdisciplinar/
    │       ├── conexao/
    │       ├── controller/
    │       ├── dao/
    │       ├── dto/
    │       ├── exception/
    │       ├── model/
    │       └── utils/
    │
    └── webapp/
        ├── assets/
        ├── css/
        ├── JS/
        ├── WEB-INF/
        │   ├── Views/
        │   └── web.xml
        ├── index.html
        ├── index.jsp
        ├── login.html
        ├── planos.html
        ├── restricao.html
        └── sobre.html
```

## Como executar o projeto

Para executar o Ceris em ambiente local, é necessário ter:

- Java JDK instalado
- Apache Tomcat compatível com Jakarta EE
- PostgreSQL
- IntelliJ IDEA ou outra IDE de sua preferência
- Git

**1. Clone o repositório**

```bash
git clone URL_DO_REPOSITORIO
```

**2. Configure o banco de dados**

Crie o banco de dados PostgreSQL e configure as informações de conexão no arquivo `.env`:

```env
DB_URL=jdbc:postgresql://localhost:5432/ceris
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

**3. Configure o Cloudinary**

Para utilizar o armazenamento de imagens, adicione também as credenciais da sua conta:

```env
CLOUDINARY_CLOUD_NAME=seu_cloud_name
CLOUDINARY_API_KEY=sua_api_key
CLOUDINARY_API_SECRET=sua_api_secret
```

**4. Execute a aplicação**

Abra o projeto na IDE, configure o servidor Tomcat e inicie a aplicação.

> Não compartilhe o arquivo `.env` ou suas credenciais. Utilize suas próprias configurações para executar o projeto.

## Equipe

Projeto desenvolvido de forma colaborativa, envolvendo atividades de planejamento, design, desenvolvimento e organização.

## Objetivo

Mais do que organizar alimentos, o Ceris busca incentivar uma relação mais consciente com o consumo, mostrando que pequenas mudanças na organização podem contribuir para um futuro mais sustentável.

**Ceris — O que você já tem pode ir mais longe.**
