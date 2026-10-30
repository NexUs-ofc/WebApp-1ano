<%--
  Created by IntelliJ IDEA.
  User: 20260024-ieg
  Date: 19/10/2026
  Time: 00:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erro</title>
</head>
<body>
<div>Ocorreu um erro ao processar a solicitação</div>

<!-- Mensagem para o usuario -->
<div>
    ${requestScope.erro != null ? requestScope.erro : "Ocorreu um erro inesperado."}
</div>

<!-- Código de erro técnico -->
<div>
    Código de referência: ${requestScope.cid != null ? requestScope.cid : "N/A"}
</div>

<button onclick="window.history.back()">Voltar para a página anterior</button>

</body>
</html>
