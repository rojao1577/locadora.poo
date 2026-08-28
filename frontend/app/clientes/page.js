import Link from "next/link";
import BotaoExcluirCliente from "./BotaoExcluirCliente";

const API_URL = process.env.BACKEND_URL || "http://localhost:8080";

async function buscarClientes() {
  const resposta = await fetch(`${API_URL}/clientes/`, {
    cache: "no-store",
  });

  if (!resposta.ok) {
    throw new Error("Erro ao buscar clientes.");
  }

  return resposta.json();
}

export default async function ClientesPage() {
  const clientes = await buscarClientes();

  return (
    <main className="p-6 max-w-6xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Clientes</h1>
        <Link href="/clientes/novo" className="bg-black text-white px-4 py-2 rounded">
          Novo cliente
        </Link>
      </div>

      <table className="w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-100 text-gray-900">
            <th className="border p-2">ID</th>
            <th className="border p-2">Nome</th>
            <th className="border p-2">CPF</th>
            <th className="border p-2">Telefone</th>
            <th className="border p-2">Endereço</th>
            <th className="border p-2">Email</th>
            <th className="border p-2">Score</th>
            <th className="border p-2">Ações</th>
          </tr>
        </thead>
        <tbody>
          {clientes.length === 0 ? (
            <tr>
              <td colSpan="8" className="border p-4 text-center">Nenhum cliente cadastrado.</td>
            </tr>
          ) : (
            clientes.map((cliente) => (
              <tr key={cliente.id} className="text-center">
                <td className="border p-2">{cliente.id}</td>
                <td className="border p-2">{cliente.nome}</td>
                <td className="border p-2">{cliente.cpf}</td>
                <td className="border p-2">{cliente.telefone}</td>
                <td className="border p-2">{cliente.endereco}</td>
                <td className="border p-2">{cliente.email}</td>
                <td className="border p-2">{cliente.scoreCredito}</td>
                <td className="border p-2">
                  <BotaoExcluirCliente id={cliente.id} />
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </main>
  );
}
