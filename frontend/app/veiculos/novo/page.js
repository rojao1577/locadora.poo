"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";

export default function NovoVeiculoPage() {
  const router = useRouter();

  const [placa, setPlaca] = useState("");
  const [modelo, setModelo] = useState("");
  const [marca, setMarca] = useState("");
  const [anoFabricacao, setAnoFabricacao] = useState("");
  const [status, setStatus] = useState("DISPONIVEL");
  const [idCategoria, setIdCategoria] = useState("");

  const [categorias, setCategorias] = useState([]);
  const [mensagem, setMensagem] = useState("");

  useEffect(() => {
    async function buscarCategorias() {
      const resposta = await fetch("http://localhost:8080/categorias");

      if (resposta.ok) {
        const dados = await resposta.json();
        setCategorias(dados);
      }
    }

    buscarCategorias();
  }, []);

  async function cadastrarVeiculo(event) {
    event.preventDefault();

    const resposta = await fetch("http://localhost:8080/veiculos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        placa: placa,
        modelo: modelo,
        marca: marca,
        anoFabricacao: Number(anoFabricacao),
        status: status,
        idCategoria: Number(idCategoria),
      }),
    });

    if (resposta.ok) {
      setMensagem("Veículo cadastrado com sucesso.");

      setTimeout(() => {
        router.push("/veiculos");
      }, 1000);
    } else {
      const erro = await resposta.text();
      setMensagem(erro || "Erro ao cadastrar veículo.");
    }
  }

  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-xl">

        <h1 className="text-3xl font-bold mb-6">
          Novo Veículo
        </h1>

        <form
          onSubmit={cadastrarVeiculo}
          className="bg-white p-6 rounded-lg shadow"
        >
          <div className="mb-5">
            <label className="block mb-2 font-medium">
              Placa
            </label>

            <input
              type="text"
              value={placa}
              onChange={(event) => setPlaca(event.target.value)}
              placeholder="Ex: ABC1D23"
              className="w-full border border-gray-300 rounded p-3"
              required
            />
          </div>

          <div className="mb-5">
            <label className="block mb-2 font-medium">
              Modelo
            </label>

            <input
              type="text"
              value={modelo}
              onChange={(event) => setModelo(event.target.value)}
              placeholder="Ex: Onix"
              className="w-full border border-gray-300 rounded p-3"
              required
            />
          </div>

          <div className="mb-5">
            <label className="block mb-2 font-medium">
              Marca
            </label>

            <input
              type="text"
              value={marca}
              onChange={(event) => setMarca(event.target.value)}
              placeholder="Ex: Chevrolet"
              className="w-full border border-gray-300 rounded p-3"
              required
            />
          </div>

          <div className="mb-5">
            <label className="block mb-2 font-medium">
              Ano de fabricação
            </label>

            <input
              type="number"
              value={anoFabricacao}
              onChange={(event) =>
                setAnoFabricacao(event.target.value)
              }
              placeholder="Ex: 2025"
              className="w-full border border-gray-300 rounded p-3"
              required
            />
          </div>

          <div className="mb-5">
            <label className="block mb-2 font-medium">
              Status
            </label>

            <select
              value={status}
              onChange={(event) => setStatus(event.target.value)}
              className="w-full border border-gray-300 rounded p-3"
            >
              <option value="DISPONIVEL">
                Disponível
              </option>

              <option value="ALUGADO">
                Alugado
              </option>

              <option value="MANUTENCAO">
                Manutenção
              </option>
            </select>
          </div>

          <div className="mb-6">
            <label className="block mb-2 font-medium">
              Categoria
            </label>

            <select
              value={idCategoria}
              onChange={(event) =>
                setIdCategoria(event.target.value)
              }
              className="w-full border border-gray-300 rounded p-3"
              required
            >
              <option value="">
                Selecione uma categoria
              </option>

              {categorias.map((categoria) => (
                <option
                  key={categoria.id}
                  value={categoria.id}
                >
                  {categoria.nome}
                </option>
              ))}
            </select>
          </div>

          <div className="flex gap-3">
            <button
              type="submit"
              className="bg-black text-white px-4 py-2 rounded hover:bg-gray-800"
            >
              Cadastrar
            </button>

            <Link
              href="/veiculos"
              className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
            >
              Cancelar
            </Link>
          </div>

          {mensagem && (
            <p className="mt-4 font-medium">
              {mensagem}
            </p>
          )}

        </form>
      </div>
    </main>
  );
}