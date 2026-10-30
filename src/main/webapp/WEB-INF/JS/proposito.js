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

