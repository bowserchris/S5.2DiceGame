package itacademy.s5t2.diceGame.config;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.service.SequenceGeneratorService;

@Component
public class DiceGameModelListener extends AbstractMongoEventListener<DiceGame> {

    private SequenceGeneratorService sequenceGenerator;

    public DiceGameModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<DiceGame> event) {
        if (event.getSource().getGameId() < 1) {
            event.getSource().setGameId(sequenceGenerator.generateSequence(DiceGame.SEQUENCE_NAME));
        }
    }
}
