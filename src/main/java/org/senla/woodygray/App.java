package org.senla.woodygray;


import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class App {

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure()
//                .build();
//
//        try {
//            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
//        } catch (Exception e) {
//            StandardServiceRegistryBuilder.destroy(registry);
//            return null;
//        }
//    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
