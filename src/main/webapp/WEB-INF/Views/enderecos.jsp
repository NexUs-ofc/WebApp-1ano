<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>enderecos</title>
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
    <a class="ativo" href="${pageContext.request.contextPath}/enderecos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-marker.svg" alt="">Endereço</a>
    <a href="${pageContext.request.contextPath}/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
    <a href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
    <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
    <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_dispensa</a>
    <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Endereço</small>
    </div>

    <c:if test="${empty enderecos}">
      <p class="vazio">Nenhum endereço cadastrado</p>
    </c:if>
    <c:if test="${not empty enderecos}">
      <div class="pesquisa">
        <form action="${pageContext.request.contextPath}/enderecos" method="get">
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

      <button type="button" class="botaoCriar" onclick="abrirPopupEndereco()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>
      <dialog id="popupEndereco" class="popupFormulario popupFormularioGrande">
        <div class="barraPopupFormulario"></div>
        <div class="conteudoPopupFormulario">
          <h2><span>Criar</span> endereço</h2>
          <form action="${pageContext.request.contextPath}/enderecos/insert" method="post">
            <div class="gradeCamposPopup">
              <label class="campoPopup" for="ruaEndereco">Rua<input id="ruaEndereco" name="rua" type="text" required></label>
              <label class="campoPopup" for="numeroEndereco">Número<input id="numeroEndereco" name="numero" type="number" required></label>
              <label class="campoPopup" for="bairroEndereco">Bairro<input id="bairroEndereco" name="bairro" type="text" required></label>
              <label class="campoPopup" for="cidadeEndereco">Cidade<input id="cidadeEndereco" name="cidade" type="text" required></label>
              <label class="campoPopup" for="estadoEndereco">Estado<input id="estadoEndereco" name="estado" type="text" maxlength="2" required></label>
              <label class="campoPopup" for="cepEndereco">CEP<input id="cepEndereco" name="cep" type="text" required><small class="ajudaCampoPopup">Formato: 00000-000</small></label>
            </div>
            <div class="acoesPopupFormulario">
              <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEndereco()">cancelar</button>
              <button type="submit" class="botaoConfirmarPopup">confirmar</button>
            </div>
          </form>
        </div>
      </dialog>

      <div class="cartao">
        <table>
          <thead>
            <tr>
              <th>id_endereco</th>
              <th>rua</th>
              <th>numero</th>
              <th>bairro</th>
              <th>cidade</th>
              <th>estado</th>
              <th>cep</th>
              <th aria-label="Ações"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="endereco" items="${enderecos}">
              <c:if test="${empty param.pesquisa or fn:contains(fn:toLowerCase(endereco.rua), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.bairro), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.cidade), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.estado), fn:toLowerCase(param.pesquisa)) or fn:contains(fn:toLowerCase(endereco.cep), fn:toLowerCase(param.pesquisa)) or endereco.id.toString() == param.pesquisa}">
                <tr>
                  <td><c:out value="${endereco.id}"/></td>
                  <td><c:out value="${endereco.rua}"/></td>
                  <td><c:out value="${endereco.numero}"/></td>
                  <td><c:out value="${endereco.bairro}"/></td>
                  <td><c:out value="${endereco.cidade}"/></td>
                  <td><c:out value="${endereco.estado}"/></td>
                  <td><c:out value="${endereco.cep}"/></td>
                  <td class="acoes">
                    <label class="botaoBloquear" title="Ocultar dados da linha">
                      <input type="checkbox" class="bloqueioLinha" aria-label="Ocultar dados da linha">
                      <img src="<c:url value='/assets/fi-br-eye.svg'/>" alt="" aria-hidden="true">
                    </label>
                    <button type="button" class="botaoEditar" title="Editar endereço" aria-label="Editar endereço" data-id="${endereco.id}" data-rua="${fn:escapeXml(endereco.rua)}" data-numero="${endereco.numero}" data-bairro="${fn:escapeXml(endereco.bairro)}" data-cidade="${fn:escapeXml(endereco.cidade)}" data-estado="${fn:escapeXml(endereco.estado)}" data-cep="${fn:escapeXml(endereco.cep)}" onclick="abrirPopupEdicao(this)">
                      <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
                    </button>
                    <button type="button" class="botaoExcluir" title="Excluir endereço" aria-label="Excluir endereço" onclick="abrirPopupExclusao('${endereco.id}')">
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

  <dialog id="popupEdicao" class="popupFormulario popupFormularioGrande">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> endereço</h2>
      <form action="${pageContext.request.contextPath}/enderecos/atualizar" method="post">
        <input type="hidden" id="idEnderecoEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="ruaEnderecoEdicao">Rua<input id="ruaEnderecoEdicao" name="rua" type="text" required></label>
          <label class="campoPopup" for="numeroEnderecoEdicao">Número<input id="numeroEnderecoEdicao" name="numero" type="number" required></label>
          <label class="campoPopup" for="bairroEnderecoEdicao">Bairro<input id="bairroEnderecoEdicao" name="bairro" type="text" required></label>
          <label class="campoPopup" for="cidadeEnderecoEdicao">Cidade<input id="cidadeEnderecoEdicao" name="cidade" type="text" required></label>
          <label class="campoPopup" for="estadoEnderecoEdicao">Estado<input id="estadoEnderecoEdicao" name="estado" type="text" maxlength="2" required></label>
          <label class="campoPopup" for="cepEnderecoEdicao">CEP<input id="cepEnderecoEdicao" name="cep" type="text" required><small class="ajudaCampoPopup">Formato: 00000-000</small></label>
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
      <h2><span>Excluir</span> endereço</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form action="${pageContext.request.contextPath}/enderecos/deletar" method="post">
        <input type="hidden" id="idEnderecoExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <script>
    function abrirPopupEndereco() {
      document.getElementById("popupEndereco").showModal();
    }
    function fecharPopupEndereco() {
      document.getElementById("popupEndereco").close();
    }
    function abrirPopupEdicao(botao) {
      document.getElementById("idEnderecoEdicao").value = botao.dataset.id;
      document.getElementById("ruaEnderecoEdicao").value = botao.dataset.rua;
      document.getElementById("numeroEnderecoEdicao").value = botao.dataset.numero;
      document.getElementById("bairroEnderecoEdicao").value = botao.dataset.bairro;
      document.getElementById("cidadeEnderecoEdicao").value = botao.dataset.cidade;
      document.getElementById("estadoEnderecoEdicao").value = botao.dataset.estado;
      document.getElementById("cepEnderecoEdicao").value = botao.dataset.cep;
      document.getElementById("popupEdicao").showModal();
    }
    function fecharPopupEdicao() {
      document.getElementById("popupEdicao").close();
    }
    function abrirPopupExclusao(idEndereco) {
      document.getElementById("idEnderecoExclusao").value = idEndereco;
      document.getElementById("popupExclusao").showModal();
    }
    function fecharPopupExclusao() {
      document.getElementById("popupExclusao").close();
    }
  </script>
</body>
</html>
