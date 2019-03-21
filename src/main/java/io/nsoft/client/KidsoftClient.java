package io.nsoft.client;

import io.nsoft.config.ConfigurationManager;
import io.nsoft.kidsoft.model.KidsoftResponse;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Component
public class KidsoftClient {

    private static final Logger log = LoggerFactory.getLogger(KidsoftClient.class);

    @Autowired
    ConfigurationManager configManager;

    public List<String> getVacancy(List<String> dates) {
        String cookie = login();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.add("Cookie", cookie);

        List<String> vacancyDates = new ArrayList<>();

        for (String date : dates) {
            try {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("ChildIDs[]", configManager.getKidsoftChildId());
                map.add("StartDate", date);
                map.add("EndDate", date);
                map.add("controller", "ParentPortal/APIv1/ParentPortalBooking");
                map.add("action", "getVacancies");
                map.add("APIKey", configManager.getKidsoftApiKey());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

                RestTemplate restTemplate = new RestTemplate();

                ResponseEntity<KidsoftResponse> stringResponseEntity = restTemplate.postForEntity("https://parents.kidsoft.com.au/index.php/ParentPortal/APIv1/ParentPortalBooking/getVacancies",
                        request, KidsoftResponse.class);
                int vacancy = Integer.valueOf(stringResponseEntity.getBody().getPayload().getRooms().get(0).dateVacancies.values().stream().findFirst().get().get("Vacancies").toString());
                log.info("{} : {}", date, vacancy);

                if (vacancy > 0) {
                    vacancyDates.add(date);
                }
            }
            catch (Exception e){
                log.error("Error while getting vacancy for {} : {}", date, e);
            }
        }
        return vacancyDates;
    }

    private String login(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.add("Connection", "keep-alive");

        MultiValueMap <String, String> map = new LinkedMultiValueMap<>();
        map.add("Username", configManager.getKidsoftUserName());
        map.add("Password", configManager.getKidsoftPassword());
        map.add("action", "login");
        map.add("controller", "Login");

        String cookie = null;

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            log.info("Logging into KidSoft");
            ResponseEntity<KidsoftResponse> stringResponseEntity = restTemplate.postForEntity("https://parentslogin.kidsoft.com.au/index.php/Login/login",
                    request, KidsoftResponse.class);

            String redirectUrl = stringResponseEntity.getBody().getPayload().redirectUrl;

            log.info("Received redirect URL {}", redirectUrl);

            ResponseEntity<String> forEntity = restTemplate.getForEntity(redirectUrl, String.class);

            log.info("Setting cookie {}", forEntity.getHeaders().get("Set-Cookie"));

            cookie = forEntity.getHeaders().get("Set-Cookie").stream().findFirst().get();

            if (cookie != null && !cookie.isEmpty()) {
                log.info("Successfully logged into KidSoft");
            } else {
                log.error("Failed log into KidSoft");
            }
        }
        catch (Exception ex){
            log.error("Error while log into KidSoft : {} : {}", ex.getMessage(), ex);
        }
        return cookie;
    }
}
