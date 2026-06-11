package com.firstclub.membership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FirstClub Membership Spring Boot Application.
 */
@SpringBootApplication
public class MembershipApplication {

    /**
     * Bootstraps the application, starts the embedded Tomcat server, and initializes the Spring context.
     *
     * @param args command-line arguments passed during startup
     */
    public static void main(String[] args) {
        SpringApplication.run(MembershipApplication.class, args);
    }

}
