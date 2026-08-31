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
      router.push("/veiculos");
    } else {
      const erro = await resposta.text();
      setMensagem(erro || "Erro ao cadastrar veículo.");
    }
  }

  return (
    <main className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-4">Novo Veículo</h1>
      {mensagem && <p className="text-red-500 mb-4">{mensagem}</p>}
      <form onSubmit={cadastrarVeiculo} className="flex flex-col gap-3">
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="Placa (Ex: ABC1D23)"
          value={placa}
          onChange={(event) => setPlaca(event.target.value)}
          required
        />
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="Modelo (Ex: Onix)"
          value={modelo}
          onChange={(event) => setModelo(event.target.value)}
          required
        />
        <input
          className="border p-2 rounded"
          type="text"
          placeholder="Marca (Ex: Chevrolet)"
          value={marca}
          onChange={(event) => setMarca(event.target.value)}
          required
        />
        <input
          className="border p-2 rounded"
          type="number"
          placeholder="Ano de fabricação"
          value={anoFabricacao}
          onChange={(event) => setAnoFabricacao(event.target.value)}
          required
        />
        <select
          className="border p-2 rounded"
          value={status}
          onChange={(event) => setStatus(event.target.value)}
        >
          <option value="DISPONIVEL">Disponível</option>
          <option value="ALUGADO">Alugado</option>
          <option value="MANUTENCAO">Manutenção</option>
        </select>
        <select
          className="border p-2 rounded"
          value={idCategoria}
          onChange={(event) => setIdCategoria(event.target.value)}
          required
        >
          <option value="">Selecione uma categoria</option>
          {categorias.map((categoria) => (
            <option key={categoria.id} value={categoria.id}>
              {categoria.nome}
            </option>
          ))}
        </select>
        <button type="submit" className="bg-black text-white p-2 rounded">
          Cadastrar
        </button>
      </form>
    </main>
  );
}
