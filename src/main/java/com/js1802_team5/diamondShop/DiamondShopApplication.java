package com.js1802_team5.diamondShop;

import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.repositories.*;
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

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DiamondShopApplication {
//	private final CustomerRepo customerRepo;
//	private final DiamondRepo diamondRepo;
//	private final DiamondShellRepo diamondShellRepo;
//	private final SizeRepo sizeRepo;
//	private final StatusOrderRepo statusOrderRepo;
//	private final SizeDiamondShellRepo sizeDiamondShellRepo;
//	public static void main(String[] args) {
//		SpringApplication.run(DiamondShopApplication.class, args);
//	}
//	@Bean
//	public CommandLineRunner initData() {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//
//				//Add customer
//				Customer customer = Customer.builder()
//						.email("phamquynh2003qs@gmail.com")
//						.memberLevel("")
//						.phone("0988546643")
//						.point(0)
//						.build();
//
//				Customer customer1 = Customer.builder()
//						.email("lykimchi@gmail.com")
//						.memberLevel("")
//						.phone("0924512422")
//						.point(0)
//						.build();
//
//				Customer customer2 = Customer.builder()
//						.email("nguyenthitrang@gmail.com")
//						.memberLevel("")
//						.phone("0981893407")
//						.point(0)
//						.build();
//
//				Customer customer3 = Customer.builder()
//						.email("nguyentramthientu@gmail.com")
//						.memberLevel("")
//						.phone("0988642532")
//						.point(0)
//						.build();
//
//				customerRepo.save(customer);
//				customerRepo.save(customer1);
//				customerRepo.save(customer2);
//				customerRepo.save(customer3);
//
//				//Add diamond
//				Diamond diamond = Diamond.builder()
//						.cut("EX")
//						.color("G")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA1234567890")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1000)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(11045000)
//						.build();
//
//				Diamond diamond1 = Diamond.builder()
//						.cut("EX")
//						.color("G")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA9876543210")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1000)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(11421000)
//						.build();
//
//				Diamond diamond2 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA1230984567")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1500)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(11891000)
//						.build();
//
//				Diamond diamond3 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA4567891230")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1500)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(12267000)
//						.build();
//
//				Diamond diamond4 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA5432109876")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1300)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(12267000)
//						.build();
//
//				Diamond diamond5 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA1122334455")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1300)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(12643000)
//						.build();
//
//				Diamond diamond6 = Diamond.builder()
//						.cut("EX")
//						.color("D")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA9988776655")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1400)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(12643000)
//						.build();
//
//				Diamond diamond7 = Diamond.builder()
//						.cut("EX")
//						.color("H")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA6677889900")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1250)
//						.clarity("VVS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(12737000)
//						.build();
//
//				Diamond diamond8 = Diamond.builder()
//						.cut("EX")
//						.color("D")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA2233445566")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1050)
//						.clarity("IF")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(18095000)
//						.build();
//
//				Diamond diamond9 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(3.6F)
//						.certificateNumber("GIA5544332211")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1250)
//						.clarity("IF")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/9191ddc7-39fd-4393-ba56-1f087466b3ea-Diamond3.6lyFrontalOrigin.png?alt=media&token=681ab590-e60a-4999-a67c-8f347e129642")
//						.statusDiamond(true)
//						.price(17155000)
//						.build();
//
//				Diamond diamond10 = Diamond.builder()
//						.cut("VG")
//						.color("F")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA4433221100")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1643)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(17672000)
//						.build();
//
//				Diamond diamond11 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA5566778899")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1250)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(18659000)
//						.build();
//
//				Diamond diamond12 = Diamond.builder()
//						.cut("EX")
//						.color("H")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA1122446688")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1250)
//						.clarity("VVS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(18894000)
//						.build();
//
//				Diamond diamond13 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA9988112233")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1150)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(18659000)
//						.build();
//
//				Diamond diamond14 = Diamond.builder()
//						.cut("EX")
//						.color("G")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA3344556677")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1250)
//						.clarity("VVS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(19646000)
//						.build();
//
//				Diamond diamond15 = Diamond.builder()
//						.cut("EX")
//						.color("D")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA1001101201")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1000)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(20116000)
//						.build();
//
//				Diamond diamond16 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(4.1F)
//						.certificateNumber("GIA1002102302")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1050)
//						.clarity("VVS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(21432000)
//						.build();
//
//				Diamond diamond17 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(4.5F)
//						.certificateNumber("GIA1003103403")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(1250)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(21582000)
//						.build();
//
//				Diamond diamond18 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(4.5F)
//						.certificateNumber("GIA1003103403")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1330)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(22438000)
//						.build();
//
//				Diamond diamond19 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(4.3F)
//						.certificateNumber("GIA1003103403")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1425)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(23594000)
//						.build();
//
//				Diamond diamond20 = Diamond.builder()
//						.cut("EX")
//						.color("H")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1004104504")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(965)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(66129000)
//						.build();
//
//				Diamond diamond21 = Diamond.builder()
//						.cut("EX")
//						.color("H")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1005105605")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(800)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(67116000)
//						.build();
//
//				Diamond diamond22 = Diamond.builder()
//						.cut("EX")
//						.color("H")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1006106706")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(965)
//						.clarity("VVS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(71487000)
//						.build();
//
//				Diamond diamond23 = Diamond.builder()
//						.cut("EX")
//						.color("H")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1007107807")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(1000)
//						.clarity("VVS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(73273000)
//						.build();
//
//				Diamond diamond24 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1008108908")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(766)
//						.clarity("VVS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(83991000)
//						.build();
//
//				Diamond diamond25 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1009110010")
//						.origin("Artificial diamonds (HTTP)")
//						.quantity(643)
//						.clarity("IF")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(98333000)
//						.build();
//
//				Diamond diamond26 = Diamond.builder()
//						.cut("EX")
//						.color("D")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1010111121")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(542)
//						.clarity("IF")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(116038000)
//						.build();
//
//				Diamond diamond27 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1011112232")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(500)
//						.clarity("IF")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(107037000)
//						.build();
//
//				Diamond diamond28 = Diamond.builder()
//						.cut("EX")
//						.color("D")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1012113343")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(340)
//						.clarity("VVS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(103519000)
//						.build();
//
//				Diamond diamond29 = Diamond.builder()
//						.cut("EX")
//						.color("D")
//						.caratWeight(5.4F)
//						.certificateNumber("GIA1013114454")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(400)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/25373e25-c60f-4ed6-b73c-9afe61b6e258-Diamond5.0lyOriginalAbove.webp?alt=media&token=15321856-2b09-4b93-b884-be8c39dc073a")
//						.statusDiamond(true)
//						.price(95051000)
//						.build();
//
//				Diamond diamond30 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(6.0F)
//						.certificateNumber("GIA1014115565")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(100)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0bd8e181-345f-4b53-92d4-1c0ce9386df1-Diamond6.0lyFrontalOrigin.png?alt=media&token=b59fb150-6770-4376-96cc-3d3acda61602")
//						.statusDiamond(true)
//						.price(144446000)
//						.build();
//
//				Diamond diamond31 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(6.0F)
//						.certificateNumber("GIA1015116676")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(110)
//						.clarity("VS1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0bd8e181-345f-4b53-92d4-1c0ce9386df1-Diamond6.0lyFrontalOrigin.png?alt=media&token=b59fb150-6770-4376-96cc-3d3acda61602")
//						.statusDiamond(true)
//						.price(157821000)
//						.build();
//
//				Diamond diamond32 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(6.0F)
//						.certificateNumber("GIA1015116676")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(100)
//						.clarity("VVS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0bd8e181-345f-4b53-92d4-1c0ce9386df1-Diamond6.0lyFrontalOrigin.png?alt=media&token=b59fb150-6770-4376-96cc-3d3acda61602")
//						.statusDiamond(true)
//						.price(195455000)
//						.build();
//
//				Diamond diamond33 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(6.6F)
//						.certificateNumber("GIA1016117787")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(100)
//						.clarity("SI1")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(282517000)
//						.build();
//
//				Diamond diamond34 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(6.3F)
//						.certificateNumber("GIA1017118898")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(100)
//						.clarity("VVS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(288686000)
//						.build();
//
//				Diamond diamond35 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(6.3F)
//						.certificateNumber("GIA1018120010")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(100)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(307105000)
//						.build();
//
//				Diamond diamond36 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(7.2F)
//						.certificateNumber("GIA1019121121")
//						.origin("Artificial diamonds (HPHT)")
//						.quantity(150)
//						.clarity("VVS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(609079000)
//						.build();
//
//				Diamond diamond37 = Diamond.builder()
//						.cut("EX")
//						.color("F")
//						.caratWeight(8.1F)
//						.certificateNumber("GIA1020122232")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(100)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(1183830000)
//						.build();
//
//				Diamond diamond38 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(8.1F)
//						.certificateNumber("GIA1021123343")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(100)
//						.clarity("VVS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(1468621000)
//						.build();
//
//				Diamond diamond39 = Diamond.builder()
//						.cut("EX")
//						.color("E")
//						.caratWeight(9.0F)
//						.certificateNumber("GIA1022124454")
//						.origin("Artificial diamonds (CVD)")
//						.quantity(120)
//						.clarity("VS2")
//						.imageDiamond("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/0cd527de-e00f-468d-91a9-859b4540c63b-Diamond5.0lyInclinedBase.png?alt=media&token=35956766-96c6-4e77-b85b-d914da5c7384")
//						.statusDiamond(true)
//						.price(1995861000)
//						.build();
//
//				diamondRepo.save(diamond);diamondRepo.save(diamond1);diamondRepo.save(diamond2);diamondRepo.save(diamond3);
//				diamondRepo.save(diamond4);diamondRepo.save(diamond5);diamondRepo.save(diamond6);diamondRepo.save(diamond7);
//				diamondRepo.save(diamond8);diamondRepo.save(diamond9);diamondRepo.save(diamond10);diamondRepo.save(diamond11);
//				diamondRepo.save(diamond12);diamondRepo.save(diamond13);diamondRepo.save(diamond14);diamondRepo.save(diamond15);
//				diamondRepo.save(diamond16);diamondRepo.save(diamond17);diamondRepo.save(diamond18);diamondRepo.save(diamond19);
//				diamondRepo.save(diamond20);diamondRepo.save(diamond21);diamondRepo.save(diamond22);diamondRepo.save(diamond23);
//				diamondRepo.save(diamond24);diamondRepo.save(diamond25);diamondRepo.save(diamond26);diamondRepo.save(diamond27);
//				diamondRepo.save(diamond28);diamondRepo.save(diamond29);diamondRepo.save(diamond30);diamondRepo.save(diamond31);
//				diamondRepo.save(diamond32);diamondRepo.save(diamond33);diamondRepo.save(diamond34);diamondRepo.save(diamond35);
//				diamondRepo.save(diamond36);diamondRepo.save(diamond37);diamondRepo.save(diamond38);diamondRepo.save(diamond39);
//
//				//Add size
//				Size size = Size.builder()
//						.size(13)
//						.build();
//				sizeRepo.save(size);
//
//				Size size1 = Size.builder()
//						.size(14)
//						.build();
//				sizeRepo.save(size1);
//
//				Size size2 = Size.builder()
//						.size(15)
//						.build();
//				sizeRepo.save(size2);
//
//				Size size3 = Size.builder()
//						.size(16)
//						.build();
//				sizeRepo.save(size3);
//
//				Size size4 = Size.builder()
//						.size(17)
//						.build();
//				sizeRepo.save(size4);
//
//				Size size5 = Size.builder()
//						.size(18)
//						.build();
//				sizeRepo.save(size5);
//
//				Size size6 = Size.builder()
//						.size(19)
//						.build();
//				sizeRepo.save(size6);
//
//				Size size7 = Size.builder()
//						.size(20)
//						.build();
//				sizeRepo.save(size7);
//
//
//				//Add diamond shell
//				DiamondShell diamondShell = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_10dfff5f0391472bb62d9199070f6867_master_female.png?alt=media&token=d8ea39c7-b26d-4365-854d-2495d1725852")
//						.material("Platinum 14K")
//						.price(12079000)
//						.quantity(100)
//						.secondaryStoneType("KC DIA WHIRD1.0x18")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell);
//				addSizesForDiamondShell(diamondShell.getId());
//
//				DiamondShell diamondShell1 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_4f231b0d662c4e05966bbb22c52b809b_master_female.png?alt=media&token=b11643a3-41f1-4ac9-b596-3c49c83bab09")
//						.material("Platinum 14K")
//						.price(16967000)
//						.quantity(110)
//						.secondaryStoneType("KC DIA WHIRD1.5x2, 1.2x18")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell1);
//				addSizesForDiamondShell(diamondShell1.getId());
//
//				DiamondShell diamondShell2 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_d072d7d72b884e24b211fc8025cc23e2_master_female.png?alt=media&token=f86acca2-f64c-40d0-87fc-c6f638229726")
//						.material("Platinum 14K")
//						.price(38258000)
//						.quantity(120)
//						.secondaryStoneType("KC DIA WHIRD1.6x2, 1.5x8, 1.3x")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell2);
//
//				DiamondShell diamondShell3 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_1fe465f2203b4f798097595fb3a80596_master_female.png?alt=media&token=b1f97aee-ac62-4cb3-af7f-95ba24adc516")
//						.material("Platinum 14K")
//						.price(19881000)
//						.quantity(120)
//						.secondaryStoneType("KC DIA WHIRD1.5x6, 1.2x16")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell3);
//
//				DiamondShell diamondShell4 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_dac95543763b413e86ba74f758f7ffe8_master_female.png?alt=media&token=e6076655-c336-48d0-a7a3-ad0eeb6de03a")
//						.material("Platinum 18K")
//						.price(12899000)
//						.quantity(100)
//						.secondaryStoneType("KC DIA WHIRD0.9x10")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell4);
//
//				DiamondShell diamondShell5 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnxmxmy010578-nhan-vang-18k-dinh-da-ecz-pnj-1-female.png?alt=media&token=1d0f4469-416d-49cc-a976-a07363f51399")
//						.material("Gold 18K")
//						.price(6359000)
//						.quantity(100)
//						.secondaryStoneType("Stone ECZ PNJ XMXMY010578")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell5);
//
//				DiamondShell diamondShell6 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnxmxmy010577-nhan-vang-18k-dinh-da-ecz-pnj-01-female.png?alt=media&token=4e67b3a7-8992-48ba-86df-c1e67853ac3f")
//						.material("Gold 18K")
//						.price(12899000)
//						.quantity(100)
//						.secondaryStoneType("Stone ECZ PNJ XMXMY010577")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell6);
//
//				DiamondShell diamondShell7 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnddddy060011-nhan-kim-cuong-vang-14k-pnj-1-female.png?alt=media&token=62b199da-51c5-4168-9d20-c9d9d52d97c2")
//						.material("Gold 14K")
//						.price(13380000)
//						.quantity(100)
//						.secondaryStoneType("Gold 14K PNJ DDDDY060011")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell7);
//
//				DiamondShell diamondShell8 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fsp-gnddddc001755-nhan-kim-cuong-vang-14k-disney-pnj-cinderella-1-female.png?alt=media&token=1a215f32-09f6-49bf-9ef1-ae64c99c253b")
//						.material("Platinum 18K")
//						.price(12899000)
//						.quantity(100)
//						.secondaryStoneType("Gold 14K PNJ Cinderella DDDDC001755")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell8);
//
//				DiamondShell diamondShell9 = DiamondShell.builder()
//						.gender("Female")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_c8ce7216a2c2468ea0ebfa622590ad83_master-female.png?alt=media&token=acc94d4e-a80d-4f03-bf22-04aa32f32c31")
//						.material("Platinum 14K")
//						.price(48410000)
//						.quantity(100)
//						.secondaryStoneType("KC DIA WHIRD1.8x10,1.3x20,0")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell9);
//
//				DiamondShell diamondShell10 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_dc0613b6df0648bbbd3e44ca3dee6330_master-male.png?alt=media&token=801f1c91-5c6f-4c2b-b461-10b83670fa38")
//						.material("Platinum 950 and Gold 18K")
//						.price(69983000)
//						.quantity(200)
//						.secondaryStoneType("KC DIA WHIRD1.3x20")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell10);
//
//				DiamondShell diamondShell11 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_0c3f9713ad174f46b8a6ce2a163a3642_master-male.png?alt=media&token=0827b7d4-0076-45da-a935-f31dfb8a9e66")
//						.material("Platinum 10K")
//						.price(19881000)
//						.quantity(160)
//						.secondaryStoneType("KC DIA WHIRD1.3x8")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell11);
//
//				DiamondShell diamondShell12 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_ed9608851a5d433da0d9796e5da061f5_master-male.png?alt=media&token=fc9a25f0-e829-477c-8fe0-48eccb4ba428")
//						.material("Platinum 14K")
//						.price(79806000)
//						.quantity(140)
//						.secondaryStoneType("KC DIA WHIRD1.3x72")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell12);
//
//				DiamondShell diamondShell13 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_f3d51660903e4d04a4d3ca31ea1be2eb_master-male.png?alt=media&token=1ba46b87-330b-4e0a-a0ba-88ffd6747338")
//						.material("Platinum 14K")
//						.price(48410000)
//						.quantity(100)
//						.secondaryStoneType("KC DIA WHIRD1.8x10,1.3x20,0")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell13);
//
//				DiamondShell diamondShell14 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_b1644158e3db49ad8b804f4899152764_master-male.png?alt=media&token=8fdc5f25-3e8d-4f52-83bb-c40fc419e39b")
//						.material("Platinum 14K")
//						.price(38211000)
//						.quantity(132)
//						.secondaryStoneType("KC DIA WHIRD1.2x14")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell14);
//
//				DiamondShell diamondShell15 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_74f3f5fcbf2b43bcb17cbe0c9833154c_master-male.png?alt=media&token=0a75d1c3-2bd4-4594-bad2-bc8a7c1511f0")
//						.material("Platinum 14K and Gold")
//						.price(33229000)
//						.quantity(200)
//						.secondaryStoneType("KC DIA WHIRD1.0x20")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell15);
//
//				DiamondShell diamondShell16 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_598b9fbda10d4536a466e781d9a5292e_master-male.png?alt=media&token=51bb409d-aab0-4b8b-9575-164c1fe1a93f")
//						.material("Gold 14K")
//						.price(48551000)
//						.quantity(230)
//						.secondaryStoneType("KC DIA WHIRD0.8x58")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell16);
//
//				DiamondShell diamondShell17 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_9fd746469d1e4e0cac4d52cc3df344d0_master-male.png?alt=media&token=38451227-a04c-4aa3-8798-2e1453504ea6")
//						.material("Platinum 14K and Gold")
//						.price(64296000)
//						.quantity(300)
//						.secondaryStoneType("KC DIA WHIRD1.5x22, 1.3x4, 1.2")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell17);
//
//				DiamondShell diamondShell18 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_e0825719c40047c28a8852510871dcdc_master-male.png?alt=media&token=593bb72f-6681-4ec3-ab8c-828dc2475f86")
//						.material("Gold 14K")
//						.price(27542000)
//						.quantity(340)
//						.secondaryStoneType("KC DIA WHIRD1.3x8")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell18);
//
//				DiamondShell diamondShell19 = DiamondShell.builder()
//						.gender("Male")
//						.imageDiamondShell("https://firebasestorage.googleapis.com/v0/b/diamondshop-db041.appspot.com/o/Diamond%20Shell%2Fupload_6bca1e0868dd40ec90a96a308715b7db_master.png?alt=media&token=3f89177b-c275-4b38-bcd5-a7adf1d37f58")
//						.material("Platinum 14K")
//						.price(49538000)
//						.quantity(160)
//						.secondaryStoneType("KC DIA WHIRD1.2x20, 1.1x8, 1.0")
//						.statusDiamondShell(true)
//						.build();
//				diamondShellRepo.save(diamondShell19);
//
//
//				//Add status order
//				StatusOrder statusOrder = StatusOrder.builder()
//						.statusName("Pending")
//						.build();
//				statusOrderRepo.save(statusOrder);
//
//				StatusOrder statusOrder1 = StatusOrder.builder()
//						.statusName("Confirmed")
//						.build();
//				statusOrderRepo.save(statusOrder1);
//
//				StatusOrder statusOrder2 = StatusOrder.builder()
//						.statusName("Delivering")
//						.build();
//				statusOrderRepo.save(statusOrder2);
//
//				StatusOrder statusOrder3 = StatusOrder.builder()
//						.statusName("Delivered")
//						.build();
//				statusOrderRepo.save(statusOrder3);
//
//				StatusOrder statusOrder4 = StatusOrder.builder()
//						.statusName("Cancel")
//						.build();
//				statusOrderRepo.save(statusOrder4);
//			}
//		};
//	}
//	private void addSizesForDiamondShell(Integer diamondShellId) {
//		List<Integer> sizeIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
//		for (Integer sizeId : sizeIds) {
//			SizeDiamondShell sizeDiamondShell = SizeDiamondShell.builder()
//					.diamondShell(DiamondShell.builder().id(diamondShellId).build())
//					.size(Size.builder().size(sizeId).build())
//					.build();
//			sizeDiamondShellRepo.save(sizeDiamondShell);
//		}
//	}

//	private final AccountRepo accountRepo;
//	private final CustomerRepo customerRepo;
//	private final PasswordEncoder passwordEncoder;
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
//				customerRepo.save(customer_1);
			}
		};
	}
}
