package com.soulcode.soulib.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String createEmprestimo(@RequestParam Integer idCliente, @RequestParam Integer idLivro,
            Emprestimo emprestimo) {
        try {
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);

            // Para inserir o empréstimo, cliente e livro devem existir
            if (clienteOpt.isPresent() && livroOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();

                // Associamos o cliente ao empréstimo
                emprestimo.setCliente(cliente);

                // Associamos o livro ao empréstimo
                emprestimo.setLivroEmprestado(livro);

                emprestimoRepository.save(emprestimo);
            }
        } catch (Exception ex) {
            return "erro";
        }
        return "redirect:/emprestimos";
    }

    @PostMapping("/emprestimos/delete")
    public String deleteEmprestimo(@RequestParam Integer idEmprestimo) {
        try {
            emprestimoRepository.deleteById(idEmprestimo);
        } catch (Exception ex) {
            return "erro";
        }

        return "redirect:/emprestimos";
    }

    @GetMapping("/emprestimos/{id}/edit")
    public ModelAndView paginaAtualizaEmprestimo(@PathVariable Integer id) {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Livro> livros = livroRepository.findAll();
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);

        if (emprestimoOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("emprestimo-atualizar");
            Emprestimo emprestimo = emprestimoOpt.get();
            mv.addObject("emp", emprestimo);
            mv.addObject("livros", livros);
            mv.addObject("clientes", clientes);
            return mv;
        } else {
            ModelAndView erro = new ModelAndView("erro");
            erro.addObject("msg", "Emprestimo não encontrado.");
            return erro;
        }

    }

    @PostMapping("/emprestimos/update")
    public String updateEmprestimo(@RequestParam Integer idLivro, @RequestParam Integer idCliente,
            Emprestimo emprestimo) {
        try {
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Emprestimo> empOpt = emprestimoRepository.findById(emprestimo.getIdEmprestimo());
            if (empOpt.isPresent() && livroOpt.isPresent() && clienteOpt.isPresent()) {
                Livro livro = livroOpt.get();
                Cliente cliente = clienteOpt.get();
                emprestimo.setCliente(cliente);
                emprestimo.setLivroEmprestado(livro);
                emprestimoRepository.save(emprestimo);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return "erro";
        }
        return "redirect:/emprestimos";
    }
}
