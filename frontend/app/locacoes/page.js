"use client";

import { useState, useEffect } from "react";
import Link from "next/link";

export default function LocacoesPage() {
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

    // Função para extrair o veículo de forma segura dependendo do formato do seu DTO
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
        <main className="p-6 max-w-6xl mx-auto">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Locações</h1>
                <Link href="/locacoes/nova" className="bg-black text-white px-4 py-2 rounded">
                    Nova Locação
                </Link>
            </div>

            <table className="w-full border-collapse border border-gray-300">
                <thead>
                    <tr className="bg-gray-100 text-gray-900">
                        <th className="border p-2">ID</th>
                        <th className="border p-2">Cliente (ID)</th>
                        <th className="border p-2">Veículo</th>
                        <th className="border p-2">Data Prevista</th>
                        <th className="border p-2">Devolução Real</th>
                        <th className="border p-2">Valor Total</th>
                        <th className="border p-2">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {locacoes.length === 0 ? (
                        <tr>
                            <td colSpan="7" className="border p-4 text-center">Nenhuma locação encontrada.</td>
                        </tr>
                    ) : (
                        locacoes.map((loc) => (
                            <tr key={loc.id} className="text-center">
                                <td className="border p-2">#{loc.id}</td>
                                <td className="border p-2">{loc.clienteId || loc.cliente?.nome || loc.cliente?.id || "-"}</td>
                                <td className="border p-2">{renderVeiculos(loc)}</td>
                                <td className="border p-2">{loc.dataDevolucaoPrevista}</td>
                                <td className="border p-2">
                                    {loc.dataDevolucaoReal || "Pendente"}
                                </td>
                                <td className="border p-2">
                                    {loc.valorTotal ? `R$ ${loc.valorTotal.toFixed(2)}` : "-"}
                                </td>
                                <td className="border p-2">
                                    {!loc.dataDevolucaoReal && (
                                        <button
                                            onClick={() => finalizarLocacao(loc.id)}
                                            className="bg-green-600 text-white px-2 py-1 rounded"
                                        >
                                            Finalizar
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </main>
    );
}
