const API_URL = process.env.BACKEND_URL || "http://localhost:8080";

async function buscarItensLocacao() {
    const resposta = await fetch(`${API_URL}/itenslocacao/`, {
        cache: "no-store",
    });

    if (!resposta.ok) {
        throw new Error("Erro ao buscar itens de locação.");
    }

    return resposta.json();
}

export default async function ItensLocacaoPage() {
    const itens = await buscarItensLocacao();

    return (
        <main className="p-6 max-w-6xl mx-auto">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold text-white">Itens de Locação</h1>
            </div>

            <p className="text-sm text-gray-400 mb-4">
                Os itens são criados automaticamente ao registrar uma nova locação.
                Aqui você pode apenas consultar os registros.
            </p>

            <table className="w-full border-collapse border border-gray-300">
                <thead>
                <tr className="bg-gray-100 text-gray-900">
                    <th className="border border-gray-700 p-2">ID</th>
                    <th className="border border-gray-700 p-2">Valor Diária</th>
                    <th className="border border-gray-700 p-2">Dias</th>
                    <th className="border border-gray-700 p-2">Locação</th>
                    <th className="border border-gray-700 p-2">Veículo</th>
                </tr>
                </thead>
                <tbody>
                {itens.length === 0 ? (
                    <tr>
                        <td colSpan="5" className="border border-gray-700 p-4 text-center text-gray-400">
                            Nenhum item de locação cadastrado.
                        </td>
                    </tr>
                ) : (
                    itens.map((item) => (
                        <tr key={item.id} className="text-center text-gray-200">
                            <td className="border border-gray-700 p-2">{item.id}</td>
                            <td className="border border-gray-700 p-2">R$ {Number(item.valorDiaria).toFixed(2)}</td>
                            <td className="border border-gray-700 p-2">{item.dias}</td>
                            <td className="border border-gray-700 p-2">#{item.locacaoId}</td>
                            <td className="border border-gray-700 p-2">{item.veiculoPlaca}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </main>
    );
}