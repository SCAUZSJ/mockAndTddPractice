package poker;

public class Card {

    private String num;
    private String type;

    public Card(String card) {
        this.num = card.substring(0,1);
        this.type = card.substring(1,2);
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
