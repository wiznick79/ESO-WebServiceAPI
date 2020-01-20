package edu.ufp.nk.ws1.repositories;

import edu.ufp.nk.ws1.models.Explainer;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ExplainerRepoTest {

    @Autowired
    private ExplainerRepo explainerRepo;

    @Test
    @VisibleForTesting
    void test() {
        Explainer explainer = new Explainer("Nikos Perris");
        Explainer explainer1 = new Explainer("Pedro Alves");
        this.explainerRepo.save(explainer);
        assertEquals(1, explainerRepo.count());
        this.explainerRepo.save(explainer1);
        assertEquals(2, explainerRepo.count());
    }

}
