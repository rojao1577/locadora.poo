"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

export default function NovaLocacaoPage() {
    const router = useRouter();

    const [clienteId, setClienteId] = useState("");
    const [funcionarioId, setFuncionarioId] = useState("");
    const [veiculoId, setVeiculoId] = useState("");
    const [dataLocacao, setDataLocacao] = useState("");
    const [dataDevolucaoPrevista, setDataDevolucaoPrevista] = useState("");

    const [clientes, setClientes] = useState([]);
    const [funcionarios, setFuncionarios] = useState([]);
    const [veiculos, setVeiculos] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [mensagem, setMensagem] = useState("");

    useEffect(() => {
        async function carregarDependencias() {
            try {
                const urlCategorias = "http://localhost:8080/categorias";

                const [resClientes, resFuncionarios, resVeiculos, resCategorias] = await Promise.all([
                    fetch("http://localhost:8080/clientes/").catch(() => null),
                    fetch("http://localhost:8080/funcionarios").catch(() => null),
                    fetch("http://localhost:8080/veiculos").catch(() => null),
                    fetch(urlCategorias).catch(() => null)
                ]);

                if (resClientes?.ok) setClientes(await resClientes.json());
                if (resFuncionarios?.ok) setFuncionarios(await resFuncionarios.json());
                if (resVeiculos?.ok) setVeiculos(await resVeiculos.json());
                if (resCategorias?.ok) setCategorias(await resCategorias.json());
            } catch (e) {
                console.error("Erro geral ao carregar:", e);
                setMensagem("Falha ao comunicar com o servidor.");
            }
        }
        carregarDependencias();
    }, []);

    const getValorDiaria = (veiculo) => {
        if (!veiculo || !veiculo.idCategoria) return 0;
        const categoria = categorias.find(c => String(c.id) === String(veiculo.idCategoria));
        return categoria ? Number(categoria.valorDiariaBase || 0) : 0;
    };

    const calcularDias = (inicio, fim) => {
        if (!inicio || !fim) return 0;
        const [a1, m1, d1] = inicio.split('-');
        const [a2, m2, d2] = fim.split('-');

        const data1 = new Date(a1, m1 - 1, d1);
        const data2 = new Date(a2, m2 - 1, d2);

        const diffTime = data2.getTime() - data1.getTime();
        const dias = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

        return dias > 0 ? dias : 1;
    };

    const calcularValorTotal = () => {
        if (!veiculoId || !dataLocacao || !dataDevolucaoPrevista) return 0;
        const veiculoSelecionado = veiculos.find(v => String(v.id) === String(veiculoId));
        if (!veiculoSelecionado) return 0;

        const diaria = getValorDiaria(veiculoSelecionado);
        const dias = calcularDias(dataLocacao, dataDevolucaoPrevista);

        return dias * diaria;
    };

    async function handleSubmit(e) {
        e.preventDefault();
        try {
            const valorTotalCalculado = calcularValorTotal();

            const locacao = {
                dataLocacao,
                dataDevolucaoPrevista,
                clienteId: parseInt(clienteId),
                funcionarioId: parseInt(funcionarioId),
                veiculosIds: [parseInt(veiculoId)],
                valorTotal: valorTotalCalculado
            };

            const resposta = await fetch("http://localhost:8080/api/v1/locacoes", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(locacao),
            });

            if (resposta.ok) {
                router.push("/locacoes");
            } else {
                const erro = await resposta.text();
                setMensagem(erro || "Erro ao processar a locação.");
            }
        } catch (e) {
            setMensagem("Erro de comunicação com o servidor.");
        }
    }

    const valorTotal = calcularValorTotal();

    return (
        <main className="p-6 max-w-lg mx-auto text-white">
            <h1 className="text-2xl font-bold mb-4">Registrar Locação</h1>
            {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}

            <form onSubmit={handleSubmit} className="flex flex-col gap-4 bg-black p-6 rounded-lg border border-gray-600 shadow-md">

                <div>
                    <label className="block text-sm mb-1 text-gray-300 font-semibold">Cliente</label>
                    <select className="w-full bg-gray-900 border border-gray-600 text-white p-3 rounded focus:outline-none focus:border-white" value={clienteId} onChange={(e) => setClienteId(e.target.value)} required>
                        <option value="" className="bg-gray-900 text-gray-400">Selecione o Cliente...</option>
                        {clientes.map((c) => (
                            <option key={c.id} value={c.id} className="bg-gray-900 text-white">{c.nome} (CPF: {c.cpf})</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className="block text-sm mb-1 text-gray-300 font-semibold">Funcionário</label>
                    <select className="w-full bg-gray-900 border border-gray-600 text-white p-3 rounded focus:outline-none focus:border-white" value={funcionarioId} onChange={(e) => setFuncionarioId(e.target.value)} required>
                        <option value="" className="bg-gray-900 text-gray-400">Selecione o Funcionário...</option>
                        {funcionarios.map((f) => (
                            <option key={f.id} value={f.id} className="bg-gray-900 text-white">{f.nome}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className="block text-sm mb-1 text-gray-300 font-semibold">Veículo</label>
                    <select className="w-full bg-gray-900 border border-gray-600 text-white p-3 rounded focus:outline-none focus:border-white" value={veiculoId} onChange={(e) => setVeiculoId(e.target.value)} required>
                        <option value="" className="bg-gray-900 text-gray-400">Selecione o Veículo...</option>
                        {veiculos.map((v) => {
                            const diaria = getValorDiaria(v);
                            return (
                                <option key={v.id} value={v.id} className="bg-gray-900 text-white">
                                    {v.modelo} - {v.placa} (Diária: R$ {diaria.toFixed(2)})
                                </option>
                            );
                        })}
                    </select>
                </div>

                <div>
                    <label className="block text-sm mb-1 text-gray-300 font-semibold">Data da Locação (Retirada)</label>
                    <input className="w-full bg-gray-900 border border-gray-600 text-white p-3 rounded focus:outline-none focus:border-white" type="date" value={dataLocacao} onChange={(e) => setDataLocacao(e.target.value)} required />
                </div>

                <div>
                    <label className="block text-sm mb-1 text-gray-300 font-semibold">Data de Devolução Prevista</label>
                    <input className="w-full bg-gray-900 border border-gray-600 text-white p-3 rounded focus:outline-none focus:border-white" type="date" value={dataDevolucaoPrevista} onChange={(e) => setDataDevolucaoPrevista(e.target.value)} required />
                </div>

                {veiculoId && dataLocacao && dataDevolucaoPrevista && (
                    <div className="bg-gray-900 p-3 rounded border border-gray-600 text-green-400 font-medium">
                        Valor Total Estimado: R$ {valorTotal.toFixed(2)}
                    </div>
                )}

                <button type="submit" className="bg-white text-black font-bold p-3 rounded hover:bg-gray-200 transition mt-2">
                    Confirmar Locação
                </button>
            </form>
        </main>
    );
}