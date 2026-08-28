"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function NovaCategoriaPage() {
  const router = useRouter();

  const [nome, setNome] = useState("");
  const [valorDiariaBase, setValorDiariaBase] = useState("");
  const [mensagem, setMensagem] = useState("");

  async function cadastrarCategoria(event) {
    event.preventDefault();

    const resposta = await fetch("http://localhost:8080/categorias", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nome: nome,
        valorDiariaBase: Number(valorDiariaBase),
      }),
    });

    if (resposta.ok) {
      router.push("/categorias");
    } else {
      const erro = await resposta.text();
      setMensagem(erro || "Erro ao cadastrar categoria.");
    }
  }

  return (
    <main className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-4">Nova Categoria</h1>
      {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}
      <form onSubmit={cadastrarCategoria} className="flex flex-col gap-3">
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
          type="number"
          step="0.01"
          min="0"
          placeholder="Valor da diária"
          value={valorDiariaBase}
          onChange={(event) => setValorDiariaBase(event.target.value)}
          required
        />
        <button type="submit" className="bg-black text-white p-2 rounded">
          Cadastrar
        </button>
      </form>
    </main>
  );
}
