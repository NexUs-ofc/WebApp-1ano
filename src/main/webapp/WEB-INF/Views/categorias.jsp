<%--
  Created by IntelliJ IDEA.
  User: 20260140-ieg
  Date: 28/09/2026
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
  </head>
  <title>Itens da Dispensa</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 40px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      padding: 10px;
      border: 1px solid #cccccc;
    }
    th {
      background-color: #eeeeee;
    }
  </style>
  </head>

  <body>
  <h1>Categorias</h1>
  <c:if test="${empty categorias}">
    <p>Nenhuma categoria encontarda</p>
  </c:if>
  <c:if test="${not empty categorias}">
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
        <tr>
          <td><c:out value="${categoria.id}"/></td>
          <td><c:out value="${categoria.nome}"/></td>
          <td><c:out value="${categoria.descricao}"/></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  </body>
</html>
