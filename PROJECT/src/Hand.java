
/**
 * An object of type Hand represents a hand of cards.  The
 * cards belong to the class Card.  A hand is empty when it
 * is created, and any number of cards can be added to it.
 */


public class Hand {

   private Card[] hand;   // The cards in the hand.
   private int count; 
   
   /**
    * Create a hand that is initially empty.
    */
   public Hand() {
      hand = new Card[5];
	  count = 0;
   }
   
   /**
    * Remove all cards from the hand, leaving it empty.
    */
   public void clear() {
      for(int i=0 ; i<hand.length; i++){ hand[i] = null; }
	  count = 0;
   }
   
   /**
    * Add a card to the hand.  It is added at the end of the current hand.
    * @param c the non-null card to be added.
    * @throws NullPointerException if the parameter c is null.
    */
   public void addCard(Card c) {
      
	  for(int i=0 ; i<hand.length; i++){ 
		if (hand[i] == null){
			hand[i] = c;
			count = count + 1;
			break;
		}
	 }

	  
   }
   
   /**
    * Remove a card from the hand, if present.
    * @param c the card to be removed.  If c is null or if the card is not in 
    * the hand, then nothing is done.
    */
   public void removeCard(Card c) {

	   for(int i=0 ; i<hand.length; i++){ 
	     if (hand[i]!=null && hand[i].equals(c)){
	       hand[i] = null;
	       count = count-1;
	     }
	   }

	    }
   
   /**
    * Remove the card in a specified position from the hand.
    * @param position the position of the card that is to be removed, where
    * positions are starting from zero.
    * @throws IllegalArgumentException if the position does not exist in
    * the hand, that is if the position is less than 0 or greater than
    * or equal to the number of cards in the hand.
    */
   public void removeCard(int position) {
	      if (position < 0 || position >= hand.length)
	         throw new IllegalArgumentException("Position does not exist in hand: "
	               + position);
	      hand[position] = null;
	      count--;
	   }

   /**
    * Returns the number of cards in the hand.
    */
   public int getCardCount() {
      return count;
   }
   
   /**
    * Gets the card in a specified position in the hand.  (Note that this card
    * is not removed from the hand!)
    * @param position the position of the card that is to be returned
    * @throws IllegalArgumentException if position does not exist in the hand
    */
   public Card getCard(int position) {
      if (position < 0 || position >= hand.length)
         throw new IllegalArgumentException("Position does not exist in hand: "
               + position);
       return hand[position];
   }
   
