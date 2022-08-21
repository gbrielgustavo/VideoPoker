import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class CheckWinningHands {

    public final int[] handsMultiplier = {250, 50, 160, 80, 50, 10, 7, 5, 3, 1, 1, 0};


    private ArrayList<Card> cardsToEvaluate = new ArrayList<>(); //ArrayList with cards to be evaluated

    private ArrayList<ArrayList<Integer>> ranksNSuitsArrayList = new ArrayList<>(); //Support/facilitate the implementation of some methods. First Ranks, then suits

    private ArrayList<ArrayList<Integer>> uniqueRanksNCounter = new ArrayList<>(); //Stores the unique Ranks and its amount

    private ArrayList<ArrayList<Integer>> uniqueSuitsNCounter = new ArrayList<>(); //Stores the unique Suits and its amount

    private ArrayList<Integer> pairs = new ArrayList<>(); //Stores the rank of Pairs. Each element is a pair.

    private Integer fourOfAKind; // If = -1, there is no Four of a Kind, else stores the four of a kind card Rank. Useful because we have different payouts for different ranks


    //Class constructor
    public CheckWinningHands() {
    }

    public int[] getHandsMultiplier() {
        return handsMultiplier;
    }

    public void evaluate(ArrayList<Card> cardsToEvaluate){
        this.cardsToEvaluate = cardsToEvaluate;
        this.ranksNSuitsArrayList = getArrayListOfSuitsNRanks();
        this.uniqueRanksNCounter = countCards("rank");
        this.uniqueSuitsNCounter = countCards("suit");
        this.fourOfAKind = isFourOfAKind();
        this.pairs = countPairs();
    }

    // Hand with higher pay have a bigger priority.
    public int checkIfWinner(ArrayList<Card> cardsToEvaluate){

        evaluate(cardsToEvaluate);
        System.out.println("______________________________________");
        if(isRoyalFlush()){
            System.out.println("\nCongratulations! It is a Royal Flush");
            System.out.println("Multiplier = " + handsMultiplier[0]+"x + bonus");

            return handsMultiplier[0];
        }
        else if (isStraightFlush()) {
            System.out.println("Congratulations! It is a Straight Flush");
            System.out.println("Multiplier = " + handsMultiplier[1]+"x");
            return handsMultiplier[1];
        }
        else if (isFourOfAKindAces()) {
            System.out.println("Congratulations! It is Four Aces");
            System.out.println("Multiplier = " + handsMultiplier[2]+"x");
            return handsMultiplier[2];
        }
        else if (isFourOfAKindTwoToFour()) {
            System.out.println("Congratulations! It is Four of a kind, going from 2 to 4");
            System.out.println("Multiplier = " + handsMultiplier[3]+"x");
            return handsMultiplier[3];
        }
        else if (isFourOfAKindFiveToKing()) {
            System.out.println("Congratulations! It is Four of a kind, going from 5 to King");
            System.out.println("Multiplier = " + handsMultiplier[4]+"x");
            return handsMultiplier[4];
        }
        else if (isFullHouse()) {
            System.out.println("Congratulations! It is a Full House");
            System.out.println("Multiplier = " + handsMultiplier[5]+"x");
            return handsMultiplier[5];
        }
        else if (isFlush()) {
            System.out.println("Congratulations! It is a Flush");
            System.out.println("Multiplier = " + handsMultiplier[6]+"x");
            return handsMultiplier[6];
        }
        else if (isStraight()) {
            System.out.println("Congratulations! It is a Straight");
            System.out.println("Multiplier = " + handsMultiplier[7]+"x");
            return handsMultiplier[7];
        }
        else if (isThreeOfAKind()) {
            System.out.println("Congratulations! It is a Three of a kind");
            System.out.println("Multiplier = " + handsMultiplier[8]+"x");
            return handsMultiplier[8];
        }
        else if (isTwoPair()) {
            System.out.println("Congratulations! It is Two Pairs");
            System.out.println("Multiplier = " + handsMultiplier[9]+"x");
            return handsMultiplier[9];
        }
        else if (isJacksOrBetter()) {
            System.out.println("Congratulations! It is Jacks or Better");
            System.out.println("Multiplier = " + handsMultiplier[10]+"x");
            return handsMultiplier[10];
        }else{
            System.out.println("Sorry, you lose!");
            return handsMultiplier[11];
        }


    }





    //Generates an ArrayList with all the Cards' rank and suit.
    private ArrayList<ArrayList<Integer>> getArrayListOfSuitsNRanks(){
        ArrayList<Integer> tempSuitList = new ArrayList<>();
        ArrayList<Integer> tempRankList = new ArrayList<>();

        for (int i =0; i < this.cardsToEvaluate.size();i++){

            tempSuitList.add(this.cardsToEvaluate.get(i).getSuit());
            tempRankList.add(this.cardsToEvaluate.get(i).getRank());
        }
        ArrayList<ArrayList<Integer>> tempArrList = new ArrayList<>();

        tempArrList.add(tempRankList); //First we add the ranks.
        tempArrList.add(tempSuitList); //Then we add the suits.

        return tempArrList;
    }

//Count suits or ranks
    private ArrayList<ArrayList<Integer>> countCards(String RankOrSuit){
        int operation = -1;
        if (RankOrSuit.toLowerCase() == "rank"){
            operation = 0;
        }else if (RankOrSuit.toLowerCase() == "suit"){
            operation = 1;
        }else {
            System.out.println("Invalid selection. must be 'rank' or 'suit'");
        }

        ArrayList<Integer> tempArrayList = ranksNSuitsArrayList.get(operation);
        Collections.sort(tempArrayList);

        HashSet<Integer> hashSet = new HashSet<Integer>(tempArrayList); // get only the unique elements;

        ArrayList<Integer> cardsRankOrSuit = new ArrayList<>(hashSet);

        ArrayList<Integer> cardsCount = new ArrayList<>();

        int counter = 0;
        for(int i = 0; i < (cardsRankOrSuit.size()); i++){

            for(int j = 0; j < (tempArrayList.size()); j++){

                if(cardsRankOrSuit.get(i) == tempArrayList.get(j)){
                    counter++;
                }
            }
            cardsCount.add(counter);
            counter = 0;
        }
        ArrayList<ArrayList<Integer>> cardsNCount = new ArrayList<>();
        cardsNCount.add(cardsRankOrSuit);
        cardsNCount.add(cardsCount);
        //System.out.println("Ranks/Suits:\n"+cardsRankOrSuit);
        //System.out.println(cardsCount+"\n\n");

        return cardsNCount;
    }


    // FOUR OF A KIND -> 4 cards with the same rank (all 4 different suits). Here we have divisions based on the Rank range:
    private int isFourOfAKind(){ // Returns -1 if it doesn't have a four of a kind, or the card Rank if the hand has
        return isNOfAKind(4);

    }


//Counts how many pair there are in the hand and saves the Rank of each pair
    private ArrayList<Integer> countPairs(){

        ArrayList<Integer> pairsArr = new ArrayList<>();

        if (uniqueRanksNCounter.get(1).contains(2)){

            for (int i = 0; i < uniqueRanksNCounter.get(1).size();i++) {
                int temp = uniqueRanksNCounter.get(1).get(i);

                if (temp == 2){
                    pairsArr.add(uniqueRanksNCounter.get(0).get(i));
                }
            }
        }
        return pairsArr;
    }

    // check if there is n cards of the same rank in the hand
    private int isNOfAKind(int n){ // Returns -1 if it doesn't have a four of a kind, or the card Rank if the hand has

        int temp4ofaKind = -1;
        if(uniqueRanksNCounter.get(1).contains(n)){
            int elementID = uniqueRanksNCounter.get(1).indexOf(n);
            temp4ofaKind = uniqueRanksNCounter.get(0).get(elementID);
        }
        return temp4ofaKind;
    }

    //Royal Flush -> 10, J, Q, K, A with all the same suit.
    public boolean isRoyalFlush() { //TODO : Done
        if(isStraightFlush() && (Collections.min(ranksNSuitsArrayList.get(0)) == 8)){
            return true;
        }

        return false;
    }



    //Here we start testing the hands

    //Straight Flush -> Straight sequence of suits with all the same suit.
    public boolean isStraightFlush() { //TODO : Done
        if(isFlush() && isStraight()){
            return true;
        }
        return false;
    }

    //Four Aces -> 4 aces
    public boolean isFourOfAKindAces() {

        if(fourOfAKind == 12){
            return true;
        }
        return false;
    }


    //Four 2–4 -> 4 cards with the same rank, with the rank going from 2 to 4
    public boolean isFourOfAKindTwoToFour() {
        if((fourOfAKind >= 0)&&(fourOfAKind <=2)){
            return true;
        }
        return false;
    }



    //Four 5–K -> 4 cards with the same rank, with the rank going from 5 to K
    public boolean isFourOfAKindFiveToKing() {
        if((fourOfAKind >= 3)&&(fourOfAKind <=11)){
            return true;
        }
        return false;
    }


    //Full House -> A Pair and a Three of a kind on the same hand. Two cards with the same rank, and other  three cards (different ranks with the previous couple cards) with the same rank
    public boolean isFullHouse() {

        if (uniqueRanksNCounter.get(1).contains(3) && uniqueRanksNCounter.get(1).contains(2)){
            return true;
        }
        return false;
    }


    //Flush -> Five nonconsecutive cards with the same Suit.
    public boolean isFlush() { //TODO: DONE

        if(uniqueSuitsNCounter.get(1).contains(5)){
            return true;
        }
        return false;
    }

    //Straight -> Five consecutive (rank) cards with the different suits
    public boolean isStraight() { //TODO : DONE

        if(uniqueRanksNCounter.size() != 5){
            return false;
        } else if ((Collections.max(ranksNSuitsArrayList.get(0)) != 12) || (Collections.min(ranksNSuitsArrayList.get(0)) != 0)) { //Tests if we have the case where we have a sequence starting with an ace
            if ((Collections.max(ranksNSuitsArrayList.get(0)) - Collections.min(ranksNSuitsArrayList.get(0))) == 4) { //Divided in two ifs to facilitate readability
                return true;
            } else {
                return false;
            }
        } else {    //Deals with the case where we have a sequence starting with an ace. In this situation the sum of all ranks is 18 (12 + 0 + 1 + 2 + 3 = 18).
            int sum = 0;
            for (int number : ranksNSuitsArrayList.get(0)) {
                sum += number;
            }
            if (sum == 18){
                return true;
            } else{
                return false;
            }
        }

    }


    //Three of a Kind -> Three Cards with the same rank.
    public boolean isThreeOfAKind() {

        if(isNOfAKind(3) != -1){
            return true;
        }
        return false;

    }


    //Two Pair -> Two cards with the same rank, and another two cards with the same rank (the rank is different from the previous pair)
    public boolean isTwoPair() {

        if (pairs.size() == 2){
            return true;
        }
        return false;
    }


    //Jacks or Better -> Pair of Jacks or of a higher rank cards
    public boolean isJacksOrBetter() {
        if ((pairs.size() == 1)&&(pairs.get(0) >= 9)){
            return true;
        }
        return false;
    }
    }

