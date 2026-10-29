<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>usuarios</title>
  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
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
    <a class="ativo" href="${pageContext.request.contextPath}/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
    <a href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
    <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
    <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_dispensa</a>
    <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Usuário</small>
    </div>

    <c:if test="${empty usuarios}">
      <p class="vazio">Nenhum usuário cadastrado</p>
    </c:if>

    <c:if test="${not empty usuarios}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/usuarios" method="get">
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
        </form>
      </div>

      <button type="button" class="botaoCriar" onclick="abrirPopupUsuario()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>

      <dialog id="popupUsuario" class="popupFormulario popupFormularioMedio">
          <div class="barraPopupFormulario"></div>
          <div class="conteudoPopupFormulario">
          <h2><span>Criar</span> usuário</h2>
          <form action="${pageContext.request.contextPath}/usuarios" method="post">
            <div class="gradeCamposPopup">
              <label class="campoPopup" for="nome">Nome<input id="nome" name="nome" type="text" required></label>
              <label class="campoPopup" for="email">E-mail<input id="email" name="email" type="email" required></label>
              <label class="campoPopup" for="preferenciasConsumo">Preferências<input id="preferenciasConsumo" name="preferenciasConsumo" type="text"></label>
              <label class="campoPopup" for="configuracaoAlerta">Alerta<input id="configuracaoAlerta" name="configuracaoAlerta" type="text"></label>
            </div>
            <div class="acoesPopupFormulario">
              <button type="button" class="botaoCancelarPopup" onclick="fecharPopupUsuario()">cancelar</button>
              <button type="submit" class="botaoConfirmarPopup">confirmar</button>
            </div>
          </form>
          </div>
      </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_usuario</th>
              <th>nome</th>
              <th>email</th>
              <th>preferencias</th>
              <th>alerta</th>
              <th aria-label="Ações"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="usuario" items="${usuarios}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(usuario.nome), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(usuario.email), fn:toLowerCase(param.pesquisa)) or usuario.id.toString() == param.pesquisa or fn:toLowerCase(usuario.configuracaoAlerta) == fn:toLowerCase(param.pesquisa)}">
                <tr>
                  <td><c:out value="${usuario.id}"/></td>
                  <td><c:out value="${usuario.nome}"/></td>
                  <td><c:out value="${usuario.email}"/></td>
                  <td><c:out value="${usuario.preferenciasConsumo}"/></td>
                  <td><c:out value="${usuario.configuracaoAlerta}"/></td>
                  <td class="acoes">
                    <label class="botaoBloquear" title="Ocultar dados da linha">
                      <input type="checkbox" class="bloqueioLinha" aria-label="Ocultar dados da linha">
                      <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                    </label>
                    <button type="button" class="botaoEditar" title="Editar usuário" aria-label="Editar usuário" data-id="${usuario.id}" data-nome="${fn:escapeXml(usuario.nome)}" data-email="${fn:escapeXml(usuario.email)}" data-preferencias="${fn:escapeXml(usuario.preferenciasConsumo)}" data-alerta="${fn:escapeXml(usuario.configuracaoAlerta)}" onclick="abrirPopupEdicao(this)">
                      <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                    </button>
                    <button type="button" class="botaoExcluir" title="Excluir usuário" aria-label="Excluir usuário" onclick="abrirPopupExclusao('${usuario.id}')"><img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="ícone de lixeira" aria-hidden="true"></button>
                  </td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:if>
  </main>

  <dialog id="popupEdicao" class="popupFormulario popupFormularioMedio">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> usuário</h2>
      <form action="${pageContext.request.contextPath}/usuarios/atualizar" method="post">
        <input type="hidden" id="idUsuarioEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="nomeUsuarioEdicao">Nome<input id="nomeUsuarioEdicao" name="nome" type="text" required></label>
          <label class="campoPopup" for="emailUsuarioEdicao">E-mail<input id="emailUsuarioEdicao" class="textoHuninn" name="email" type="email" required></label>
          <label class="campoPopup" for="preferenciasUsuarioEdicao">Preferências<input id="preferenciasUsuarioEdicao" name="preferenciasConsumo" type="text"></label>
          <label class="campoPopup" for="alertaUsuarioEdicao">Alerta<input id="alertaUsuarioEdicao" name="configuracaoAlerta" type="text"></label>
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
      <h2><span>Excluir</span> usuário</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/usuarios/deletar" method="post">
        <input type="hidden" id="idUsuarioExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <script>
    function abrirPopupUsuario() {
      document.getElementById("popupUsuario").showModal();
    }
    function fecharPopupUsuario() {
      document.getElementById("popupUsuario").close();
    }
    function abrirPopupEdicao(botao) {
      document.getElementById("idUsuarioEdicao").value = botao.dataset.id;
      document.getElementById("nomeUsuarioEdicao").value = botao.dataset.nome;
      document.getElementById("emailUsuarioEdicao").value = botao.dataset.email;
      document.getElementById("preferenciasUsuarioEdicao").value = botao.dataset.preferencias;
      document.getElementById("alertaUsuarioEdicao").value = botao.dataset.alerta;
      document.getElementById("popupEdicao").showModal();
    }
    function fecharPopupEdicao() {
      document.getElementById("popupEdicao").close();
    }
    function abrirPopupExclusao(idUsuario) {
      document.getElementById("idUsuarioExclusao").value = idUsuario;
      document.getElementById("popupExclusao").showModal();
    }
    function fecharPopupExclusao() {
      document.getElementById("popupExclusao").close();
    }
  </script>

</body>
</html>
