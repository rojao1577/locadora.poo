"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function LocacoesPage() {
    const router = useRouter();
    const [locacoes, setLocacoes] = useState([]);
    const [veiculos, setVeiculos] = useState([]);

    useEffect(() => {
        carregarDados();
    }, []);

    async function carregarDados() {
        try {
            const [resLocacoes, resVeiculos] = await Promise.all([
                fetch("http://localhost:8080/api/v1/locacoes"),
                fetch("http://localhost:8080/veiculos")
            ]);

            if (resLocacoes.ok) setLocacoes(await resLocacoes.json());
            if (resVeiculos.ok) setVeiculos(await resVeiculos.json());
        } catch (error) {
            console.error("Erro ao buscar dados:", error);
        }
    }

    async function finalizarLocacao(id) {
        try {
            const dataHoje = new Date().toISOString().split('T')[0];
            const response = await fetch(`http://localhost:8080/api/v1/locacoes/${id}/finalizar`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ dataDevolucao: dataHoje })
            });

            if (response.ok) {
                carregarDados();
            } else {
                const erro = await response.text();
                alert("Erro ao finalizar a locação: " + erro);
            }
        } catch (error) {
            console.error("Erro ao finalizar:", error);
            alert("Falha ao comunicar com o servidor.");
        }
    }

    async function excluirLocacao(id) {
        if (!confirm("Tem certeza que deseja excluir esta locação?")) return;

        try {
            const response = await fetch(`http://localhost:8080/api/v1/locacoes/${id}`, {
                method: "DELETE",
            });

            if (response.ok || response.status === 204) {
                carregarDados();
            } else {
                alert("Erro ao excluir a locação. O backend pode ter bloqueado.");
            }
        } catch (error) {
            console.error("Erro ao excluir:", error);
            alert("Falha de conexão com o servidor.");
        }
    }


    const renderVeiculos = (locacao) => {
        if (locacao.veiculosIds && locacao.veiculosIds.length > 0) {
            return locacao.veiculosIds.map(id => {
                const veiculoEncontrado = veiculos.find(v => v.id === id);
                return veiculoEncontrado ? `${veiculoEncontrado.modelo}` : `ID: ${id}`;
            }).join(', ');
        }
        return "-";
    };

    return (
        <main className="p-6 max-w-5xl mx-auto text-white">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Listagem de Locações</h1>
                <Link href="/locacoes/nova" className="bg-black text-white px-4 py-2 border border-gray-600 rounded hover:bg-gray-800 transition font-bold">
                    + Nova Locação
                </Link>
            </div>

            <div className="bg-white text-black rounded-lg shadow overflow-hidden">
                <table className="w-full text-left border-collapse">
                    <thead className="bg-gray-100 border-b">
                        <tr>
                            <th className="p-4">ID</th>
                            <th className="p-4">Cliente (ID)</th>
                            <th className="p-4">Veículo</th>
                            <th className="p-4">Data Prevista</th>
                            <th className="p-4">Devolução Real</th>
                            <th className="p-4">Valor Total</th>
                            <th className="p-4">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {locacoes.map((loc) => (
                            <tr key={loc.id} className="border-b hover:bg-gray-50">
                                <td className="p-4 text-gray-500">#{loc.id}</td>
                                <td className="p-4">{loc.clienteId || "-"}</td>
                                <td className="p-4 font-semibold text-blue-700">{renderVeiculos(loc)}</td>
                                <td className="p-4">{loc.dataDevolucaoPrevista}</td>
                                <td className="p-4 font-bold text-green-600">
                                    {loc.dataDevolucaoReal || <span className="text-orange-500 font-normal">Pendente</span>}
                                </td>
                                <td className="p-4 font-medium">
                                    {loc.valorTotal != null ? `R$ ${Number(loc.valorTotal).toFixed(2)}` : "-"}
                                </td>
                                <td className="p-4 flex gap-2">
                                    {!loc.dataDevolucaoReal && (
                                        <button onClick={() => finalizarLocacao(loc.id)} className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 transition font-bold shadow">
                                            Finalizar
                                        </button>
                                    )}
                                    <button onClick={() => excluirLocacao(loc.id)} className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 transition font-bold shadow">
                                        Excluir
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </main>
    );
}