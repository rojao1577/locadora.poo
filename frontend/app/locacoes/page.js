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
                fetch("http://localhost:8080/api/v1/locacoes").catch(() => null),
                fetch("http://localhost:8080/veiculos").catch(() => null)
            ]);

            if (resLocacoes?.ok) setLocacoes(await resLocacoes.json());
            if (resVeiculos?.ok) setVeiculos(await resVeiculos.json());
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
                alert("Erro ao excluir a locação.");
            }
        } catch (error) {
            console.error("Erro ao excluir:", error);
            alert("Falha de conexão com o servidor.");
        }
    }

    const renderVeiculos = (locacao) => {
        if (locacao.veiculosIds && locacao.veiculosIds.length > 0) {
            return locacao.veiculosIds.map(id => {
                const veiculoEncontrado = veiculos.find(v => String(v.id) === String(id));
                return veiculoEncontrado ? `${veiculoEncontrado.modelo}` : `ID: ${id}`;
            }).join(', ');
        }
        return "-";
    };

    return (
        <main className="p-6 max-w-6xl mx-auto text-white">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Listagem de Locações</h1>
                <Link href="/locacoes/nova" className="bg-black text-white px-4 py-2 border border-gray-600 rounded hover:bg-gray-800 transition font-bold text-sm">
                    + Nova Locação
                </Link>
            </div>

            <div className="overflow-x-auto">
                <table className="w-full text-center border-collapse border border-gray-600">
                    <thead>
                        <tr className="bg-white text-black font-bold">
                            <th className="border border-gray-600 p-3">ID</th>
                            <th className="border border-gray-600 p-3">Cliente</th>
                            <th className="border border-gray-600 p-3">Veículo</th>
                            <th className="border border-gray-600 p-3">Data Retirada</th>
                            <th className="border border-gray-600 p-3">Data Prevista</th>
                            <th className="border border-gray-600 p-3">Devolução Real</th>
                            <th className="border border-gray-600 p-3">Valor Total</th>
                            <th className="border border-gray-600 p-3">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {locacoes.length === 0 ? (
                            <tr>
                                <td colSpan="8" className="border border-gray-600 p-4 text-center text-gray-400">
                                    Nenhuma locação encontrada.
                                </td>
                            </tr>
                        ) : (
                            locacoes.map((loc) => (
                                <tr key={loc.id} className="text-white hover:bg-gray-900 transition">
                                    <td className="border border-gray-600 p-3">#{loc.id}</td>
                                    <td className="border border-gray-600 p-3">{loc.clienteId || "-"}</td>
                                    <td className="border border-gray-600 p-3 font-semibold text-blue-400">{renderVeiculos(loc)}</td>
                                    <td className="border border-gray-600 p-3">{loc.dataLocacao || "-"}</td>
                                    <td className="border border-gray-600 p-3">{loc.dataDevolucaoPrevista}</td>
                                    <td className="border border-gray-600 p-3">
                                        {loc.dataDevolucaoReal ? (
                                            <span className="text-green-400">{loc.dataDevolucaoReal}</span>
                                        ) : (
                                            <span className="text-orange-400 font-bold">Pendente</span>
                                        )}
                                    </td>

                                    <td className="border border-gray-600 p-3 font-bold text-green-400">
                                        R$ {Number(loc.valorTotal || 0).toFixed(2)}
                                    </td>

                                    <td className="border border-gray-600 p-3 space-x-2">
                                        {!loc.dataDevolucaoReal && (
                                            <button
                                                onClick={() => finalizarLocacao(loc.id)}
                                                className="bg-green-600 text-white px-3 py-1 rounded text-sm hover:bg-green-700 transition"
                                            >
                                                Finalizar
                                            </button>
                                        )}
                                        <button
                                            onClick={() => excluirLocacao(loc.id)}
                                            className="bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700 transition"
                                        >
                                            Excluir
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </main>
    );
}