package com.soulcode.soulib.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soulcode.soulib.models.Cliente;
import com.soulcode.soulib.models.Emprestimo;
import com.soulcode.soulib.models.Livro;
import com.soulcode.soulib.repositories.ClienteRepository;
import com.soulcode.soulib.repositories.EmprestimoRepository;
import com.soulcode.soulib.repositories.LivroRepository;

@Controller // expor os mapeamentos
public class EmprestimoController {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("/emprestimos")
    public ModelAndView paginaEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<Livro> livros = livroRepository.findAll();

        ModelAndView mv = new ModelAndView("emprestimos");
        mv.addObject("listaEmprestimos", emprestimos);
        mv.addObject("listaClientes", clientes);
        mv.addObject("listaLivros", livros);

        return mv;
    }

    @PostMapping("/emprestimos/create")
    public String createEmprestimo(@RequestParam Integer idCliente, @RequestParam Integer idLivro, Emprestimo emprestimo) {
        try {
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);

            // Para inserir o empréstimo, cliente e livro devem existir
            if(clienteOpt.isPresent() && livroOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();
                
                // Associamos o cliente ao empréstimo
                emprestimo.setCliente(cliente);

                // Associamos o livro ao empréstimo
                emprestimo.setLivroEmprestado(livro);

                emprestimoRepository.save(emprestimo);
            }
        } catch(Exception ex) {
            return "erro";
        }
        return "redirect:/emprestimos";
    }
}
