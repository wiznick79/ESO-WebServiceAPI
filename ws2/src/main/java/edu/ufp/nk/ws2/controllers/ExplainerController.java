package edu.ufp.nk.ws2.controllers;

import edu.ufp.nk.ws2.models.Explainer;
import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.services.ExplainerService;
import edu.ufp.nk.ws2.services.UniversityService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @RequestMapping(value="",method = RequestMethod.GET)
    public ResponseEntity<List<Explainer>> getExplainers(@RequestParam Map<String,String> query){
        this.logger.info("Received GET Request");

        Long university_id = null;
        try {
            university_id = Long.parseLong(query.get("university") );
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }

        String degree = query.get("degree");
        LocalDate day = LocalDate.parse(query.get("day"));
        LocalTime start = LocalTime.parse(query.get("start"));
        LocalTime end = LocalTime.parse(query.get("end"));
        // when we search for explainers in specific university
        if (university_id!=null) {
            Optional<University> optionalUniversity = universityService.getUniversityById(university_id);
            if (optionalUniversity.isPresent()) {
                University university = optionalUniversity.get();

                String path = university.getIp().concat("explainer?degree="+degree+"&day="+day+"&start="+start+"&end="+end);

                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> nullBody = new HttpEntity<>(null, headers);
                ResponseEntity<List<Explainer>> responseEntity = makeRequest(path, HttpMethod.GET, nullBody, List.class);
                this.logger.info("Send GET Request to university "+university.getName());

                return ResponseEntity.ok(responseEntity.getBody());
            }
        }
        // when we search for explainers without specifying a university
        List<Explainer> explainers=new ArrayList<>();
        for (University university: universityService.getAllUniversities()) {
            String path = university.getIp().concat("explainer?degree="+degree+"&day="+day+"&start="+start+"&end="+end);

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> nullBody = new HttpEntity<>(null, headers);
            try {
                ResponseEntity<List<Explainer>> responseEntity = makeRequest(path, HttpMethod.GET, nullBody, List.class);
                this.logger.info("Send GET Request to university "+university.getName());
                if(responseEntity.getBody()!=null) {
                    explainers.addAll(responseEntity.getBody());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return ResponseEntity.ok(explainers);
    }

    private ResponseEntity makeRequest(String path, HttpMethod method, HttpEntity request, Class responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(path, method, request, responseType);
    }

}
