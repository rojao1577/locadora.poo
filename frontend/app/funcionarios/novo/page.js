"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function NovoFuncionarioPage() {
    const router = useRouter();

    const [nome, setNome] = useState("");
    const [cpf, setCpf] = useState("");
    const [telefone, setTelefone] = useState("");
    const [endereco, setEndereco] = useState("");
    const [cargo, setCargo] = useState("");
    const [salario, setSalario] = useState("");
    const [dataContratacao, setDataContratacao] = useState("");
    const [mensagem, setMensagem] = useState("");

    async function handleSubmit(e) {
        e.preventDefault();
        setMensagem("");

        try {
            const funcionario = {
                nome,
                cpf,
                telefone,
                endereco,
                cargo,
                salario: parseFloat(salario),
                dataContratacao,
            };

            const resposta = await fetch("http://localhost:8080/funcionarios", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(funcionario),
            });

            if (resposta.ok) {
                router.push("/funcionarios");
            } else {
                setMensagem("Erro ao cadastrar funcionário.");
            }
        } catch (e) {
            setMensagem("Falha de comunicação com o servidor.");
        }
    }

    return (
        <main className="p-6 max-w-lg mx-auto">
            <h1 className="text-2xl font-bold mb-4">Cadastrar Funcionário</h1>
            {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}
            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
                <input className="border p-2 rounded" type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
                <input className="border p-2 rounded" type="text" placeholder="CPF" value={cpf} onChange={(e) => setCpf(e.target.value)} required />
                <input className="border p-2 rounded" type="text" placeholder="Telefone" value={telefone} onChange={(e) => setTelefone(e.target.value)} />
                <input className="border p-2 rounded" type="text" placeholder="Endereço" value={endereco} onChange={(e) => setEndereco(e.target.value)} />
                <input className="border p-2 rounded" type="text" placeholder="Cargo" value={cargo} onChange={(e) => setCargo(e.target.value)} required />
                <input className="border p-2 rounded" type="number" step="0.01" placeholder="Salário" value={salario} onChange={(e) => setSalario(e.target.value)} required />
                <input className="border p-2 rounded" type="date" value={dataContratacao} onChange={(e) => setDataContratacao(e.target.value)} required />
                <button type="submit" className="bg-black text-white p-2 rounded">Salvar</button>
            </form>
        </main>
    );
}