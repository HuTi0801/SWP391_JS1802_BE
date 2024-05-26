package com.js1802_team5.diamondShop;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class DiamondShopApplication {

	//private final DiamondRepo diamondRepo;

	public static void main(String[] args) {
		SpringApplication.run(DiamondShopApplication.class, args);
	}
	//@Bean
//	public CommandLineRunner initData() {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				Diamond diamond1 = Diamond.builder()
//						.cut("EX")
//						.color('F')
//						.caratWeight(3.6f)
//						.clarity("VS2")
//						.price(11045000d)
//						.origin("Natural diamond")
//						.quantity(100)
//						.certificateNumber("123456789")
//						.build();
//
//				Diamond diamond2 = Diamond.builder()
//						.cut("EX")
//						.color('E')
//						.caratWeight(3.6f)
//						.clarity("VS1")
//						.price(11421000d)
//						.origin("Artificial diamond")
//						.quantity(100)
//						.certificateNumber("987654321")
//						.build();
//
//				diamondRepo.save(diamond1);
//				diamondRepo.save(diamond2);
//			}
//		};
//	}
}
