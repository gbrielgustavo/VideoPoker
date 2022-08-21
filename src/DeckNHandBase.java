import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeckNHandBase {

    private ArrayList<Card> cardsDeck = new ArrayList<>(); //ArrayList
    final String[] suitArr = {"Clubs", "Diamonds", "Hearts", "Spades" };
    final String[] rankArr = {"2 of ","3 of ","4 of ","5 of ","6 of ","7 of ","8 of ","9 of ","10 of ","Jack of ","Queen of ","King of ", "Ace of "};

    Map<Integer,String> suitMap = new HashMap<>();
    Map<Integer,String> rankMap = new HashMap<>();

    public DeckNHandBase() {
        createMap();
    }

    public void createMap() {
        if (rankMap.isEmpty()) {
            for (int i = 0; i < rankArr.length; i++) {
                rankMap.put(i, rankArr[i]);
            }
        }

        if (suitMap.isEmpty()) {
            for (int i = 0; i < suitArr.length; i++) {
                suitMap.put(i, suitArr[i]);
            }

        }
    }

    //Used only for debug purposes
    public void printDeck(ArrayList<Card> theCards){
        System.out.println("\nThese are the cards:");

        for(int i = 0; i< theCards.size(); i++){
            System.out.println(i + " - "+rankMap.get(theCards.get(i).getRank())+suitMap.get(theCards.get(i).getSuit()));
        }
    }


}
