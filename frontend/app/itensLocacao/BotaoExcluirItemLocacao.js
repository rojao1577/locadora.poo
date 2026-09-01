"use client";

import { useRouter } from "next/navigation";

export default function BotaoExcluirItemLocacao({ id }) {
    const router = useRouter();

    async function handleExcluir() {
        if (!confirm("Tem certeza que deseja excluir este item de locação?")) return;

        try {
            const resposta = await fetch(`http://localhost:8080/itenslocacao/${id}`, {
                method: "DELETE",
            });

            if (resposta.ok || resposta.status === 204) {
                router.refresh();
            } else {
                alert("Erro ao excluir o item. Verifique se o backend permite esta exclusão.");
            }
        } catch (erro) {
            console.error("Erro ao excluir:", erro);
            alert("Falha de conexão com o servidor.");
        }
    }

    return (
        <button
            onClick={handleExcluir}
            className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 transition font-bold shadow"
        >
            Excluir
        </button>
    );
}