   /**
    * Sorts the cards in the hand so that cards of the same suit are
    * grouped together, and within a suit the cards are sorted by value.
    * Note that aces are considered to have the lowest value, 1.
    */
   public void sortBySuit() {
	  int size = count;
	  int nonnull = 0;
	  int index = 0;
	  
      Card[] newHand = new Card[5];
      while (size > 0) {
		 if (hand[nonnull] == null) { nonnull = nonnull+1; continue;}
         int pos = nonnull;  // Position of minimal card.
         Card c = hand[nonnull];  // Minimal card.
		 
         for (int i = nonnull+1; i < hand.length; i++) {
            Card c1 = hand[i];
			if (c1 != null){
				if ( c1.getSuit() < c.getSuit() ||
						(c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
					pos = i;
					c = c1;
				}
			}
         }
         hand[pos] = null;
		 size = size - 1;
         newHand[index++] = c;
		 nonnull = 0;
      }
      hand = newHand;
   }
   
   /**
    * Sorts the cards in the hand so that cards of the same value are
    * grouped together.  Cards with the same value are sorted by suit.
    * Note that aces are considered to have the lowest value, 1.
    */
   public void sortByValue() {
	  int size = count;
	  int nonnull = 0;
	  int index = 0;
	  
      Card[] newHand = new Card[5];
      while (size > 0) {
		 if (hand[nonnull] == null) { nonnull = nonnull+1; continue;}
         int pos = nonnull;  // Position of minimal card.
         Card c = hand[nonnull];  // Minimal card.
		 
         for (int i = nonnull+1; i < hand.length; i++) {
            
			Card c1 = hand[i];
            if (c1 != null){
				if ( c1.getValue() < c.getValue() ||
						(c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
					pos = i;
					c = c1;
				}
			}
         }
         hand[pos] = null;
		 size = size - 1;
         newHand[index++] = c;
		 nonnull = 0;
      }
      hand = newHand;
   }
   
   public void printHand(){
	   
	   for(int i=0; i<hand.length; i++){
		   if (hand[i] != null){
			   System.out.println(hand[i]);
		   }
	   }
	   System.out.println();
   }
   

   /******************************** Implement your methods here ****************************************/

   
   
   //Returns the number of pairs this hand contains
   	public int numPairs() {
	sortByValue();
	int counter = 0;
	
	for (int i = 0; i < hand.length-1; i++) {
		if (hand[i].getValue() == hand[i+1].getValue()) {
			counter++;
			i++;
		}
	}
	return counter;	
   	}
	
   	
   
   //Returns true if this hand has 3 cards that are of the same value
   public boolean hasTriplet() {
	   sortByValue();
	   for (int i = 1; i < hand.length-1; i++) {
		   Card card1 = hand[i-1];
		   Card card2 = hand[i];
		   Card card3 = hand[i+1];
		   if(card1.getValue() == card2.getValue() && card2.getValue() == card3.getValue()) {
			   return true;
		   }    
	   }
	   return false;
   }
   
   //Returns true if this hand has all cards that are of the same suit
   public boolean hasFlush() {  
	   
	if (hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() &&
			   hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()){
			   return true;
		}
		
			return false;
		
		}
   
   //Returns true if this hand has 5 consecutive cards of any suit
   public boolean hasStraight() {
	sortByValue();  
	
	if (hand[0].getValue() == 1 && hand[1].getValue() == 10 && hand[2].getValue() == 11 && hand[3].getValue() == 12 && hand[4].getValue() == 13) {
		return true;
	}
	else if(hand[0].getValue() == hand[1].getValue()-1 && hand[1].getValue() == hand[2].getValue()-1 &&
			hand[2].getValue() == hand[3].getValue()-1 && hand[3].getValue() == hand[4].getValue()-1){
		return true;
	}
   return false;
   
   
   }
    
   //Returns true if this hand has a triplet and a pair of different values
    public boolean hasFullHouse() {
    sortByValue();
    
    if (hasTriplet() == true && numPairs() == 2 && hasFourOfAKind() == false) {
    	return true;
    }
    
    return false;
    }
    
   //Returns true if this hand has 4 cards that are of the same value
   public boolean hasFourOfAKind() {
	   sortByValue();
	   int counter = 0;

	   if(hand[0].getValue() == hand[3].getValue() || hand[1].getValue() == hand[4].getValue()) {
		   counter++;
	   }
   if(counter == 1) {
	   return true;
   }else {
	   return false;
   }
   }
   
   //Returns the card with the highest value in the hand. When there is
   //more than one highest value card, you may return any one of them
   public Card highestValue() {
	sortByValue();
	
	if (hand[0].getValue()== 1) {
		return hand[0];
		
	}
	return hand[4];   	
   }
    
   //Returns the highest valued Card of any pair or triplet found, null if
   // none. When values are equal, you may return either
   public Card highestDuplicate() {
	   sortByValue();
	   if (numPairs() == 0 && hasTriplet() == false) {
		   return null;
	   }
	   
	   Card highDoub = null;
	   Card highTrip = null;
	   
	   // Triplets Code
	   for (int i = 1; i < hand.length-1; i++) {
		   Card c1 = hand[i-1];
		   Card c2 = hand[i];
		   Card c3 = hand[i+1];
	   
	   if (c1.getValue() == c2.getValue() && c2.getValue() == c3.getValue()) {
		   highTrip = c3;
		   if (highTrip.getValue() == 1) {
			   return highTrip;
			   
		   }
	   }
	   
	   }
   
	// Doubles code
	   for (int i = 0; i < hand.length-1; i++) {
		   Card c1 = hand[i];
		   Card c2 = hand[i+1];
		   
		   if (c1.getValue() == c2.getValue()) {
			   highDoub = c2;
			   if (highDoub.getValue() == 1) {
				   return highDoub;
			   }
			   i+=2; //setting the position of i
		   }
	   }
	   if (highTrip == null) {
		   return highDoub;
	   } else if (highDoub == null) {
		   return null;
	   }
 
	   if(highTrip.getValue() > highDoub.getValue()) {
		return highTrip;
	   } return highDoub;
   }   
   
   // Made a ranking system for compareTo 
   
   // Straight flush
   	public int straightFlush(Hand h) {
   	
   		if(h.hasStraight() == true && h.hasFlush() == true) {
   		return 9;
   		}
   	return -1;	
   	}
   
   //Four of a kind
   	public int fourOfAKind(Hand h) {
   	
   		if (h.hasFourOfAKind() == true) {
   			return 8;
   		}
   		return -1;
   	}
   	
   	// Full house
   	public int fullHouse(Hand h) {
   		
   		if (h.hasFullHouse() == true) {
   			return 7;
   		}
   	return -1;
   	}
   
   	// Flush
   	public int flush(Hand h) {
   		
   		if (h.hasFlush() == true) {
   			return 6;
   		}
   	return -1;
   	}
   	
   	// Straight
   	public int straight(Hand h) {
   		
   		if (h.hasStraight() == true) {
   			return 5;
   		}
   	return -1;
   	}
   	
   	// Triplet and different last 2 (3 of a Kind)
   	public int threeOfAKind(Hand h) {
   		
   		if (h.hasTriplet() == true && h.numPairs() == 1) {
   			return 4;
   		}
   	return -1;
   	}
   	
   	// Two Pair
   	public int twoPair (Hand h) {
   		
   		if (h.numPairs() == 2 && h.hasTriplet() == false) {
   			return 3;
   		}
   	return -1;
   	}
   	
   	// One pair 
   	public int onePair (Hand h) {
   		
   		if (h.numPairs() == 1) {
   			return 2;
   		}
   		return -1;
   	}
   	
   	// High Card
   	public int highCard (Hand h) {
   		
   		if(h.hasFlush() == false && h.hasFourOfAKind() == false && h.hasFullHouse() == false && h.hasStraight() == false && straightFlush(h) == -1 && threeOfAKind(h) == -1 && twoPair(h) == -1 && onePair(h) == -1){
   		return 1;	
   		}
   		return -1;
   	}
   //Returns -1 if the instance hand loses to the parameter hand, 0 if //the hands are equal, and +1 if the instance hand wins over the //parameter hand. Hint: you might want to use some of the methods //above
   public int compareTo(Hand h) {
	   
	   int hand1Value = 0;
	   Hand hand1 = new Hand();
	   
	   hand1.addCard(hand[0]);
		   hand1.addCard(hand[1]);
		   hand1.addCard(hand[2]);
		   hand1.addCard(hand[3]);
		   hand1.addCard(hand[4]);
	   
	 
	   if(highCard(hand1) > hand1Value) {
		   hand1Value = highCard(hand1);
	   }
	   if(onePair(hand1) > hand1Value) {
		   hand1Value = onePair(hand1);
	   }
	   if(twoPair(hand1) > hand1Value) {
		   hand1Value = twoPair(hand1);
	   }
	   if(threeOfAKind(hand1) > hand1Value) {
		   hand1Value = threeOfAKind(hand1);
	   }
	   if(straight(hand1) > hand1Value) {
		   hand1Value = straight(hand1);
	   }
	   if(flush(hand1) > hand1Value) {
		   hand1Value = flush(hand1);
	   }
	   if(fullHouse(hand1) > hand1Value) {
		   hand1Value = fullHouse(hand1);
	   }
	   if(fourOfAKind(hand1) > hand1Value) {
		   hand1Value = fourOfAKind(hand1);
	   }
	   if(straightFlush(hand1) > hand1Value) {
		   hand1Value = straightFlush(hand1);
	   }
	   
	   
	   
	   
	   int hand2Value = 0;
	   if(highCard(h) > hand2Value) {
		   hand2Value = highCard(h);
	   }
	   if(onePair(h) > hand2Value) {
		   hand2Value = onePair(h);
	   }
	   if(twoPair(h) > hand2Value) {
		   hand2Value = twoPair(h);
	   }
	   if(threeOfAKind(h) > hand2Value) {
		   hand2Value = threeOfAKind(h);
	   }
	   if(straight(h) > hand2Value) {
		   hand2Value = straight(h);
	   }
	   if(flush(h) > hand2Value) {
		   hand2Value = flush(h);
	   }
	   if(fullHouse(h) > hand2Value) {
		   hand2Value = fullHouse(h);
	   }
	   if(fourOfAKind(h) > hand2Value) {
		   hand2Value = fourOfAKind(h);
	   }
	   if(straightFlush(h) > hand2Value) {
		   hand2Value = straightFlush(h);
	   }
	   
	   System.out.println(hand1Value + " " + hand2Value);
	   if (hand1Value == hand2Value) {
		   return 0;
	   }
	   if(hand1Value < hand2Value) {
		   return -1;
	   }
	   return 1; 
	   }
	  
   }
    
  










