<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <link rel="preconnect" href="https://googleapis.com">
    <link rel="preconnect" href="https://gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Huninn&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Red+Hat+Display:ital,wght@0,300..900;1,300..900&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PI do crud</title>

    <style>
        body{
            padding: 0;
            margin: 0;
            display: flex;
            font-family: 'Inter', 'Huninn', sans-serif;
            height: 100vh;
        }
        .sidebar{
        	font-family: 'Inter', 'Huninn', sans-serif;
            color: white;
            width: 295px;
            background-color: #4F2078;
            display: flex;
            flex-direction: column;
            padding-left: 20px;
            box-sizing: border-box;
        }
        .sidebar a:first-of-type {
            margin-top: 60px;
        }
        a:link, a:visited{
            color: white;
        }
        .conteudo{
            margin-left: 20px;
        }
    </style>

</head>
<body>
    <div class="sidebar">
        <h1>Ceris</h1>
        <a href="${pageContext.request.contextPath}/enderecos">Endereço</a>
        <a href="${pageContext.request.contextPath}/usuarios">Usuário</a>
        <a href="${pageContext.request.contextPath}/categorias">Categoria</a>
        <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
        <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
        <a href="${pageContext.request.contextPath}/itensDispensa">Item Dispensa</a>
    </div>
    <div class="conteudo">
        <h1>Área Restrita!</h1>
        <h4>insira codigo sla o que barras inicio LOGIN FAZ LOGIN #grrrrmondays</h4>
    </div>
</body>
</html>