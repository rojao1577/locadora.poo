package br.edu.ufape.poo.locadora.negocio.basica;

public class Veiculo {

	private Long id;
	private String placa;
	private String modelo;
	private String marca;
	private int anoFabricacao;
	private StatusVeiculo status;
	private Categoria categoria;

	
	public Veiculo() {
		super();
	}

	
	public Veiculo(final Long id, final String placa, final String modelo, final String marca, final int anoFabricacao, final StatusVeiculo status,
			final Categoria categoria) {
		super();
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.anoFabricacao = anoFabricacao;
		this.status = status;
		this.categoria = categoria;
	}

	public boolean verificarDisponibilidade() {
		return this.status == StatusVeiculo.DISPONIVEL;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(final String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(final String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(final String marca) {
		this.marca = marca;
	}

	public int getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(final int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public StatusVeiculo getStatus() {
		return status;
	}

	public void setStatus(final StatusVeiculo status) {
		this.status = status;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

}