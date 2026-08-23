import Link from "next/link";
import BotaoExcluirVeiculo from "./BotaoExcluirVeiculo";

async function buscarVeiculos() {
  const resposta = await fetch("http://localhost:8080/veiculos", {
    cache: "no-store",
  });

  if (!resposta.ok) {
    throw new Error("Erro ao buscar veículos.");
  }

  return resposta.json();
}

async function buscarCategorias() {
  const resposta = await fetch("http://localhost:8080/categorias", {
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
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-6xl">

        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">
            Veículos
          </h1>

          <Link
            href="/veiculos/novo"
            className="bg-black text-white px-4 py-2 rounded hover:bg-gray-800"
          >
            Novo veículo
          </Link>
        </div>

        {veiculos.length === 0 ? (
          <div className="bg-white rounded-lg shadow p-6">
            <p className="text-gray-600">
              Nenhum veículo cadastrado.
            </p>
          </div>
        ) : (
          <div className="bg-white rounded-lg shadow overflow-hidden">
            <table className="w-full">
              <thead className="bg-gray-200">
                <tr>
                  <th className="text-left p-4">ID</th>
                  <th className="text-left p-4">Placa</th>
                  <th className="text-left p-4">Modelo</th>
                  <th className="text-left p-4">Marca</th>
                  <th className="text-left p-4">Ano</th>
                  <th className="text-left p-4">Status</th>
                  <th className="text-left p-4">Categoria</th>
                  <th className="text-left p-4">Ações</th>
                </tr>
              </thead>

              <tbody>
                {veiculos.map((veiculo) => (
                  <tr
                    key={veiculo.id}
                    className="border-t hover:bg-gray-50"
                  >
                    <td className="p-4">
                      {veiculo.id}
                    </td>

                    <td className="p-4">
                      {veiculo.placa}
                    </td>

                    <td className="p-4">
                      {veiculo.modelo}
                    </td>

                    <td className="p-4">
                      {veiculo.marca}
                    </td>

                    <td className="p-4">
                      {veiculo.anoFabricacao}
                    </td>

                    <td className="p-4">
                      {veiculo.status}
                    </td>

                    <td className="p-4">
                      {nomeCategoria(veiculo.idCategoria)}
                    </td>

                    <td className="p-4">
                      <BotaoExcluirVeiculo id={veiculo.id} />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

      </div>
    </main>
  );
}