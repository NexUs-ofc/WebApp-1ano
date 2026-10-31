<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>empresas</title>
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
        <a href="${pageContext.request.contextPath}/index.jsp"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-chart-histogram.svg" alt="">Análise</a>
        <a href="${pageContext.request.contextPath}/enderecos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-marker.svg" alt="">Endereço</a>
        <a href="${pageContext.request.contextPath}/usuarios"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-user.svg" alt="">Usuário</a>
        <a href="${pageContext.request.contextPath}/alimentos"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-utensils.svg" alt="">Alimento</a>
        <a href="${pageContext.request.contextPath}/categorias"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-apps.svg" alt="">Categoria</a>
        <a href="${pageContext.request.contextPath}/itensDespensa"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-bread-slice.svg" alt="">Item_despensa</a>
        <a class="ativo" href="${pageContext.request.contextPath}/empresas"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-enterprise.svg" alt="">Empresas</a>
        <a href="${pageContext.request.contextPath}/administradores"><img class="iconeSidebar" src="${pageContext.request.contextPath}/assets/fi-br-shield-check.svg" alt="">Administrador</a>
  </nav>

  <!-- Conteudo -->
  <main class="conteudo">
    <div class="titulo">
      <h1>Área Restrita</h1>
      <small>Empresa</small>
    </div>

    <!-- Pesquisa -->
    <div class="pesquisa">
      <form id="formPesquisaEmpresa">
        <input id="pesquisaEmpresa" type="text" name="pesquisa" placeholder="Procurar">
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
    <dialog id="popupFiltro" class="popupFiltro popupFiltroAlimento" aria-labelledby="tituloPopupFiltro">
      <div class="conteudoPopupFiltro">
        <div class="cabecalhoPopupFiltro">
          <h2 id="tituloPopupFiltro">Filtros</h2>
          <button type="button" class="botaoFecharPopupFiltro" aria-label="Fechar filtros" onclick="fecharPopupFiltro()">&times;</button>
        </div>
        <form onsubmit="event.preventDefault(); fecharPopupFiltro()">
          <div class="camposPopupFiltro">
            <div class="campoPopupFiltro">
              <span>id</span>
              <div class="intervaloPopupFiltro">
                <input name="idMinimo" type="number" placeholder="M&iacute;nimo">
                <span aria-hidden="true">&ndash;</span>
                <input name="idMaximo" type="number" placeholder="M&aacute;ximo">
              </div>
            </div>
            <label class="campoPopupFiltro">nome
              <input name="nome" type="text" placeholder="Digite o nome">
            </label>
            <label class="campoPopupFiltro">CNPJ
              <input name="cnpj" type="text" placeholder="Digite o CNPJ">
            </label>
            <label class="campoPopupFiltro">CEP
              <input name="cep" type="text" placeholder="Digite o CEP">
            </label>
          </div>
          <img class="divisorPopupFiltro" src="${pageContext.request.contextPath}/assets/popup-filter-divider.svg" alt="">
          <fieldset class="ordemPopupFiltro">
            <legend>Ordenar por</legend>
            <div class="opcoesOrdemPopupFiltro">
              <label><input name="ordenar" type="radio" value="nome">Ordenar por nome</label>
              <label><input name="ordenar" type="radio" value="cnpj">Ordenar por CNPJ</label>
            </div>
          </fieldset>
          <div class="acoesPopupFiltro">
            <button type="reset" class="botaoLimparPopupFiltro">Limpar</button>
            <button type="submit" class="botaoAplicarPopupFiltro">Aplicar</button>
          </div>
        </form>
      </div>
    </dialog>

    <!-- Cadastro -->
    <button type="button" class="botaoCriar" onclick="abrirPopupEmpresa()">Adicionar<img class="iconeAdicionar" src="${pageContext.request.contextPath}/assets/fi-br-add.svg" alt="" aria-hidden="true"></button>

    <dialog id="popupEmpresa" class="popupFormulario popupFormularioMedio">
      <div class="barraPopupFormulario"></div>
      <div class="conteudoPopupFormulario">
        <h2><span>Criar</span> empresa</h2>
        <form onsubmit="event.preventDefault(); fecharPopupEmpresa()">
          <div class="gradeCamposPopup">
            <label class="campoPopup" for="nomeEmpresa">Nome<input id="nomeEmpresa" name="nome" type="text" required></label>
            <label class="campoPopup" for="cnpjEmpresa">CNPJ<input id="cnpjEmpresa" name="cnpj" type="text" required></label>
            <label class="campoPopup" for="cepEmpresa">CEP<input id="cepEmpresa" name="cep" type="text" required></label>
            <label class="campoPopup" for="telefoneEmpresa">Telefone<input id="telefoneEmpresa" name="telefone" type="tel" required></label>
          </div>
          <div class="acoesPopupFormulario">
            <button type="button" class="botaoCancelarPopup" onclick="fecharPopupEmpresa()">cancelar</button>
            <button type="submit" class="botaoConfirmarPopup">confirmar</button>
          </div>
        </form>
      </div>
    </dialog>

    <!-- Tabela -->
    <div class="cartao">
      <table>
        <thead>
          <tr>
            <th>id_empresa</th>
            <th>nome</th>
            <th>CNPJ</th>
            <th>CEP</th>
            <th>telefone</th>
            <th aria-label="Ações"></th>
          </tr>
        </thead>
        <tbody id="tabelaEmpresas">
          <tr>
            <td>001</td>
            <td>Atacadão</td>
            <td>00.000.000/0001-91</td>
            <td>01001-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="001" data-nome="Atacadão" data-cnpj="00.000.000/0001-91" data-cep="01001-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('001')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>002</td>
            <td>Carrefour</td>
            <td>00.000.001/0001-50</td>
            <td>04101-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="002" data-nome="Carrefour" data-cnpj="00.000.001/0001-50" data-cep="04101-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('002')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>003</td>
            <td>Swift</td>
            <td>00.000.002/0001-14</td>
            <td>04520-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="003" data-nome="Swift" data-cnpj="00.000.002/0001-14" data-cep="04520-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('003')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>004</td>
            <td>São Vicente</td>
            <td>00.000.003/0001-82</td>
            <td>05422-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="004" data-nome="São Vicente" data-cnpj="00.000.003/0001-82" data-cep="05422-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('004')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>005</td>
            <td>Assaí Atacadista</td>
            <td>00.000.004/0001-40</td>
            <td>01310-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="005" data-nome="Assaí Atacadista" data-cnpj="00.000.004/0001-40" data-cep="01310-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('005')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>006</td>
            <td>Pão de Açúcar</td>
            <td>00.000.005/0001-05</td>
            <td>01504-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="006" data-nome="Pão de Açúcar" data-cnpj="00.000.005/0001-05" data-cep="01504-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('006')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>007</td>
            <td>Seara</td>
            <td>00.000.006/0001-71</td>
            <td>01305-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="007" data-nome="Seara" data-cnpj="00.000.006/0001-71" data-cep="01305-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('007')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>008</td>
            <td>Friboi</td>
            <td>00.000.007/0001-30</td>
            <td>04045-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="008" data-nome="Friboi" data-cnpj="00.000.007/0001-30" data-cep="04045-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('008')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>009</td>
            <td>Tenda Atacado</td>
            <td>00.000.008/0001-03</td>
            <td>03310-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="009" data-nome="Tenda Atacado" data-cnpj="00.000.008/0001-03" data-cep="03310-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('009')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
          <tr>
            <td>010</td>
            <td>Dia Supermercados</td>
            <td>00.000.009/0001-62</td>
            <td>02010-000</td>
            <td>11987654321</td>
            <td class="acoes">
              <button type="button" class="botaoEditar" title="Editar empresa" aria-label="Editar empresa" data-id="010" data-nome="Dia Supermercados" data-cnpj="00.000.009/0001-62" data-cep="02010-000" data-telefone="11987654321" onclick="abrirPopupEdicao(this)">
                <img src="<c:url value='/assets/fi-br-pencil.svg'/>" alt="" aria-hidden="true">
              </button>
              <button type="button" class="botaoExcluir" title="Excluir empresa" aria-label="Excluir empresa" onclick="abrirPopupExclusao('010')">
                <img src="<c:url value='/assets/fi-br-trash.svg'/>" alt="" aria-hidden="true">
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </main>

  <!-- Edicao -->
  <dialog id="popupEdicao" class="popupFormulario popupFormularioMedio">
    <div class="barraPopupFormulario"></div>
    <div class="conteudoPopupFormulario">
      <h2><span>Editar</span> empresa</h2>
      <form onsubmit="event.preventDefault(); fecharPopupEdicao()">
        <input type="hidden" id="idEmpresaEdicao" name="id">
        <div class="gradeCamposPopup">
          <label class="campoPopup" for="nomeEmpresaEdicao">Nome<input id="nomeEmpresaEdicao" name="nome" type="text" required></label>
          <label class="campoPopup" for="cnpjEmpresaEdicao">CNPJ<input id="cnpjEmpresaEdicao" name="cnpj" type="text" required></label>
          <label class="campoPopup" for="cepEmpresaEdicao">CEP<input id="cepEmpresaEdicao" name="cep" type="text" required></label>
          <label class="campoPopup" for="telefoneEmpresaEdicao">Telefone<input id="telefoneEmpresaEdicao" name="telefone" type="tel" required></label>
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
      <h2><span>Excluir</span> empresa</h2>
      <p>Tem certeza de que deseja <strong>excluir</strong> este item?
      <br>Essa ação não poderá ser desfeita.</p>
      <form onsubmit="event.preventDefault(); fecharPopupExclusao()">
        <input type="hidden" id="idEmpresaExclusao" name="id">
        <div class="acoesPopupExclusao">
          <button type="button" class="botaoCancelarExclusao" onclick="fecharPopupExclusao()">cancelar</button>
          <button type="submit" class="botaoConfirmarExclusao">confirmar</button>
        </div>
      </form>
    </div>
  </dialog>

  <!-- JavaScript -->
  <script>
    function abrirPopupFiltro() {
      document.getElementById("popupFiltro").showModal();
    }
    function fecharPopupFiltro() {
      document.getElementById("popupFiltro").close();
    }
    function abrirPopupEmpresa() {
      document.getElementById("popupEmpresa").showModal();
    }
    function fecharPopupEmpresa() {
      document.getElementById("popupEmpresa").close();
    }
    function abrirPopupEdicao(botao) {
      document.getElementById("idEmpresaEdicao").value = botao.dataset.id;
      document.getElementById("nomeEmpresaEdicao").value = botao.dataset.nome;
      document.getElementById("cnpjEmpresaEdicao").value = botao.dataset.cnpj;
      document.getElementById("cepEmpresaEdicao").value = botao.dataset.cep;
      document.getElementById("telefoneEmpresaEdicao").value = botao.dataset.telefone;
      document.getElementById("popupEdicao").showModal();
    }
    function fecharPopupEdicao() {
      document.getElementById("popupEdicao").close();
    }
    function abrirPopupExclusao(idEmpresa) {
      document.getElementById("idEmpresaExclusao").value = idEmpresa;
      document.getElementById("popupExclusao").showModal();
    }
    function fecharPopupExclusao() {
      document.getElementById("popupExclusao").close();
    }
    document.getElementById("formPesquisaEmpresa").addEventListener("submit", function (event) {
      event.preventDefault();
      const pesquisa = document.getElementById("pesquisaEmpresa").value.toLowerCase();
      document.querySelectorAll("#tabelaEmpresas tr").forEach(function (linha) {
        linha.hidden = !linha.textContent.toLowerCase().includes(pesquisa);
      });
    });
  </script>
</body>
</html>
