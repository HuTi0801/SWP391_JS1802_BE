package com.js1802_team5.diamondShop;

import com.js1802_team5.diamondShop.enums.MemberLevel;
import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.entity_models.Customer;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
import com.js1802_team5.diamondShop.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class DiamondShopApplication {
	private final AccountRepo accountRepo;
	private final CustomerRepo customerRepo;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(DiamondShopApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
//				Account manager = Account.builder()
//						.username("tienbh")
//						.firstName("Tien")
//						.lastName("Bui")
//						.pass(passwordEncoder.encode("123456"))
//						.role(Role.MANAGER)
//						.isActive(true)
//						.build();
//
//				Account admin = Account.builder()
//						.username("duyenntp")
//						.firstName("Duyen")
//						.lastName("Nguyen")
//						.pass(passwordEncoder.encode("123456"))
//						.role(Role.ADMIN)
//						.isActive(true)
//						.build();
//
//				Account sale_staff = Account.builder()
//						.username("khoatnn")
//						.firstName("Khoa")
//						.lastName("Tran")
//						.pass(passwordEncoder.encode("123456"))
//						.role(Role.SALE_STAFF)
//						.isActive(true)
//						.build();
//
//				Account delivery_staff = Account.builder()
//						.username("datnd")
//						.firstName("Dat")
//						.lastName("Ngo")
//						.pass(passwordEncoder.encode("123456"))
//						.role(Role.DELIVERY_STAFF)
//						.isActive(true)
//						.build();
//
//				Account customer = Account.builder()
//						.username("0773898293")
//						.firstName("Huong")
//						.lastName("Nguyen")
//						.pass(passwordEncoder.encode("12345"))
//						.role(Role.CUSTOMER)
//						.isActive(true)
//						.build();
//
//
//				Customer customer_1= Customer.builder()
//						.email("huongntc@gmail.com")
//						.phone("0773898293")
//						.point(0)
//						.memberLevel(MemberLevel.SILVER.toString())
//						.account(customer)
//						.build();
//
//				accountRepo.save(customer);
//				accountRepo.save(admin);
//				accountRepo.save(sale_staff);
//				accountRepo.save(delivery_staff);
//				accountRepo.save(manager);
//
//				customerRepo.save(customer_1);

			}
		};
	}
}
