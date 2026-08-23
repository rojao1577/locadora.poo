"use client";

import { useRouter } from "next/navigation";

export default function BotaoExcluirVeiculo({ id }) {
  const router = useRouter();

  async function excluirVeiculo() {
    const confirmar = window.confirm(
      "Deseja realmente excluir este veículo?"
    );

    if (!confirmar) {
      return;
    }

    const resposta = await fetch(
      `http://localhost:8080/veiculos/${id}`,
      {
        method: "DELETE",
      }
    );

    if (resposta.ok) {
      router.refresh();
    } else {
      const erro = await resposta.text();
      alert(erro || "Não foi possível excluir o veículo.");
    }
  }

  return (
    <button
      onClick={excluirVeiculo}
      className="bg-red-600 text-white px-3 py-1 rounded"
    >
      Excluir
    </button>
  );
}