package com.alura.libreria;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GutendexService service = new GutendexService();
        int opcion = 0;
        System.out.println("¡Bienvenido a la Librería!");
        while (opcion != 6) {
            mostrarMenu();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo(service, scanner);
                    break;
               case 2:
                    listarLibrosRegistrados(service);
                    break;
                case 3:
                    listarAutoresRegistr1ados(service);
                    break;
                case 4:
                    listarAutoresVivos(service, scanner);
                    break;
                case 5:
                    listarLibrosPorIdioma(service, scanner);
                    break;
                case 6:
                    System.out.println("Adiós!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("Menú de opciones:");
        System.out.println("1. Buscar libro por título");
        System.out.println("2. Listar libros registrados");
        System.out.println("3. Listar autores registrados");
        System.out.println("4. Listar autores vivos en un determinado año");
        System.out.println("5. Listar libros por idioma");
        System.out.println("6. Salir");
    }

    // Métodos para manejar cada opción
    private static void buscarLibroPorTitulo(GutendexService service, Scanner scanner) {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.next();
        List<Libro> libros = service.buscarLibroPorTitulo(titulo);
        boolean encontrado = false;
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("ID: " + libro.getId());
                System.out.println("Descargas: " + libro.getDownloadCount());
                System.out.println("Autor: ");
                for (Autor autor : libro.getAutor()) {
                    System.out.println("  - " + autor.nombre() + " (Nacimiento: " + autor.nacimiento() + ", Fallecimiento: " + autor.fallecimiento() + ")");
                }
                System.out.println("Temas: " + libro.getTema());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Copyright: " + libro.getCopyright());
                System.out.println("Tipo de medio: " + libro.getMediaType());
                System.out.println("Formato: " + libro.getFormato());
                System.out.println(); // espacio en blanco entre libros
                encontrado = true;
            }
        }
        if (!encontrado){
            System.out.println("No se encontraron libros con el título '" + titulo + "'");
        }
    }
    private static void listarLibrosRegistrados(GutendexService service) {
        List<Libro> libros = service.getAllBooks();
        System.out.println("Todos los libros registrados son: ");
        for (Libro libro : libros) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("ID: " + libro.getId());
                System.out.println(); // espacio en blanco entre libros
        }
    }
    private static void listarAutoresRegistr1ados(GutendexService service) {
        List<Libro> libros = service.getAllBooks();
        System.out.println("Todos los autores registrados son: ");
        for (Libro libro : libros) {
                for (Autor autor : libro.getAutor()) {
                    System.out.println("  - " + autor.nombre() );
                }
        }
    }
    private static void listarAutoresVivos(GutendexService service, Scanner scanner) {
        System.out.print("Ingrese el año en que el autor podría estar/haber estado vivo: ");
        int anio = scanner.nextInt();
        List<Libro> libros = service.getAllBooks(); // o similar, dependiendo de tu API
        boolean encontrado = false;
        for (Libro libro : libros) {
            for (Autor autor : libro.getAutor()) {
                if (anio >= autor.nacimiento() && anio < autor.fallecimiento()) {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("ID: " + libro.getId());
                    System.out.println("Descargas: " + libro.getDownloadCount());
                    System.out.println("Autor: ");
                    System.out.println("  - " + autor.nombre() + " (Nacimiento: " + autor.nacimiento() + ", Fallecimiento: " + autor.fallecimiento() + ")");
                    System.out.println("Temas: " + libro.getTema());
                    System.out.println("Idioma: " + libro.getIdioma());
                    System.out.println("Copyright: " + libro.getCopyright());
                    System.out.println("Tipo de medio: " + libro.getMediaType());
                    System.out.println("Formato: " + libro.getFormato());
                    System.out.println(); // espacio en blanco entre libros
                    encontrado = true;
                }
            }
        }
        if (!encontrado) {
            System.out.println("Ningún autor estuvo vivo en esa época");
        }
    }
    private static void listarLibrosPorIdioma(GutendexService service, Scanner scanner) {
        System.out.print("Ingrese el idioma: ");
        String idioma = scanner.next();
        List<Libro> libros = service.buscarLibroPorIdioma(idioma);

        boolean encontrado = false;
        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("ID: " + libro.getId());
            System.out.println("Descargas: " + libro.getDownloadCount());
            System.out.println("Autor: ");
            for (Autor autor : libro.getAutor()) {
                System.out.println("  - " + autor.nombre() + " (Nacimiento: " + autor.nacimiento() + ", Fallecimiento: " + autor.fallecimiento() + ")");
            }
            System.out.println("Temas: " + libro.getTema());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Copyright: " + libro.getCopyright());
            System.out.println("Tipo de medio: " + libro.getMediaType());
            System.out.println("Formato: " + libro.getFormato());
            System.out.println(); // espacio en blanco entre libros
            encontrado = true;
        }
        if (!encontrado) {
            System.out.println("No se encontraron libros con el idioma '" + idioma + "'");
        }
    }
}