package br.net.dotbr.listadecomprasmercado.controller;

import br.net.dotbr.listadecomprasmercado.domain.usuario.DadosAutenticacao;
import br.net.dotbr.listadecomprasmercado.domain.usuario.DadosCadastroUsuario;
import br.net.dotbr.listadecomprasmercado.domain.usuario.DadosPesquisaUsuario;
import br.net.dotbr.listadecomprasmercado.domain.usuario.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {



    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados){
       return  usuarioService.cadastrarUsuario(dados);
    }

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        return usuarioService.efetuarLogin(dados);
    }


    @PostMapping("/listar")
    public ResponseEntity listarUsuarios() {
        return usuarioService.listarUsuario();
    }

    @PostMapping("/pesquisa")
    public ResponseEntity pesquisarUsuario(@RequestBody @Valid DadosPesquisaUsuario dados) {
        return  usuarioService.pesquisarUsuario(dados);
    }
}
