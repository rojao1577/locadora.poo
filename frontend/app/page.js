import Link from "next/link";

export default function Home() {
  const modulos = [
    {
      titulo: "Categorias",
      descricao: "Gerenciar categorias de veículos e valores das diárias.",
      href: "/categorias",
    },
    {
      titulo: "Veículos",
      descricao: "Gerenciar a frota de veículos cadastrados na locadora.",
      href: "/veiculos",
    },
    {
      titulo: "Funcionários",
      descricao: "Gerenciar a equipe de funcionários da empresa.",
      href: "/funcionarios",
    },
    {
      titulo: "Pagamentos",
      descricao: "Gerenciar histórico e novos registros de pagamentos.",
      href: "/pagamentos",
    },
    {
      titulo: "Locações",
      descricao: "Gerenciar contratos e locações de veículos ativas.",
      href: "/locacoes",
    },
  ];

  return (
      <main className="p-8 max-w-6xl mx-auto">
        <div className="mb-8">
          <h1 className="text-3xl font-black tracking-tight text-brand-blue mb-2">
            Painel <span className="text-brand-green">LocaFácil</span>
          </h1>
          <p className="text-gray-600">
            Selecione uma das opções abaixo para gerenciar o sistema.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {modulos.map((modulo) => (
              <Link
                  key={modulo.href}
                  href={modulo.href}
                  className="group bg-white p-6 rounded-xl border border-gray-200 shadow-sm hover:shadow-md hover:border-brand-blue transition-all duration-200"
              >
                <h2 className="text-xl font-bold text-gray-800 group-hover:text-brand-blue transition-colors mb-2">
                  {modulo.titulo}
                </h2>
                <p className="text-gray-500 text-sm leading-relaxed">
                  {modulo.descricao}
                </p>
              </Link>
          ))}
        </div>
      </main>
  );
}