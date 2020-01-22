package edu.ufp.nk.ws2.controllers;

import edu.ufp.nk.ws2.models.Explainer;
import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.services.ExplainerService;
import edu.ufp.nk.ws2.services.UniversityService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@RestController
@RequestMapping("/explainer")
public class ExplainerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExplainerService explainerService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    public ExplainerController(ExplainerService explainerService){
        this.explainerService = explainerService;
    }

    @RequestMapping(value="/{university}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> createExplainer(@RequestBody Explainer explainer, @PathVariable("university") Long university_id) {
        Optional<University> optionalUniversity = universityService.getUniversityById(university_id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();
            String path = university.getIp().concat("explainer");
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Explainer> body = new HttpEntity<>(explainer, headers);
            ResponseEntity<Explainer> responseEntity = makeRequest(path, HttpMethod.POST, body, Explainer.class);
            this.logger.info("Send POST request to "+university.getName());
            if (responseEntity.getStatusCodeValue() == 200) {
                Explainer tutor = responseEntity.getBody();
                if (tutor!=null) {
                    tutor.setUniversity(university);
                    return ResponseEntity.ok(tutor);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }


    @RequestMapping(value="", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Explainer>> getAllExplainers(){
        this.logger.info("Received GET Request");
        Iterable<Explainer> explainer = explainerService.getAllExplainers();
        return ResponseEntity.ok(explainer);
    }

    private ResponseEntity makeRequest(String path, HttpMethod method, HttpEntity request, Class responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(path, method, request, responseType);
    }

}
