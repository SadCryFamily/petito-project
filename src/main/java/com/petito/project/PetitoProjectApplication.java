package com.petito.project;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class PetitoProjectApplication
{
    @Transactional
    public static void main(String[] args)
	{
        SpringApplication.run(PetitoProjectApplication.class, args);
    }
}
