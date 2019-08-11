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

        List<Integer> cardsValue1 =sortCard(cards1);
        List<Integer> cardsValue2 = sortCard(cards2);
        boolean isFlush1 = isFlush(cards1);
        boolean isFlush2 = isFlush(cards2);

        if(cardsValue1.equals(cardsValue2)&&isFlush1 == isFlush2){
            return "DRAW";
        }
        return compareCardIntegerList(cardsValue1, cardsValue2,cards1.size(),isFlush1,isFlush2);
    }


    /**
     * 比较牌组的大小
     * @param cardsValue1
     * @param cardsValue2
     * @param cardSize 当前牌组的数量
     * @return
     */
    public String compareCardIntegerList(List<Integer> cardsValue1, List<Integer> cardsValue2,int cardSize,boolean isFlush1,boolean isFlush2) {

        String result = null;

        if(cardSize == 1){
            //一张牌 直接比较
            result = compareCard(cardsValue1.get(0),cardsValue2.get(0));
            return result;
        }
        //转map
        Map<Integer,Integer> cardMap1 = changeToMap(cardsValue1);
        Map<Integer,Integer> cardMap2 = changeToMap(cardsValue2);

        //判断同花顺
        if(isFlush1&&isStraight(cardsValue1)){
            if(isFlush2&&isStraight(cardsValue2)){
               return compareCard(cardsValue1.get(0),cardsValue2.get(0));
            }
            return "WIN1";
        }
        if(isFlush2&&isStraight(cardsValue2)){
            return "WIN2";
        }

        //判断同花
        if(isFlush1){
            if(isFlush2){
                return compareCard(cardsValue1.get(0),cardsValue2.get(0));
            }
            if(cardMap2.size()>2){
                return "WIN1";
            }
            return "WIN2";
        }
        if(isFlush2){
            if(cardMap1.size()>2){
                return "WIN2";
            }
            return "WIN1";
        }

        //判断顺子
        if(isStraight(cardsValue1)){
            if(isStraight(cardsValue2)){
                return compareCard(cardsValue1.get(0),cardsValue2.get(0)); //都是顺子，比大小
            }
            if(cardMap2.size()<3 || isFlush2){
                return "WIN2"; //顺子小
            }
            return "WIN1"; //顺子大
        }
        if(isStraight(cardsValue2)){
            if(cardMap1.size()<3 || isFlush1){
                return "WIN1";
            }
            return "WIN2";
        }


        /**
         * size相等，可能如下：
         * 1. 4+1&3+2
         * 2. 3+1+1%2+2+1
         * 3. 牌型相同
         */
        if(cardMap1.size() == cardMap2.size()){
            //a.牌型（mapSize = cardSize）：全没对子
            if(cardMap1.size() == cardSize){
                result = compareCardList(cardsValue1,cardsValue2);
                if(result!=null) return result;
            }

            //b.牌型(mapSize = cardSize-2 = 3)： 3+1+1&3+1+1 或 3+1+1&2+2+1 或 2+2+1&2+2+1
            if(cardMap1.size() == cardSize - 2){
                if(getThreePair(cardMap1)!= -1){
                    if(getThreePair(cardMap2)!= -1){
                        return compareThreePair(cardMap1, cardMap2); //3+1+1&3+1+1
                    }
                    return "WIN1";
                }else if(getThreePair(cardMap2)!= -1){
                    return "WIN2";
                }
                //此处将2+2+1&2+2+1的判断放在下一步
            }

            //c.牌型（mapSize = cardSize - 1 || cardSize - 2）: 2+2+1&2+2+1 或 2+1+1+1&2+1+1+1
            if(cardMap1.size()==cardSize-1||cardMap1.size()==cardSize-2){
                result = comparePair(cardMap1,cardMap2);
                if (result != null) return result;
                //获得刚比较的对子
                Integer pair = getPairKey(cardMap1);
                //过滤
                cardsValue1 =  cardsValue1.stream().filter(card->card != pair).collect(Collectors.toList());
                cardsValue2 =  cardsValue2.stream().filter(card->card != pair).collect(Collectors.toList());
                //递归
                return compareCardIntegerList(cardsValue1,cardsValue2,cardSize-2,false,false);
            }

            //d.牌型(mapSize = cardSize-3 = 2)：4+1&4+1 或 4+1&3+2 或 3+2&&3+2
            if(cardMap1.size() == cardSize - 3){
                if(getFourPair(cardMap1)!= -1){
                    if(getFourPair(cardMap2)!= -1){
                        return compareFourPair(cardMap1, cardMap2);//4+1&4+1
                    }
                    return "WIN1";//4+1&3+2
                }else if(getFourPair(cardMap2)!= -1){
                    return "WIN2";//3+2&4+1
                }
                return compareThreePair(cardMap1, cardMap2);//3+2&&3+2
            }
        }

        /**
         * 剩余情况，size越小牌型越大
         */
        if(cardMap1.size()<cardMap2.size()){
            return "WIN1";
        }
        return "WIN2";
    }
    /**
     * 是否同花
     * @param cards
     * @return
     */
    private boolean isFlush(List<Card> cards) {
        String type = cards.get(0).getType();
        List<Card> filterList = cards.stream().filter(card -> card.getType().equals(type)).collect(Collectors.toList());
        if(filterList.size() == cards.size()){
            return true;
        }
        return false;
    }

    /**
     * 是否顺子
     * @param cardsValue
     * @return
     */
    private boolean isStraight(List<Integer> cardsValue) {
        if(cardsValue.size() == 5){
             if(cardsValue.get(0) - cardsValue.get(cardsValue.size()-1) == 4){
                 return true;
             }
        }
        return false;
    }

    /**
     * 比较4pair
     * @param cardMap1
     * @param cardMap2
     * @return
     */
    private String compareFourPair(Map<Integer, Integer> cardMap1, Map<Integer, Integer> cardMap2) {
        if(getFourPair(cardMap1)>getFourPair(cardMap2)){
            return "WIN1";
        }
        return "WIN2";
    }

    /**
     * 获取4pair
     * @param cardMap
     * @return
     */
    private Integer getFourPair(Map<Integer, Integer> cardMap) {
        for (Integer key : cardMap.keySet()) {
            if (cardMap.get(key) == 4) {
                return key;
            }
        }
        return -1;
    }

    /**
     * 比较3pair
     * @param cardMap1
     * @param cardMap2
     * @return
     */
    public String compareThreePair(Map<Integer, Integer> cardMap1, Map<Integer, Integer> cardMap2) {
        if(getThreePair(cardMap1)>getThreePair(cardMap2)){
            return "WIN1";
        }
        return "WIN2";
    }

    /**
     * 获取3pair
     * @param cardMap
     * @return
     */
    private Integer getThreePair(Map<Integer, Integer> cardMap) {
        for (Integer key : cardMap.keySet()) {
            if (cardMap.get(key) == 3) {
                return key;
            }
        }
        return -1;
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
     * 获取一个Map中的最大的pair key （value == 2）
     * @param pairMap
     * @return
     */
    private Integer getPairKey(Map<Integer, Integer> pairMap) {
        Integer pair1 = -1;
        for (Integer key : pairMap.keySet()) {
            if (pairMap.get(key) == 2) {
                pair1 = pair1 > key ? pair1 : key;
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
