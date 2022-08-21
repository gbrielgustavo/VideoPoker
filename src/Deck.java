import java.util.ArrayList;
import java.util.Collections;

public class Deck extends DeckNHandBase{

    private ArrayList<Card> cardsDeck = new ArrayList<>(); //ArrayList
    private ArrayList<Card> cardsToDeal = new ArrayList<>();   //Stores the Cards to send deal

    // private Card[] CardsDeck = new Card[52]; //Array



    // Deck Class constructor.
    // Constructor creates 52 Card objects and store it in a Card's array.
    public Deck(){
        super();
        for(int j = 0; j< rankArr.length; j++){
            for( int k = 0; k<suitArr.length;k++){
                    this.cardsDeck.add(new Card(j,k)); // ArrayList
            }
        }
        //It would be possible to implement a deck without using a Card as an object, but this is a good example of a composition
     }

    //Constructor of a deck based on a CSV file
    public Deck(String path) {
        super();
        CSVManipulation csvManipulation = new CSVManipulation();
        ArrayList<ArrayList<Integer>> deckArrList = new ArrayList<>(csvManipulation.openDeckFromCSVFile(path));

        for(int i = 0; i < deckArrList.get(0).size();i++){
            int j = deckArrList.get(0).get(i);
            int k = deckArrList.get(1).get(i);
            this.cardsDeck.add(new Card(j,k)); // ArrayList
        }

    }

    //ArrayList
    public ArrayList<Card> getCardsDeck(){
        return this.cardsDeck;
    }


    //Function responsible to shuffle the deck.
    public void shuffleDeck(){
        Collections.shuffle(cardsDeck);
    }


    //Used only for debug purposes
    public void printDeck(){
        System.out.println("\nThis is the current Deck:");

        for(int i = 0; i< cardsDeck.size(); i++){
            System.out.println((i+1) + " - "+rankMap.get(this.cardsDeck.get(i).getRank())+suitMap.get(this.cardsDeck.get(i).getSuit()));
        }
    }

    //deal cards and remove them from the deck
    public ArrayList<Card> dealCards(int numberOfCards){

        if(!this.cardsToDeal.isEmpty()){
            this.cardsToDeal.clear();
        }
        for(int i = 0; i < numberOfCards;i++){
            this.cardsToDeal.add(this.cardsDeck.get(0));
            this.cardsDeck.remove(0);
        }
        return this.cardsToDeal;
    }


}
