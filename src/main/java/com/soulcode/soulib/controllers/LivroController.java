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

import com.soulcode.soulib.models.Livro;
import com.soulcode.soulib.repositories.LivroRepository;

@Controller
public class LivroController {
    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("/livros")
    public ModelAndView paginaLivros() {
        List<Livro> livros = livroRepository.findAll();
        ModelAndView mvLivros = new ModelAndView("livros");
        mvLivros.addObject("listaLivros", livros);

        return mvLivros;
    }

    @GetMapping("/livros/{id}")
    public ModelAndView paginaDetalheLivro(@PathVariable Integer id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);

        if (livroOpt.isPresent()) {
            Livro livro = livroOpt.get();
            ModelAndView mvDetalhe = new ModelAndView("livro-detalhe");
            mvDetalhe.addObject("livro", livro);
            return mvDetalhe;
        } else {
            ModelAndView erro = new ModelAndView("erro");
            erro.addObject("msg", "Livro não encontrado.");
            return erro;
        }
    }

    @PostMapping("/livros/delete")
    public String deleteLivro(@RequestParam Integer idLivro) {
        try {
            livroRepository.deleteById(idLivro);
        } catch (Exception ex) {
            return "erro";
        }

        return "redirect:/livros";
    }

    @PostMapping("/livros/create")
    public String createLivro(Livro livro) {
        try {
            livroRepository.save(livro);
        } catch (Exception ex) {
            return "erro";
        }
        return "redirect:/livros";
    }

    @GetMapping("/livros/{id}/edit")
    public ModelAndView paginaAtualizaLivro(@PathVariable Integer id) {

        Optional<Livro> livroOpt = livroRepository.findById(id);

        if (livroOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("livro-atualizar");
            Livro livro = livroOpt.get();
            mv.addObject("livro", livro);
            return mv;
        } else {
            ModelAndView erro = new ModelAndView("erro");
            erro.addObject("msg", "Livro não encontrado.");
            return erro;
        }
    }

    @PostMapping("/livros/update")
    public String updateLivro(Livro livro) {
        try {
            Optional<Livro> livroOpt = livroRepository.findById(livro.getIdLivro());
            if (livroOpt.isPresent()) {
                livroRepository.save(livro);
            }
        } catch (Exception ex) {
            return "erro";
        }
        return "redirect:/livros";
    }
}