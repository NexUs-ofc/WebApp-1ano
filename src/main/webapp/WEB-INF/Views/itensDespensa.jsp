<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Itens da Despensa</title>
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
    <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
    <a class="ativo" href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_dispensa</a>
    <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>item_dispensa</small>
    </div>

    <c:if test="${empty itensDespensa}">
      <p class="vazio">Nenhum item cadastrado</p>
    </c:if>

    <c:if test="${not empty itensDespensa}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/itensDespensa" method="get">
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
    <button type="button" class="botaoCriar" onclick="abrirPopupItemDespensa()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>
    <dialog id="popupItemDespensa" class="popupFormulario popupFormularioMedio">
      <div class="barraPopupFormulario"></div>
      <div class="conteudoPopupFormulario">
      <h2><span>Criar</span> item_despensa</h2>
      <form action="${pageContext.request.contextPath}/itensDespensa" method="post">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="idCasa">ID do usuário<input id="idCasa" name="idCasa" type="number" required></label>
          <label class="campoPopup" for="idAlimento">ID do alimento<input id="idAlimento" name="idAlimento" type="number" required></label>
          <label class="campoPopup" for="quantidade">Quantidade<input id="quantidade" name="quantidade" type="number" min="1" required></label>
          <label class="campoPopup" for="validade">Data de validade<input id="validade" name="validade" type="date" required></label>
        </div>
        <div class="acoesPopupFormulario">
          <button type="button" class="botaoCancelarPopup" onclick="fecharPopupItemDespensa()">cancelar</button>
          <button type="submit" class="botaoConfirmarPopup">confirmar</button>
        </div>
      </form>
      </div>
    </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_item</th>
              <th>id_usuario</th>
              <th>id_alimento</th>
              <th>quantidade</th>
              <th>data_validade</th>
              <th aria-label="Ações"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="itemDaDespensa" items="${itensDespensa}">
              <c:if test="${empty param.pesquisa or itemDaDespensa.id.toString() == param.pesquisa or itemDaDespensa.idCasa.toString() == param.pesquisa or itemDaDespensa.idAlimento.toString() == param.pesquisa or fn:contains(itemDaDespensa.validade.toString(), param.pesquisa)}">
                <tr>
                  <td><c:out value="${itemDaDespensa.id}"/></td>
                  <td><c:out value="${itemDaDespensa.idCasa}"/></td>
                  <td><c:out value="${itemDaDespensa.idAlimento}"/></td>
                  <td><c:out value="${itemDaDespensa.quantidade}"/></td>
                  <td><c:out value="${itemDaDespensa.validade}"/></td>
                  <td class="acoes">
                    <label class="botaoBloquear" title="Ocultar dados da linha">
                      <input type="checkbox" class="bloqueioLinha" aria-label="Ocultar dados da linha">
                      <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                    </label>
                    <button type="button" class="botaoEditar" title="Editar item" aria-label="Editar item" data-id="${itemDaDespensa.id}" data-usuario="${itemDaDespensa.idCasa}" data-alimento="${itemDaDespensa.idAlimento}" data-quantidade="${itemDaDespensa.quantidade}" data-validade="${itemDaDespensa.validade}" onclick="abrirPopupEdicao(this)">
                      <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                    </button>
                    <button type="button" class="botaoExcluir" title="Excluir item" aria-label="Excluir item" onclick="abrirPopupExclusao('${itemDaDespensa.id}')">
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
      <h2><span>Editar</span> item_despensa</h2>
      <form action="${pageContext.request.contextPath}/itensDespensa/atualizar" method="post">
        <input type="hidden" id="idItemEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="usuarioItemEdicao">ID do usuário<input id="usuarioItemEdicao" name="idCasa" type="number" required></label>
          <label class="campoPopup" for="alimentoItemEdicao">ID do alimento<input id="alimentoItemEdicao" name="idAlimento" type="number" required></label>
          <label class="campoPopup" for="quantidadeItemEdicao">Quantidade<input id="quantidadeItemEdicao" name="quantidade" type="number" min="1" required></label>
          <label class="campoPopup" for="validadeItemEdicao">Data de validade<input id="validadeItemEdicao" name="validade" type="date" required></label>
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
      <h2><span>Excluir</span> item</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/itensDespensa/deletar" method="post">
        <input type="hidden" id="idItemExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <script>
    function abrirPopupItemDespensa() {
      document.getElementById("popupItemDespensa").showModal();
    }

    function fecharPopupItemDespensa() {
      document.getElementById("popupItemDespensa").close();
    }
    function abrirPopupEdicao(botao) {
      document.getElementById("idItemEdicao").value = botao.dataset.id;
      document.getElementById("usuarioItemEdicao").value = botao.dataset.usuario;
      document.getElementById("alimentoItemEdicao").value = botao.dataset.alimento;
      document.getElementById("quantidadeItemEdicao").value = botao.dataset.quantidade;
      document.getElementById("validadeItemEdicao").value = botao.dataset.validade;
      document.getElementById("popupEdicao").showModal();
    }
    function fecharPopupEdicao() {
      document.getElementById("popupEdicao").close();
    }
    function abrirPopupExclusao(idItem) {
      document.getElementById("idItemExclusao").value = idItem;
      document.getElementById("popupExclusao").showModal();
    }
    function fecharPopupExclusao() {
      document.getElementById("popupExclusao").close();
    }
  </script>

</body>
</html>
