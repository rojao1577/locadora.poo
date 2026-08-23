import Link from "next/link";

export default function Home() {
  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-5xl">
        <h1 className="text-3xl font-bold mb-4">
          Locadora de Veículos
        </h1>

        <p className="text-gray-600 mb-8">
          Sistema para gerenciamento da locadora.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Link
            href="/categorias"
            className="bg-white p-6 rounded-lg shadow hover:shadow-md"
          >
            <h2 className="text-xl font-semibold">
              Categorias
            </h2>

            <p className="text-gray-500">
              Gerenciar categorias de veículos
            </p>
          </Link>

          <Link
            href="/veiculos"
            className="bg-white p-6 rounded-lg shadow hover:shadow-md"
          >
            <h2 className="text-xl font-semibold">
              Veículos
            </h2>

            <p className="text-gray-500">
              Gerenciar veículos da locadora
            </p>
          </Link>
        </div>
      </div>
    </main>
  );
}