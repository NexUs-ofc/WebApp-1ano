// ===== POP-UPS DAS TELAS DO CRUD =====
// Esse arquivo tem todas as funções usadas pelas JSPs da área restrita.
// Resumindo o que ele faz:
//   - abre e fecha os pop-ups de filtro, cadastro, edição e exclusão
//   - coloca no formulário de edição os dados da linha escolhida
//   - guarda no formulário de exclusão o id do registro escolhido
//   - mostra uma prévia da foto do administrador antes do envio

// Abre o pop-up de filtros da página atual.
// O showModal deixa o dialog visível e vai impedir a interação com o restante da página.
// Ajusta somente as distâncias verticais da sidebar e mantém os elementos no tamanho original.
function ajustarEspacosSidebar() {
  const sidebar = document.querySelector(".sidebar");

  if (!sidebar) {
    return;
  }

  const altura = sidebar.clientHeight;
  const proporcao = Math.max(0, Math.min(1, (altura - 652) / 347));

  sidebar.style.setProperty("--inicioBotoesSidebar", `${71 + 203 * proporcao}px`);
  sidebar.style.setProperty("--espacoBotoesSidebar", `${9 * proporcao}px`);
}

window.addEventListener("resize", ajustarEspacosSidebar);
document.addEventListener("DOMContentLoaded", ajustarEspacosSidebar);

function abrirPopupFiltro() {
  document.getElementById("popupFiltro").showModal();
}

// Fecha o pop-up de filtros da página atual.
function fecharPopupFiltro() {
  document.getElementById("popupFiltro").close();
}

// Fecha o pop-up de edição.
// Cada JSP possui o próprio dialog com esse mesmo id.
function fecharPopupEdicao() {
  document.getElementById("popupEdicao").close();
}

// Fecha o pop-up de confirmação de exclusão.
function fecharPopupExclusao() {
  document.getElementById("popupExclusao").close();
}

// Guarda o conteúdo das células enquanto os dados da linha estão ocultos.
const conteudoLinhasOcultas = new WeakMap();

// Oculta ou mostra os dados da linha sem depender de checkbox ou seletor CSS.
function alternarDadosLinha(botao) {
  const linha = botao.closest("tr");
  const celulas = Array.from(linha.querySelectorAll("td:not(.acoes)"));
  const conteudoOriginal = conteudoLinhasOcultas.get(linha);

  if (conteudoOriginal) {
    celulas.forEach((celula, indice) => {
      celula.replaceChildren(...conteudoOriginal[indice]);
    });
    conteudoLinhasOcultas.delete(linha);
    botao.setAttribute("aria-label", "Ocultar dados da linha");
    botao.setAttribute("title", "Ocultar dados da linha");
    botao.setAttribute("aria-pressed", "false");
    return;
  }

  conteudoLinhasOcultas.set(linha, celulas.map(celula => Array.from(celula.childNodes)));
  celulas.forEach(celula => celula.replaceChildren("********"));
  botao.setAttribute("aria-label", "Mostrar dados da linha");
  botao.setAttribute("title", "Mostrar dados da linha");
  botao.setAttribute("aria-pressed", "true");
}


/* ---------------------------------------------------------------
   ADMINISTRADOR
   --------------------------------------------------------------- */

// Abre a edição do administrador selecionado.
// O botão da tabela guarda os dados do registro em atributos data.
function abrirPopupEdicaoAdministrador(botao) {
  // Deixa apenas os números do telefone para separar o DDD do restante.
  const telefone = (botao.dataset.telefone || "").replace(/\D/g, "");
  const foto = botao.dataset.foto || "";
  const previewFoto = document.getElementById("previewFotoAdministrador");

  // Preenche os campos do formulário com os dados da linha selecionada.
  document.getElementById("idAdministradorEdicao").value = botao.dataset.id;
  document.getElementById("nomeAdministradorEdicao").value = botao.dataset.nome;
  document.getElementById("dddAdministradorEdicao").value = telefone.length > 10 ? telefone.slice(0, 2) : "";
  document.getElementById("telefoneAdministradorEdicao").value = telefone.length > 10 ? telefone.slice(2) : telefone;
  document.getElementById("emailAdministradorEdicao").value = botao.dataset.email;
  document.getElementById("senhaAdministradorEdicao").value = botao.dataset.senha;
  document.getElementById("arquivoFotoAdministrador").value = "";

  const botaoMostrarSenha = document.querySelector(".botaoMostrarSenha");
  document.getElementById("senhaAdministradorEdicao").type = "password";
  botaoMostrarSenha.setAttribute("aria-label", "Mostrar senha");
  botaoMostrarSenha.setAttribute("aria-pressed", "false");
  botaoMostrarSenha.querySelector("img").src = botaoMostrarSenha.querySelector("img").src.replace("fi-br-eye.svg", "fi-br-eye-crossed.svg");

  // Se o administrador já tiver foto, mostra a imagem recebida do banco.
  // Caso contrário, mantém o espaço reservado vazio.
  if (foto) {
    previewFoto.src = foto;
    previewFoto.classList.remove("fotoPopupAdministradorVazia");
  } else {
    previewFoto.removeAttribute("src");
    previewFoto.classList.add("fotoPopupAdministradorVazia");
  }

  document.getElementById("popupEdicao").showModal();
}

