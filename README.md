**What is this?**

Across Australia [OSHClub](https://www.oshclub.com.au/) has been providing high-quality Outside School Hours Care (OSHC) services for school communities since 2008. In many schools, parents are struggling to get a vacancy for their kids for OSHC because seats are limited and often they will be overbooked. This application will notifies you when there is a vacancy available for the given dates.

**Prerequisites**
* Java 8 or more
* Maven 3

**How to run**

1. Provide details like your OSCH club login, email details etc in `src/main/resources/application.properties`
2. Specify the dates you are seeking service for in `src/main/resources/dates.txt` in `DD-MMM-YYYY` format. e.g `01-01-2019`
3. This application polls the online booking portal of [OSHClub](https://parentslogin.kidsoft.com.au) every 3 hours.
4. Run `mvn spring-boot:run`
