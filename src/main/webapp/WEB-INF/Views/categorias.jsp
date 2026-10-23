<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Categorias</title>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <nav class="sidebar">
    <h2>Ceris</h2>
    <a href="${pageContext.request.contextPath}/index.jsp">Análise</a>
    <a href="${pageContext.request.contextPath}/enderecos">Endereço</a>
    <a href="${pageContext.request.contextPath}/usuarios">Usuário</a>
    <a class="ativo" href="${pageContext.request.contextPath}/categorias">Categoria</a>
    <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDespensa">Item Despensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Categorias</small>
    </div>

    <c:if test="${empty categorias}">
      <p class="vazio">Nenhuma categoria encontrada</p>
    </c:if>
    <c:if test="${not empty categorias}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/categorias" method="get">
          <input type="text" name="pesquisa" placeholder="Pesquisar categoria..." value="${param.pesquisa}">
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

      <button type="button" class="botaoCriar" onclick="abrirPopupCategoria()">Adicionar</button>
      <dialog id="popupCategoria">
        <h2>Nova categoria</h2>
        <form action="${pageContext.request.contextPath}/categorias" method="post">
        <label for="nomeCategoria">Nome</label>
        <input id="nomeCategoria" name="nome" type="text" required>
        <label for="descricaoCategoria">Descrição</label>
        <input id="descricaoCategoria" name="descricao" type="text" required>
        <div class="botoesPopup">
        <button type="submit">Salvar</button>
        <button type="button" onclick="fecharPopupCategoria()">Cancelar</button>
            </div>
        </form>
      </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Descrição</th>
              <th>Ações</th>
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
