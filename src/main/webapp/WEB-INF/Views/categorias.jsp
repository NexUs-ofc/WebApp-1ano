<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris - Categorias</title>
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
    <a class="ativo" href="${pageContext.request.contextPath}/categorias">Categoria</a>
    <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDispensa">Item Dispensa</a>
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
          <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
          <button type="submit">Pesquisar</button>
        </form>
      </div>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Descrição</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="categoria" items="${categorias}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(categoria.id), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(categoria.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(categoria.descricao), fn:toLowerCase(param.pesquisa))}">
                <tr>
                <td><c:out value="${categoria.id}"/></td>
                <td><c:out value="${categoria.nome}"/></td>
                <td><c:out value="${categoria.descricao}"/></td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:if>
  </main>
</body>
</html>
