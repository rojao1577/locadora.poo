import { Geist, Geist_Mono } from "next/font/google";
import Link from "next/link";
import "./globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata = {
  title: "LocaFácil - Locação de Veículos",
  description: "Sistema de gerenciamento da locadora LocaFácil",
};

export default function RootLayout({ children }) {
  return (
      <html
          lang="pt-BR"
          className={`${geistSans.variable} ${geistMono.variable} h-full antialiased`}
      >
      <body className="min-h-full flex flex-col bg-gray-50">
      <header className="bg-brand-blue text-white shadow-lg">
        <div className="mx-auto max-w-7xl px-6 py-4 flex flex-col sm:flex-row items-center justify-between gap-4">

          <Link href="/" className="text-2xl font-black tracking-tight flex items-center">
            <span>LOCA</span>
            <span className="text-brand-green">FÁCIL</span>
          </Link>

          <nav className="flex flex-wrap items-center justify-center gap-6 text-sm font-semibold">
            <Link href="/" className="hover:text-brand-green transition-colors">
              Início
            </Link>
            <Link href="/categorias" className="hover:text-brand-green transition-colors">
              Categorias
            </Link>
            <Link href="/veiculos" className="hover:text-brand-green transition-colors">
              Veículos
            </Link>
            <Link href="/funcionarios" className="hover:text-brand-green transition-colors">
              Funcionários
            </Link>
            <Link href="/pagamentos" className="hover:text-brand-green transition-colors">
              Pagamentos
            </Link>
            <Link href="/locacoes" className="hover:text-brand-green transition-colors">
              Locações
            </Link>
          </nav>
        </div>
      </header>

      <main className="flex-1">
        {children}
      </main>
      </body>
      </html>
  );
}