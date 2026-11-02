// ===== ARQUIVO PRINCIPAL DA PÁGINA INICIAL =====
// Esse arquivo tem duas partes:
//   1) o carrossel da seção "Duas interfaces" (botões Cliente/Mercado + arrastar com o mouse ou o dedo)
//   2) as perguntas que abrem e fecham nas "Dúvidas Frequentes"


/* ---------------------------------------------------------------
   PARTE 1: DUAS INTERFACES (carrossel)
   --------------------------------------------------------------- */

// aqui a gente pega as peças do carrossel no HTML e guarda em variáveis
const carrossel = document.getElementById('carrossel');            // a caixa do carrossel inteiro
const faixa = carrossel.querySelector('.carrossel-faixa');         // a fita comprida com os slides um do lado do outro
const slides = carrossel.querySelectorAll('.slide');               // a lista dos slides (um pra cada interface)
const botoesInterface = document.querySelectorAll('.botao-interface'); // os botões "Interface Cliente" e "Interface Mercado"

// a ordem dos slides. A posição no array vira o número do slide:
// 'cliente' é o 0 e 'mercado' é o 1 (computador começa a contar do zero)
const ordem = ['cliente', 'mercado'];

// guarda qual slide está aparecendo agora (começa no 0, o do cliente)
let indiceAtual = 0;

// desliza a faixa até o slide que a gente pedir
function irPara(indice) {
    // Math.min e Math.max travam o número entre 0 e o último slide,
    // assim ninguém consegue ir pra um slide que não existe
    indiceAtual = Math.max(0, Math.min(ordem.length - 1, indice));

    // move a faixa pra esquerda. Cada slide ocupa 100% da largura, então:
    // slide 0 = 0%, slide 1 = -100% (o sinal de menos faz ir pra esquerda).
    // O "+ 0px" é pra esse valor ter o mesmo formato do que usamos quando arrasta (que soma pixels),
    // aí a animação fica suave dos dois jeitos
    faixa.style.transform = `translateX(calc(${-indiceAtual * 100}% + 0px))`;

    // o slide que está fora da tela não pode receber foco do teclado (tecla Tab),
    // senão a pessoa ia "entrar" num botão que ela nem está vendo.
    // O "inert" faz isso: ele fica true em todos os slides, menos no atual
    slides.forEach((slide, i) => { slide.inert = i !== indiceAtual; });

    // o botão do slide atual fica "ativo" (roxinho). Quem faz a transição suave de cor é o CSS.
    // O toggle com dois valores funciona assim: se for true, coloca a classe; se for false, tira
    botoesInterface.forEach((botao) => {
        botao.classList.toggle('ativo', botao.dataset.interface === ordem[indiceAtual]);
    });
}

// clique nos botões
// (o título e os botões ficam parados, só o conteúdo desliza)
botoesInterface.forEach((botao) => {
    // cada botão tem no HTML um data-interface="cliente" ou "mercado".
    // O indexOf transforma esse nome no número do slide (cliente = 0, mercado = 1)
    botao.addEventListener('click', () => irPara(ordem.indexOf(botao.dataset.interface)));
});


/* ----- ARRASTAR EM QUALQUER PONTO DO CARROSSEL ----- */

// variáveis pra controlar o arrasto:
let pressionando = false;  // true quando o mouse/dedo está apertado em cima do carrossel
let arrastando = false;    // true quando já começou a arrastar de verdade
let bloquearClique = false; // pra não contar o fim do arrasto como um clique sem querer
let inicioX = 0;           // onde o mouse/dedo estava na horizontal quando apertou
let inicioY = 0;           // e na vertical
let deslocamento = 0;      // quantos pixels já arrastou (negativo = esquerda, positivo = direita)
let larguraSlide = 0;      // a largura do carrossel, pra calcular a porcentagem arrastada

// quando aperta o mouse ou encosta o dedo
carrossel.addEventListener('pointerdown', (e) => {
    // se for o mouse, só vale o botão esquerdo (button 0). Assim o botão direito não faz nada
    if (e.pointerType === 'mouse' && e.button !== 0) return;

    pressionando = true;
    arrastando = false;
    bloquearClique = false;

    // anota onde começou
    inicioX = e.clientX;
    inicioY = e.clientY;
    deslocamento = 0;
    larguraSlide = carrossel.clientWidth;
});

