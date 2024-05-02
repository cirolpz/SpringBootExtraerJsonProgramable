package org.example;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling // Activa el programador de tareas de Spring
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);}
}