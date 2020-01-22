package edu.ufp.nk.ws2.controllers;

import edu.ufp.nk.ws2.models.College;
import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.services.UniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/university")
public class UniversityController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UniversityService universityService;

    //Construtor
    @Autowired
    public UniversityController(UniversityService universityService){
        this.universityService=universityService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<University>> getAllUniversities(){
        this.logger.info("GET");
        Iterable<University> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }

    @GetMapping(value = "/college")
    public Iterable<University> getAllCollegesOfUniversity(){
        for (University university: universityService.getAllUniversities()){
            String path = university.getIp().concat("college");
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> nullBody = new HttpEntity<>(null,headers);
            ResponseEntity<College[]> responseEntity= makeRequest(path,HttpMethod.GET,nullBody,College[].class);
            this.logger.info("Get Request");

            for (College college: responseEntity.getBody()){
                university.addCollege(college);
            }
            universityService.createUniversity(university);
        }
        return universityService.getAllUniversities();
    }


    private ResponseEntity makeRequest(String path, HttpMethod method, HttpEntity request, Class responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(path, method, request, responseType);
    }
}
