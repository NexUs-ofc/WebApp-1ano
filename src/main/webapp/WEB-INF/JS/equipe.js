// ===== CARROSSEL DA EQUIPE (página Sobre Nós) =====
// Resumindo o que esse código faz:
//   - as setas passam os cards de 1 em 1
//   - quando chega no último card, ele volta pro primeiro (fica girando, tipo um loop)
//   - também dá pra arrastar com o dedo (ou com o mouse) e usar as setinhas do teclado

// Isso aqui é uma função que roda sozinha assim que a página abre.
// Eu coloquei tudo dentro dela pras variáveis daqui não se misturarem com as de outros arquivos JS.
(function () {

    // pega o carrossel inteiro pelo id que a gente colocou no HTML
    const carrossel = document.getElementById('equipe-carrossel');

    // se a página não tiver o carrossel (tipo a página inicial), o código para por aqui.
    // Assim não aparece erro no console
    if (!carrossel) return;

    // aqui a gente guarda as partes do carrossel em variáveis pra usar mais pra baixo
    const janela = carrossel.querySelector('.equipe-janela');             // a "janelinha" que mostra só alguns cards
    const faixa = carrossel.querySelector('.equipe-faixa');               // a fita comprida com TODOS os cards um do lado do outro
    const botaoAnterior = carrossel.querySelector('.equipe-seta.anterior'); // seta da esquerda
    const botaoProximo = carrossel.querySelector('.equipe-seta.proximo');   // seta da direita

    // quanto tempo a animação dura, em milissegundos (450 = quase meio segundo)
    const DURACAO = 450;

    // uma "trava": enquanto for true, a pessoa não consegue clicar de novo.
    // Sem isso, se clicasse várias vezes rápido os cards se embolavam
    let animando = false;

    // se a pessoa configurou o celular/computador pra reduzir animações, a duração vira 0 (sem animação).
    // Se não, usa os 450 lá de cima
    const duracao = () => (window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 0 : DURACAO);

    // calcula o tamanho de "um passo", ou seja, a distância de um card até o próximo
    // (largura do card + o espacinho entre eles).
    // O jeito que eu fiz foi pegar a posição do 2º card e diminuir a posição do 1º
    function passo() {
        const cards = faixa.children;   // a lista com todos os cards

        // só calcula se tiver pelo menos 2 cards, se não tiver devolve 0
        return cards.length > 1 ? cards[1].offsetLeft - cards[0].offsetLeft : 0;
    }

    // descobre quantos cards aparecem na tela agora.
    // Esse número vem do CSS (a variável --visiveis) e muda conforme o tamanho da tela
    // (4 no computador, 3 no tablet e 2 no celular).
    // O "|| 1" é pra usar 1 caso não ache o número
    function visiveis() {
        return parseInt(getComputedStyle(carrossel).getPropertyValue('--visiveis'), 10) || 1;
    }

    // ---------- SETA DA DIREITA (próximo) ----------
    function irParaProximo() {
        // não faz nada se ainda estiver animando
        // OU se todos os cards já cabem na tela (aí não tem o que passar)
        if (animando || faixa.children.length <= visiveis()) return;

        animando = true;   // ativa a trava

        // liga a animação e empurra a faixa pra esquerda a distância de 1 card.
        // Por isso o sinal de menos no passo(): números negativos vão pra esquerda
        faixa.style.transition = `transform ${duracao()}ms ease`;
        faixa.style.transform = `translateX(${-passo()}px)`;

        // quando a animação termina, a gente faz um truque pra criar o loop infinito:
        setTimeout(() => {
            // 1) tira a animação, senão ia aparecer o "pulo" que vem agora
            faixa.style.transition = 'none';

            // 2) pega o primeiro card (que já saiu da tela) e joga lá pro final da fila
            faixa.appendChild(faixa.firstElementChild);

            // 3) volta a faixa pra posição zero.
            // Como o card mudou de lugar, parece que nada mudou, mas agora o 2º card virou o 1º
            faixa.style.transform = 'translateX(0)';

            animando = false;   // tira a trava
        }, duracao());   // espera a animação acabar pra fazer tudo isso
    }

    // ---------- SETA DA ESQUERDA (anterior) ----------
    // é o contrário da de cima
    function irParaAnterior() {
        // mesma checagem de antes
        if (animando || faixa.children.length <= visiveis()) return;

        animando = true;   // ativa a trava

        // 1) sem animação, pega o ÚLTIMO card e coloca no começo da fila
        faixa.style.transition = 'none';
        faixa.insertBefore(faixa.lastElementChild, faixa.firstElementChild);

        // 2) como entrou um card novo no começo, tudo foi empurrado pra direita.
        // Então a gente puxa a faixa 1 passo pra esquerda pra pessoa nem perceber a mudança
        faixa.style.transform = `translateX(${-passo()}px)`;

        // essa linha estranha serve pra forçar o navegador a aplicar o que a gente fez acima
        // ANTES de começar a animação (se tirar, a animação não funciona direito)
        void faixa.offsetWidth;

        // 3) agora sim liga a animação e desliza a faixa de volta pro zero.
        // Aí o card que a gente colocou no começo aparece entrando pela esquerda
        faixa.style.transition = `transform ${duracao()}ms ease`;
        faixa.style.transform = 'translateX(0)';

        // tira a trava depois que a animação termina
        setTimeout(() => { animando = false; }, duracao());
    }

    // esconde as setas quando não precisa delas (quando todos os cards já aparecem de uma vez)
    function atualizarSetas() {
        const semNecessidade = faixa.children.length <= visiveis();

        botaoAnterior.hidden = semNecessidade;   // hidden = true esconde o botão
        botaoProximo.hidden = semNecessidade;
    }

    // ---------- CLIQUE NAS SETAS ----------
    // quando clicar em cada botão, chama a função correspondente
    botaoProximo.addEventListener('click', irParaProximo);
    botaoAnterior.addEventListener('click', irParaAnterior);

    // ---------- TECLADO ----------
    // seta → passa pro próximo e seta ← volta pro anterior
    // (funciona quando o foco do teclado estiver dentro do carrossel)
    carrossel.addEventListener('keydown', (e) => {
        if (e.key === 'ArrowRight') irParaProximo();
        if (e.key === 'ArrowLeft') irParaAnterior();
    });

    // ---------- ARRASTAR COM O DEDO OU COM O MOUSE ----------
    // guarda onde o dedo/mouse estava quando encostou (null = ninguém está encostando agora)
    let inicioX = null;

    // quando encosta, anota a posição na horizontal (clientX)
    janela.addEventListener('pointerdown', (e) => { inicioX = e.clientX; });

    // quando solta, vê o quanto o dedo andou
    janela.addEventListener('pointerup', (e) => {
        // se nem começou a encostar aqui dentro, ignora
        if (inicioX === null) return;

        // quanto andou: negativo = foi pra esquerda, positivo = foi pra direita
        const deslocamento = e.clientX - inicioX;
        inicioX = null;   // zera pra próxima vez

        // só conta como "arrastar" se andou mais de 50 pixels
        // (menos que isso deve ser só um toque ou um clique)
        if (Math.abs(deslocamento) > 50) {
            if (deslocamento < 0) irParaProximo();   // arrastou pra esquerda → mostra o próximo
            else irParaAnterior();                   // arrastou pra direita → volta pro anterior
        }
    });

    // o "pointercancel" acontece quando o navegador cancela o toque
    // (por exemplo, quando a pessoa começa a rolar a página). Aí a gente só esquece o que tinha anotado
    janela.addEventListener('pointercancel', () => { inicioX = null; });

    // quando o tamanho da tela mudar (ex.: virar o celular), confere de novo se precisa mostrar as setas
    window.addEventListener('resize', atualizarSetas);

    // roda uma vez quando a página abre, pra já deixar as setas do jeito certo
    atualizarSetas();
})();