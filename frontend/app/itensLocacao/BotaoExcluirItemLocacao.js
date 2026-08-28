"use client";

import { useRouter } from "next/navigation";

export default function BotaoExcluirItemLocacao({ id }) {
  const router = useRouter();

  async function excluirItem() {
    const confirmar = window.confirm(
      "Deseja realmente excluir este item de locação?"
    );

    if (!confirmar) {
      return;
    }

    const resposta = await fetch(
      `http://localhost:8080/itenslocacao/${id}`,
      {
        method: "DELETE",
      }
    );

    if (resposta.ok) {
      router.refresh();
    } else {
      const erro = await resposta.text();
      alert(erro || "Não foi possível excluir o item de locação.");
    }
  }

  return (
    <button
      onClick={excluirItem}
      className="bg-red-600 text-white px-2 py-1 rounded"
    >
      Excluir
    </button>
  );
}