// quando mexe o mouse/dedo
carrossel.addEventListener('pointermove', (e) => {
    // se não estiver apertando, não faz nada
    if (!pressionando) return;

    // quanto já andou na horizontal desde que apertou
    const dx = e.clientX - inicioX;

    // só conta como "arrasto" depois de andar um pouquinho na horizontal.
    // Antes disso a gente trata como um clique normal (senão os botões nunca iam funcionar)
    if (!arrastando) {
        // se andou menos de 8px, OU andou mais na vertical do que na horizontal
        // (a pessoa deve estar só rolando a página), ainda não é arrasto
        if (Math.abs(dx) < 8 || Math.abs(dx) < Math.abs(e.clientY - inicioY)) return;

        arrastando = true;

        // tira a animação do CSS enquanto arrasta, pra faixa seguir o dedo na hora, sem atraso
        faixa.classList.add('sem-transicao');

        // essa classe muda o cursor pra mãozinha fechada (quem faz isso é o CSS)
        carrossel.classList.add('arrastando');

        // faz o carrossel continuar recebendo o movimento mesmo se o mouse sair de cima dele
        carrossel.setPointerCapture(e.pointerId);
    }

    deslocamento = dx;

    // nas pontas, o carrossel "segura" um pouco (tipo um elástico) pra mostrar que não tem mais slides:
    // no primeiro slide arrastando pra direita, ou no último arrastando pra esquerda
    const naPrimeira = indiceAtual === 0 && deslocamento > 0;
    const naUltima = indiceAtual === ordem.length - 1 && deslocamento < 0;

    // nas pontas só anda 30% do que o dedo andou; no meio anda 100%
    const resistencia = naPrimeira || naUltima ? 0.3 : 1;

    // posição do slide atual (em %) + quantos pixels arrastou
    faixa.style.transform = `translateX(calc(${-indiceAtual * 100}% + ${deslocamento * resistencia}px))`;
});

// quando solta o mouse/dedo (ou quando o navegador cancela o toque)
function soltar() {
    pressionando = false;

    // se nem chegou a arrastar, não tem nada pra fazer
    if (!arrastando) return;

    arrastando = false;
    bloquearClique = true; // o clique que vem logo depois de soltar não deve valer

    // devolve a animação do CSS e o cursor normal
    faixa.classList.remove('sem-transicao');
    carrossel.classList.remove('arrastando');

    // se arrastou mais de 20% da largura, troca de slide
    const limite = larguraSlide * 0.2;
    if (deslocamento < -limite) irPara(indiceAtual + 1);        // arrastou pra esquerda: vai pro próximo
    else if (deslocamento > limite) irPara(indiceAtual - 1);    // arrastou pra direita: volta pro anterior
    else irPara(indiceAtual);                                   // arrastou pouco: volta pro lugar
}

carrossel.addEventListener('pointerup', soltar);
carrossel.addEventListener('pointercancel', soltar);

// esse bloqueio impede que, depois de arrastar, o navegador entenda como um clique em algum botão.
// O "true" no final faz esse código pegar o clique ANTES dele chegar nos botões.
// O e.detail !== 0 serve pra só bloquear clique de mouse/dedo
// (quando é pelo teclado, com a tecla Enter, o detail é 0 e deixa passar)
carrossel.addEventListener('click', (e) => {
    if (bloquearClique && e.detail !== 0) {
        e.stopPropagation();
        e.preventDefault();
    }
}, true);

// roda uma vez quando a página abre, pra deixar tudo no estado inicial
// (slide do cliente aparecendo, botão certo ativo e o outro slide bloqueado pro teclado)
irPara(0);


/* ---------------------------------------------------------------
   PARTE 2: DÚVIDAS FREQUENTES (as perguntas que abrem e fecham)
   --------------------------------------------------------------- */

// pega todos os botões de pergunta e, pra cada um, define o que acontece no clique
document.querySelectorAll('.duvida-pergunta').forEach((pergunta) => {
    pergunta.addEventListener('click', () => {
        // o "pai" do botão é a caixinha inteira da dúvida (pergunta + resposta)
        const item = pergunta.parentElement;

        // o toggle liga/desliga a classe "aberta": se não tinha, coloca (e devolve true);
        // se já tinha, tira (e devolve false). Quem abre e fecha a resposta com animação é o CSS
        const aberta = item.classList.toggle('aberta');

        // avisa os leitores de tela se a resposta está aberta ou fechada (acessibilidade)
        pergunta.setAttribute('aria-expanded', aberta);
    });
});
