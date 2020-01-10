package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.services.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/language")
public class LanguageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LanguageService languageService;

    //Constructor
    @Autowired
    public LanguageController(LanguageService languageService) {

        this.languageService = languageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Language>> getAllLanguages() {
        return ResponseEntity.ok(this.languageService.findAll());
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<Language> getLanguageById(@PathVariable("id") long id) throws NoLanguageException {
        this.logger.info("Received a get request");

        Optional<Language> optionalLanguage = this.languageService.findById(id);
        if (optionalLanguage.isPresent()){
            return ResponseEntity.ok(optionalLanguage.get());
        }
        throw new NoLanguageException(id);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Language> createLanguage(@RequestBody Language language){
        Optional<Language> languageOptional = this.languageService.createLanguage(language);
        if(languageOptional.isPresent()) {
            return ResponseEntity.ok(languageOptional.get());
        }
        throw new LanguageAlreadyExistsException(language.getName());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Language> editLanguage(@PathVariable("id") long id, @RequestBody Language language){
        Optional<Language> languageOptional = this.languageService.updateLanguage(id, language);

        if(languageOptional.isPresent()) {
            return ResponseEntity.ok(languageOptional.get());
        }
        throw new LanguageAlreadyExistsException(language.getName());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such language")
    private static class NoLanguageException extends RuntimeException {
        public NoLanguageException(Long id) {
            super("No such language with id: " + id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Language already exists")
    private static class LanguageAlreadyExistsException extends RuntimeException {
        public LanguageAlreadyExistsException(String name) {
            super("A language with name: "+name+" already exists");
        }
    }

}
