#Literalura

*Literalura fue un challenge de [Alura Latam](https://github.com/alura-es-cursos "Alura Latam"). 
El proyecto consiste en un menú que te permite consultar acerca de libros disponibles, autores registrados, buscar un libro por su título o por su idioma, etc. Funciona a través del lenguaje de programación **Java** utilizando una API que almacena la información de los libros. También se utilizó **Spring**. *
## Características

>- Un menú con 6 opciones. Buscar libros por sus títulos o idiomas y listar libros o autores.


## Estructura

El proyecto utiliza 6 archivos .java:
- **Main.java**: Este archivo corre el programa y utiliza métodos para hacer **request** a **GutendexService.java**.

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
			 }

- **GutendexService.java**: Es la clase que trae la información de la API.

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
  


- **LibreriaApplication.java**: Conecta con SpringBoot.

   	import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;

		@SpringBootApplication
		public class LibreriaApplication {

			public static void main(String[] args) {
				SpringApplication.run(LibreriaApplication.class, args);
			}

			}

- **Controller.java**: Se encarga de hacer listas y maps a **GutendexService.java**. 
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
- **Libro.java**: Sus atributos son utilizados como referencias para los valores de la API.

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
			//...
		}
- **Autor.java**: Al igual que **Libro.java**, sus atributos son utilizados como referencia para **GutendexService.java**. 

  		public record Autor(String nombre, int nacimiento, int fallecimiento) {}
## Tecnología utilizada
- Java (Lenguaje de programación dónde se trabajó el proyecto)
- SpringBoot (Se utilizó con Maven)
- [API](https://gutendex.com/) (Contenedor de la información acerca de los libros)
- El Gson utilizado es  **com.google.gson.Gson**.
