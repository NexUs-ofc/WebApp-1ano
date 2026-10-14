<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Usuários</title>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <nav class="sidebar">
    <h2>Ceris</h2>
    <a href="${pageContext.request.contextPath}/index.jsp">Análise</a>
    <a href="${pageContext.request.contextPath}/enderecos">Endereço</a>
    <a class="ativo" href="${pageContext.request.contextPath}/usuarios">Usuário</a>
    <a href="${pageContext.request.contextPath}/categorias">Categoria</a>
    <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDispensa">Item Dispensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Usuários</small>
    </div>

    <c:if test="${empty usuarios}">
      <p class="vazio">Nenhum usuário cadastrado</p>
    </c:if>

    <c:if test="${not empty usuarios}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/usuarios" method="get">
          <input type="text" name="pesquisa" placeholder="Pesquisar usuário..." value="${param.pesquisa}">
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
              <th>Preferências</th>
              <th>Config. do alerta</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="usuario" items="${usuarios}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(usuario.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(usuario.email), fn:toLowerCase(param.pesquisa)) or usuario.id.toString() == param.pesquisa or fn:toLowerCase(usuario.configuracaoAlerta) == fn:toLowerCase(param.pesquisa)}">
                <tr>
                  <td><c:out value="${usuario.id}"/></td>
                  <td><c:out value="${usuario.nome}"/></td>
                  <td><c:out value="${usuario.email}"/></td>
                  <td><c:out value="${usuario.preferenciasConsumo}"/></td>
                  <td><c:out value="${usuario.configuracaoAlerta}"/></td>
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
