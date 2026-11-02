<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>empresas</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <!-- Sidebar -->
  <nav class="sidebar">
    <div class="logoSidebar">
      <div class="simboloLogo">
        <img src="${pageContext.request.contextPath}/assets/logo-ceris.svg" alt="">
      </div>
      <span class="nomeLogo">Ceris</span>
      <img class="linhaLogo" src="${pageContext.request.contextPath}/assets/logo-underline.svg" alt="">
    </div>
        <a href="${pageContext.request.contextPath}/index.jsp"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-chart-histogram.svg" alt="">Análise</a>
        <a href="${pageContext.request.contextPath}/enderecos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-marker.svg" alt="">Endereço</a>
        <a href="${pageContext.request.contextPath}/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
        <a href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
        <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
        <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_despensa</a>
        <a class="ativo" href="${pageContext.request.contextPath}/empresas"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-enterprise.svg" alt="">Empresas</a>
        <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
    <!-- Perfil -->
    <div class="rodapeSidebar">
      <img class="linhaPerfilSidebar" src="${pageContext.request.contextPath}/assets/profile-line.svg" alt="">
      <div class="perfilSidebar">
        <div class="fotoPerfilSidebar">
          <img src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="Foto de perfil">
        </div>
        <div class="dadosPerfilSidebar">
          <strong>Nome do administrador</strong>
          <span>Administrador</span>
        </div>
      </div>
      <a class="botaoSairSidebar" href="${pageContext.request.contextPath}/index.html">
        <img src="${pageContext.request.contextPath}/assets/fi-br-exit.svg" alt="">
        Sair
      </a>
    </div>
  </nav>

  <!-- Conteudo -->
  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Empresa</small>
    </div>

    <c:if test="${empty empresas}">
      <p class="vazio">Nenhuma empresa cadastrada</p>
    </c:if>

    <c:if test="${not empty empresas}">
    <!-- Pesquisa -->
    <div class="pesquisa">
      <form action="${pageContext.request.contextPath}/empresas" method="get">
        <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
        <button type="submit" class="botaoPesquisar">Pesquisar</button>
        <button type="button" class="botaoFiltro" aria-label="Aplicar filtro de pesquisa" onclick="abrirPopupFiltro()">
          <span>Filtro</span>
          <span class="iconeFiltro" aria-hidden="true">
            <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
            <img src="<c:url value='/assets/filter-line-b.svg'/>" alt="">
            <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
          </span>
        </button>
      </form>
    </div>

    <!-- Filtro -->
    <dialog id="popupFiltro" class="popupFiltro popupFiltroAlimento" aria-labelledby="tituloPopupFiltro">
      <div class="conteudoPopupFiltro">
        <div class="cabecalhoPopupFiltro">
          <h2 id="tituloPopupFiltro">Filtros</h2>
          <button type="button" class="botaoFecharPopupFiltro" aria-label="Fechar filtros" onclick="fecharPopupFiltro()">&times;</button>
        </div>
        <form onsubmit="event.preventDefault(); fecharPopupFiltro()">
          <div class="camposPopupFiltro">
            <div class="campoPopupFiltro">
              <span>id</span>
              <div class="intervaloPopupFiltro">
                <input name="idMinimo" type="number" placeholder="M&iacute;nimo">
                <span aria-hidden="true">&ndash;</span>
                <input name="idMaximo" type="number" placeholder="M&aacute;ximo">
              </div>
            </div>
            <label class="campoPopupFiltro">nome
              <input name="nome" type="text" placeholder="Digite o nome">
            </label>
            <label class="campoPopupFiltro">CNPJ
              <input name="cnpj" type="text" placeholder="Digite o CNPJ">
            </label>
            <label class="campoPopupFiltro">CEP
              <input name="cep" type="text" placeholder="Digite o CEP">
            </label>
          </div>
          <img class="divisorPopupFiltro" src="${pageContext.request.contextPath}/assets/popup-filter-divider.svg" alt="">
          <fieldset class="ordemPopupFiltro">
            <legend>Ordenar por</legend>
            <div class="opcoesOrdemPopupFiltro">
              <label><input name="ordenar" type="radio" value="nome">Ordenar por nome</label>
              <label><input name="ordenar" type="radio" value="cnpj">Ordenar por CNPJ</label>
            </div>
          </fieldset>
          <div class="acoesPopupFiltro">
            <button type="reset" class="botaoLimparPopupFiltro">Limpar</button>
            <button type="submit" class="botaoAplicarPopupFiltro">Aplicar</button>
          </div>
        </form>
      </div>
    </dialog>
    </c:if>

    <!-- Cadastro -->
    <button type="button" class="botaoCriar" onclick="abrirPopupEmpresa()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>

    <dialog id="popupEmpresa" class="popupFormulario popupFormularioMedio">
      <div class="barraPopupFormulario"></div>
      <div class="conteudoPopupFormulario">
        <h2><span>Criar</span> empresa</h2>
        <form onsubmit="event.preventDefault(); fecharPopupEmpresa()">
          <div class="gradeCamposPopup">
            <label class="campoPopup" for="nomeEmpresa">Nome<input id="nomeEmpresa" name="nome" type="text" required></label>
            <label class="campoPopup" for="cnpjEmpresa">CNPJ<input id="cnpjEmpresa" name="cnpj" type="text" required></label>
            <label class="campoPopup" for="cepEmpresa">CEP<input id="cepEmpresa" name="cep" type="text" required></label>
            <label class="campoPopup" for="telefoneEmpresa">Telefone<input id="telefoneEmpresa" name="telefone" type="tel" required></label>
          </div>
          <div class="acoesPopupFormulario">
            <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEmpresa()">cancelar</button>
            <button type="submit" class="botaoConfirmarPopup">confirmar</button>
          </div>
        </form>
      </div>
    </dialog>

    <c:if test="${not empty empresas}">
    <!-- Tabela -->
    <div class="cartao">
      <table>
        <thead>
          <tr>
            <th>id_empresa</th>
            <th>nome</th>
            <th>CNPJ</th>
            <th>CEP</th>
            <th>telefone</th>
            <th aria-label="Ações"></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="empresa" items="${empresas}">
            <c:if test="${empty param.pesquisa or empresa.id.toString() == param.pesquisa or fn:contains(fn:toLowerCase(empresa.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(empresa.cnpj), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(empresa.cep), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(empresa.telefone), fn:toLowerCase(param.pesquisa))}">
              <tr>
                <td><c:out value="${empresa.id}"/></td>
                <td><c:out value="${empresa.nome}"/></td>
                <td><c:out value="${empresa.cnpj}"/></td>
                <td><c:out value="${empresa.cep}"/></td>
                <td><c:out value="${empresa.telefone}"/></td>
                <td class="acoes">
                  <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="${empresa.id}" data-nome="${fn:escapeXml(empresa.nome)}" data-cnpj="${fn:escapeXml(empresa.cnpj)}" data-cep="${fn:escapeXml(empresa.cep)}" data-telefone="${fn:escapeXml(empresa.telefone)}" onclick="abrirPopupEdicao(this)">
                    <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                  </button>
                  <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('${empresa.id}')">
                    <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
                  </button>
                </td>
              </tr>
            </c:if>
          </c:forEach>
        </tbody>
      </table>
    </div>
    </c:if>
  </main>

  <!-- Edicao -->
  <dialog id="popupEdicao" class="popupFormulario popupFormularioMedio">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> empresa</h2>
      <form onsubmit="event.preventDefault(); fecharPopupEdicao()">
        <input type="hidden" id="idEmpresaEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="nomeEmpresaEdicao">Nome<input id="nomeEmpresaEdicao" name="nome" type="text" required></label>
          <label class="campoPopup" for="cnpjEmpresaEdicao">CNPJ<input id="cnpjEmpresaEdicao" name="cnpj" type="text" required></label>
          <label class="campoPopup" for="cepEmpresaEdicao">CEP<input id="cepEmpresaEdicao" name="cep" type="text" required></label>
          <label class="campoPopup" for="telefoneEmpresaEdicao">Telefone<input id="telefoneEmpresaEdicao" name="telefone" type="tel" required></label>
        </div>
        <div class="acoesPopupFormulario">
          <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEdicao()">cancelar</button>
          <button type="submit" class="botaoConfirmarPopup">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <!-- Exclusao -->
  <dialog id="popupExclusao" class="popupExclusao">
    <div class="barraPopupExclusao"></div>
    <div class="conteudoPopupExclusao">
      <h2><span>Excluir</span> empresa</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form onsubmit="event.preventDefault(); fecharPopupExclusao()">
        <input type="hidden" id="idEmpresaExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <!-- JavaScript -->
  <script src="${pageContext.request.contextPath}/JS/views.js"></script>
</body>
</html>
