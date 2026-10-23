<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Alimentos</title>
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
    <a class="ativo" href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDespensa">Item Despensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Alimentos</small>
    </div>

    <c:if test="${empty alimentos}">
      <p class="vazio">Nenhum alimento cadastrado</p>
    </c:if>

    <c:if test="${not empty alimentos}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/alimentos" method="get">
          <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
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

    <div class="botaoCriar">
                <button type="button" onclick="document.getElementById('popupAlimento').showModal()">Adicionar</button>
            </div>

            <div class="popupCriar">
                <dialog id="popupAlimento">
                    <h2>Novo alimento</h2>
                    <form action="${pageContext.request.contextPath}/alimentos" method="post">
                        <label for="codigoBarras">Código de barras</label>
                        <input id="codigoBarras" name="codigoBarras" type="text" required>
                        <label for="nome">Nome</label>
                        <input id="nome" name="nome" type="text" required>
                        <label for="marcaAlimento">Marca</label>
                        <input id="marcaAlimento" name="marca" type="text" required>
                        <label for="idCategoria">ID da categoria</label>
                        <input id="idCategoria" name="idCategoria" type="number" required>
                        <div class="botoesPopup">
                            <button type="submit">Salvar</button>
                            <button type="button" onclick="document.getElementById('popupAlimento').close()">Cancelar</button>
                        </div>
                    </form>
                </dialog>
            </div>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Cód. de Barras</th>
              <th>Nome</th>
              <th>Marca</th>
              <th>ID da Categoria</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="alimento" items="${alimentos}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(alimento.id), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.codigoBarras), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.marca), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(alimento.idCategoria), fn:toLowerCase(param.pesquisa))}">
                <tr>
                <td><c:out value="${alimento.id}"/></td>
                <td><c:out value="${alimento.codigoBarras}"/></td>
                <td><c:out value="${alimento.nome}"/></td>
                <td><c:out value="${alimento.marca}"/></td>
                <td><c:out value="${alimento.idCategoria}"/></td>
                <td class="acoes">
                  <button type="button" class="botaoExcluir" title="Excluir alimento" aria-label="Excluir alimento" onclick="abrirPopupExclusao('${alimento.id}')">
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
