package br.edu.ufape.poo.locadora;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.ufape.poo.locadora.dados.CategoriaRepository;
import br.edu.ufape.poo.locadora.dados.VeiculoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;

@Component
public class DataSeeder implements CommandLineRunner{
	
	private final VeiculoRepository veiculoRepository;
	private final CategoriaRepository categoriaRepository;
	
	public DataSeeder (VeiculoRepository veiculoRepository, CategoriaRepository categoriaRepository) {
		
		this.veiculoRepository = veiculoRepository;
        this.categoriaRepository = categoriaRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		if(veiculoRepository.count() == 0) {
			Categoria economico = categoriaRepository.save(
					new Categoria("Econômico", new BigDecimal("70.00")));
			Categoria compacto = categoriaRepository.save(
		            new Categoria("Compacto", new BigDecimal("90.00")));
			Categoria intermediario = categoriaRepository.save(
		            new Categoria("Intermediário", new BigDecimal("130.00")));
			Categoria suvCompacto = categoriaRepository.save(
		            new Categoria("SUV Compacto", new BigDecimal("160.00")));
			Categoria suv = categoriaRepository.save(
					new Categoria("SUV", new BigDecimal("200.00")));
			Categoria luxo = categoriaRepository.save(
					new Categoria("Luxo", new BigDecimal("300.00")));
			
			veiculoRepository.save(new Veiculo("KJQ-9250", "Mobi", "Fiat", 2020,
					StatusVeiculo.DISPONIVEL, economico));
			veiculoRepository.save(new Veiculo("KJF-2044", "Kwid", "Renault", 2023,
					StatusVeiculo.DISPONIVEL, economico));
			veiculoRepository.save(new Veiculo("KHY-2994", "HB20", "Hyundai", 2024,
					StatusVeiculo.DISPONIVEL, compacto));
			veiculoRepository.save(new Veiculo("KJG-1974", "Viturs", "Volkswagen", 2024,
					StatusVeiculo.DISPONIVEL, intermediario));
			veiculoRepository.save(new Veiculo("KFU-5979", "Nivus", "Volkswagen", 2026,
					StatusVeiculo.DISPONIVEL, suvCompacto));
			veiculoRepository.save(new Veiculo("KJR-2462", "Pulse", "Fiat", 2024,
					StatusVeiculo.DISPONIVEL, suvCompacto));
			veiculoRepository.save(new Veiculo("KJZ-1793", "Kardian", "Renault", 2022,
					StatusVeiculo.DISPONIVEL, suvCompacto));
			veiculoRepository.save(new Veiculo("KLA-6533", "Creta", "Hyundai", 2025,
					StatusVeiculo.DISPONIVEL, suv));
			veiculoRepository.save(new Veiculo("KLT-4109", "XC40", "Volvo", 2025,
					StatusVeiculo.DISPONIVEL, luxo));
			veiculoRepository.save(new Veiculo("KKO-8926", "Compass", "Jeep", 2025,
					StatusVeiculo.DISPONIVEL, luxo));
			
			System.out.println(">>> Veiculos inseridos com sucesso!");

		}
		
	}
}
