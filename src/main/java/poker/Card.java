package poker;

public class Card {

    private String num;

    public Card(String card) {
        this.num = card.substring(0,1);
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
