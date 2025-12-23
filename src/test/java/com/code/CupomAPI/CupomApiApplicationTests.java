package com.code.CupomAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		properties = {
				"springdoc.api-docs.enabled=false",
				"springdoc.swagger-ui.enabled=false"
		}
)
class CupomApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
