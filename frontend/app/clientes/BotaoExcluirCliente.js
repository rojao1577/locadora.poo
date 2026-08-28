"use client";

import { useRouter } from "next/navigation";

export default function BotaoExcluirCliente({ id }) {
  const router = useRouter();

  async function excluirCliente() {
    const confirmar = window.confirm(
      "Deseja realmente excluir este cliente?"
    );

    if (!confirmar) {
      return;
    }

    const resposta = await fetch(
      `http://localhost:8080/clientes/${id}`,
      {
        method: "DELETE",
      }
    );

    if (resposta.ok) {
      router.refresh();
    } else {
      const erro = await resposta.text();
      alert(erro || "Não foi possível excluir o cliente.");
    }
  }

  return (
    <button
      onClick={excluirCliente}
      className="bg-red-600 text-white px-2 py-1 rounded"
    >
      Excluir
    </button>
  );
}