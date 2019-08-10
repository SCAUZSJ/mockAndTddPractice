package poker;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {

    @Test
    public void should_return_win2_when_give_two_cards(){

        Poker poker = new Poker();
        Assert.assertEquals("win2",poker.compare("3S","4D"));
    }

}
