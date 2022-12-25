package Server.Calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorServerApplication.class, args);
		System.out.println("Server is listening on port 8496...");
	}

}
