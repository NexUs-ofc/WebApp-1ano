<%--
  Created by IntelliJ IDEA.
  User: 20260140-ieg
  Date: 27/09/2026
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
  <head>
    <title>Enderecos</title>
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
    <h1>Enderecos</h1>
    <c:if test="${empty enderecos}">
      <p>Nenhum endereco cadastrado</p>
    </c:if>

    <c:if test="${not empty enderecos}">
      <table>
        <thead>
        <tr>
          <th>ID Endereço</th>
          <th>Rua</th>
          <th>Número</th>
          <th>Bairro</th>
          <th>Cidade</th>
          <th>Estado</th>
          <th>Cep</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="endereco" items="${enderecos}">
          <tr>
            <td><c:out value="${endereco.id}"/></td>
            <td><c:out value="${endereco.rua}"/></td>
            <td><c:out value="${endereco.numero}"/></td>
            <td><c:out value="${endereco.bairro}"/></td>
            <td><c:out value="${endereco.cidade}"/></td>
            <td><c:out value="${endereco.estado}"/></td>
            <td><c:out value="${endereco.cep}"/></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>

    </c:if>
  </body>
</html>
