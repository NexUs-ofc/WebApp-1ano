// Menu hambúrguer (celular). Use em todas as páginas: <script src="JS/menu.js"></script>
(() => {
    const box = document.querySelector('.container-header');
    const btn = document.getElementById('menu-toggle');
    if (!box || !btn) return;

    function setMenu(aberto) {
        box.classList.toggle('menu-aberto', aberto);
        btn.classList.toggle('aberto', aberto);
        btn.setAttribute('aria-expanded', aberto);
        btn.setAttribute('aria-label', aberto ? 'Fechar menu' : 'Abrir menu');
    }

    btn.addEventListener('click', () => setMenu(!box.classList.contains('menu-aberto')));

    // fecha ao clicar em um link (importante pros links de âncora, como #plataforma)
    box.querySelectorAll('nav a, .acoes a').forEach(a => a.addEventListener('click', () => setMenu(false)));

    // fecha com ESC e ao voltar pra tela grande
    document.addEventListener('keydown', e => { if (e.key === 'Escape') setMenu(false); });
    window.addEventListener('resize', () => { if (window.innerWidth > 800) setMenu(false); });
})();