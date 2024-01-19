package com.soulcode.soulib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Os mapeamentos dentro deste controller
// serão utilizados pelo Spring
@Controller
public class ClienteController {
    @GetMapping("/clientes")
    public String paginaClientes() {
        return "clientes"; // arquivo html da pasta template
    }
}