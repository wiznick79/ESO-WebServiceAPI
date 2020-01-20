package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Language;
import edu.ufp.nk.ws1.repositories.LanguageRepo;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LanguageService.class)
@VisibleForTesting
class LanguageServiceTest {
    @Autowired
    private LanguageService languageService;
    @MockBean
    private LanguageRepo languageRepo;

    @Test
    @VisibleForTesting
    void accessLanguage(){
        Language language = new Language("Greek");
        Language language1 = new Language("Portuguese");
        Language language2 = new Language("English");
        this.languageRepo.save(language);
        this.languageRepo.save(language1);
        this.languageRepo.save(language2);
        Set<Language> languages = new HashSet<>();
        languages.add(language);
        languages.add(language1);
        languages.add(language2);

        when(this.languageService.findById(1L)).thenReturn(Optional.of(language));
        assertNotNull(language);
        when(this.languageService.findAll()).thenReturn(languages);
        when(this.languageService.findByName(language.getName())).thenReturn(Optional.of(language));
        when(this.languageService.createLanguage(language)).thenReturn(Optional.of(language));
        when(this.languageService.updateLanguage(language1.getId(),language1)).thenReturn(Optional.of(language2));
    }

}
