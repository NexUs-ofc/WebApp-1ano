function abrirPopupFiltro() {
  document.getElementById("popupFiltro").showModal();
}

function fecharPopupFiltro() {
  document.getElementById("popupFiltro").close();
}

function fecharPopupEdicao() {
  document.getElementById("popupEdicao").close();
}

function fecharPopupExclusao() {
  document.getElementById("popupExclusao").close();
}

// Administrador

function abrirPopupEdicaoAdministrador(botao) {
  const telefone = (botao.dataset.telefone || "").replace(/\D/g, "");
  const foto = botao.dataset.foto || "";
  const previewFoto = document.getElementById("previewFotoAdministrador");

  document.getElementById("idAdministradorEdicao").value = botao.dataset.id;
  document.getElementById("nomeAdministradorEdicao").value = botao.dataset.nome;
  document.getElementById("dddAdministradorEdicao").value = telefone.length > 10 ? telefone.slice(0, 2) : "";
  document.getElementById("telefoneAdministradorEdicao").value = telefone.length > 10 ? telefone.slice(2) : telefone;
  document.getElementById("emailAdministradorEdicao").value = botao.dataset.email;
  document.getElementById("senhaAdministradorEdicao").value = botao.dataset.senha;
  document.getElementById("arquivoFotoAdministrador").value = "";

  if (foto) {
    previewFoto.src = foto;
    previewFoto.classList.remove("fotoPopupAdministradorVazia");
  } else {
    previewFoto.removeAttribute("src");
    previewFoto.classList.add("fotoPopupAdministradorVazia");
  }

  document.getElementById("popupEdicao").showModal();
}

const arquivoFotoAdministrador = document.getElementById("arquivoFotoAdministrador");

if (arquivoFotoAdministrador) {
  arquivoFotoAdministrador.addEventListener("change", function () {
    const arquivo = this.files[0];

    if (arquivo) {
      const previewFoto = document.getElementById("previewFotoAdministrador");
      previewFoto.src = URL.createObjectURL(arquivo);
      previewFoto.classList.remove("fotoPopupAdministradorVazia");
    }
  });
}

function abrirPopupExclusaoAdministrador(idAdministrador) {
  document.getElementById("idAdministradorExclusao").value = idAdministrador;
  document.getElementById("popupExclusao").showModal();
}

// Alimento

function abrirPopupAlimento() {
  document.getElementById("popupAlimento").showModal();
}

function fecharPopupAlimento() {
  document.getElementById("popupAlimento").close();
}

function abrirPopupEdicaoAlimento(botao) {
  document.getElementById("idAlimentoEdicao").value = botao.dataset.id;
  document.getElementById("codigoBarrasEdicao").value = botao.dataset.codigoBarras;
  document.getElementById("marcaAlimentoEdicao").value = botao.dataset.marca;
  document.getElementById("nomeAlimentoEdicao").value = botao.dataset.nome;
  document.getElementById("categoriaAlimentoEdicao").value = botao.dataset.categoria;
  document.getElementById("popupEdicao").showModal();
}

function abrirPopupExclusaoAlimento(idAlimento) {
  document.getElementById("idAlimentoExclusao").value = idAlimento;
  document.getElementById("popupExclusao").showModal();
}

// Categoria

function abrirPopupCategoria() {
  document.getElementById("popupCategoria").showModal();
}

function fecharPopupCategoria() {
  document.getElementById("popupCategoria").close();
}

function abrirPopupEdicaoCategoria(botao) {
  document.getElementById("idCategoriaEdicao").value = botao.dataset.id;
  document.getElementById("nomeCategoriaEdicao").value = botao.dataset.nome;
  document.getElementById("descricaoCategoriaEdicao").value = botao.dataset.descricao;
  document.getElementById("popupEdicao").showModal();
}

function abrirPopupExclusaoCategoria(idCategoria) {
  document.getElementById("idCategoriaExclusao").value = idCategoria;
  document.getElementById("popupExclusao").showModal();
}

// Empresa

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

function abrirPopupExclusao(idEmpresa) {
  document.getElementById("idEmpresaExclusao").value = idEmpresa;
  document.getElementById("popupExclusao").showModal();
}

const formPesquisaEmpresa = document.getElementById("formPesquisaEmpresa");

if (formPesquisaEmpresa) {
  formPesquisaEmpresa.addEventListener("submit", function (event) {
    event.preventDefault();
    const pesquisa = document.getElementById("pesquisaEmpresa").value.toLowerCase();

    document.querySelectorAll("#tabelaEmpresas tr").forEach(function (linha) {
      linha.hidden = !linha.textContent.toLowerCase().includes(pesquisa);
    });
  });
}

// Endereço

function abrirPopupEndereco() {
  document.getElementById("popupEndereco").showModal();
}

function fecharPopupEndereco() {
  document.getElementById("popupEndereco").close();
}

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

function abrirPopupExclusaoEndereco(idEndereco) {
  document.getElementById("idEnderecoExclusao").value = idEndereco;
  document.getElementById("popupExclusao").showModal();
}

// Item da despensa

function abrirPopupItemDespensa() {
  document.getElementById("popupItemDespensa").showModal();
}

function fecharPopupItemDespensa() {
  document.getElementById("popupItemDespensa").close();
}

function abrirPopupEdicaoItemDespensa(botao) {
  document.getElementById("idItemEdicao").value = botao.dataset.id;
  document.getElementById("usuarioItemEdicao").value = botao.dataset.usuario;
  document.getElementById("alimentoItemEdicao").value = botao.dataset.alimento;
  document.getElementById("quantidadeItemEdicao").value = botao.dataset.quantidade;
  document.getElementById("validadeItemEdicao").value = botao.dataset.validade;
  document.getElementById("popupEdicao").showModal();
}

function abrirPopupExclusaoItemDespensa(idItem) {
  document.getElementById("idItemExclusao").value = idItem;
  document.getElementById("popupExclusao").showModal();
}

// Usuário

function abrirPopupUsuario() {
  document.getElementById("popupUsuario").showModal();
}

function fecharPopupUsuario() {
  document.getElementById("popupUsuario").close();
}

function abrirPopupEdicaoUsuario(botao) {
  document.getElementById("idUsuarioEdicao").value = botao.dataset.id;
  document.getElementById("nomeUsuarioEdicao").value = botao.dataset.nome;
  document.getElementById("emailUsuarioEdicao").value = botao.dataset.email;
  document.getElementById("preferenciasUsuarioEdicao").value = botao.dataset.preferencias;
  document.getElementById("alertaUsuarioEdicao").value = botao.dataset.alerta;
  document.getElementById("popupEdicao").showModal();
}

function abrirPopupExclusaoUsuario(idUsuario) {
  document.getElementById("idUsuarioExclusao").value = idUsuario;
  document.getElementById("popupExclusao").showModal();
}
