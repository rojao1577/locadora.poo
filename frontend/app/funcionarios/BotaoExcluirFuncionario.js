"use client";

import { useRouter } from "next/navigation";

export default function BotaoExcluirFuncionario({ id }) {
    const router = useRouter();

    async function handleExcluir() {
        if (confirm("Deseja realmente excluir este funcionário?")) {
            const res = await fetch(`http://localhost:8080/funcionarios/${id}`, {
                method: "DELETE",
            });
            if (res.ok) {
                router.refresh();
            }
        }
    }

    return (
        <button onClick={handleExcluir}>
            Excluir
        </button>
    );
}