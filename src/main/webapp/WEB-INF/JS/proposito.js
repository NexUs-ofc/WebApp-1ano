/* DUAS INTERFACES (carrossel: botões + arrastar com o mouse/dedo) */
const carrossel = document.getElementById('carrossel');
const faixa = carrossel.querySelector('.carrossel-faixa');
const slides = carrossel.querySelectorAll('.slide');
const botoesInterface = document.querySelectorAll('.botao-interface');

const ordem = ['cliente', 'mercado']; // ordem dos slides
let indiceAtual = 0;

// desliza a faixa até o slide pedido
function irPara(indice) {
    indiceAtual = Math.max(0, Math.min(ordem.length - 1, indice));
    faixa.style.transform = `translateX(calc(${-indiceAtual * 100}% + 0px))`;

    // o slide fora da tela não recebe foco do teclado
    slides.forEach((slide, i) => { slide.inert = i !== indiceAtual; });

    // o botão do slide atual fica "ativo" (o fade vem do transition do CSS)
    botoesInterface.forEach((botao) => {
        botao.classList.toggle('ativo', botao.dataset.interface === ordem[indiceAtual]);
    });
}
// clique nos botões (título e botões ficam parados; só o conteúdo desliza)
botoesInterface.forEach((botao) => {
    botao.addEventListener('click', () => irPara(ordem.indexOf(botao.dataset.interface)));
});
// arrastar em qualquer ponto do carrossel
let pressionando = false;
let arrastando = false;
let bloquearClique = false;
let inicioX = 0;
let inicioY = 0;
let deslocamento = 0;
let larguraSlide = 0;

carrossel.addEventListener('pointerdown', (e) => {
    if (e.pointerType === 'mouse' && e.button !== 0) return;
    pressionando = true;
    arrastando = false;
    bloquearClique = false;
    inicioX = e.clientX;
    inicioY = e.clientY;
    deslocamento = 0;
    larguraSlide = carrossel.clientWidth;
});

carrossel.addEventListener('pointermove', (e) => {
    if (!pressionando) return;
    const dx = e.clientX - inicioX;

    // só vira "arrasto" depois de mover um pouco na horizontal;
    // antes disso é um clique normal
    if (!arrastando) {
        if (Math.abs(dx) < 8 || Math.abs(dx) < Math.abs(e.clientY - inicioY)) return;
        arrastando = true;
        faixa.classList.add('sem-transicao');
        carrossel.classList.add('arrastando');
        carrossel.setPointerCapture(e.pointerId);
    }

    deslocamento = dx;

    // nas pontas o carrossel "segura" um pouco, para mostrar que não há mais slides
    const naPrimeira = indiceAtual === 0 && deslocamento > 0;
    const naUltima = indiceAtual === ordem.length - 1 && deslocamento < 0;
    const resistencia = naPrimeira || naUltima ? 0.3 : 1;

    faixa.style.transform = `translateX(calc(${-indiceAtual * 100}% + ${deslocamento * resistencia}px))`;
});
function soltar() {
    pressionando = false;
    if (!arrastando) return;
    arrastando = false;
    bloquearClique = true; // evita que o fim do arrasto conte como clique

    faixa.classList.remove('sem-transicao');
    carrossel.classList.remove('arrastando');

    const limite = larguraSlide * 0.2; // arrastou mais de 20%? troca de slide
    if (deslocamento < -limite) irPara(indiceAtual + 1);
    else if (deslocamento > limite) irPara(indiceAtual - 1);
    else irPara(indiceAtual); // senão volta para o lugar
}

carrossel.addEventListener('pointerup', soltar);
carrossel.addEventListener('pointercancel', soltar);

carrossel.addEventListener('click', (e) => {
    if (bloquearClique && e.detail !== 0) {
        e.stopPropagation();
        e.preventDefault();
    }
}, true);

irPara(0);

/* DÚVIDAS FREQUENTES */
document.querySelectorAll('.duvida-pergunta').forEach((pergunta) => {
    pergunta.addEventListener('click', () => {
        const item = pergunta.parentElement;
        const aberta = item.classList.toggle('aberta');
        pergunta.setAttribute('aria-expanded', aberta);
    });
});

