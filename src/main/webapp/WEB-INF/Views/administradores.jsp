<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris · Administradores</title>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
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
    <a href="${pageContext.request.contextPath}/index.jsp"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-chart-histogram.svg" alt="">Análise</a>
    <a href="${pageContext.request.contextPath}/enderecos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-marker.svg" alt="">Endereço</a>
    <a href="${pageContext.request.contextPath}/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
    <a href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
    <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
    <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_dispensa</a>
    <a class="ativo" href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Administrador</small>
    </div>

    <c:if test="${empty administradores}">
      <p class="vazio">Nenhum administrador cadastrado</p>
    </c:if>

    <c:if test="${not empty administradores}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/administradores" method="get">
          <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
          <button type="submit" class="botaoPesquisar">Pesquisar</button>
          <button type="button" class="botaoFiltro" aria-label="Aplicar filtro de pesquisa">
            <span>Filtro</span>
            <span class="iconeFiltro" aria-hidden="true">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-b.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
            </span>
          </button>
          <select class="ordenacaoTabela" name="ordenacao" aria-label="Ordenar administradores">
            <option value="">Ordenar por</option>
            <option value="nome_asc">Nome: A-Z</option>
            <option value="nome_desc">Nome: Z-A</option>
            <option value="email_asc">E-mail: A-Z</option>
            <option value="id_asc">Menor ID</option>
            <option value="id_desc">Maior ID</option>
          </select>
        </form>
      </div>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_administrador</th>
              <th>nome</th>
              <th>e-mail</th>
              <th>senha</th>
              <th aria-label="Ações"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="administrador" items="${administradores}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(administrador.id), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(administrador.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(administrador.email), fn:toLowerCase(param.pesquisa))}">
                <tr>
                <td>
                  <div class="identificacaoAdministrador">
                    <c:choose>
                      <c:when test="${not empty administrador.fotoPerfil}">
                        <img class="fotoPerfil" src="${fn:escapeXml(administrador.fotoPerfil)}" alt="Foto de ${fn:escapeXml(administrador.nome)}">
                      </c:when>
                      <c:otherwise>
                        <span class="fotoPerfil fotoPerfilVazia" aria-hidden="true"></span>
                      </c:otherwise>
                    </c:choose>
                    <span><c:out value="${administrador.id}"/></span>
                  </div>
                </td>
                <td><c:out value="${administrador.nome}"/></td>
                <td><c:out value="${administrador.email}"/></td>
                <td><c:out value="${administrador.senha}"/></td>
                <td class="acoes">
                  <label class="botaoBloquear" title="Ocultar dados da linha">
                    <input type="checkbox" class="bloqueioLinha" aria-label="Ocultar dados da linha">
                    <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                  </label>
                  <button type="button" class="botaoEditar" title="Editar administrador" aria-label="Editar administrador" data-id="${administrador.id}" data-nome="${fn:escapeXml(administrador.nome)}" data-email="${fn:escapeXml(administrador.email)}" data-senha="${fn:escapeXml(administrador.senha)}" onclick="abrirPopupEdicao(this)">
                    <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                  </button>
                  <button type="button" class="botaoExcluir" title="Excluir administrador" aria-label="Excluir administrador" onclick="abrirPopupExclusao('${administrador.id}')">
                    <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
                  </button>
                </td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:if>
  </main>

  <dialog id="popupEdicao" class="popupFormulario popupFormularioAdmin">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> administrador</h2>
      <form action="${pageContext.request.contextPath}/administradores/atualizar" method="post">
        <input type="hidden" id="idAdministradorEdicao" name="id">
        <div class="camposPopupAdmin">
          <label class="campoPopupAdmin" for="nomeAdministradorEdicao"><span>nome</span><input id="nomeAdministradorEdicao" name="nome" type="text" required></label>
          <label class="campoPopupAdmin" for="emailAdministradorEdicao"><span>e-mail</span><input id="emailAdministradorEdicao" name="email" type="email" required></label>
          <label class="campoPopupAdmin" for="senhaAdministradorEdicao"><span>senha</span><input id="senhaAdministradorEdicao" name="senha" type="text" required></label>
        </div>
        <div class="acoesPopupFormulario">
          <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEdicao()">cancelar</button>
          <button type="submit" class="botaoConfirmarPopup">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <dialog id="popupExclusao" class="popupExclusao">
    <div class="barraPopupExclusao"></div>
    <div class="conteudoPopupExclusao">
      <h2><span>Excluir</span> administrador</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/administradores/deletar" method="post">
        <input type="hidden" id="idAdministradorExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <script>
    function abrirPopupEdicao(botao) {
      document.getElementById("idAdministradorEdicao").value = botao.dataset.id;
      document.getElementById("nomeAdministradorEdicao").value = botao.dataset.nome;
      document.getElementById("emailAdministradorEdicao").value = botao.dataset.email;
      document.getElementById("senhaAdministradorEdicao").value = botao.dataset.senha;
      document.getElementById("popupEdicao").showModal();
    }
    function fecharPopupEdicao() {
      document.getElementById("popupEdicao").close();
    }
    function abrirPopupExclusao(idAdministrador) {
      document.getElementById("idAdministradorExclusao").value = idAdministrador;
      document.getElementById("popupExclusao").showModal();
    }
    function fecharPopupExclusao() {
      document.getElementById("popupExclusao").close();
    }
  </script>
</body>
</html>
