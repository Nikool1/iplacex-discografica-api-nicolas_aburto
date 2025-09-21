package org.iplacex.proyectos.discografia.discos;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {
    private final IDiscoRepository discoRepo;
    private final IArtistaRepository artistaRepo;

    public DiscoController(IDiscoRepository discoRepo, IArtistaRepository artistaRepo) {
        this.discoRepo = discoRepo;
        this.artistaRepo = artistaRepo;
    }

    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepo.existsById(disco.idArtista)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El artista no existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(discoRepo.save(disco));
    }

    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(discoRepo.findAll());
    }

    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> disco = discoRepo.findById(id);
        return disco.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disco no encontrado"));
    }

    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        return ResponseEntity.ok(discoRepo.findDiscosByIdArtista(id));
    }
}
