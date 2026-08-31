"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

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
      try {
        const resposta = await fetch("http://localhost:8080/categorias");
        if (resposta.ok) {
          const dados = await resposta.json();
          setCategorias(dados);
        }
      } catch (error) {
        console.error("Erro ao buscar categorias:", error);
      }
    }

    buscarCategorias();
  }, []);

  async function cadastrarVeiculo(event) {
    event.preventDefault();

    try {
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
        router.push("/veiculos");
      } else {
        const erro = await resposta.text();
        setMensagem(erro || "Erro ao cadastrar veículo.");
      }
    } catch (error) {
      console.error("Erro ao cadastrar:", error);
      setMensagem("Falha na comunicação com o servidor.");
    }
  }

  return (
      <main className="p-6 max-w-lg mx-auto">
        <h1 className="text-2xl font-bold mb-4 text-white">Novo Veículo</h1>
        {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}

        <form onSubmit={cadastrarVeiculo} className="flex flex-col gap-3">
          <input
              className="border border-gray-700 bg-gray-900 text-white p-2 rounded placeholder-gray-400"
              type="text"
              placeholder="Placa (Ex: ABC1D23)"
              value={placa}
              onChange={(event) => setPlaca(event.target.value)}
              required
          />
          <input
              className="border border-gray-700 bg-gray-900 text-white p-2 rounded placeholder-gray-400"
              type="text"
              placeholder="Modelo (Ex: Onix)"
              value={modelo}
              onChange={(event) => setModelo(event.target.value)}
              required
          />
          <input
              className="border border-gray-700 bg-gray-900 text-white p-2 rounded placeholder-gray-400"
              type="text"
              placeholder="Marca (Ex: Chevrolet)"
              value={marca}
              onChange={(event) => setMarca(event.target.value)}
              required
          />
          <input
              className="border border-gray-700 bg-gray-900 text-white p-2 rounded placeholder-gray-400"
              type="number"
              placeholder="Ano de fabricação"
              value={anoFabricacao}
              onChange={(event) => setAnoFabricacao(event.target.value)}
              required
          />

          {/* Select com classes de modo escuro corrigidas */}
          <select
              className="border border-gray-700 bg-gray-900 text-white p-2 rounded"
              value={status}
              onChange={(event) => setStatus(event.target.value)}
          >
            <option value="DISPONIVEL" className="bg-gray-900 text-white">Disponível</option>
            <option value="ALUGADO" className="bg-gray-900 text-white">Alugado</option>
            <option value="MANUTENCAO" className="bg-gray-900 text-white">Manutenção</option>
          </select>

          {/* Select de categorias corrigido */}
          <select
              className="border border-gray-700 bg-gray-900 text-white p-2 rounded"
              value={idCategoria}
              onChange={(event) => setIdCategoria(event.target.value)}
              required
          >
            <option value="" disabled className="bg-gray-900 text-gray-400">
              Selecione uma categoria
            </option>
            {categorias.map((categoria) => (
                <option key={categoria.id} value={categoria.id} className="bg-gray-900 text-white">
                  {categoria.nome}
                </option>
            ))}
          </select>

          <button type="submit" className="bg-white text-black font-medium p-2 rounded hover:bg-gray-200 transition">
            Cadastrar
          </button>
        </form>
      </main>
  );
}