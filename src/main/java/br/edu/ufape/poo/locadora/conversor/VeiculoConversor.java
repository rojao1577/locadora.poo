package br.edu.ufape.poo.locadora.conversor;

import org.springframework.stereotype.Component;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.VeiculoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.VeiculoDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;

@Component
public class VeiculoConversor {

    public Veiculo requestToEntity(VeiculoDTORequest dto) {

        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(dto.placa());
        veiculo.setModelo(dto.modelo());
        veiculo.setMarca(dto.marca());
        veiculo.setAnoFabricacao(dto.anoFabricacao());
        veiculo.setStatus(dto.status());

        return veiculo;
    }

    public VeiculoDTOResponse entityToResponse(Veiculo veiculo) {

        return new VeiculoDTOResponse(
            veiculo.getId(),
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getMarca(),
            veiculo.getAnoFabricacao(),
            veiculo.getStatus(),
            veiculo.getCategoria().getId()
        );
    }
}