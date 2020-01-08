package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.repositories.LanguageRepo;
import edu.ufp.nk.ws1.services.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/language")
public class LanguageController {
    private LanguageRepo languageRepo;
    private LanguageService languageService;

    //Constructor
    public LanguageController(LanguageRepo languageRepo) {
        this.languageRepo = languageRepo;
    }

    @GetMapping
    public ResponseEntity<Iterable<Language>> getAllLanguages(){
        return ResponseEntity.ok(this.languageRepo.findAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Language> searchLanguage(@PathVariable long id){
        Language found = languageRepo.findById(id);

        if(found == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(found);
    }

    @PostMapping
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    public ResponseEntity<Language> addLanguage(@Valid @RequestBody Language novo){
        if(languageRepo.findByName(novo.getName()) != null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(languageRepo.save(novo));
    }
}
