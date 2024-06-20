package com.alura.libreria;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final GutendexService gutendexService;

    public Controller(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        return gutendexService.getAllBooks();
    }
}