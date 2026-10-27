<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>categorias</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
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
    <a class="ativo" href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
    <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_dispensa</a>
    <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Categoria</small>
    </div>

    <c:if test="${empty categorias}">
      <p class="vazio">Nenhuma categoria encontrada</p>
    </c:if>
    <c:if test="${not empty categorias}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/categorias" method="get">
          <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
          <button type="submit" class="botaoPesquisar">Pesquisar</button>
          <button type="button" class="botaoFiltro" aria-label="Aplicar filtro de pesquisa">
            <span>Filtro</span>
            <span class="iconeFiltro" aria-hidden="true">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-b.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
            </span>
          </button>
        </form>
      </div>

      <button type="button" class="botaoCriar" onclick="abrirPopupCategoria()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>
      <dialog id="popupCategoria" class="popupFormulario popupFormularioPequeno">
        <div class="barraPopupFormulario"></div>
        <div class="conteudoPopupFormulario">
        <h2><span>Criar</span> categoria</h2>
        <form action="${pageContext.request.contextPath}/categorias" method="post">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="nomeCategoria">Nome<input id="nomeCategoria" name="nome" type="text" required></label>
          <label class="campoPopup" for="descricaoCategoria">Descrição<input id="descricaoCategoria" name="descricao" type="text" required></label>
        </div>
        <div class="acoesPopupFormulario">
          <button type="button" class="botaoCancelarPopup" onclick="fecharPopupCategoria()">cancelar</button>
          <button type="submit" class="botaoConfirmarPopup">confirmar</button>
            </div>
        </form>
        </div>
      </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_categoria</th>
              <th>nome</th>
              <th>descricao</th>
              <th aria-label="Ações"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="categoria" items="${categorias}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(categoria.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(categoria.descricao), fn:toLowerCase(param.pesquisa)) or categoria.id.toString() == param.pesquisa}">
                <tr>
                  <td><c:out value="${categoria.id}"/></td>
                  <td><c:out value="${categoria.nome}"/></td>
                  <td><c:out value="${categoria.descricao}"/></td>
                  <td class="acoes">
                    <label class="botaoBloquear" title="Ocultar dados da linha">
                      <input type="checkbox" class="bloqueioLinha" aria-label="Ocultar dados da linha">
                      <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                    </label>
                    <button type="button" class="botaoEditar" title="Editar categoria" aria-label="Editar categoria" data-id="${categoria.id}" data-nome="${fn:escapeXml(categoria.nome)}" data-descricao="${fn:escapeXml(categoria.descricao)}" onclick="abrirPopupEdicao(this)">
                      <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                    </button>
                    <button type="button" class="botaoExcluir" title="Excluir categoria" aria-label="Excluir categoria" onclick="abrirPopupExclusao('${categoria.id}')">
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

  <dialog id="popupEdicao" class="popupFormulario popupFormularioPequeno">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> categoria</h2>
      <form action="${pageContext.request.contextPath}/categorias/atualizar" method="post">
        <input type="hidden" id="idCategoriaEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="nomeCategoriaEdicao">Nome<input id="nomeCategoriaEdicao" name="nome" type="text" required></label>
          <label class="campoPopup" for="descricaoCategoriaEdicao">Descrição<input id="descricaoCategoriaEdicao" class="textoHuninn" name="descricao" type="text" required></label>
        </div>
        <div class="acoesPopupFormulario">
          <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEdicao()">cancelar</button>
          <button type="submit" class="botaoConfirmarPopup">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <dialog id="popupExclusao" class="popupExclusao">
    <div class="barraPopupExclusao"></div>
    <div class="conteudoPopupExclusao">
      <h2><span>Excluir</span> categoria</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/categorias/deletar" method="post">
        <input type="hidden" id="idCategoriaExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <script>
    function abrirPopupCategoria() {
      document.getElementById("popupCategoria").showModal();
    }
    function fecharPopupCategoria() {
      document.getElementById("popupCategoria").close();
    }
    function abrirPopupEdicao(botao) {
      document.getElementById("idCategoriaEdicao").value = botao.dataset.id;
      document.getElementById("nomeCategoriaEdicao").value = botao.dataset.nome;
      document.getElementById("descricaoCategoriaEdicao").value = botao.dataset.descricao;
      document.getElementById("popupEdicao").showModal();
    }
    function fecharPopupEdicao() {
      document.getElementById("popupEdicao").close();
    }
    function abrirPopupExclusao(idCategoria) {
      document.getElementById("idCategoriaExclusao").value = idCategoria;
      document.getElementById("popupExclusao").showModal();
    }
    function fecharPopupExclusao() {
      document.getElementById("popupExclusao").close();
    }
  </script>

</body>
</html>
