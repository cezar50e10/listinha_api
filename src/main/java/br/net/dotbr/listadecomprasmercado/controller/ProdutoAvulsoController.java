package br.net.dotbr.listadecomprasmercado.controller;

import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.DadosAlteracaoProdutoAvulso;
import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.DadosCadastroProdutoAvulso;
import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.ProdutoAvulsoService;
import br.net.dotbr.listadecomprasmercado.domain.usuario.DadosAutenticacao;
import br.net.dotbr.listadecomprasmercado.domain.usuario.DadosCadastroUsuario;
import br.net.dotbr.listadecomprasmercado.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/produto_avulso")
public class ProdutoAvulsoController {



    @Autowired
    private ProdutoAvulsoService produtoAvulsoService;




    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrarNovoProdutoAvulso(@RequestBody @Valid DadosCadastroProdutoAvulso dados){
       return  produtoAvulsoService.cadastrarNovoProdutoAvulso(dados);
    }


    @PostMapping("/listar")
    public ResponseEntity listarUsuarios() {
        return produtoAvulsoService.listarProdutos();
    }


    @PostMapping("/alterar")
    @Transactional
    public ResponseEntity alterarProdutoAvulso(@RequestBody @Valid DadosAlteracaoProdutoAvulso dados){
        return  produtoAvulsoService.alterarProdutoAvulso(dados);
    }

}
