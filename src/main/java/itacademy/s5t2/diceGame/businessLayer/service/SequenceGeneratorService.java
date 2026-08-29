package itacademy.s5t2.diceGame.businessLayer.service;

import static itacademy.s5t2.diceGame.constants.CommonConstants.SEQ;
import static itacademy.s5t2.diceGame.constants.CommonConstants.UNDERSCORE_ID;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.config.MongoSequenceConfig;

/**
 * Service class for the MongoDB ID sequence generator to fit to conformity and
 * not be randomized
 * 
 * @author bowser-chris
 */
@Service
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;

	/**
	 * @param seqName the string from the player or game object
	 * @return mongoId new generated sequence for the object id in MongoDB
	 */
	public long generateSequence(String seqName) {
		MongoSequenceConfig counter = this.mongoOperations.findAndModify(query(where(UNDERSCORE_ID).is(seqName)),
				new Update().inc(SEQ, 1), options().returnNew(true).upsert(true),
				MongoSequenceConfig.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
}