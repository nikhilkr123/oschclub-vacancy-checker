package io.nsoft.service;

import io.nsoft.client.EmailClient;
import io.nsoft.client.KidsoftClient;
import io.nsoft.config.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class VacancySeekerService {

    private static final Logger logger = LoggerFactory.getLogger(VacancySeekerService.class);

    private static final int POLL_FREQUENCY = 3*60*60*1000;

    @Autowired
    EmailClient emailClient;

    @Autowired
    KidsoftClient kidsoftClient;

    @Autowired
    ConfigurationManager configManager;

    public static int execTimes = 0;
    public static Date lastExecutionTime;

    @Scheduled(fixedDelay = POLL_FREQUENCY)
    public String seekVacancies(){

        execTimes++;
        lastExecutionTime = new Date();

        logger.info("Starting Vacancy Seeker Service Job");
        Scanner scanner = new Scanner(VacancySeekerService.class.getResourceAsStream("/dates.txt"));
        List<String> dates = new ArrayList<>();
        while ((scanner.hasNext())){
            dates.add(scanner.next());
        }

        List<String> vacancies = kidsoftClient.getVacancy(dates);
        logger.info("Number of vacancies available {} ", vacancies.size());

        if(configManager.isNotifyAlways()) {
            emailClient.sendEmail(vacancies);
        }
        else if(vacancies.size() > 0) {
            emailClient.sendEmail(vacancies);
        }
        logger.info("Completed Vacancy Seeker Service Job");
        return vacancies.size() + " vacancies found";
    }
}
