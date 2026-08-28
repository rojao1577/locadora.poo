import Link from "next/link";
import BotaoExcluirCategoria from "./BotaoExcluirCategoria";

const API_URL = process.env.BACKEND_URL || "http://localhost:8080";

async function buscarCategorias() {
  const resposta = await fetch(`${API_URL}/categorias`, {
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
    <main className="p-6 max-w-6xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Categorias</h1>
        <Link href="/categorias/nova" className="bg-black text-white px-4 py-2 rounded">
          Nova categoria
        </Link>
      </div>

      <table className="w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-100 text-gray-900">
            <th className="border p-2">ID</th>
            <th className="border p-2">Nome</th>
            <th className="border p-2">Valor da diária</th>
            <th className="border p-2">Ações</th>
          </tr>
        </thead>
        <tbody>
          {categorias.length === 0 ? (
            <tr>
              <td colSpan="4" className="border p-4 text-center">Nenhuma categoria cadastrada.</td>
            </tr>
          ) : (
            categorias.map((categoria) => (
              <tr key={categoria.id} className="text-center">
                <td className="border p-2">{categoria.id}</td>
                <td className="border p-2">{categoria.nome}</td>
                <td className="border p-2">
                  {Number(categoria.valorDiariaBase).toLocaleString("pt-BR", {
                    style: "currency",
                    currency: "BRL",
                  })}
                </td>
                <td className="border p-2">
                  <BotaoExcluirCategoria id={categoria.id} />
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </main>
  );
}
