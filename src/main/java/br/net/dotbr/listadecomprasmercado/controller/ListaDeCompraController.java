package br.net.dotbr.listadecomprasmercado.controller;

import br.net.dotbr.listadecomprasmercado.domain.listadecompra.*;
import br.net.dotbr.listadecomprasmercado.domain.usuario.DadosCadastroUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lista_de_compra")
public class ListaDeCompraController {

    @Autowired
    private ListaDeCompraService listaDeCompraService;

    @PostMapping("/cria_nova_lista")
    @Transactional
    public ResponseEntity cadastrarNovaLista(@RequestBody @Valid DadosCadastroListaDeCompra dados){
        return listaDeCompraService.cadastrarNovaLista(dados);
    }


    @PostMapping("/adicionar_produto_lista")
    @Transactional
    public ResponseEntity adicionaProdutoLista(@RequestBody @Valid DadosAdicionaProdutoListaDeCompra dados){
        return listaDeCompraService.adicionaProdutoLista(dados);
    }

    @PostMapping("/listar_produtos_lista")
    public ResponseEntity listarProdutosLista(@RequestBody @Valid DadosPesquisaListaDeCompra dados){
        return listaDeCompraService.listarProdutosLista(dados);
    }

    @PostMapping("/alterar_produto_lista")
    @Transactional
    public ResponseEntity alterarProdutoLista(@RequestBody @Valid DadosAlterarProdutoListaDeCompra dados){
        return listaDeCompraService.alterarProdutoLista(dados);
    }


    @PostMapping("/consome_produto_lista")
    @Transactional
    public ResponseEntity consomeProdutoLista(@RequestBody @Valid DadosConsomeProdutoListaDeCompra dados){
        return listaDeCompraService.consomeProdutoLista(dados);
    }

    @PostMapping("/repoe_produto_lista")
    @Transactional
    public ResponseEntity repoeProdutoLista(@RequestBody @Valid DadosRepoeProdutoListaDeCompra dados){
        return listaDeCompraService.repoeProdutoLista(dados);
    }

    @PostMapping("/adiciona_participante_lista")
    @Transactional
    public ResponseEntity adicionaParticipanteLista(@RequestBody @Valid DadosAdicionaParticipanteListaDeCompra dados){
        return listaDeCompraService.adicionaParticipanteLista(dados);
    }

    @PostMapping("/altera_participante_lista")
    @Transactional
    public ResponseEntity alterarParticipanteLista(@RequestBody @Valid DadosAdicionaParticipanteListaDeCompra dados){
        return listaDeCompraService.alterarParticipanteLista(dados);
    }

    @PostMapping("/remove_participante_lista")
    @Transactional
    public ResponseEntity removeParticipanteLista(@RequestBody @Valid DadosRemoveParticipanteListaDeCompra dados){
        return listaDeCompraService.removeParticipanteLista(dados);
    }
}
