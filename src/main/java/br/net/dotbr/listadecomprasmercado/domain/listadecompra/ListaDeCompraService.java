package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import br.net.dotbr.listadecomprasmercado.domain.ValidacaoException;
import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.ProdutoAvulso;
import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.ProdutoAvulsoRepository;
import br.net.dotbr.listadecomprasmercado.domain.usuario.Usuario;
import br.net.dotbr.listadecomprasmercado.domain.usuario.UsuarioRepository;
import br.net.dotbr.listadecomprasmercado.infra.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ListaDeCompraService {

    @Autowired
    private ListaDeCompraRepository listaDeCompraRepository;
    @Autowired
    private ParticipanteListaCompraRepository participanteListaCompraRepository;
    @Autowired
    private ProdutoAvulsoRepository produtoAvulsoRepository;
    @Autowired
    private ProdutoListaDeCompraRepository produtoListaDeCompraRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final SessionService sessionService;

    public ListaDeCompraService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public ResponseEntity cadastrarNovaLista(DadosCadastroListaDeCompra dados) {

        var listaDeCompra = new ListaDeCompra(dados);

        Usuario usuario = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");

        ParticipanteListaCompra participante1 = new ParticipanteListaCompra(listaDeCompra,usuario);


        // Adicionando participantes à lista
        listaDeCompra.getParticipantes().add(participante1);

        // Salvar a lista de compras, que também salvará os participantes
        // Salvar a lista de compras e recuperar o ID gerado
        var savedListaDeCompra = listaDeCompraRepository.save(listaDeCompra);

        // Agora você pode acessar o ID gerado
        Long idGerado = savedListaDeCompra.getId();


        return  ResponseEntity.ok(idGerado);
    }

    public ResponseEntity adicionaProdutoLista(DadosAdicionaProdutoListaDeCompra dados) {
        Optional<ProdutoAvulso> produtoAvulso = null;
        Optional<ListaDeCompra> listaDeCompra = null;
        if(dados.tipoProduto().equals(TipoProdutoLista.AVULSO))
            produtoAvulso = produtoAvulsoRepository.findById(dados.idProduto());

        if(!produtoAvulso.isPresent())
            throw new ValidacaoException("Produto Inválido");

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");

        ProdutoListaDeCompra produtoListaDeCompra = new ProdutoListaDeCompra(dados,listaDeCompra.get(),produtoAvulso.get());

        ListaDeCompra listaDeCompraDoProdutoAtual = produtoListaDeCompra.getListaDeCompra();

        Usuario usuarioSessao = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");
        boolean usuarioFazParteDaLista = listaDeCompraDoProdutoAtual.getParticipantes().stream()
                .anyMatch(participante ->
                        participante.getId().equals(usuarioSessao.getId()) &&
                                participante.getStatus().equals(StatusUsuarioListaCompra.ATIVO) &&
                                (participante.getCargo().equals(CargoUsuarioLista.ADMIN) || participante.getCargo().equals(CargoUsuarioLista.EDITOR))
                );
        if(!usuarioFazParteDaLista)
            throw new ValidacaoException("Usuario Não pode Inserir produtos nesta lista");


        boolean produtoJaCadastroNaLista = listaDeCompraDoProdutoAtual.getProdutoListaDeCompras().stream()
                .anyMatch(produto ->
                        produto.getId().equals(produtoListaDeCompra.getProdutoAvulso().getId()) &&
                                produtoListaDeCompra.getStatus().equals(StatusProdutoLista.ATIVO) &&
                                produtoListaDeCompra.getTipoProduto().equals(produto.getTipoProduto())
                );
        if(produtoJaCadastroNaLista)
            throw new ValidacaoException("Produto ja esta cadastrado nesta lista");

        AtomicBoolean usuarioTemPermissao = new AtomicBoolean(false);
        listaDeCompra.get().getParticipantes().forEach(participante -> {

            if (participante.getUsuario().getId().equals(usuarioSessao.getId())
                    && participante.getStatus() == StatusUsuarioListaCompra.ATIVO
                    && participante.getCargo() == CargoUsuarioLista.ADMIN || participante.getCargo() == CargoUsuarioLista.EDITOR) {
                usuarioTemPermissao.set(true);
            }
        });

        if(!usuarioTemPermissao.get())
            throw new ValidacaoException("O usuário não tem permissão para editar lista.");


        produtoListaDeCompraRepository.save(produtoListaDeCompra);

        return  ResponseEntity.ok("produto adicionado a lista");
    }

    public ResponseEntity listarProdutosLista(DadosPesquisaListaDeCompra dados) {

        List<DadosRetornoListagemListaCompra> listaDeProdutos = produtoListaDeCompraRepository.findAllByListaDeCompraIdAndStatus(dados.idListaCompra(),StatusProdutoLista.ATIVO)
                .stream()
                .map(p -> new DadosRetornoListagemListaCompra(
                        p.getId(),
                        p.getProdutoAvulso().getId(),
                        p.getListaDeCompra().getId(),
                        p.getQtdEmEstoque(),
                        p.getQtdDesejada(),
                        p.getTipoProduto(),
                        p.getProdutoAvulso().getNome(),
                        p.getProdutoAvulso().getDescricao()))
                .toList();


        return  ResponseEntity.ok(listaDeProdutos);

    }

    public ResponseEntity alterarProdutoLista(DadosAlterarProdutoListaDeCompra dados) {
        Optional<ProdutoAvulso> produtoAvulso = null;
        Optional<ListaDeCompra> listaDeCompra = null;
        if(dados.tipoProduto().equals(TipoProdutoLista.AVULSO))
            produtoAvulso = produtoAvulsoRepository.findById(dados.idProduto());

        if(!produtoAvulso.isPresent())
            throw new ValidacaoException("Produto Inválido");

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");

        ProdutoListaDeCompra produtoListaDeCompra = new ProdutoListaDeCompra(dados,listaDeCompra.get(),produtoAvulso.get());

        ListaDeCompra listaDeCompraDoProdutoAtual = produtoListaDeCompra.getListaDeCompra();

        Usuario usuarioSessao = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");
        boolean usuarioFazParteDaLista = listaDeCompraDoProdutoAtual.getParticipantes().stream()
                .anyMatch(participante ->
                        participante.getId().equals(usuarioSessao.getId()) &&
                                participante.getStatus().equals(StatusUsuarioListaCompra.ATIVO) &&
                                (participante.getCargo().equals(CargoUsuarioLista.ADMIN) || participante.getCargo().equals(CargoUsuarioLista.EDITOR))
                );
        if(!usuarioFazParteDaLista)
            throw new ValidacaoException("Usuario Não pode Alterar produtos nesta lista");


        boolean produtoJaCadastroNaLista = listaDeCompraDoProdutoAtual.getProdutoListaDeCompras().stream()
                .anyMatch(produto ->
                        !produto.getId().equals(produtoListaDeCompra.getId()) &&
                                produtoListaDeCompra.getStatus().equals(StatusProdutoLista.ATIVO) &&
                                produtoListaDeCompra.getTipoProduto().equals(produto.getTipoProduto()) &&
                                produto.getProdutoAvulso().getId().equals(produtoListaDeCompra.getProdutoAvulso().getId())
                );
        if(produtoJaCadastroNaLista)
            throw new ValidacaoException("Produto ja esta cadastrado nesta lista");

        AtomicBoolean usuarioTemPermissao = new AtomicBoolean(false);
        listaDeCompra.get().getParticipantes().forEach(participante -> {

            if (participante.getUsuario().getId().equals(usuarioSessao.getId())
                    && participante.getStatus() == StatusUsuarioListaCompra.ATIVO
                    && participante.getCargo() == CargoUsuarioLista.ADMIN || participante.getCargo() == CargoUsuarioLista.EDITOR) {
                usuarioTemPermissao.set(true);
            }
        });

        if(!usuarioTemPermissao.get())
            throw new ValidacaoException("O usuário não tem permissão para editar lista.");

        produtoListaDeCompraRepository.save(produtoListaDeCompra);

        return  ResponseEntity.ok("produto alterado a lista");
    }

    public ResponseEntity consomeProdutoLista(DadosConsomeProdutoListaDeCompra dados) {
        Optional<ProdutoAvulso> produtoAvulso = null;
        Optional<ListaDeCompra> listaDeCompra = null;
        if(dados.tipoProduto().equals(TipoProdutoLista.AVULSO))
            produtoAvulso = produtoAvulsoRepository.findById(dados.idProduto());

        if(!produtoAvulso.isPresent())
            throw new ValidacaoException("Produto Inválido");

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");

        //verificando se a quantidade consumida não é maior do que o estoque
        ProdutoListaDeCompra produtoListaDeCompra= listaDeCompra.get().getProdutoListaDeCompras().stream()
                .filter(produtoLista ->
                        produtoLista.getId().equals(dados.idSeqProdutoLista()) &&
                                produtoLista.getProdutoAvulso().getId().equals(dados.idProduto()) &&
                                produtoLista.getListaDeCompra().getId().equals(dados.idListaCompra()))
                .findFirst()
                .orElse(null);


        if((produtoListaDeCompra.getQtdEmEstoque()-dados.qtd_consumida())<0)
            throw new ValidacaoException("Não Pode Consumir produtos acima do que tem no estoque");


        produtoListaDeCompra.setQtdEmEstoque(produtoListaDeCompra.getQtdEmEstoque()-dados.qtd_consumida());

        ListaDeCompra listaDeCompraDoProdutoAtual = produtoListaDeCompra.getListaDeCompra();

        Usuario usuarioSessao = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");
        boolean usuarioFazParteDaLista = listaDeCompraDoProdutoAtual.getParticipantes().stream()
                .anyMatch(participante ->
                        participante.getId().equals(usuarioSessao.getId()) &&
                                participante.getStatus().equals(StatusUsuarioListaCompra.ATIVO) &&
                                (participante.getCargo().equals(CargoUsuarioLista.ADMIN) || participante.getCargo().equals(CargoUsuarioLista.EDITOR))
                );
        if(!usuarioFazParteDaLista)
            throw new ValidacaoException("Usuario Não pode consumir produtos nesta lista");

        AtomicBoolean usuarioTemPermissao = new AtomicBoolean(false);
        listaDeCompra.get().getParticipantes().forEach(participante -> {

            if (participante.getUsuario().getId().equals(usuarioSessao.getId())
                    && participante.getStatus() == StatusUsuarioListaCompra.ATIVO
                    && participante.getCargo() == CargoUsuarioLista.ADMIN || participante.getCargo() == CargoUsuarioLista.EDITOR) {
                usuarioTemPermissao.set(true);
            }
        });

        if(!usuarioTemPermissao.get())
            throw new ValidacaoException("O usuário não tem permissão para editar lista.");

        produtoListaDeCompraRepository.save(produtoListaDeCompra);

        return  ResponseEntity.ok("produto consumido da lista");
    }

    public ResponseEntity repoeProdutoLista(DadosRepoeProdutoListaDeCompra dados) {
        Optional<ProdutoAvulso> produtoAvulso = null;
        Optional<ListaDeCompra> listaDeCompra = null;
        if(dados.tipoProduto().equals(TipoProdutoLista.AVULSO))
            produtoAvulso = produtoAvulsoRepository.findById(dados.idProduto());

        if(!produtoAvulso.isPresent())
            throw new ValidacaoException("Produto Inválido");

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");


        ProdutoListaDeCompra produtoListaDeCompra= listaDeCompra.get().getProdutoListaDeCompras().stream()
                .filter(produtoLista ->
                        produtoLista.getId().equals(dados.idSeqProdutoLista()) &&
                                produtoLista.getProdutoAvulso().getId().equals(dados.idProduto()) &&
                                produtoLista.getListaDeCompra().getId().equals(dados.idListaCompra()))
                .findFirst()
                .orElse(null);


        if(dados.tipoReposicao().equals(TipoReposicaoLista.TOTAL))
            produtoListaDeCompra.setQtdEmEstoque(produtoListaDeCompra.getQtdDesejada());
        else{
            produtoListaDeCompra.setQtdEmEstoque(produtoListaDeCompra.getQtdEmEstoque()+ dados.qtd_reposta());

            if(produtoListaDeCompra.getQtdEmEstoque()>produtoListaDeCompra.getQtdDesejada())
                produtoListaDeCompra.setQtdDesejada(produtoListaDeCompra.getQtdEmEstoque());
        }
        ListaDeCompra listaDeCompraDoProdutoAtual = produtoListaDeCompra.getListaDeCompra();

        Usuario usuarioSessao = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");
        boolean usuarioFazParteDaLista = listaDeCompraDoProdutoAtual.getParticipantes().stream()
                .anyMatch(participante ->
                        participante.getId().equals(usuarioSessao.getId()) &&
                                participante.getStatus().equals(StatusUsuarioListaCompra.ATIVO) &&
                                (participante.getCargo().equals(CargoUsuarioLista.ADMIN) || participante.getCargo().equals(CargoUsuarioLista.EDITOR))
                );
        if(!usuarioFazParteDaLista)
            throw new ValidacaoException("Usuario Não pode consumir produtos nesta lista");

        AtomicBoolean usuarioTemPermissao = new AtomicBoolean(false);
        listaDeCompra.get().getParticipantes().forEach(participante -> {

            if (participante.getUsuario().getId().equals(usuarioSessao.getId())
                    && participante.getStatus() == StatusUsuarioListaCompra.ATIVO
                    && participante.getCargo() == CargoUsuarioLista.ADMIN || participante.getCargo() == CargoUsuarioLista.EDITOR) {
                usuarioTemPermissao.set(true);
            }
        });

        if(!usuarioTemPermissao.get())
            throw new ValidacaoException("O usuário não tem permissão para editar lista.");

        produtoListaDeCompraRepository.save(produtoListaDeCompra);

        return  ResponseEntity.ok("produto reposto no estoque da lista");
    }

    public ResponseEntity adicionaParticipanteLista(DadosAdicionaParticipanteListaDeCompra dados) {

        Optional<ListaDeCompra> listaDeCompra = null;

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");

        Usuario usuarioSessao = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");

        listaDeCompra.get().getParticipantes().forEach(participante -> {
            if (participante.getUsuario().getId().equals(usuarioSessao.getId()) &&
                    participante.getStatus().equals(StatusUsuarioListaCompra.ATIVO) &&
                    participante.getCargo() != CargoUsuarioLista.ADMIN) {
                throw new ValidacaoException("O usuário não possui permissão para adicionar participantes à lista de compra.");
            }

            if (participante.getUsuario().getId().equals(dados.idUsuario()) && participante.getStatus() == StatusUsuarioListaCompra.ATIVO) {
                throw new ValidacaoException("O usuário já existe nesta lista.");
            }
        });

        var listaDeCompraAtual = listaDeCompra.get();

        Usuario usuarioAdd = usuarioRepository.findById(dados.idUsuario()).get();

        ParticipanteListaCompra participante1 = new ParticipanteListaCompra(listaDeCompraAtual,usuarioAdd,dados.cargo());


        // Adicionando participantes à lista
        listaDeCompraAtual.getParticipantes().add(participante1);

        listaDeCompraRepository.save(listaDeCompraAtual);

        return  ResponseEntity.ok("Participante adicionado a lista");
    }


    public ResponseEntity alterarParticipanteLista(DadosAdicionaParticipanteListaDeCompra dados) {

        Optional<ListaDeCompra> listaDeCompra = null;

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");

        Usuario usuarioSessao = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");

        AtomicBoolean usuarioExiste = new AtomicBoolean(false);

        listaDeCompra.get().getParticipantes().forEach(participante -> {
            if (participante.getUsuario().getId().equals(usuarioSessao.getId()) &&
                    participante.getStatus().equals(StatusUsuarioListaCompra.ATIVO) &&
                    participante.getCargo() != CargoUsuarioLista.ADMIN) {
                throw new ValidacaoException("O usuário não possui permissão para adicionar participantes à lista de compra.");
            }

            if (participante.getUsuario().getId().equals(dados.idUsuario()) && participante.getStatus() == StatusUsuarioListaCompra.ATIVO) {
                usuarioExiste.set(true);
                participante.setCargo(dados.cargo());
            }
        });

        if(!usuarioExiste.get())
            throw new ValidacaoException("O usuário não existe nesta lista.");


        listaDeCompraRepository.save(listaDeCompra.get());

        return  ResponseEntity.ok("Participante alterado na lista");
    }


    public ResponseEntity removeParticipanteLista(DadosRemoveParticipanteListaDeCompra dados) {

        Optional<ListaDeCompra> listaDeCompra = null;

        listaDeCompra = listaDeCompraRepository.findById(dados.idListaCompra());

        if(!listaDeCompra.isPresent())
            throw new ValidacaoException("Lista de Compra Inválido");

        AtomicBoolean usuarioExiste = new AtomicBoolean(false);

        listaDeCompra.get().getParticipantes().forEach(participante -> {

            if (participante.getUsuario().getId().equals(dados.idUsuario()) && participante.getStatus() == StatusUsuarioListaCompra.ATIVO) {
                usuarioExiste.set(true);
                participante.setStatus(StatusUsuarioListaCompra.INATIVO);
            }
        });

        if(!usuarioExiste.get())
            throw new ValidacaoException("O usuário não existe nesta lista.");


        listaDeCompraRepository.save(listaDeCompra.get());

        return  ResponseEntity.ok("Participante removido da lista");
    }
}
