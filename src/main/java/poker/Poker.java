package poker;

import java.util.HashMap;
import java.util.Map;

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

    public String compare(String card1,String card2){
        if(mapValue(card1.substring(0,1))>mapValue(card2.substring(0,1))){
            return "WIN1";
        }
        if(mapValue(card1.substring(0,1))<mapValue(card2.substring(0,1))){
             return "WIN2";
        }
        return "DRAW";
    }

    private Integer mapValue(String cardNum){
        if(cardNum.compareTo("2")>=0&&cardNum.compareTo("9")<=0){
            return Integer.parseInt(cardNum);
        }
        return this.cardMap.get(cardNum);


    }
}
