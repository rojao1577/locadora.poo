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
  title: "Locadora de Veículos",
  description: "Sistema de gerenciamento da locadora",
};

export default function RootLayout({ children }) {
  return (
    <html
      lang="pt-BR"
      className={`${geistSans.variable} ${geistMono.variable} h-full antialiased`}
    >
      <body className="min-h-full flex flex-col">
        <header className="bg-black text-white">
          <nav className="mx-auto max-w-6xl p-4 flex gap-6">
            <Link href="/" className="hover:underline">
              Início
            </Link>

            <Link href="/categorias" className="hover:underline">
              Categorias
            </Link>

            <Link href="/veiculos" className="hover:underline">
              Veículos
            </Link>
          </nav>
        </header>

        <div className="flex-1">
          {children}
        </div>
      </body>
    </html>
  );
}