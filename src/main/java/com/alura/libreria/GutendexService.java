package com.alura.libreria;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class GutendexService {

    private static final String API_URL = "https://gutendex.com/books/";

    public List<Libro> buscarLibroPorTitulo(String titulo) {
        try {
            URL url = new URL(API_URL + "?search[]=title:" + titulo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Error al buscar el libro: " + connection.getResponseMessage());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            Map<String, Object> response = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());

            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

            return results.stream()
                    .map(map -> new Libro(
                            ((Double) map.get("id")).intValue(), // Cast to Double and then to int
                            ((Double) map.get("download_count")).intValue(), // Cast to Double and then to int
                            (map.get("title")!= null && map.get("title") instanceof Boolean)? "" : (String) map.get("title"), // Check if title is Boolean and cast accordingly
                            (List<Map<String, Object>>) map.get("authors"),
                            (List<String>) map.get("subjects"),
                            (List<String>) map.get("languages"),
                            map.get("copyright") != null && map.get("copyright") instanceof String ? (String) map.get("copyright") : "Unknown",
                            (String) map.get("media_type"),
                            (Map<String, String>) map.get("formats")
                    ))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Error al buscar el libro: " + e.getMessage(), e);
        }

    }
    public List<Libro> buscarLibroPorIdioma(String idioma) {
        try {
            URL url = new URL(API_URL + "?search[]=languages:" + idioma);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Error al buscar el libro: " + connection.getResponseMessage());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            Map<String, Object> response = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());

            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

            return results.stream()
                    .filter(map -> {
                        List<String> languages = (List<String>) map.get("languages");
                        return languages!= null && languages.size() == 1 && languages.get(0).equals(idioma);
                    })
                    .map(map -> new Libro(
                            ((Double) map.get("id")).intValue(), // Cast to Double and then to int
                            ((Double) map.get("download_count")).intValue(), // Cast to Double and then to int
                            (map.get("title")!= null && map.get("title") instanceof Boolean)? "" : (String) map.get("title"), // Check if title is Boolean and cast accordingly
                            (List<Map<String, Object>>) map.get("authors"),
                            (List<String>) map.get("subjects"),
                            (List<String>) map.get("languages"),
                            map.get("copyright") != null && map.get("copyright") instanceof String ? (String) map.get("copyright") : "Unknown",
                            (String) map.get("media_type"),
                            (Map<String, String>) map.get("formats")
                    ))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Error al buscar el idioma: " + e.getMessage(), e);
        }

    }
    public List<Libro> getAllBooks() {
        return buscarLibroPorTitulo("");
    }
}