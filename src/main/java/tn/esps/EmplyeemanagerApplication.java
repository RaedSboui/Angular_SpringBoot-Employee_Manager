package tn.esps;

import java.io.File;

import org.apache.commons.io.FileUtils;
// import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.SneakyThrows;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
*/

@SpringBootApplication
@EnableScheduling
public class EmplyeemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmplyeemanagerApplication.class, args);
	}

	
	/********************************  Cron Scheduled Configuration  *******************************/
	@SneakyThrows
	@Scheduled(cron = "${cron.expression}")
	public void cleanAttachmentsFolder() {
		File directory = new File(
				"C:\\Users\\lenovo\\Documents\\workspace-spring-tool-suite-4-4.12.0.RELEASE\\emplyeemanager\\src\\main\\resources\\Attachments\\");
		FileUtils.cleanDirectory(directory);
		System.out.println("Attachments folder is clear now !");
	}

	
	/***********************************  Cors Configuration  **********************************/

	// I replaced it with annotation @CrossOrigin in all controllers
/*
	@Bean
	public CorsFilter corsFilter() {

		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);

		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

		corsConfiguration.setAllowedHeaders(Arrays.asList("origin", "Acces-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Request-With", "Access-Control-Request-Method",
				"Acces-Control-Request-Headers"));

		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Acces-Contol-Allow-Origin", "Acces-Contol-Allow-Origin", "Acces-Contol-Allow-Credentials"));

		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
*/

}
