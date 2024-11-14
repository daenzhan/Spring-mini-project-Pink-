package Lab5.Project_mini_final_without_Security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing // чтобы манго автоматом следил за датой для  @CreatedDate
public class ProjectMiniFinalWithoutSecurityApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjectMiniFinalWithoutSecurityApplication.class, args);
	}

}
