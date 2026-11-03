/* ===== MENU HAMBÚRGUER (celular) =====
   Cria o botão de menu no header e abre/fecha a caixa com os links.
   Coloque <script src="JS/menu-mobile.js"></script> antes do </body> em index.html, sobre.html e planos.html.
   No computador não aparece nada: o botão só é exibido em telas de até 800px. */
(function () {
    const header = document.querySelector('header');
    if (!header) return;
    const contenedor = header.querySelector('.container-header');
    const nav = header.querySelector('nav');
    if (!contenedor || !nav) return;

    // esconde o botão e os links extras no computador, mesmo se o mobile.css não carregar
    const estilo = document.createElement('style');
    estilo.textContent = '.menu-toggle, header nav .nav-extra { display: none; }';
    document.head.appendChild(estilo);

    // "Entrar" e "Cadastrar" passam a existir também dentro do menu (só aparecem no celular)
    header.querySelectorAll('.acoes a').forEach((a) => {
        const copia = a.cloneNode(true);
        copia.classList.add('nav-extra');
        nav.appendChild(copia);
    });

    nav.id = nav.id || 'menu-principal';

    const botao = document.createElement('button');
    botao.type = 'button';
    botao.className = 'menu-toggle';
    botao.setAttribute('aria-label', 'Abrir menu');
    botao.setAttribute('aria-expanded', 'false');
    botao.setAttribute('aria-controls', nav.id);
    botao.innerHTML =
        '<svg class="icone-menu" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.4" stroke-linecap="round"><path d="M3 6h18M3 12h18M3 18h18"/></svg>' +
        '<svg class="icone-x" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><path d="M4 4l16 16M20 4 4 20"/></svg>';
    contenedor.appendChild(botao);

    function definir(aberto) {
        header.classList.toggle('menu-aberto', aberto);
        botao.setAttribute('aria-expanded', String(aberto));
        botao.setAttribute('aria-label', aberto ? 'Fechar menu' : 'Abrir menu');
    }

    botao.addEventListener('click', () => definir(!header.classList.contains('menu-aberto')));
    nav.addEventListener('click', (e) => { if (e.target.closest('a')) definir(false); });
    document.addEventListener('keydown', (e) => { if (e.key === 'Escape') definir(false); });
    document.addEventListener('click', (e) => { if (!header.contains(e.target)) definir(false); });

    const grande = window.matchMedia('(min-width: 801px)');
    const aoMudar = () => { if (grande.matches) definir(false); };
    if (grande.addEventListener) grande.addEventListener('change', aoMudar);
    else grande.addListener(aoMudar);
})();