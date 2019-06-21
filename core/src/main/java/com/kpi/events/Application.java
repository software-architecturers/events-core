package com.kpi.events;

import com.kpi.events.model.files.FileStorageProperties;
import com.kpi.miss.model.repository.MissKpiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"com.kpi.events", "com.kpi.miss"})
@EnableJpaRepositories(
        basePackages = {"com.kpi.events.model.repository", "com.kpi.miss.model.repository"},
        basePackageClasses = MissKpiRepository.class)
@EntityScan(basePackages = {"com.kpi.miss", "com.kpi.events"})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").allowedOrigins("*");
            }
        };
    }
}