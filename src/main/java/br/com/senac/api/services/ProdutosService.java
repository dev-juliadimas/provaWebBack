package br.com.senac.api.services;

import br.com.senac.api.dtos.ProdutosRequestDto;
import br.com.senac.api.entidades.Produtos;
import br.com.senac.api.repositorios.ProdutosRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutosService {

    private final ProdutosRepositorio produtosRepositorio;

    public ProdutosService(ProdutosRepositorio produtosRepositorio) {
        this.produtosRepositorio = produtosRepositorio;
    }

    public List<Produtos> listarTodos() {
        return produtosRepositorio.findAll();
    }

    public Produtos listarPorId(Long id) {
        return buscarPorId(id);
    }

    public Produtos criar(ProdutosRequestDto dto) {
        Produtos produto = new Produtos();
        preencher(produto, dto);
        return produtosRepositorio.save(produto);
    }

    public Produtos atualizar(Long id, ProdutosRequestDto dto) {
        Produtos produto = buscarPorId(id);
        preencher(produto, dto);
        return produtosRepositorio.save(produto);
    }

    public void deletar(Long id) {
        produtosRepositorio.delete(buscarPorId(id));
    }

    private Produtos buscarPorId(Long id) {
        return produtosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }

    private void preencher(Produtos produto, ProdutosRequestDto dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RuntimeException("Nome do produto é obrigatório!");
        }
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
    }
}
