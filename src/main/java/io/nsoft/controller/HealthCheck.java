package io.nsoft.controller;

import io.nsoft.VacancySeekerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @RequestMapping("/stats")
    ResponseEntity<String> statistics() {
        StringBuilder resp = new StringBuilder();

        resp.append("Job executed " + VacancySeekerService.execTimes + " times <br>");
        resp.append("Last executed at " + VacancySeekerService.lastExecutionTime);

        return new ResponseEntity(resp, HttpStatus.OK);
    }
}
