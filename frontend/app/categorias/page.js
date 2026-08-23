import Link from "next/link";
import BotaoExcluirCategoria from "./BotaoExcluirCategoria";

async function buscarCategorias() {
  const resposta = await fetch("http://localhost:8080/categorias", {
    cache: "no-store",
  });

  if (!resposta.ok) {
    throw new Error("Erro ao buscar categorias.");
  }

  return resposta.json();
}

export default async function CategoriasPage() {
  const categorias = await buscarCategorias();

  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-5xl">

        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">
            Categorias
          </h1>

          <Link
            href="/categorias/nova"
            className="bg-black text-white px-4 py-2 rounded hover:bg-gray-800"
          >
            Nova categoria
          </Link>
        </div>

        {categorias.length === 0 ? (
          <div className="bg-white rounded-lg shadow p-6">
            <p className="text-gray-600">
              Nenhuma categoria cadastrada.
            </p>
          </div>
        ) : (
          <div className="bg-white rounded-lg shadow overflow-hidden">
            <table className="w-full">
              <thead className="bg-gray-200">
                <tr>
                  <th className="text-left p-4">ID</th>
                  <th className="text-left p-4">Nome</th>
                  <th className="text-left p-4">Valor da diária</th>
                  <th className="text-left p-4">Ações</th>
                </tr>
              </thead>

              <tbody>
                {categorias.map((categoria) => (
                  <tr
                    key={categoria.id}
                    className="border-t hover:bg-gray-50"
                  >
                    <td className="p-4">
                      {categoria.id}
                    </td>

                    <td className="p-4">
                      {categoria.nome}
                    </td>

                    <td className="p-4">
                      {Number(categoria.valorDiariaBase).toLocaleString(
                        "pt-BR",
                        {
                          style: "currency",
                          currency: "BRL",
                        }
                      )}
                    </td>

                    <td className="p-4">
                      <BotaoExcluirCategoria id={categoria.id} />
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