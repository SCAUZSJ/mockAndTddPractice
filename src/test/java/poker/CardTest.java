package poker;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {

    @Test
    public void should_return_WIN2_when_give_two_cards(){

        Poker poker = new Poker();
        Assert.assertEquals("WIN2",poker.compare("3S","4D"));
    }

    @Test
    public void should_return_DRAW_when_give_two_cards(){

        Poker poker = new Poker();
        Assert.assertEquals("DRAW",poker.compare("4S","4D"));
    }

}
