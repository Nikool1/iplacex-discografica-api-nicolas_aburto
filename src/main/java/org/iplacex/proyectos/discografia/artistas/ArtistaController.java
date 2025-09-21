package org.iplacex.proyectos.discografia.artistas;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {
    private final IArtistaRepository repo;
    public ArtistaController(IArtistaRepository repo) {
        this.repo = repo;
    }

    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(artista));
    }

    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        Optional<Artista> artista = repo.findById(id);
        if (artista.isPresent()) {
            return ResponseEntity.ok(artista.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
        }
    }

    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }
        artista._id = id;
        return ResponseEntity.ok(repo.save(artista));
    }

    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Eliminado con éxito");
    }
}
