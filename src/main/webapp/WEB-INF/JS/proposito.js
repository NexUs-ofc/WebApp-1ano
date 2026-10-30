/* DUAS INTERFACES (carrossel: botões + arrastar com o mouse/dedo) */
const carrossel = document.getElementById('carrossel');
const faixa = carrossel.querySelector('.carrossel-faixa');
const slides = carrossel.querySelectorAll('.slide');
const botoesInterface = document.querySelectorAll('.botao-interface');

const ordem = ['cliente', 'mercado']; // ordem dos slides
let indiceAtual = 0;
