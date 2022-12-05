package bessa.morangon.rafael.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FinanceiroApplication {

	public static void main(String[] args) {

		SpringApplication.run(FinanceiroApplication.class, args);
	}

}
