package poker;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;
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

    /**
     * 牌组比较的入口
     * @param cards1
     * @param cards2
     * @return
     */
    public String compare(List<Card> cards1, List<Card> cards2){

        String result = null;
        List<Integer> cardsValue1 =sortCard(cards1);
        List<Integer> cardsValue2 = sortCard(cards2);
        return compareCardIntegerList(cardsValue1, cardsValue2,5);
    }

    /**
     * 比较牌组的大小
     * @param cardsValue1
     * @param cardsValue2
     * @param cardSize 当前牌组的数量
     * @return
     */
    public String compareCardIntegerList(List<Integer> cardsValue1, List<Integer> cardsValue2,int cardSize) {

        String result = null;
        if(cardSize == 0){
            //全部牌相等
            return "DRAW";
        }
        if(cardSize == 1){
            //剩一张牌 直接比较
            result = compareCard(cardsValue1.get(0),cardsValue2.get(0));
            return result;
        }
        //转map
        Map<Integer,Integer> cardMap1 = changeToMap(cardsValue1);
        Map<Integer,Integer> cardMap2 = changeToMap(cardsValue2);

        //两组牌都存在一个对子
        if(cardMap1.size() == cardMap2.size()&&cardMap1.size()==cardSize-1){
            result = comparePair(cardMap1,cardMap2);
            if (result != null) return result;
            //先找到那个对子
            Integer pair = getPairKey(cardMap1);
            //过滤那个对子
            cardsValue1 =  cardsValue1.stream().filter(card->card != pair).collect(Collectors.toList());
            cardsValue2 =  cardsValue2.stream().filter(card->card != pair).collect(Collectors.toList());
            //剩下牌,递归
            compareCardIntegerList(cardsValue1,cardsValue2,cardSize-2);
        }

        //牌组1存在多一个对子
        if(cardMap1.size()<cardMap2.size()){
            return "WIN1";
        }

        //牌组2存在多一个对子
        if(cardMap1.size()>cardMap2.size()){
            return "WIN2";
        }

        return "DRAW";
    }

    /**
     * 比较pair大小
     * @param pairMap1
     * @param pairMap2
     * @return
     */
    private String comparePair(Map<Integer, Integer> pairMap1, Map<Integer, Integer> pairMap2) {

        Integer pair1 = 0;
        Integer pair2 = 0;

        pair1 = getPairKey(pairMap1);
        pair2 = getPairKey(pairMap2);
        if(pair1 > pair2) return "WIN1";
        if(pair1 < pair2) return  "WIN2";
        return null;
    }

    /**
     * 获取一个Map中的pari key （value == 2）
     * @param pairMap
     * @return
     */
    private Integer getPairKey(Map<Integer, Integer> pairMap) {
        Integer pair1 = null;
        for (Integer key : pairMap.keySet()) {
            if (pairMap.get(key) == 2) {
                pair1 = key;
                break;
            }
        }
        return pair1;
    }

    /**
     * 将牌值List转成Map,若有对子，则value！=1
     * @param cardList
     * @return
     */
    public Map<Integer,Integer> changeToMap(List<Integer> cardList) {

        Map<Integer,Integer> pairMap = new HashMap<>();
        for(Integer integer : cardList){
            if(pairMap.containsKey(integer)){
                pairMap.put(integer,pairMap.get(integer)+1);
            }else {
                pairMap.put(integer, 1);
            }
            }
        return pairMap;
    }

    /**
     *  比较两组牌（没对子）
     * @param cardsValue1
     * @param cardsValue2
     * @return
     */
    public String compareCardList(List<Integer> cardsValue1, List<Integer> cardsValue2) {
        for(int i = 0;i < cardsValue1.size();i++){
            String result = compareCard(cardsValue1.get(i),cardsValue2.get(i));
            if(result.equals("DRAW")){
                continue;
            }
            return result;
        }
        return null;
    }

    /**
     * 比较两张牌
     * @param value1
     * @param value2
     * @return
     */
    private String compareCard(Integer value1,Integer value2){
        if(value1>value2){
            return "WIN1";
        }
        if(value1<value2){
            return "WIN2";
        }
        return "DRAW";
    }

    /**
     * 牌组排序
     * @param cards
     * @return
     */
    private List<Integer> sortCard(List<Card> cards){
        return cards.stream().map(card -> mapValue(card.getNum())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    /**
     * 牌值映射
     * @param cardNum
     * @return
     */
    private Integer mapValue(String cardNum){
        if(cardNum.compareTo("2")>=0&&cardNum.compareTo("9")<=0){
            return Integer.parseInt(cardNum);
        }
        return this.cardMap.get(cardNum);


    }
}
