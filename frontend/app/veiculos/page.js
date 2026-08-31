import Link from "next/link";
import BotaoExcluirVeiculo from "./BotaoExcluirVeiculo";

const API_URL = process.env.BACKEND_URL || "http://localhost:8080";

async function buscarVeiculos() {
  const resposta = await fetch(`${API_URL}/veiculos`, {
    cache: "no-store",
  });

  if (!resposta.ok) {
    throw new Error("Erro ao buscar veículos.");
  }

  return resposta.json();
}

async function buscarCategorias() {
  const resposta = await fetch(`${API_URL}/categorias`, {
    cache: "no-store",
  });

  if (!resposta.ok) {
    throw new Error("Erro ao buscar categorias.");
  }

  return resposta.json();
}

export default async function VeiculosPage() {
  const veiculos = await buscarVeiculos();
  const categorias = await buscarCategorias();

  function nomeCategoria(idCategoria) {
    const categoria = categorias.find(
      (categoria) => categoria.id === idCategoria
    );

    return categoria ? categoria.nome : "Não encontrada";
  }

  return (
    <main className="p-6 max-w-6xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Veículos</h1>
        <Link href="/veiculos/novo" className="bg-black text-white px-4 py-2 rounded">
          Novo veículo
        </Link>
      </div>

      <table className="w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-100 text-gray-900">
            <th className="border p-2">ID</th>
            <th className="border p-2">Placa</th>
            <th className="border p-2">Modelo</th>
            <th className="border p-2">Marca</th>
            <th className="border p-2">Ano</th>
            <th className="border p-2">Status</th>
            <th className="border p-2">Categoria</th>
            <th className="border p-2">Ações</th>
          </tr>
        </thead>
        <tbody>
          {veiculos.length === 0 ? (
            <tr>
              <td colSpan="8" className="border p-4 text-center">Nenhum veículo cadastrado.</td>
            </tr>
          ) : (
            veiculos.map((veiculo) => (
              <tr key={veiculo.id} className="text-center">
                <td className="border p-2">{veiculo.id}</td>
                <td className="border p-2">{veiculo.placa}</td>
                <td className="border p-2">{veiculo.modelo}</td>
                <td className="border p-2">{veiculo.marca}</td>
                <td className="border p-2">{veiculo.anoFabricacao}</td>
                <td className="border p-2">{veiculo.status}</td>
                <td className="border p-2">{nomeCategoria(veiculo.idCategoria)}</td>
                <td className="border p-2">
                  <BotaoExcluirVeiculo id={veiculo.id} />
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </main>
  );
}
