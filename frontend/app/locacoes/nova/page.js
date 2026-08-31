"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

export default function NovaLocacaoPage() {
    const router = useRouter();

    const [clienteId, setClienteId] = useState("");
    const [funcionarioId, setFuncionarioId] = useState("");
    const [veiculoId, setVeiculoId] = useState("");
    const [dataDevolucaoPrevista, setDataDevolucaoPrevista] = useState("");

    const [clientes, setClientes] = useState([]);
    const [funcionarios, setFuncionarios] = useState([]);
    const [veiculos, setVeiculos] = useState([]);
    const [mensagem, setMensagem] = useState("");

    useEffect(() => {
        async function carregarDependencias() {
            try {
                const [resClientes, resFuncionarios, resVeiculos] = await Promise.all([
                    fetch("http://localhost:8080/clientes/"),
                    fetch("http://localhost:8080/funcionarios"),
                    fetch("http://localhost:8080/veiculos")
                ]);

                if (resClientes.ok) setClientes(await resClientes.json());
                if (resFuncionarios.ok) setFuncionarios(await resFuncionarios.json());
                if (resVeiculos.ok) setVeiculos(await resVeiculos.json());
            } catch (e) {
                console.error("Erro ao carregar:", e);
                setMensagem("Falha ao comunicar com o servidor.");
            }
        }
        carregarDependencias();
    }, []);

    async function handleSubmit(e) {
        e.preventDefault();
        try {
            const locacao = {
                dataDevolucaoPrevista,
                clienteId: parseInt(clienteId),
                funcionarioId: parseInt(funcionarioId),
                veiculosIds: [parseInt(veiculoId)]
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

    return (
        <main className="p-6 max-w-lg mx-auto">
            <h1 className="text-2xl font-bold mb-4">Registrar Locação</h1>
            {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}
            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
                <select className="border p-2 rounded" value={clienteId} onChange={(e) => setClienteId(e.target.value)} required>
                    <option value="">Selecione o Cliente...</option>
                    {clientes.map((c) => (
                        <option key={c.id} value={c.id}>
                            {c.nome} (CPF: {c.cpf})
                        </option>
                    ))}
                </select>

                <select className="border p-2 rounded" value={funcionarioId} onChange={(e) => setFuncionarioId(e.target.value)} required>
                    <option value="">Selecione o Funcionário...</option>
                    {funcionarios.map((f) => (
                        <option key={f.id} value={f.id}>
                            {f.nome}
                        </option>
                    ))}
                </select>

                <select className="border p-2 rounded" value={veiculoId} onChange={(e) => setVeiculoId(e.target.value)} required>
                    <option value="">Selecione o Veículo...</option>
                    {veiculos.map((v) => (
                        <option key={v.id} value={v.id}>
                            {v.modelo} - {v.placa}
                        </option>
                    ))}
                </select>

                <input
                    className="border p-2 rounded"
                    type="date"
                    value={dataDevolucaoPrevista}
                    onChange={(e) => setDataDevolucaoPrevista(e.target.value)}
                    required
                />

                <button type="submit" className="bg-black text-white p-2 rounded">
                    Confirmar Locação
                </button>
            </form>
        </main>
    );
}
