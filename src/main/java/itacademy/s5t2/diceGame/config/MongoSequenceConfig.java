package itacademy.s5t2.diceGame.config;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "database_sequences")
public class MongoSequenceConfig {
	
	@Id
	private String id;
	
	private long seq;

}
