public class Statistics {

	private int nHands = 0;
	private int nJacksOrBetter = 0;
	private int nTwoPair = 0;
	private int nThreeOfAKind = 0;
	private int nStraight = 0;
	private int nFlush = 0;
	private int nFullHouse = 0;
	private int nFourOfAKind = 0;
	private int nStraightFlush = 0;
	private int nRoyalFlush = 0;
	private int nOther = 0;
	private int nTotal = 0;
	private int nCredit = 0;
	private int balance = 0;
	private int gainPercentage = 0;

	public Statistics() {
		// TODO - implement Statistics
	}

	public void printStatistics(){
		System.out.println("Hand\t\t\t\t"+nHands+
				"\n___________________________________"+
				"\nJacks or Better\t\t"+nJacksOrBetter+
				"\nTwo Pair\t\t\t"+nTwoPair+
				"\nThree of a Kind\t\t"+nThreeOfAKind+
				"\nFlush\t\t\t\t"+nFlush+
				"\nFull House\t\t\t"+nFullHouse+
				"\nFour of a Kind\t\t"+nFourOfAKind+
				"\nStraight Flush\t\t"+nStraight+
				"\nOther\t\t\t\t"+nOther+
				"\n___________________________________"+
				"\nTotal\t\t\t\t"+nTotal+
				"\n___________________________________"+
				"\nCredit\t\t\t"+balance+"  ("+gainPercentage+"%)\n\n");

	}



}