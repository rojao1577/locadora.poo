"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function NovoClientePage() {
  const router = useRouter();

  const [nome, setNome] = useState("");
  const [cpf, setCpf] = useState("");
  const [telefone, setTelefone] = useState("");
  const [endereco, setEndereco] = useState("");
  const [email, setEmail] = useState("");
  const [scoreCredito, setScoreCredito] = useState("");

  const [mensagem, setMensagem] = useState("");

  async function cadastrarCliente(event) {
    event.preventDefault();

    const resposta = await fetch("http://localhost:8080/clientes/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nome: nome,
        cpf: cpf,
        telefone: telefone,
        endereco: endereco,
        email: email,
        scoreCredito: Number(scoreCredito),
      }),
    });

    if (resposta.ok) {
      router.push("/clientes");
    } else {
      const erro = await resposta.text();
      setMensagem(erro || "Erro ao cadastrar cliente.");
    }
  }

  return (
    <main className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-4">Novo Cliente</h1>
      {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}
      <form onSubmit={cadastrarCliente} className="flex flex-col gap-3">
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(event) => setNome(event.target.value)}
          required
        />
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="CPF"
          value={cpf}
          onChange={(event) => setCpf(event.target.value)}
          required
        />
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="Telefone"
          value={telefone}
          onChange={(event) => setTelefone(event.target.value)}
        />
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="Endereço"
          value={endereco}
          onChange={(event) => setEndereco(event.target.value)}
        />
        <input
          className="border p-2 rounded"
          type="email"
          placeholder="Email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
        />
        <input
          className="border p-2 rounded"
          type="number"
          min="0"
          placeholder="Score de crédito"
          value={scoreCredito}
          onChange={(event) => setScoreCredito(event.target.value)}
          required
        />
        <button type="submit" className="bg-black text-white p-2 rounded">
          Cadastrar
        </button>
      </form>
    </main>
  );
}
