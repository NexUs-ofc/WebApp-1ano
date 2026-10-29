<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="codigoErro" value="${requestScope['jakarta.servlet.error.status_code']}"/>

<c:if test="${empty codigoErro}">
  <c:set var="codigoErro" value="500"/>
</c:if>

<c:set var="tituloErro" value="Ops! algo deu errado"/>

<c:choose>
  <c:when test="${codigoErro == 404}">
    <c:set var="explicacaoErro" value="Não encontramos a página que você procura."/>
  </c:when>
  <c:when test="${codigoErro == 403}">
    <c:set var="explicacaoErro" value="Você não possui permissão para acessar esta página."/>
  </c:when>
  <c:when test="${codigoErro == 500}">
    <c:set var="explicacaoErro" value="Parece que encontramos um erro ao carregar a página."/>
  </c:when>
  <c:otherwise>
    <c:set var="explicacaoErro" value="Não foi possível concluir a sua solicitação."/>
  </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Erro ${codigoErro} | Ceris</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&family=Montserrat:wght@400;600&family=Poppins:wght@500;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorpage.css">
</head>
<body>
  <main class="Errorpage">
    <a class="logoErrorpage" href="${pageContext.request.contextPath}/index.jsp" aria-label="Voltar ao início">
      <img src="${pageContext.request.contextPath}/assets/errorpage-logo-symbol.svg" alt="" width="36" height="36">
      <span>Ceris</span>
    </a>

    <div class="decoracaoErrorpage decoracaoErrorpageCima">
      <span class="decoracaoEscuraErrorpage"></span>
      <span class="decoracaoClaraErrorpage"></span>
    </div>

    <div class="decoracaoErrorpage decoracaoErrorpageBaixa">
      <span class="decoracaoEscuraErrorpage"></span>
      <span class="decoracaoClaraErrorpage"></span>
    </div>

    <div class="conteudoErrorpage">
      <section class="ilustracaoErrorpage" aria-hidden="true">
        <img class="ceritaErrorpage" src="${pageContext.request.contextPath}/assets/cerita-errorpage.svg" alt="" width="452" height="603">
      </section>

      <section class="mensagemErrorpage">
        <p class="codigoErrorpage"><c:out value="${codigoErro}"/></p>
        <h1><c:out value="${tituloErro}"/></h1>
        <p class="explicacaoErrorpage"><c:out value="${explicacaoErro}"/></p>

        <div class="acoesErrorpage">
          <button class="botaoErrorpage botaoTentarNovamenteErrorpage" type="button" onclick="window.location.reload()">
            <img src="${pageContext.request.contextPath}/assets/errorpage-refresh.svg" alt="" width="23" height="23">
            <span>Tentar novamente</span>
          </button>

          <a class="botaoErrorpage botaoVoltarInicioErrorpage" href="${pageContext.request.contextPath}/index.jsp">
            <img src="${pageContext.request.contextPath}/assets/errorpage-home.svg" alt="" width="23" height="23">
            <span>Voltar ao início</span>
          </a>
        </div>

        <p class="orientacaoSuporteErrorpage">Se o problema continuar, fale com nosso suporte.</p>

        <a class="contatoSuporteErrorpage" href="mailto:nexus.inter2026@gmail.com">
          <img src="${pageContext.request.contextPath}/assets/errorpage-support.svg" alt="" width="20" height="20">
          <span>Falar com suporte</span>
        </a>
      </section>
    </div>
  </main>
</body>
</html>
