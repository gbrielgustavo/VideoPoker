import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Hand extends DeckNHandBase{

    private ArrayList<Card> cardsOnHand; //ArrayLis

    public Hand(ArrayList<Card> cardsOnHand) {
        this.cardsOnHand = new ArrayList<>(cardsOnHand);
    }

    public ArrayList<Card> getCardsOnHand() {
        return cardsOnHand;
    }

    //Useful only for debug purposes
    public void shuffleHand(){
        Collections.shuffle(cardsOnHand);
    }


    public void printHand(){ // TODO: Improve readability
        System.out.println("\nThis is the current Hand:");
        for(int i = 0; i < cardsOnHand.size();i++){
            System.out.println((i+1) + " - "+rankMap.get(cardsOnHand.get(i).getRank())+suitMap.get(cardsOnHand.get(i).getSuit()));
        }
    }






    public ArrayList<Integer> cardsToHold() { //User selects the cards she wants to hold, and returns the Cards that must be replaced
        printHand();

        ArrayList<Integer> tempList = new ArrayList<>();

        System.out.println("\nPlease, type the number of the cards you want to hold: " +
                "\ne.g.: '1,2,6' - use a comma to separate cards.\n");


        Scanner scanner = new Scanner(System.in);
        String cardsToHold = scanner.next();
        System.out.println("Cards to hold: " + cardsToHold);

        //scanner.close();
        if(cardsToHold.isEmpty()){
            return tempList;
        }


        try {


            cardsToHold = cardsToHold.replaceAll("\\s+","");   // Remove spaces
            String[] tempValues = cardsToHold.split(","); //Slipts the String

            for (int i = 0; i<tempValues.length;i++){
                tempList.add((Integer.valueOf(tempValues[i]))-1);

            }

            if(tempList.size()>5){
                System.out.println("You entered more than 5 cards to hold. Try again!");
                cardsToHold();
            }
            if(Collections.min(tempList)<0 || Collections.max(tempList)>4){
                System.out.println("The cards you entered are out of the range. Enter Cards 1 - 5 and try again!");
                cardsToHold();
            }



        } catch (NumberFormatException e) {
            //throw new RuntimeException(e);
            System.out.println("Invalid Format! Try again!");
            cardsToHold();
        }

        Collections.sort(tempList); //
        //System.out.println("To keep: "+ tempList);
        ArrayList<Integer> cardsToRemove = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        //System.out.println("Cards to Remove: "+cardsToRemove);


        cardsToRemove.removeAll(tempList);
        //System.out.println("To replace: "+cardsToRemove);

        return cardsToRemove;/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    public void replaceCards(int index, Card tempCard){

        cardsOnHand.set(index,tempCard);

        //printHand();
    }



}
