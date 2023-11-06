package itacademy.s5t2.diceGame.config;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.service.SequenceGeneratorService;

@Component
public class PlayerModelListener extends AbstractMongoEventListener<Player> {

    private SequenceGeneratorService sequenceGenerator;

    public PlayerModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Player> event) {
        if (event.getSource().getIdPlayer() < 1) {
            event.getSource().setIdPlayer(sequenceGenerator.generateSequence(Player.SEQUENCE_NAME));
        }
    }
    
}
