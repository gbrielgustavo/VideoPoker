import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class VirtualDealer {
    private int credits;
    private int bet = 5;

    private int prize;
    private char command;


    public VirtualDealer(int credits) {
        this.credits = credits;
        System.out.println("\n**********THE VIDEO POKER**********\n");
        Statistics statistics = new Statistics();

        gameMenu();

    }
    void gameMenu(){

        boolean gameMenuControl = true;
        while (gameMenuControl){

            System.out.println("Command\t\tMeaning\n" +
                    "\tb\t\tbet\n"+
                    "\t$\t\tcredit\n"+
                    "\td\t\tdeal\n"+
                    "\th\t\thold\n"+
                    "\ta\t\tadvice\n"+
                    "\ts\t\tstatistics\n"+
                    "\te\t\texit\n");
            System.out.println("Enter your command:");

            try{
                Scanner scan = new Scanner(System.in);
                String read = scan.next();
                read.toLowerCase();
                this.command=read.charAt(0);
                //scan.close();
            } catch (Exception e) {
                System.out.println("Try again!!!");
                gameMenu();
                //throw new RuntimeException(e);
            }finally {

                if (command == 'b') {
                    readBet();
                } else if (command == '$') { //TODO : To implement
                    continue;
                } else if (command == 'd') {
                    newGame();
                } else if (command == 'h') {//TODO : To implement hold
                    continue;
                } else if (command == 'a') {//TODO : To implement advice
                    continue;
                } else if (command == 's') {

                    continue;
                } else if (command == 'e') {//TODO : To implement exit
                    //statistics.printStatistics();
                    gameMenuControl = false;
                    break;
                } else {
                    System.out.println("Try again!!!");
                }
            }
        }
    }

    void newGame(){
        //String path = "D:\\OneDrive\\Documentos\\IntelliJ\\Projects\\VideoPoker\\BaralhoRoyalFlush.csv"; // CSV file encoding MUST be UTF-8 ***without BOM***, otherwise errors will happen

        // PROCEDURE TO BEGIN GAME


        System.out.println("**********THE GAME BEGINS**********");



        System.out.println("Your credit were " + credits);
        this.credits -= bet;
        System.out.println("Your credit is " + credits);

        //After bet if ok, creates a new deck
        //Deck deck = new Deck(); // Creates a new standard deck
        //Deck deck = new Deck(path); // Creates a new deck based on a CSV file
        Deck deck = new Deck(); // Creates a new deck based on a CSV file
        //System.out.println("Deck created");
        deck.shuffleDeck(); // Shuffle cards on the deck
        //System.out.println("Deck shuffled");
        Hand hand = new Hand(deck.dealCards(5)); // Creates a new hand using cards on the deck
        //System.out.println("Hand Created");
        //hand.printHand(); //Prints the new hand
        ArrayList<Integer> cardsToRemove = new ArrayList<>(hand.cardsToHold()); // Shows the hand and asks the cards the player want to keep. hand.cardsToHold() returns the cards that must be replaced


        // TODO : send a ArrayList to the function hand.replaceCards
        //////////////////////////////////////////////////////////
        for (int i = 0; i < cardsToRemove.size();i++){

            hand.replaceCards(cardsToRemove.get(i),deck.dealCards(1).get(0));
        }

        //////////////////////////////////////////////////////////

        hand.printHand(); //Prints the new hand

        CheckWinningHands checkWinningHands = new CheckWinningHands();
        int handMultiplier = checkWinningHands.checkIfWinner(hand.getCardsOnHand());
        System.out.println("______________________________________");

        if (handMultiplier == 250 && bet == 5){
            prize = 4000;
        } else{
            prize = handMultiplier * bet;
        }

        System.out.println("Your prize is " +  prize);
        credits += prize;
        System.out.println("Your credit is " + credits);
        System.out.println("______________________________________\n\n");

    }

    private void readBet(){
        System.out.println(printPayoutTable());
        System.out.println("Place your bet: (1,2,3,4 or 5) ");

        try{
            Scanner scanner = new Scanner(System.in);

            String betString = scanner.next();

            betString = betString.replaceAll("\\s+","");
            bet = Integer.valueOf(betString);
        } catch (NumberFormatException e) {
            //throw new RuntimeException(e);
            System.out.println("Invalid Format! Try again! Choose between 1 and 5.\n");
            readBet();

        }catch (NoSuchElementException e) {
            //throw new RuntimeException(e);
            System.out.println("NoSuchElementException\n");
            readBet();
        }
        finally {
            if (bet<1 || bet>5){
                System.out.println("Invalid bet amount! Try again! Choose between 1 and 5.\n");
                bet = 5;
                readBet();
            }else if (bet>credits){
                System.out.println("Not enough credits! Choose a smaller bet.\n");
                bet = 1;
                readBet();
            }
            System.out.println("\nYour bet is set to " + bet+"\n");
            gameMenu();
        }
    }

    private String printPayoutTable(){
        CheckWinningHands checkWinningHands = new CheckWinningHands();
        int[] m = checkWinningHands.getHandsMultiplier();
        int[] b = {1,2,3,4,5};
        String c = "credit";
        String payout = ("***************PAYOUT TABLE***************\n" +
                "______________________________________________________________________________________________\n"+
                "Hand\t\t\t"+b[0] + " "+ c+"\t"+b[1] + " "+c+"\t"+b[2] + " "+ c+"\t"+b[3] + " "+ c+"\t"+b[4] + " "+ c+"\n"+
                "______________________________________________________________________________________________\n"+
                "Royal Flush\t\t"+(b[0]*m[0])+"\t\t"+(b[1]*m[0])+"\t\t"+(b[2]*m[0])+"\t\t"+(b[3]*m[0])+"\t\t4000"+"\n"+
                "Straight Flush\t\t"+(b[0]*m[1])+"\t\t"+(b[1]*m[1])+"\t\t"+(b[2]*m[1])+"\t\t"+(b[3]*m[1])+"\t\t"+(b[4]*m[1])+"\n"+
                "Four Aces\t\t"+(b[0]*m[2])+"\t\t"+(b[1]*m[2])+"\t\t"+(b[2]*m[2])+"\t\t"+(b[3]*m[2])+"\t\t"+(b[4]*m[2])+"\n"+
                "Four 2–4\t\t"+(b[0]*m[3])+"\t\t"+(b[1]*m[3])+"\t\t"+(b[2]*m[3])+"\t\t"+(b[3]*m[3])+"\t\t"+(b[4]*m[3])+"\n"+
                "Four 5–K\t\t"+(b[0]*m[4])+"\t\t"+(b[1]*m[4])+"\t\t"+(b[2]*m[4])+"\t\t"+(b[3]*m[4])+"\t\t"+(b[4]*m[4])+"\n"+
                "Full House\t\t"+(b[0]*m[5])+"\t\t"+(b[1]*m[5])+"\t\t"+(b[2]*m[5])+"\t\t"+(b[3]*m[5])+"\t\t"+(b[4]*m[5])+"\n"+
                "Flush\t\t\t"+(b[0]*m[6])+"\t\t"+(b[1]*m[6])+"\t\t"+(b[2]*m[6])+"\t\t"+(b[3]*m[6])+"\t\t"+(b[4]*m[6])+"\n"+
                "Straight\t\t"+(b[0]*m[7])+"\t\t"+(b[1]*m[7])+"\t\t"+(b[2]*m[7])+"\t\t"+(b[3]*m[7])+"\t\t"+(b[4]*m[7])+"\n"+
                "Three of a Kind\t\t"+(b[0]*m[8])+"\t\t"+(b[1]*m[8])+"\t\t"+(b[2]*m[8])+"\t\t"+(b[3]*m[8])+"\t\t"+(b[4]*m[8])+"\n"+
                "Two Pair\t\t"+(b[0]*m[9])+"\t\t"+(b[1]*m[9])+"\t\t"+(b[2]*m[9])+"\t\t"+(b[3]*m[9])+"\t\t"+(b[4]*m[9])+"\n"+
                "Jacks or Better\t\t"+(b[0]*m[10])+"\t\t"+(b[1]*m[10])+"\t\t"+(b[2]*m[10])+"\t\t"+(b[3]*m[10])+"\t\t"+(b[4]*m[10])+"\n"+
                "______________________________________________________________________________________________\n");
        return payout;
    }

}

