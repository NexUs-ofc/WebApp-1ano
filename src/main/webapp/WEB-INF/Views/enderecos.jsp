<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Endereços</title>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <nav class="sidebar">
    <h2>Ceris</h2>
    <a href="${pageContext.request.contextPath}/index.jsp">Análise</a>
    <a class="ativo" href="${pageContext.request.contextPath}/enderecos">Endereço</a>
    <a href="${pageContext.request.contextPath}/usuarios">Usuário</a>
    <a href="${pageContext.request.contextPath}/categorias">Categoria</a>
    <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDispensa">Item Dispensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Endereços</small>
    </div>

    <c:if test="${empty enderecos}">
      <p class="vazio">Nenhum endereço cadastrado</p>
    </c:if>

    <c:if test="${not empty enderecos}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/enderecos" method="get">
          <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
          <button type="submit">Pesquisar</button>
        </form>
      </div>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>ID Endereço</th>
              <th>Rua</th>
              <th>Número</th>
              <th>Bairro</th>
              <th>Cidade</th>
              <th>Estado</th>
              <th>CEP</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="endereco" items="${enderecos}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(endereco.id), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.rua), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.numero), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.bairro), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.cidade), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.estado), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.cep), fn:toLowerCase(param.pesquisa))}">
                <tr>
                <td><c:out value="${endereco.id}"/></td>
                <td><c:out value="${endereco.rua}"/></td>
                <td><c:out value="${endereco.numero}"/></td>
                <td><c:out value="${endereco.bairro}"/></td>
                <td><c:out value="${endereco.cidade}"/></td>
                <td><c:out value="${endereco.estado}"/></td>
                <td><c:out value="${endereco.cep}"/></td>
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
