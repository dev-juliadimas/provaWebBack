package br.com.senac.api.services;

import br.com.senac.api.dtos.MarcasRequestDto;
import br.com.senac.api.entidades.Marcas;
import br.com.senac.api.repositorios.MarcasRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcasService {

    private final MarcasRepositorio marcasRepositorio;

    public MarcasService(MarcasRepositorio marcasRepositorio) {
        this.marcasRepositorio = marcasRepositorio;
    }

    public List<Marcas> listarTodos() {
        return marcasRepositorio.findAll();
    }

    public Marcas listarPorId(Long id) {
        return buscarPorId(id);
    }

    public Marcas criar(MarcasRequestDto dto) {
        Marcas marca = new Marcas();
        preencher(marca, dto);
        return marcasRepositorio.save(marca);
    }

    public Marcas atualizar(Long id, MarcasRequestDto dto) {
        Marcas marca = buscarPorId(id);
        preencher(marca, dto);
        return marcasRepositorio.save(marca);
    }

    public void deletar(Long id) {
        marcasRepositorio.delete(buscarPorId(id));
    }

    private Marcas buscarPorId(Long id) {
        return marcasRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada!"));
    }

    private void preencher(Marcas marca, MarcasRequestDto dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RuntimeException("Nome da marca é obrigatório!");
        }
        marca.setNome(dto.getNome());
    }
}
