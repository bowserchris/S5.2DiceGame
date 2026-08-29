package itacademy.s5t2.diceGame.config;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

/**
 * Configuratoin class for the Mongo Id Sequence generator
 *
 * @author bowser-chris
 */
@Document(collection = "database_sequences")
public class MongoSequenceConfig {

	@Id
	private String id;
	private long seq;

	public MongoSequenceConfig() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeq() {
		return this.seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, Long.valueOf(this.seq));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		MongoSequenceConfig other = (MongoSequenceConfig) obj;
		return Objects.equals(this.id, other.id) && this.seq == other.seq;
	}
}
