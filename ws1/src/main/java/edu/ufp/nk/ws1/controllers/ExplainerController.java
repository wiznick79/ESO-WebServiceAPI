package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.services.ExplainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/explainer")
public class ExplainerController {
    private ExplainerRepo explainerRepo;
    private ExplainerService explainerService;

    //Constructor
    public ExplainerController(ExplainerRepo explainerRepo) {
        this.explainerRepo = explainerRepo;
    }

    @GetMapping
    public ResponseEntity<Iterable<Explainer>> getAllExplainers(){
        return ResponseEntity.ok(this.explainerRepo.findAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Explainer> searchExplainer(@PathVariable long id){
        Explainer found = explainerRepo.findById(id);

        if(found == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(found);
    }

    @PostMapping
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    // TODO: Verificar o findByStartAndDate
    public ResponseEntity<Explainer> addExplainer(@Valid @RequestBody Explainer novo){
        if (explainerRepo.findByName(novo.getName())!=null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(explainerRepo.save(novo));
    }

}
