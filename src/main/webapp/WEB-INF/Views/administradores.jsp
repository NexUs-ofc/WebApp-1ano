<%--
  Created by IntelliJ IDEA.
  User: 20260140-ieg
  Date: 28/09/2026
  Time: 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
  <head>
    <title>Administradores</title>
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
  <h1>Administradores</h1>
  <c:if test="${empty administradores}">
    <p>Nenhum administrador cadastrado</p>
  </c:if>
  <c:if test="${not empty administradores}">
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
        <tr>
          <td><c:out value="${administrador.id}"/></td>
          <td><c:out value="${administrador.nome}"/></td>
          <td><c:out value="${administrador.email}"/></td>
          <td><c:out value="${administrador.senha}"/></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  </body>
</html>
