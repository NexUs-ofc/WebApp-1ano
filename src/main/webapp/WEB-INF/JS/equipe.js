(function () {
    const carrossel = document.getElementById('equipe-carrossel');
    if (!carrossel) return;

    const janela = carrossel.querySelector('.equipe-janela');
    const faixa = carrossel.querySelector('.equipe-faixa');
    const botaoAnterior = carrossel.querySelector('.equipe-seta.anterior');
    const botaoProximo = carrossel.querySelector('.equipe-seta.proximo');

    const DURACAO = 450;
    let animando = false;

    const duracao = () => (window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 0 : DURACAO);