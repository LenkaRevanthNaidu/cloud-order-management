package com.revanth.cloudordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CloudOrderManagementApplication {

    public static void main(String[] args) {

        System.out.println("Java timezone property: "
                + System.getProperty("user.timezone"));

        System.out.println("Java default timezone: "
                + java.util.TimeZone.getDefault().getID());

        SpringApplication.run(CloudOrderManagementApplication.class, args);
    }

}
