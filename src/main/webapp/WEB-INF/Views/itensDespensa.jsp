<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Itens da Despensa</title>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <nav class="sidebar">
    <h2>Ceris</h2>
    <a href="${pageContext.request.contextPath}/index.jsp">Análise</a>
    <a href="${pageContext.request.contextPath}/enderecos">Endereço</a>
    <a href="${pageContext.request.contextPath}/usuarios">Usuário</a>
    <a href="${pageContext.request.contextPath}/categorias">Categoria</a>
    <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a class="ativo" href="${pageContext.request.contextPath}/itensDespensa">Item Despensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Itens da Despensa</small>
    </div>

    <c:if test="${empty itensDespensa}">
      <p class="vazio">Nenhum item cadastrado</p>
    </c:if>

    <c:if test="${not empty itensDespensa}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/itensDespensa" method="get">
          <input type="text" name="pesquisa" placeholder="Pesquisar item..." value="${param.pesquisa}">
          <button type="submit" class="botaoPesquisar">Pesquisar</button>
          <button type="submit" class="botaoFiltro" aria-label="Aplicar filtro de pesquisa">
            <span>Filtro</span>
            <span class="iconeFiltro" aria-hidden="true">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-b.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
            </span>
          </button>
        </form>
      </div>
    <button type="button" class="botaoCriar" onclick="abrirPopupItemDespensa()">Adicionar</button>
    <dialog id="popupItemDespensa">
      <h2>Novo item da despensa</h2>
      <form action="${pageContext.request.contextPath}/itensDespensa" method="post">
        <label for="idCasa">ID da casa</label>
        <input id="idCasa" name="idCasa" type="number" required>
        <label for="idAlimento">ID do alimento</label>
        <input id="idAlimento" name="idAlimento" type="number" required>
        <label for="quantidade">Quantidade</label>
        <input id="quantidade" name="quantidade" type="number" min="1" required>
        <label for="validade">Validade</label>
        <input id="validade" name="validade" type="date" required>
        <div class="botoesPopup">
          <button type="submit">Salvar</button>
          <button type="button" onclick="fecharPopupItemDespensa()">Cancelar</button>
        </div>
      </form>
    </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>ID da casa</th>
              <th>ID do alimento</th>
              <th>Quantidade</th>
              <th>Validade</th>
              <th>Ações</th>
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
