package poker;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Poker {

    private static Map<String,Integer> cardMap = null;

    public Poker(){
        this.cardMap = new HashMap<String,Integer>(){{
            put("T",10);
            put("J",11);
            put("Q",12);
            put("K",13);
            put("A",14);
        }};
    }

    public String compare(List<Card> cards1, List<Card> cards2){

        List<Integer> cardsValue1 =sortCard(cards1);
        List<Integer> cardsValue2 = sortCard(cards2);
        for(int i = 0;i < cardsValue1.size();i++){
            String result = compareCard(cardsValue1.get(i),cardsValue2.get(i));
            if(result.equals("DRAW")){
                continue;
            }
            return result;
        }
        return "DRAW";
    }
    public String compareCard(Integer value1,Integer value2){
        if(value1>value2){
            return "WIN1";
        }
        if(value1<value2){
            return "WIN2";
        }
        return "DRAW";
    }

    private List<Integer> sortCard(List<Card> cards){
        return cards.stream().map(card -> mapValue(card.getNum())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    private Integer mapValue(String cardNum){
        if(cardNum.compareTo("2")>=0&&cardNum.compareTo("9")<=0){
            return Integer.parseInt(cardNum);
        }
        return this.cardMap.get(cardNum);


    }
}
