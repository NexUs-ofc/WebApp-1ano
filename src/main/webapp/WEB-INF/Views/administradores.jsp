<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Administradores</title>
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
    <a class="ativo" href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDispensa">Item Dispensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Administradores</small>
    </div>

    <c:if test="${empty administradores}">
      <p class="vazio">Nenhum administrador cadastrado</p>
    </c:if>

    <c:if test="${not empty administradores}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/administradores" method="get">
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
              <th>Email</th>
              <th>Senha</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="administrador" items="${administradores}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(administrador.id), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(administrador.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(administrador.email), fn:toLowerCase(param.pesquisa))}">
                <tr>
                <td><c:out value="${administrador.id}"/></td>
                <td><c:out value="${administrador.nome}"/></td>
                <td><c:out value="${administrador.email}"/></td>
                <td><c:out value="${administrador.senha}"/></td>
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
