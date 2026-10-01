<%--
  Created by IntelliJ IDEA.
  User: 20260140-ieg
  Date: 28/09/2026
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
  <head>
    <title>Title</title>
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
  <h1>Usuários</h1>
  <c:if test="${empty usuarios}">
    <p>Nenhum usuário cadastrado</p>
  </c:if>
  <c:if test="${not empty usuarios}">
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
        <tr>
          <td><c:out value="${usuario.id}"/></td>
          <td><c:out value="${usuario.nome}"/></td>
          <td><c:out value="${usuario.email}"/></td>
          <td><c:out value="${usuario.preferenciasConsumo}"/></td>
          <td><c:out value="${usuario.configuracaoAlerta}"/></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  
  </body>
</html>
