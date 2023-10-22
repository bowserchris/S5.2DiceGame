package itacademy.s5t2.diceGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import itacademy.s5t2.diceGame.domain.Die;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = Die.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class DiceGameApplicationTests {

	@Autowired
    private MockMvc mvc;
	
	@Test
	void dieRoll() {
		int[] results = {1,2,3,4,5,6};
		int[] expected = Die.roll();
		Assertions.assertArrayEquals(results, expected);
	}

}
