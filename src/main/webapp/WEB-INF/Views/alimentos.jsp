<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>alimentos</title>
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
    <a class="ativo" href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
    <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
    <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_dispensa</a>
    <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Alimento</small>
    </div>

    <c:if test="${empty alimentos}">
      <p class="vazio">Nenhum alimento cadastrado</p>
    </c:if>

    <c:if test="${not empty alimentos}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/alimentos" method="get">
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

      <button type="button" class="botaoCriar" onclick="abrirPopupAlimento()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>

      <dialog id="popupAlimento" class="popupFormulario popupFormularioMedio">
                    <div class="barraPopupFormulario"></div>
                    <div class="conteudoPopupFormulario">
                    <h2><span>Criar</span> alimento</h2>
                    <form action="${pageContext.request.contextPath}/alimentos" method="post">
                        <div class="gradeCamposPopup">
                            <label class="campoPopup" for="codigoBarras">Cod_barras<input id="codigoBarras" name="codigoBarras" type="text" required></label>
                            <label class="campoPopup" for="marcaAlimento">Marca<input id="marcaAlimento" name="marca" type="text" required></label>
                            <label class="campoPopup" for="nome">Nome<input id="nome" name="nome" type="text" required></label>
                            <label class="campoPopup" for="idCategoria">Categoria<input id="idCategoria" name="idCategoria" type="number" required></label>
                        </div>
                        <div class="acoesPopupFormulario">
                            <button type="button" class="botaoCancelarPopup" onclick="fecharPopupAlimento()">cancelar</button>
                            <button type="submit" class="botaoConfirmarPopup">confirmar</button>
                        </div>
                    </form>
                    </div>
      </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_alimento</th>
              <th>alimento</th>
              <th>marca</th>
              <th>categoria</th>
              <th aria-label="Ações"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="alimento" items="${alimentos}" varStatus="status">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(alimento.id()), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.alimento()), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.marca()), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.nomeCategoria()), fn:toLowerCase(param.pesquisa))}">
                <tr>
                <td><c:out value="${alimento.id()}"/></td>
                <td><c:out value="${alimento.alimento()}"/></td>
                <td><c:out value="${alimento.marca()}"/></td>
                <td><c:out value="${alimento.nomeCategoria()}"/></td>
                <td class="acoes">
                  <label class="botaoBloquear" title="Ocultar dados da linha">
                    <input type="checkbox" class="bloqueioLinha" aria-label="Ocultar dados da linha">
                    <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                  </label>
                  <button type="button" class="botaoEditar" title="Editar alimento" aria-label="Editar alimento" data-id="${alimento.id()}" data-codigo-barras="${fn:escapeXml(alimentosOriginais[status.index].codigoBarras)}" data-nome="${fn:escapeXml(alimentosOriginais[status.index].nome)}" data-marca="${fn:escapeXml(alimentosOriginais[status.index].marca)}" data-categoria="${alimentosOriginais[status.index].idCategoria}" onclick="abrirPopupEdicao(this)">
                    <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                  </button>
                  <button type="button" class="botaoExcluir" title="Excluir alimento" aria-label="Excluir alimento" onclick="abrirPopupExclusao('${alimento.id()}')">
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

  <dialog id="popupEdicao" class="popupFormulario popupFormularioMedio">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> alimento</h2>
      <form action="${pageContext.request.contextPath}/alimentos/atualizar" method="post">
        <input type="hidden" id="idAlimentoEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="codigoBarrasEdicao">Cod_barras<input id="codigoBarrasEdicao" name="codigoBarras" type="text" required></label>
          <label class="campoPopup" for="marcaAlimentoEdicao">Marca<input id="marcaAlimentoEdicao" name="marca" type="text" required></label>
          <label class="campoPopup" for="nomeAlimentoEdicao">Nome<input id="nomeAlimentoEdicao" name="nome" type="text" required></label>
          <label class="campoPopup" for="categoriaAlimentoEdicao">Categoria<input id="categoriaAlimentoEdicao" name="idCategoria" type="number" required></label>
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
      <h2><span>Excluir</span> alimento</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/alimentos/deletar" method="post">
        <input type="hidden" id="idAlimentoExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <script>
        function abrirPopupAlimento() {
            document.getElementById("popupAlimento").showModal();
        }
        function fecharPopupAlimento() {
            document.getElementById("popupAlimento").close();
        }
        function abrirPopupEdicao(botao) {
            document.getElementById("idAlimentoEdicao").value = botao.dataset.id;
            document.getElementById("codigoBarrasEdicao").value = botao.dataset.codigoBarras;
            document.getElementById("marcaAlimentoEdicao").value = botao.dataset.marca;
            document.getElementById("nomeAlimentoEdicao").value = botao.dataset.nome;
            document.getElementById("categoriaAlimentoEdicao").value = botao.dataset.categoria;
            document.getElementById("popupEdicao").showModal();
        }
        function fecharPopupEdicao() {
            document.getElementById("popupEdicao").close();
        }
        function abrirPopupExclusao(idAlimento) {
            document.getElementById("idAlimentoExclusao").value = idAlimento;
            document.getElementById("popupExclusao").showModal();
        }
        function fecharPopupExclusao() {
            document.getElementById("popupExclusao").close();
        }
    </script>

</body>
</html>
