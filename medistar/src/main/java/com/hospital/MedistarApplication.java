package com.hospital;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import com.hospital.service.MailService;


@SpringBootApplication
@ComponentScan(basePackages = {"com.hospital"})
@EntityScan(basePackages = {"com.hospital"})
@EnableJpaRepositories(basePackages = {"com.hospital"})
public class MedistarApplication{

	public static void main(String[] args) {

		SpringApplication.run(MedistarApplication.class, args);
	}

   /* @Bean
    public CommandLineRunner testMail(MailService mailService) {
        return args -> {
            try {
                System.out.println("Mail gönderiliyor...");
                mailService.sendSimpleMail(
                    "fatmagularabaci108@gmail.com",
                    "Test Maili",
                    "hayili gunler guzum"
                );
                System.out.println("Mail basariyla gonderildi!");
            } catch (Exception e) {
                System.err.println("HATA: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }*/
}
