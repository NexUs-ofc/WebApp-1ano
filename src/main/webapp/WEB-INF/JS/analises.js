// Desenha o gráfico com os registros mensais de empresas fornecidos pelo ServletMostrarAnalises.
(function () {
    const canvas = document.getElementById("graficoEmpresasAnalise");
    const dados = document.getElementById("dadosGraficoAnalise");

    if (!canvas || !dados) return;

    const meses = ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"];
    const valores = Array(12).fill(0);

    dados.querySelectorAll("[data-mes]").forEach(item => {
        const mes = Number(item.dataset.mes);
        const total = Number(item.dataset.total);

        if (Number.isInteger(mes) && mes >= 1 && mes <= 12
            && Number.isInteger(total) && total >= 0) {

            valores[mes - 1] = total;
        }
    });

    function desenhar() {
        const contexto = canvas.getContext("2d");

        if (!contexto) return;

        const largura = Math.max(320, canvas.parentElement.clientWidth);
        const altura = 270;
        const escala = Math.min(window.devicePixelRatio || 1, 2);

        canvas.width = Math.round(largura * escala);
        canvas.height = Math.round(altura * escala);
        canvas.style.width = "100%";
        canvas.style.height = altura + "px";

        contexto.setTransform(escala, 0, 0, escala, 0, 0);
        contexto.clearRect(0, 0, largura, altura);

        const inicioX = 40;
        const fimX = largura - 12;
        const topo = 15;
        const base = altura - 30;
        const maior = Math.max(1, ...valores);
        const teto = Math.max(4, Math.ceil(maior / 4) * 4);

        contexto.font = "12px Huninn, sans-serif";
        contexto.textAlign = "right";
        contexto.strokeStyle = "#ede7f4";
        contexto.lineWidth = 1;

        // Desenha as linhas e os valores do eixo vertical.
        for (let i = 0; i <= 4; i++) {
            const valor = teto * i / 4;
            const y = base - (base - topo) * i / 4;

            contexto.beginPath();
            contexto.moveTo(inicioX, y);
            contexto.lineTo(fimX, y);
            contexto.stroke();

            contexto.fillStyle = "#777";
            contexto.fillText(String(valor), inicioX - 7, y + 4);
        }

        const passo = (fimX - inicioX) / 12;
        const larguraBarra = Math.min(34, passo * 0.6);

        // Desenha uma barra para cada mês.
        for (let i = 0; i < 12; i++) {
            const alturaBarra = valores[i] / teto * (base - topo);
            const x = inicioX + passo * i + (passo - larguraBarra) / 2;
            const y = base - alturaBarra;

            if (alturaBarra > 0) {
                const gradiente = contexto.createLinearGradient(0, y, 0, base);

                gradiente.addColorStop(0, "#522080");
                gradiente.addColorStop(1, "#9468c3");

                contexto.fillStyle = gradiente;
                contexto.fillRect(x, y, larguraBarra, alturaBarra);
            }

            contexto.fillStyle = "#61566b";
            contexto.textAlign = "center";
            contexto.fillText(meses[i], inicioX + passo * (i + 0.5), altura - 10);
        }

        // Mensagem para quando não existem cadastros com data neste ano.
        if (valores.every(valor => valor === 0)) {
            contexto.fillStyle = "#70637c";
            contexto.textAlign = "center";

            contexto.fillText(
                "Sem empresas com data de cadastro neste ano.",
                (inicioX + fimX) / 2,
                altura / 2
            );
        }
    }

    // Atualiza o gráfico quando o tamanho da área muda.
    if ("ResizeObserver" in window) {
        new ResizeObserver(desenhar).observe(canvas.parentElement);
    } else {
        window.addEventListener("resize", desenhar);
    }

    desenhar();
})();
