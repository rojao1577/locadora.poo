"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function FuncionariosPage() {
    const [funcionarios, setFuncionarios] = useState([]);
    const [erro, setErro] = useState("");
    const router = useRouter();

    async function buscarFuncionarios() {
        try {
            const res = await fetch("http://localhost:8080/funcionarios");
            if (res.ok) {
                const dados = await res.json();
                setFuncionarios(dados);
            } else {
                setErro("Erro ao buscar funcionários do servidor.");
            }
        } catch (e) {
            setErro("Não foi possível conectar ao servidor (Spring Boot).");
        }
    }

    async function handleExcluir(id) {
        if (confirm("Deseja realmente excluir este funcionário?")) {
            try {
                const res = await fetch(`http://localhost:8080/funcionarios/${id}`, {
                    method: "DELETE",
                });
                if (res.ok) {
                    buscarFuncionarios();
                }
            } catch (e) {
                alert("Erro ao tentar excluir funcionário.");
            }
        }
    }

    useEffect(() => {
        buscarFuncionarios();
    }, []);

    return (
        <main className="p-6 max-w-6xl mx-auto">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Funcionários</h1>
                <Link href="/funcionarios/novo" className="bg-black text-white px-4 py-2 rounded">
                    Novo Funcionário
                </Link>
            </div>

            {erro && <p className="text-red-500 mb-4">{erro}</p>}

            <table className="w-full border-collapse border border-gray-300">
                <thead>
                <tr className="bg-gray-100">
                    <th className="border p-2">ID</th>
                    <th className="border p-2">Nome</th>
                    <th className="border p-2">CPF</th>
                    <th className="border p-2">Cargo</th>
                    <th className="border p-2">Salário</th>
                    <th className="border p-2">Ações</th>
                </tr>
                </thead>
                <tbody>
                {funcionarios.length === 0 ? (
                    <tr>
                        <td colSpan="6" className="border p-4 text-center">Nenhum funcionário encontrado.</td>
                    </tr>
                ) : (
                    funcionarios.map((f) => (
                        <tr key={f.id} className="text-center">
                            <td className="border p-2">{f.id}</td>
                            <td className="border p-2">{f.nome}</td>
                            <td className="border p-2">{f.cpf}</td>
                            <td className="border p-2">{f.cargo}</td>
                            <td className="border p-2">R$ {f.salario}</td>
                            <td className="border p-2">
                                <button onClick={() => handleExcluir(f.id)} className="bg-red-600 text-white px-2 py-1 rounded">
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