<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Itens da Dispensa</title>
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
    <a class="ativo" href="${pageContext.request.contextPath}/itensDispensa">Item Dispensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Itens da Dispensa</small>
    </div>

    <c:if test="${empty itensDispensa}">
      <p class="vazio">Nenhum item cadastrado</p>
    </c:if>

    <c:if test="${not empty itensDispensa}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/itensDispensa" method="get">
          <input type="text" name="pesquisa" placeholder="Pesquisar item..." value="${param.pesquisa}">
          <button type="submit">Pesquisar</button>
        </form>
      </div>
    <button type="button" onclick="abrirPopupItemDispensa()">Adicionar item</button>
    <dialog id="popupItemDispensa">
      <h2>Novo item da dispensa</h2>
      <form action="${pageContext.request.contextPath}/itensDispensa" method="post">
        <label for="idCasa">ID da casa</label>
        <input id="idCasa" name="idCasa" type="number" required>
        <label for="idAlimento">ID do alimento</label>
        <input id="idAlimento" name="idAlimento" type="number" required>
        <label for="quantidade">Quantidade</label>
        <input id="quantidade" name="quantidade" type="number" min="1" required>
        <label for="validade">Validade</label>
        <input id="validade" name="validade" type="date" required>
        <div class="botoes-popup">
          <button type="submit">Salvar</button>
          <button type="button" onclick="fecharPopupItemDispensa()">Cancelar</button>
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
            </tr>
          </thead>
          <tbody>
            <c:forEach var="itemDispensa" items="${itensDispensa}">
              <c:if test="${empty param.pesquisa or itemDispensa.id.toString() == param.pesquisa or itemDispensa.idCasa.toString() == param.pesquisa or itemDispensa.idAlimento.toString() == param.pesquisa or fn:contains(itemDispensa.validade.toString(), param.pesquisa)}">
                <tr>
                  <td><c:out value="${itemDispensa.id}"/></td>
                  <td><c:out value="${itemDispensa.idCasa}"/></td>
                  <td><c:out value="${itemDispensa.idAlimento}"/></td>
                  <td><c:out value="${itemDispensa.quantidade}"/></td>
                  <td><c:out value="${itemDispensa.validade}"/></td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:if>
  </main>

  <script>
    function abrirPopupItemDispensa() {
      document.getElementById("popupItemDispensa").showModal();
    }

    function fecharPopupItemDispensa() {
      document.getElementById("popupItemDispensa").close();
    }
  </script>

</body>
</html>