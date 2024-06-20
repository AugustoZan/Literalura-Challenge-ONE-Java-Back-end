package com.alura.libreria;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Libro {
    final private int id;
    final private String titulo;
    final private List<Autor> autor;
    final private List<String> tema;
    final private List<String> idioma;
    final private String copyright;
    final private String mediaType;
    final private Map<String, String> formato;
    final private int downloadCount;


    public Libro(int id, int downloadCount, String titulo, List<Map<String, Object>> authors, List<String> subjects, List<String> idioma, String copyright, String mediaType, Map<String, String> formato) {
        this.id = id;
        this.downloadCount = downloadCount;
        this.titulo = titulo;
        this.autor = authors.stream().map(author -> new Autor(
                (String) author.get("name"), // nombre
                author.containsKey("birth_year")? (int) ((Double) author.get("birth_year")).doubleValue() : 0, // nacimiento
                author.containsKey("death_year")? (int) ((Double) author.get("death_year")).doubleValue() : 0 // fallecimiento
        )).collect(Collectors.toList());
        this.tema = subjects;
        this.idioma = idioma;
        this.copyright = copyright;
        this.mediaType = mediaType;
        this.formato = formato;
    }

    public int getId() {
        return id;
    }



    public String getTitulo() {
        return titulo;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public List<String> getTema() {
        return tema;
    }

    public List<String> getIdioma() {
        return idioma;
    }


    public Map<String, String> getFormato() {
        return formato;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getCopyright() {
        return copyright;
    }

}

