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
    function passo() {
        const cards = faixa.children;
        return cards.length > 1 ? cards[1].offsetLeft - cards[0].offsetLeft : 0;
    }

    function visiveis() {
        return parseInt(getComputedStyle(carrossel).getPropertyValue('--visiveis'), 10) || 1;
    }
    function irParaProximo() {
        if (animando || faixa.children.length <= visiveis()) return;
        animando = true;

        faixa.style.transition = `transform ${duracao()}ms ease`;
        faixa.style.transform = `translateX(${-passo()}px)`;

        setTimeout(() => {
            faixa.style.transition = 'none';
            faixa.appendChild(faixa.firstElementChild);
            faixa.style.transform = 'translateX(0)';
            animando = false;
        }, duracao());
    }

    function irParaAnterior() {
        if (animando || faixa.children.length <= visiveis()) return;
        animando = true;

        faixa.style.transition = 'none';
        faixa.insertBefore(faixa.lastElementChild, faixa.firstElementChild);
        faixa.style.transform = `translateX(${-passo()}px)`;
        void faixa.offsetWidth;

        faixa.style.transition = `transform ${duracao()}ms ease`;
        faixa.style.transform = 'translateX(0)';

        setTimeout(() => { animando = false; }, duracao());
    }