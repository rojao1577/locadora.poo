"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function LocacoesPage() {
    const router = useRouter();
    const [locacoes, setLocacoes] = useState([]);

    useEffect(() => {
        carregarLocacoes();
    }, []);

    async function carregarLocacoes() {
        try {
            const response = await fetch("http://localhost:8080/api/v1/locacoes");
            if (response.ok) {
                const data = await response.json();
                setLocacoes(data);
            }
        } catch (error) {
            console.error("Erro ao buscar locações:", error);
        }
    }

    async function finalizarLocacao(id) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/locacoes/${id}/finalizar`, {
                method: "PUT",
            });
            if (response.ok) {
                carregarLocacoes(); // Recarrega a tabela após finalizar
            } else {
                alert("Erro ao finalizar a locação.");
            }
        } catch (error) {
            console.error("Erro ao finalizar locação:", error);
        }
    }

    const renderVeiculos = (locacao) => {
        if (locacao.itens && locacao.itens.length > 0) {
            return locacao.itens.map(i => i.veiculo?.modelo || `ID: ${i.veiculo?.id}`).join(', ');
        }
        if (locacao.veiculos && locacao.veiculos.length > 0) {
            return locacao.veiculos.map(v => v.modelo || `ID: ${v.id}`).join(', ');
        }
        if (locacao.veiculosIds && locacao.veiculosIds.length > 0) {
            return locacao.veiculosIds.map(id => `ID: ${id}`).join(', ');
        }
        return "Veículo indisponível";
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
                                <td className="p-4">{loc.clienteId || loc.cliente?.nome || loc.cliente?.id || "-"}</td>
                                <td className="p-4 font-semibold text-blue-700">{renderVeiculos(loc)}</td>
                                <td className="p-4">{loc.dataDevolucaoPrevista}</td>
                                <td className="p-4 font-bold text-green-600">
                                    {loc.dataDevolucaoReal || <span className="text-orange-500 font-normal">Pendente</span>}
                                </td>
                                <td className="p-4">
                                    {loc.valorTotal ? `R$ ${loc.valorTotal.toFixed(2)}` : "-"}
                                </td>
                                <td className="p-4">
                                    {!loc.dataDevolucaoReal && (
                                        <button
                                            onClick={() => finalizarLocacao(loc.id)}
                                            className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 transition font-bold shadow"
                                        >
                                            Finalizar
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))}
                        {locacoes.length === 0 && (
                            <tr>
                                <td colSpan="7" className="p-4 text-center text-gray-500">
                                    Nenhuma locação encontrada.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </main>
    );
}