// Alterna a senha do administrador entre oculta e visível.
function alternarVisibilidadeSenha(botao) {
  const campoSenha = document.getElementById("senhaAdministradorEdicao");
  const icone = botao.querySelector("img");
  const mostrarSenha = campoSenha.type === "password";

  campoSenha.type = mostrarSenha ? "text" : "password";
  botao.setAttribute("aria-label", mostrarSenha ? "Ocultar senha" : "Mostrar senha");
  botao.setAttribute("aria-pressed", String(mostrarSenha));
  icone.src = mostrarSenha
      ? icone.src.replace("fi-br-eye-crossed.svg", "fi-br-eye.svg")
      : icone.src.replace("fi-br-eye.svg", "fi-br-eye-crossed.svg");
}

// Procura o campo de arquivo da foto.
// Esse campo só existe na JSP de administrador, por isso ele pode ser null nas outras páginas.
const arquivoFotoAdministrador = document.getElementById("arquivoFotoAdministrador");

// Só adiciona o evento se o campo realmente existir na página atual.
if (arquivoFotoAdministrador) {
  arquivoFotoAdministrador.addEventListener("change", function () {
    const arquivo = this.files[0];

    // Cria uma URL temporária para mostrar a imagem antes de enviar o formulário.
    if (arquivo) {
      const previewFoto = document.getElementById("previewFotoAdministrador");
      previewFoto.src = URL.createObjectURL(arquivo);
      previewFoto.classList.remove("fotoPopupAdministradorVazia");
    }
  });
}

// Abre a confirmação de exclusão e guarda o id do administrador escolhido.
function abrirPopupExclusaoAdministrador(idAdministrador) {
  document.getElementById("idAdministradorExclusao").value = idAdministrador;
  document.getElementById("popupExclusao").showModal();
}


/* ---------------------------------------------------------------
   ALIMENTO
   --------------------------------------------------------------- */

// Abre o formulário de cadastro de alimento.
function abrirPopupAlimento() {
  document.getElementById("popupAlimento").showModal();
}

// Fecha o formulário de cadastro de alimento.
function fecharPopupAlimento() {
  document.getElementById("popupAlimento").close();
}

// Preenche o formulário com os dados do alimento selecionado e abre a edição.
function abrirPopupEdicaoAlimento(botao) {
  document.getElementById("idAlimentoEdicao").value = botao.dataset.id;
  document.getElementById("codigoBarrasEdicao").value = botao.dataset.codigoBarras;
  document.getElementById("marcaAlimentoEdicao").value = botao.dataset.marca;
  document.getElementById("nomeAlimentoEdicao").value = botao.dataset.nome;
  document.getElementById("categoriaAlimentoEdicao").value = botao.dataset.categoria;
  document.getElementById("popupEdicao").showModal();
}

// Guarda o id do alimento e abre a confirmação de exclusão.
function abrirPopupExclusaoAlimento(idAlimento) {
  document.getElementById("idAlimentoExclusao").value = idAlimento;
  document.getElementById("popupExclusao").showModal();
}


/* ---------------------------------------------------------------
   CATEGORIA
   --------------------------------------------------------------- */

// Abre o formulário de cadastro de categoria.
function abrirPopupCategoria() {
  document.getElementById("popupCategoria").showModal();
}

// Fecha o formulário de cadastro de categoria.
function fecharPopupCategoria() {
  document.getElementById("popupCategoria").close();
}

// Preenche o formulário com os dados da categoria selecionada e abre a edição.
function abrirPopupEdicaoCategoria(botao) {
  document.getElementById("idCategoriaEdicao").value = botao.dataset.id;
  document.getElementById("nomeCategoriaEdicao").value = botao.dataset.nome;
  document.getElementById("descricaoCategoriaEdicao").value = botao.dataset.descricao;
  document.getElementById("popupEdicao").showModal();
}

// Guarda o id da categoria e abre a confirmação de exclusão.
function abrirPopupExclusaoCategoria(idCategoria) {
  document.getElementById("idCategoriaExclusao").value = idCategoria;
  document.getElementById("popupExclusao").showModal();
}


/* ---------------------------------------------------------------
   EMPRESA
   --------------------------------------------------------------- */

// Abre o formulário de cadastro de empresa.
function abrirPopupEmpresa() {
  document.getElementById("popupEmpresa").showModal();
}

