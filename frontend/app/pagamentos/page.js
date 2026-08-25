"use client";

import { useState, useEffect } from "react";
import Link from "next/link";

export default function PagamentosPage() {
    const [pagamentos, setPagamentos] = useState([]);
    const [erro, setErro] = useState("");

    async function buscarPagamentos() {
        try {
            const res = await fetch("http://localhost:8080/pagamentos");
            if (res.ok) {
                const dados = await res.json();
                setPagamentos(dados);
            } else {
                setErro("Erro ao carregar pagamentos.");
            }
        } catch (e) {
            setErro("Servidor indisponível.");
        }
    }

    async function handleExcluir(id) {
        if (confirm("Deseja cancelar/excluir este pagamento?")) {
            try {
                const res = await fetch(`http://localhost:8080/pagamentos/${id}`, { method: "DELETE" });
                if (res.ok) buscarPagamentos();
            } catch (e) {
                alert("Erro ao excluir pagamento.");
            }
        }
    }

    useEffect(() => {
        buscarPagamentos();
    }, []);

    return (
        <main className="p-6 max-w-6xl mx-auto">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Pagamentos</h1>
                <Link href="/pagamentos/novo" className="bg-black text-white px-4 py-2 rounded">
                    Novo Pagamento
                </Link>
            </div>

            {erro && <p className="text-red-500 mb-4">{erro}</p>}

            <table className="w-full border-collapse border border-gray-300">
                <thead>
                <tr className="bg-gray-100">
                    <th className="border p-2">ID</th>
                    <th className="border p-2">Locação</th>
                    <th className="border p-2">Valor</th>
                    <th className="border p-2">Data</th>
                    <th className="border p-2">Forma</th>
                    <th className="border p-2">Ações</th>
                </tr>
                </thead>
                <tbody>
                {pagamentos.length === 0 ? (
                    <tr>
                        <td colSpan="6" className="border p-4 text-center">Nenhum pagamento registrado.</td>
                    </tr>
                ) : (
                    pagamentos.map((p) => (
                        <tr key={p.id} className="text-center">
                            <td className="border p-2">{p.id}</td>
                            <td className="border p-2">#{p.locacao?.id || p.locacaoId}</td>
                            <td className="border p-2">R$ {p.valor}</td>
                            <td className="border p-2">{p.dataPagamento}</td>
                            <td className="border p-2">{p.formaPagamento}</td>
                            <td className="border p-2">
                                <button onClick={() => handleExcluir(p.id)} className="bg-red-600 text-white px-2 py-1 rounded">
                                    Excluir
                                </button>
                            </td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </main>
    );
}