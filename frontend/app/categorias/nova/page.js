"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";

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
      setMensagem("Categoria cadastrada com sucesso.");

      setTimeout(() => {
        router.push("/categorias");
      }, 1000);
    } else {
      const erro = await resposta.text();
      setMensagem(erro || "Erro ao cadastrar categoria.");
    }
  }

  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-xl">

        <h1 className="text-3xl font-bold mb-6">
          Nova Categoria
        </h1>

        <form
          onSubmit={cadastrarCategoria}
          className="bg-white p-6 rounded-lg shadow"
        >
          <div className="mb-5">
            <label className="block mb-2 font-medium">
              Nome
            </label>

            <input
              type="text"
              value={nome}
              onChange={(event) => setNome(event.target.value)}
              placeholder="Ex: Executivo"
              className="w-full border border-gray-300 rounded p-3"
              required
            />
          </div>

          <div className="mb-6">
            <label className="block mb-2 font-medium">
              Valor da diária
            </label>

            <input
              type="number"
              step="0.01"
              min="0"
              value={valorDiariaBase}
              onChange={(event) =>
                setValorDiariaBase(event.target.value)
              }
              placeholder="Ex: 150.00"
              className="w-full border border-gray-300 rounded p-3"
              required
            />
          </div>

          <div className="flex gap-3">
            <button
              type="submit"
              className="bg-black text-white px-4 py-2 rounded hover:bg-gray-800"
            >
              Cadastrar
            </button>

            <Link
              href="/categorias"
              className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
            >
              Cancelar
            </Link>
          </div>

          {mensagem && (
            <p className="mt-4 font-medium">
              {mensagem}
            </p>
          )}

        </form>
      </div>
    </main>
  );
}