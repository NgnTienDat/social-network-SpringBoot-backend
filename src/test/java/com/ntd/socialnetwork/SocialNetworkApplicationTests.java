package com.ntd.socialnetwork;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class SocialNetworkApplicationTests {

	@Test
	void contextLoads() {
		ApplicationModules modules = ApplicationModules.of(SocialNetworkApplication.class);
		modules.verify();
//		modules.forEach(System.out::println);


	}

}