// Fecha o formulário de cadastro de empresa.
function fecharPopupEmpresa() {
  document.getElementById("popupEmpresa").close();
}

// Preenche o formulário com os dados da empresa selecionada e abre a edição.
function abrirPopupEdicao(botao) {
  document.getElementById("idEmpresaEdicao").value = botao.dataset.id;
  document.getElementById("nomeEmpresaEdicao").value = botao.dataset.nome;
  document.getElementById("cnpjEmpresaEdicao").value = botao.dataset.cnpj;
  document.getElementById("cepEmpresaEdicao").value = botao.dataset.cep;
  document.getElementById("telefoneEmpresaEdicao").value = botao.dataset.telefone;
  document.getElementById("popupEdicao").showModal();
}

// Guarda o id da empresa e abre a confirmação de exclusão.
function abrirPopupExclusao(idEmpresa) {
  document.getElementById("idEmpresaExclusao").value = idEmpresa;
  document.getElementById("popupExclusao").showModal();
}


/* ---------------------------------------------------------------
   ENDEREÇO
   --------------------------------------------------------------- */

// Abre o formulário de cadastro de endereço.
function abrirPopupEndereco() {
  document.getElementById("popupEndereco").showModal();
}

// Fecha o formulário de cadastro de endereço.
function fecharPopupEndereco() {
  document.getElementById("popupEndereco").close();
}

// Preenche todos os campos com os dados do endereço escolhido e abre a edição.
function abrirPopupEdicaoEndereco(botao) {
  document.getElementById("idEnderecoEdicao").value = botao.dataset.id;
  document.getElementById("ruaEnderecoEdicao").value = botao.dataset.rua;
  document.getElementById("numeroEnderecoEdicao").value = botao.dataset.numero;
  document.getElementById("bairroEnderecoEdicao").value = botao.dataset.bairro;
  document.getElementById("cidadeEnderecoEdicao").value = botao.dataset.cidade;
  document.getElementById("estadoEnderecoEdicao").value = botao.dataset.estado;
  document.getElementById("cepEnderecoEdicao").value = botao.dataset.cep;
  document.getElementById("popupEdicao").showModal();
}

// Guarda o id do endereço e abre a confirmação de exclusão.
function abrirPopupExclusaoEndereco(idEndereco) {
  document.getElementById("idEnderecoExclusao").value = idEndereco;
  document.getElementById("popupExclusao").showModal();
}


/* ---------------------------------------------------------------
   ITEM DA DESPENSA
   --------------------------------------------------------------- */

// Abre o formulário de cadastro de item da despensa.
function abrirPopupItemDespensa() {
  document.getElementById("popupItemDespensa").showModal();
}

// Fecha o formulário de cadastro de item da despensa.
function fecharPopupItemDespensa() {
  document.getElementById("popupItemDespensa").close();
}

// Preenche o formulário com o item selecionado e abre a edição.
function abrirPopupEdicaoItemDespensa(botao) {
  document.getElementById("idItemEdicao").value = botao.dataset.id;
  document.getElementById("usuarioItemEdicao").value = botao.dataset.usuario;
  document.getElementById("alimentoItemEdicao").value = botao.dataset.alimento;
  document.getElementById("quantidadeItemEdicao").value = botao.dataset.quantidade;
  document.getElementById("validadeItemEdicao").value = botao.dataset.validade;
  document.getElementById("popupEdicao").showModal();
}

// Guarda o id do item e abre a confirmação de exclusão.
function abrirPopupExclusaoItemDespensa(idItem) {
  document.getElementById("idItemExclusao").value = idItem;
  document.getElementById("popupExclusao").showModal();
}


/* ---------------------------------------------------------------
   USUÁRIO
   --------------------------------------------------------------- */

// Abre o formulário de cadastro de usuário.
function abrirPopupUsuario() {
  document.getElementById("popupUsuario").showModal();
}

// Fecha o formulário de cadastro de usuário.
function fecharPopupUsuario() {
  document.getElementById("popupUsuario").close();
}

// Preenche o formulário com os dados do usuário selecionado e abre a edição.
function abrirPopupEdicaoUsuario(botao) {
  document.getElementById("idUsuarioEdicao").value = botao.dataset.id;
  document.getElementById("nomeUsuarioEdicao").value = botao.dataset.nome;
  document.getElementById("emailUsuarioEdicao").value = botao.dataset.email;
  document.getElementById("preferenciasUsuarioEdicao").value = botao.dataset.preferencias;
  document.getElementById("alertaUsuarioEdicao").value = botao.dataset.alerta;
  document.getElementById("popupEdicao").showModal();
}

// Guarda o id do usuário e abre a confirmação de exclusão.
function abrirPopupExclusaoUsuario(idUsuario) {
  document.getElementById("idUsuarioExclusao").value = idUsuario;
  document.getElementById("popupExclusao").showModal();
}
