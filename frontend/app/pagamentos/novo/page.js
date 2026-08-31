"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

export default function NovoPagamentoPage() {
    const router = useRouter();

    const [idLocacao, setIdLocacao] = useState("");
    const [valor, setValor] = useState("");
    const [dataPagamento, setDataPagamento] = useState("");
    const [formaPagamento, setFormaPagamento] = useState("PIX");
    const [locacoes, setLocacoes] = useState([]);
    const [mensagem, setMensagem] = useState("");

    useEffect(() => {
        async function buscarLocacoes() {
            try {
                const res = await fetch("http://localhost:8080/api/v1/locacoes");
                if (res.ok) {
                    const dados = await res.json();
                    setLocacoes(dados);
                }
            } catch (e) {
                console.error("Não foi possível carregar as locações.");
            }
        }
        buscarLocacoes();
    }, []);

    async function handleSubmit(e) {
        e.preventDefault();
        try {
            const pagamento = {
                idLocacao: parseInt(idLocacao),
                valor: parseFloat(valor),
                dataPagamento,
                formaPagamento,
            };

            const resposta = await fetch("http://localhost:8080/pagamentos", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(pagamento),
            });

            if (resposta.ok) {
                router.push("/pagamentos");
            } else {
                setMensagem("Erro ao processar pagamento.");
            }
        } catch (e) {
            setMensagem("Erro de comunicação com o servidor.");
        }
    }

    return (
        <main className="p-6 max-w-lg mx-auto">
            <h1 className="text-2xl font-bold mb-4 text-white">Registrar Pagamento</h1>
            {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}

            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
                {/* Select de locações corrigido */}
                <select
                    className="border border-gray-700 bg-gray-900 text-white p-2 rounded"
                    value={idLocacao}
                    onChange={(e) => setIdLocacao(e.target.value)}
                    required
                >
                    <option value="" disabled className="bg-gray-900 text-gray-400">
                        Selecione a Locação...
                    </option>
                    {locacoes.map((l) => (
                        <option key={l.id} value={l.id} className="bg-gray-900 text-white">
                            Locação #{l.id}
                        </option>
                    ))}
                </select>

                <input
                    className="border border-gray-700 bg-gray-900 text-white p-2 rounded placeholder-gray-400"
                    type="number"
                    step="0.01"
                    placeholder="Valor"
                    value={valor}
                    onChange={(e) => setValor(e.target.value)}
                    required
                />

                <input
                    className="border border-gray-700 bg-gray-900 text-white p-2 rounded"
                    type="date"
                    value={dataPagamento}
                    onChange={(e) => setDataPagamento(e.target.value)}
                    required
                />

                {/* Select de forma de pagamento corrigido */}
                <select
                    className="border border-gray-700 bg-gray-900 text-white p-2 rounded"
                    value={formaPagamento}
                    onChange={(e) => setFormaPagamento(e.target.value)}
                    required
                >
                    <option value="PIX" className="bg-gray-900 text-white">PIX</option>
                    <option value="CARTAO" className="bg-gray-900 text-white">Cartão</option>
                    <option value="DINHEIRO" className="bg-gray-900 text-white">Dinheiro</option>
                </select>

                <button type="submit" className="bg-white text-black font-medium p-2 rounded hover:bg-gray-200 transition">
                    Confirmar
                </button>
            </form>
        </main>
    );
}