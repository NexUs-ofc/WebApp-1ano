<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ceris - Usuários</title>
  <link href="https://fonts.googleapis.com/css2?family=Huninn&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body>
  <nav class="sidebar">
    <h2>Ceris</h2>
    <a href="${pageContext.request.contextPath}/index.jsp">Análise</a>
    <a href="${pageContext.request.contextPath}/enderecos">Endereço</a>
    <a class="ativo" href="${pageContext.request.contextPath}/usuarios">Usuário</a>
    <a href="${pageContext.request.contextPath}/categorias">Categoria</a>
    <a href="${pageContext.request.contextPath}/administradores">Administrador</a>
    <a href="${pageContext.request.contextPath}/alimentos">Alimento</a>
    <a href="${pageContext.request.contextPath}/itensDespensa">Item Despensa</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Usuários</small>
    </div>

    <c:if test="${empty usuarios}">
      <p class="vazio">Nenhum usuário cadastrado</p>
    </c:if>

    <c:if test="${not empty usuarios}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/usuarios" method="get">
          <input type="text" name="pesquisa" placeholder="Pesquisar usuário..." value="${param.pesquisa}">
          <button type="submit" class="botaoPesquisar">Pesquisar</button>
          <button type="submit" class="botaoFiltro" aria-label="Aplicar filtro de pesquisa">
            <span>Filtro</span>
            <span class="iconeFiltro" aria-hidden="true">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-b.svg'/>" alt="">
              <img src="<c:url value='/assets/filter-line-a.svg'/>" alt="">
            </span>
          </button>
        </form>
      </div>

      <div class="botaoCriar">
        <button type="button" onclick="document.getElementById('popupUsuario').showModal()">Adicionar</button>
      </div>

      <div class="popupCriar">
        <dialog id="popupUsuario">
          <h2>Novo usuário</h2>
          <form action="${pageContext.request.contextPath}/usuarios" method="post">
            <label for="nome">Nome</label>
            <input id="nome" name="nome" type="text" required>
            <label for="email">Email</label>
            <input id="email" name="email" type="email" required>
            <label for="preferenciasConsumo">Preferências de consumo</label>
            <input id="preferenciasConsumo" name="preferenciasConsumo" type="text">
            <label for="configuracaoAlerta">configurações de alerta</label>
            <input id="configuracaoAlerta" name="configuracaoAlerta" type="text">
            <div class="botoesPopup">
              <button type="submit">Salvar</button>
              <button type="button" onclick="document.getElementById('popupUsuario').close()">Cancelar</button>
            </div>
          </form>
        </dialog>
      </div>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Email</th>
              <th>Preferências</th>
              <th>Config. do alerta</th>
              <th></th>
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
