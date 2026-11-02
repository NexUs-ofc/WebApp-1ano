<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <link rel="preconnect" href="https://googleapis.com">
    <link rel="preconnect" href="https://gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Huninn&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Red+Hat+Display:ital,wght@0,300..900;1,300..900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
    <title>Análise</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
</head>
<body>

    <nav class="sidebar">
        <div class="logoSidebar">
          <div class="simboloLogo">
            <img src="${pageContext.request.contextPath}/assets/logo-ceris.svg" alt="">
          </div>
          <span class="nomeLogo">Ceris</span>
          <img class="linhaLogo" src="${pageContext.request.contextPath}/assets/logo-underline.svg" alt="">
        </div>
            <a class="ativo" href="${pageContext.request.contextPath}/index.jsp"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-chart-histogram.svg" alt="">Análise</a>
            <a href="${pageContext.request.contextPath}/enderecos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-marker.svg" alt="">Endereço</a>
            <a href="${pageContext.request.contextPath}/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
            <a href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
            <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
            <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_despensa</a>
            <a href="${pageContext.request.contextPath}/empresas"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-enterprise.svg" alt="">Empresas</a>
            <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
        <!-- Perfil -->
        <div class="rodapeSidebar">
          <img class="linhaPerfilSidebar" src="${pageContext.request.contextPath}/assets/profile-line.svg" alt="">
          <div class="perfilSidebar">
            <div class="fotoPerfilSidebar">
              <c:choose>
                <c:when test="${not empty sessionScope.administradorFoto}">
                  <img class="fotoAdministradorSidebar" src="<c:out value='${sessionScope.administradorFoto}'/>" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                  <img class="fotoAdministradorSidebar" src="${pageContext.request.contextPath}/assets/administrador-placeholder.jpeg" alt="Foto de perfil">
                </c:otherwise>
              </c:choose>
            </div>
            <div class="dadosPerfilSidebar">
              <strong><c:out value="${sessionScope.administradorNome}" default="Administrador"/></strong>
              <span>Administrador</span>
            </div>
          </div>
          <form action="${pageContext.request.contextPath}/logout" method="post">
            <button class="botaoSairSidebar" type="submit">
              <img src="${pageContext.request.contextPath}/assets/fi-br-exit.svg" alt="">
              Sair
            </button>
          </form>
        </div>
    </nav>
    <main class="conteudo conteudoAnalise">
      <div class="painelAnalise">
      <div class="titulo">
        <h1>Área Restrita</h1>
        <small>Início</small>
      </div>

      <!-- Boas-vindas -->
      <section class="boasVindasAnalise">
        <c:choose>
          <c:when test="${not empty sessionScope.administradorFoto}">
            <img class="fotoBoasVindasAnalise" src="<c:out value='${sessionScope.administradorFoto}'/>" alt="Foto do administrador">
          </c:when>
          <c:otherwise>
            <img class="fotoBoasVindasAnalise" src="${pageContext.request.contextPath}/assets/administrador-placeholder.jpeg" alt="Foto do administrador">
          </c:otherwise>
        </c:choose>
        <div class="textoBoasVindasAnalise">
          <span>Bem-vindo de volta</span>
          <h2>Olá <c:out value="${sessionScope.administradorNome}" default="Kauê"/> !</h2>
          <p>Acompanhe novos cadastros, estoque, categorias e atividades recentes em um único painel.</p>
        </div>
        <img class="decoracaoBoasVindasAnalise" src="${pageContext.request.contextPath}/assets/analise-decoracao.png" alt="">
      </section>

      <div class="gradeAnalise">
        <!-- Indicadores -->
        <section class="indicadoresAnalise" aria-label="Indicadores gerais">
          <article class="cartaoIndicadorAnalise">
            <h2>Novos Cadastros</h2>
            <strong>78</strong>
            <p>+12% em relação ao mês anterior</p>
          </article>
          <article class="cartaoIndicadorAnalise">
            <h2>Total Usuários</h2>
            <strong>12.040</strong>
            <p>10 administradores</p>
          </article>
          <article class="cartaoIndicadorAnalise">
            <h2>Total de itens Cadastrados</h2>
            <strong>4.502</strong>
            <p>678 itens próximos ao vencimento</p>
          </article>
        </section>

        <div class="resumoAnalise">
          <!-- Atividades recentes -->
          <section class="atividadesAnalise">
            <h2>Atividades Recentes</h2>
            <ul>
              <li>Nova empresa cadastrada: <strong>Mercado Raio de Luz</strong></li>
              <li>Relatório mensal enviado para <strong>13 empresas parceiras</strong></li>
              <li>Categoria com maior numero de itens cadastrados: <strong>Carnes</strong></li>
            </ul>
          </section>

          <!-- Gráfico -->
          <section class="graficoAnalise">
            <div class="cabecalhoGraficoAnalise">
              <h2>Empresas cadastradas por mês</h2>
              <span><i></i>Empresas</span>
            </div>
            <canvas id="graficoEmpresasAnalise" role="img" aria-label="Gráfico de empresas cadastradas por mês"></canvas>
          </section>
        </div>
      </div>
      </div>
    </main>
    <script src="${pageContext.request.contextPath}/JS/views.js"></script>
</body>
</html>
