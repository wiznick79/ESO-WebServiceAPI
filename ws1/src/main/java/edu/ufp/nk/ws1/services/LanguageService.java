package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.repositories.LanguageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LanguageService {
    private LanguageRepo languageRepo;

    @Autowired
    public LanguageService(LanguageRepo languageRepo) {
        this.languageRepo=languageRepo;
    }

    public Set<Language> findAll() {
        Set<Language> languages = new HashSet<>();
        for (Language language:this.languageRepo.findAll()){
            languages.add(language);
        }
        return languages;
    }

    public Optional<Language> findById(Long id) {
        return this.languageRepo.findById(id);
    }

    public Optional<Language> createLanguage(Language language){
        Optional<Language> optionalLanguage = this.languageRepo.findByName(language.getName());
        if (optionalLanguage.isPresent()){
            return Optional.empty();
        }
        Language createLanguage= this.languageRepo.save(language);
        return Optional.of(createLanguage);
    }

    public Optional<Language> findByName (String name) {
        return this.languageRepo.findByName(name);
    }
}
