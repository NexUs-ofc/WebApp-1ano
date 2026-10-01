<%--
  Created by IntelliJ IDEA.
  User: 20260140-ieg
  Date: 28/09/2026
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
  <head>
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
  <h1>Itens da dispensa</h1>
  <c:if test="${empty itensDispensa}">
    <p>Nenhum item cadastrado</p>
  </c:if>
  <c:if test="${not empty itensDispensa}">
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>ID do Usuário</th>
        <th>ID do Alimento</th>
        <th>Quantidade</th>
        <th>Validade</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="itemDispensa" items="${itensDispensa}">
        <tr>
          <td><c:out value="${itemDispensa.id}"/></td>
          <td><c:out value="${itemDispensa.idUsuario}"/></td>
          <td><c:out value="${itemDispensa.idAlimento}"/></td>
          <td><c:out value="${itemDispensa.quantidade}"/></td>
          <td><c:out value="${itemDispensa.validade}"/></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  </body>
</html>
