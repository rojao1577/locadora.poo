"use client";

import { useRouter } from "next/navigation";

export default function BotaoExcluirCategoria({ id }) {
  const router = useRouter();

  async function excluirCategoria() {
    const confirmar = window.confirm(
      "Deseja realmente excluir esta categoria?"
    );

    if (!confirmar) {
      return;
    }

    const resposta = await fetch(
      `http://localhost:8080/categorias/${id}`,
      {
        method: "DELETE",
      }
    );

    if (resposta.ok) {
      router.refresh();
    } else {
      const erro = await resposta.text();
      alert(erro || "Não foi possível excluir a categoria.");
    }
  }

  return (
    <button
      onClick={excluirCategoria}
      className="bg-red-600 text-white px-3 py-1 rounded"
    >
      Excluir
    </button>
  );
}