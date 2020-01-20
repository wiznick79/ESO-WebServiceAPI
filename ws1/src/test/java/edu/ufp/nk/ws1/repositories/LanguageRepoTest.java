package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Language;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class LanguageRepoTest {
    @Autowired
    private LanguageRepo languageRepo;

    @Test
    @VisibleForTesting
    void test() {
        Language language = new Language("Portuguese");
        Language language1 = new Language("Greek");
        this.languageRepo.save(language);
        assertEquals(1, languageRepo.count());
        this.languageRepo.save(language1);
        assertEquals(2, languageRepo.count());
    }
}
