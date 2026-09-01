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
<<<<<<< HEAD
                carregarDados();
=======
                carregarLocacoes();
>>>>>>> f9d723f324d052fd8dbd61511f15cac1dd6767ee
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
<<<<<<< HEAD
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
=======
        const confirmar = window.confirm("Deseja realmente excluir esta locação?");

        if (!confirmar) {
            return;
        }

        try {
            const resposta = await fetch(`http://localhost:8080/api/v1/locacoes/${id}`, {
                method: "DELETE",
            });

            if (resposta.ok) {
                carregarLocacoes();
            } else {
                const erro = await resposta.text();
                alert(erro || "Não foi possível excluir a locação.");
            }
        } catch (error) {
            console.error("Erro ao excluir locação:", error);
>>>>>>> f9d723f324d052fd8dbd61511f15cac1dd6767ee
            alert("Falha de conexão com o servidor.");
        }
    }

<<<<<<< HEAD

    const renderVeiculos = (locacao) => {
        if (locacao.veiculosIds && locacao.veiculosIds.length > 0) {
            return locacao.veiculosIds.map(id => {
                const veiculoEncontrado = veiculos.find(v => v.id === id);
                return veiculoEncontrado ? `${veiculoEncontrado.modelo}` : `ID: ${id}`;
=======
    const renderVeiculos = (locacao) => {
        const itensLista = locacao.itens || locacao.veiculos || locacao.itensLocacao || [];
        if (itensLista.length > 0) {
            return itensLista.map(item => {
                const v = item.veiculo || item;
                const marca = v.marca || "";
                const modelo = v.modelo || "";
                const nomeCompleto = `${marca} ${modelo}`.trim();
                return nomeCompleto || `Veículo ID: ${v.id || item.veiculoId}`;
>>>>>>> f9d723f324d052fd8dbd61511f15cac1dd6767ee
            }).join(', ');
        }
        return "-";
    };

    const renderCliente = (locacao) => {
        const cliente = locacao.cliente;
        if (cliente) {
            const nome = cliente.nome || "Cliente sem nome";
            const id = cliente.id ? ` (ID: ${cliente.id})` : "";
            return `${nome}${id}`;
        }
        return locacao.clienteId ? `ID: ${locacao.clienteId}` : "Cliente não informado";
    };

    const calcularValorTotal = (locacao) => {
        const dataInicioStr = locacao.dataLocacao || locacao.dataInicio;
        const dataFimStr = locacao.dataDevolucaoReal || locacao.dataDevolucaoPrevista;

        if (!dataInicioStr || !dataFimStr) return locacao.valorTotal || 0;

        const diffTime = new Date(dataFimStr) - new Date(dataInicioStr);
        const dias = Math.max(1, Math.ceil(diffTime / (1000 * 60 * 60 * 24)));

        const itensLista = locacao.itens || locacao.veiculos || locacao.itensLocacao || [];
        let totalDiarias = 0;

        itensLista.forEach(item => {
            const v = item.veiculo || item;
            const diaria = v.valorDiaria || v.diaria || 0;
            totalDiarias += Number(diaria);
        });

        return dias * totalDiarias;
    };

    return (
        <main className="p-6 max-w-5xl mx-auto text-white">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Listagem de Locações</h1>
                <Link href="/locacoes/nova" className="bg-black text-white px-4 py-2 border border-gray-600 rounded hover:bg-gray-800 transition font-bold">
                    + Nova Locação
                </Link>
            </div>

<<<<<<< HEAD
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
=======
            <table className="w-full border-collapse border border-gray-300">
                <thead>
                <tr className="bg-gray-100 text-gray-900">
                    <th className="border p-2">ID</th>
                    <th className="border p-2">Cliente</th>
                    <th className="border p-2">Veículo(s)</th>
                    <th className="border p-2">Data da Locação</th>
                    <th className="border p-2">Data de Devolução</th>
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
                    locacoes.map((loc) => {
                        const valorCalculado = calcularValorTotal(loc);
                        return (
                            <tr key={loc.id} className="text-center">
                                <td className="border p-2">#{loc.id}</td>
                                <td className="border p-2">{renderCliente(loc)}</td>
                                <td className="border p-2">{renderVeiculos(loc)}</td>
                                <td className="border p-2">{loc.dataLocacao || loc.dataInicio || "-"}</td>
                                <td className="border p-2">{loc.dataDevolucaoReal || loc.dataDevolucaoPrevista || "Pendente"}</td>
                                <td className="border p-2">
                                    {valorCalculado ? `R$ ${valorCalculado.toFixed(2)}` : "-"}
                                </td>
                                <td className="border p-2 space-x-2">
                                    {!loc.dataDevolucaoReal && (
                                        <button
                                            onClick={() => finalizarLocacao(loc.id)}
                                            className="bg-green-600 text-white px-2 py-1 rounded text-sm"
                                        >
                                            Finalizar
                                        </button>
                                    )}
                                    <button
                                        onClick={() => excluirLocacao(loc.id)}
                                        className="bg-red-600 text-white px-2 py-1 rounded text-sm"
                                    >
>>>>>>> f9d723f324d052fd8dbd61511f15cac1dd6767ee
                                        Excluir
                                    </button>
                                </td>
                            </tr>
<<<<<<< HEAD
                        ))}
                    </tbody>
                </table>
            </div>
=======
                        );
                    })
                )}
                </tbody>
            </table>
>>>>>>> f9d723f324d052fd8dbd61511f15cac1dd6767ee
        </main>
    );
}