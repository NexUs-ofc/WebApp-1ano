<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>administradores</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <!-- Sidebar -->
  <nav class="sidebar">
    <div class="logoSidebar">
      <div class="simboloLogo">
        <img src="${pageContext.request.contextPath}/assets/logo-ceris.svg" alt="">
      </div>
      <span class="nomeLogo">Ceris</span>
      <img class="linhaLogo" src="${pageContext.request.contextPath}/assets/logo-underline.svg" alt="">
    </div>
        <a href="${pageContext.request.contextPath}/admin/analises"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-chart-histogram.svg" alt="">Análise</a>
        <a href="${pageContext.request.contextPath}/admin/enderecos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-marker.svg" alt="">Endereço</a>
        <a href="${pageContext.request.contextPath}/admin/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
        <a href="${pageContext.request.contextPath}/admin/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
        <a href="${pageContext.request.contextPath}/admin/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
        <a href="${pageContext.request.contextPath}/admin/itens"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_despensa</a>
        <a href="${pageContext.request.contextPath}/admin/empresas"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-enterprise.svg" alt="">Empresas</a>
        <a class="ativo" href="${pageContext.request.contextPath}/admin/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
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
              <img src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="Foto de perfil">
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

  <!-- Conteudo -->
  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Administrador</small>
    </div>

    <c:if test="${empty administradores}">
      <p class="vazio">Nenhum administrador cadastrado</p>
    </c:if>

    <c:if test="${not empty administradores}">
      <!-- Pesquisa -->
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/admin/administradores" method="get">
          <input type="text" name="pesquisa" placeholder="Procurar" value="${param.pesquisa}">
          <button type="submit" class="botaoPesquisar">Pesquisar</button>
          <button type="button" class="botaoFiltro" aria-label="Aplicar filtro de pesquisa" onclick="abrirPopupFiltro()">
            <span>Filtro</span>
            <span class="iconeFiltro" aria-hidden="true">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-b.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
            </span>
          </button>
        </form>
      </div>

      <!-- Filtro -->
      <dialog id="popupFiltro" class="popupFiltro" aria-labelledby="tituloPopupFiltro">
        <div class="conteudoPopupFiltro">
          <div class="cabecalhoPopupFiltro">
            <h2 id="tituloPopupFiltro">Filtros</h2>
            <button type="button" class="botaoFecharPopupFiltro" aria-label="Fechar filtros" onclick="fecharPopupFiltro()">&times;</button>
          </div>
          <form action="${pageContext.request.contextPath}/admin/administradores" method="get">
            <div class="camposPopupFiltro">
          <div class="campoPopupFiltro">
            <span>id</span>
            <div class="intervaloPopupFiltro">
              <input name="idInicio" type="number" placeholder="M&iacute;nimo">
              <span aria-hidden="true">&ndash;</span>
              <input name="idFim" type="number" placeholder="M&aacute;ximo">
            </div>
          </div>
          <label class="campoPopupFiltro">nome
            <input name="nome" type="text" placeholder="Digite o nome">
          </label>
          <label class="campoPopupFiltro">email
            <input name="email" type="text" placeholder="Digite o email">
          </label>
            </div>
            <img class="divisorPopupFiltro" src="${pageContext.request.contextPath}/assets/popup-filter-divider.svg" alt="">
            <fieldset class="ordemPopupFiltro">
              <legend>Ordenar por</legend>
              <div class="opcoesOrdemPopupFiltro">
                <label><input name="ordenar" type="radio" value="nome">Ordenar por nome</label>
                <label><input name="ordenar" type="radio" value="email">Ordenar por email</label>
              </div>
            </fieldset>
            <div class="acoesPopupFiltro">
              <button type="reset" class="botaoLimparPopupFiltro">Limpar</button>
              <button type="submit" class="botaoAplicarPopupFiltro">Aplicar</button>
            </div>
          </form>
        </div>
      </dialog>
      <!-- Tabela -->
      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_administrador</th>
              <th>nome</th>
              <th>email</th>
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
                      <c:when test="${not empty administrador.urlFoto}">
                        <img class="fotoPerfil" src="${fn:escapeXml(administrador.urlFoto)}" alt="Foto de ${fn:escapeXml(administrador.nome)}">
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
                  <button type="button" class="botaoBloquear" title="Ocultar dados da linha" aria-label="Ocultar dados da linha" aria-pressed="false" onclick="alternarDadosLinha(this)">
                    <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                  </button>
                  <button type="button" class="botaoEditar" title="Editar administrador" aria-label="Editar administrador" data-id="${administrador.id}" data-nome="${fn:escapeXml(administrador.nome)}" data-telefone="${fn:escapeXml(administrador.telefone)}" data-email="${fn:escapeXml(administrador.email)}" data-senha="" data-foto="${fn:escapeXml(administrador.urlFoto)}" onclick="abrirPopupEdicaoAdministrador(this)">
                    <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                  </button>
                  <button type="button" class="botaoExcluir" title="Excluir administrador" aria-label="Excluir administrador" onclick="abrirPopupExclusaoAdministrador('${administrador.id}')">
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

  <!-- Edicao -->
  <dialog id="popupEdicao" class="popupFormulario popupFormularioAdmin">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> administrador</h2>
      <form action="${pageContext.request.contextPath}/admin/administradores/editar" method="post" enctype="multipart/form-data">
        <input type="hidden" id="idAdministradorEdicao" name="id">
        <div class="edicaoFotoAdministrador">
          <label for="arquivoFotoAdministrador">
            <img id="previewFotoAdministrador" class="fotoPopupAdministrador" alt="Foto do administrador">
            <span>editar foto</span>
          </label>
          <input type="file" id="arquivoFotoAdministrador" name="fotoPerfilArquivo" accept="image/png, image/jpeg, image/webp" hidden>
        </div>
        <div class="camposPopupAdmin">
          <label class="campoPopupAdmin" for="nomeAdministradorEdicao"><span>nome</span><input id="nomeAdministradorEdicao" name="nome" type="text" required></label>
          <div class="campoPopupAdmin campoTelefoneAdministrador">
            <span>telefone</span>
            <div>
              <input id="dddAdministradorEdicao" name="ddd" type="text" inputmode="numeric" maxlength="2" aria-label="DDD">
              <input id="telefoneAdministradorEdicao" name="telefone" type="tel" aria-label="Telefone">
            </div>
          </div>
          <label class="campoPopupAdmin" for="emailAdministradorEdicao"><span>e-mail</span><input id="emailAdministradorEdicao" name="email" type="email" required></label>
          <div class="campoPopupAdmin campoSenhaAdministrador">
            <label for="senhaAdministradorEdicao">senha</label>
            <span class="entradaSenhaAdministrador">
              <input id="senhaAdministradorEdicao" name="senha" type="password" autocomplete="new-password">
              <button type="button" class="botaoMostrarSenha" aria-label="Mostrar senha" aria-pressed="false" onclick="alternarVisibilidadeSenha(this)">
                <img src="<c:url value='/assets/fi-br-eye-crossed.svg'/>" alt="" aria-hidden="true">
              </button>
            </span>
          </div>
        </div>
        <div class="acoesPopupFormulario">
          <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEdicao()">cancelar</button>
          <button type="submit" class="botaoConfirmarPopup">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <!-- Exclusao -->
  <dialog id="popupExclusao" class="popupExclusao">
    <div class="barraPopupExclusao"></div>
    <div class="conteudoPopupExclusao">
      <h2><span>Excluir</span> administrador</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/admin/administradores/deletar" method="post">
        <input type="hidden" id="idAdministradorExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <!-- JavaScript -->
  <script src="${pageContext.request.contextPath}/JS/views.js"></script>
</body>
</html>

