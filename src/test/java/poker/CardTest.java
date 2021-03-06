package poker;

import org.junit.Assert;
import org.junit.Test;
import parking.Car;

import java.util.Arrays;
import java.util.List;

public class CardTest {

    @Test
    public void should_return_WIN2_when_give_two_cards(){

        Poker poker = new Poker();
        Assert.assertEquals("WIN2",poker.compare(Arrays.asList(new Card("3S")),Arrays.asList(new Card("4D"))));
    }

    @Test
    public void should_return_DRAW_when_give_two_cards(){

        Poker poker = new Poker();
        Assert.assertEquals("DRAW",poker.compare(Arrays.asList(new Card("4S")),Arrays.asList(new Card("4D"))));
    }

    @Test
    public void should_return_WIN2_when_give_two_cardList(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("2H"),new Card("7S"),new Card("9H"));
        List<Card> cards2 = Arrays.asList(new Card("3H"),new Card("JH"),new Card("TS"),new Card("4D"),new Card("2S"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_DRAW_when_give_two_cardList(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("2H"),new Card("7S"),new Card("9H"));
        List<Card> cards2 = Arrays.asList(new Card("8H"),new Card("3H"),new Card("2S"),new Card("7D"),new Card("9S"));
        Assert.assertEquals("DRAW",poker.compare(cards1,cards2));
    }
    @Test
    public void should_return_WIN2_when_cardList2_have_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("2H"),new Card("7S"),new Card("9H"));
        List<Card> cards2 = Arrays.asList(new Card("8H"),new Card("3H"),new Card("2S"),new Card("9D"),new Card("9S"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_all_cardList_have_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("2H"),new Card("7S"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("8H"),new Card("3H"),new Card("2S"),new Card("9D"),new Card("9S"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_have_same_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("2H"),new Card("7S"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("TH"),new Card("3H"),new Card("2S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }
    @Test
    public void should_return_WIN2_when_cardList1_have_two_pair_And_cardList2_have_one_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("3C"),new Card("7S"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("TH"),new Card("3H"),new Card("2S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN1",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_cardList1_have_two_pair_And_cardList2_have_two_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("3C"),new Card("7S"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("TH"),new Card("5H"),new Card("5S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_all_cardList_have_two_same_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("3C"),new Card("7S"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("TH"),new Card("3H"),new Card("3S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_cardList1_have_two_pair_And_cardList2_have_three_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("3C"),new Card("JS"),new Card("JH"));
        List<Card> cards2 = Arrays.asList(new Card("TH"),new Card("3H"),new Card("7S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN1_when_all_cardList_have_three_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("JC"),new Card("JS"),new Card("JH"));
        List<Card> cards2 = Arrays.asList(new Card("TH"),new Card("3H"),new Card("7S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN1",poker.compare(cards1,cards2));
    }
    @Test
    public void should_return_WIN2_when_cardList2_have_three_pair_and_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("3D"),new Card("JC"),new Card("JS"),new Card("JH"));
        List<Card> cards2 = Arrays.asList(new Card("3S"),new Card("3H"),new Card("7S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }
    @Test
    public void should_return_WIN1_when_cardList2_have_three_pair_and_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("8D"),new Card("JC"),new Card("JS"),new Card("JH"));
        List<Card> cards2 = Arrays.asList(new Card("KS"),new Card("KH"),new Card("7S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN1",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_cardList2_have_four_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("8D"),new Card("JC"),new Card("JS"),new Card("JH"));
        List<Card> cards2 = Arrays.asList(new Card("KS"),new Card("7H"),new Card("7S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }
    @Test
    public void should_return_WIN2_when_all_cardList_have_four_pair(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("8S"),new Card("2D"),new Card("2C"),new Card("2S"),new Card("2H"));
        List<Card> cards2 = Arrays.asList(new Card("KS"),new Card("7H"),new Card("7S"),new Card("7C"),new Card("7D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_cardList2_is_straight(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("AS"),new Card("2D"),new Card("AC"),new Card("2S"),new Card("KH"));
        List<Card> cards2 = Arrays.asList(new Card("4S"),new Card("5H"),new Card("6S"),new Card("7C"),new Card("8D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }
    @Test
    public void should_return_WIN2_when_all_cardList_is_straight(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("3S"),new Card("4D"),new Card("5C"),new Card("6D"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("4S"),new Card("5H"),new Card("6S"),new Card("7C"),new Card("8D"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_cardList2_is_flush(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("3S"),new Card("4D"),new Card("5C"),new Card("6D"),new Card("7H"));
        List<Card> cards2 = Arrays.asList(new Card("2S"),new Card("4S"),new Card("AS"),new Card("TS"),new Card("8S"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_cardList2_is_straight_flush(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("AS"),new Card("AD"),new Card("AC"),new Card("AH"),new Card("KH"));
        List<Card> cards2 = Arrays.asList(new Card("4C"),new Card("5C"),new Card("6C"),new Card("7C"),new Card("8C"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }

    @Test
    public void should_return_WIN2_when_all_cardLists_is_straight_flush(){

        Poker poker = new Poker();
        List<Card> cards1 = Arrays.asList(new Card("3S"),new Card("4S"),new Card("5S"),new Card("6S"),new Card("7S"));
        List<Card> cards2 = Arrays.asList(new Card("4C"),new Card("5C"),new Card("6C"),new Card("7C"),new Card("8C"));
        Assert.assertEquals("WIN2",poker.compare(cards1,cards2));
    }



}
