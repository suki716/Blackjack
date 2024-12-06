import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Blackjack {

    // static variables (common across all objects)
    private static String[] cards = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static int[] cardAmt = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}; // number of cards per card type
    private static int[] cardValue = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10}; // card value

    // general instance variables
    private List<String> hand = new ArrayList<String>();
    private int handValue = 0;

    // dealer instance variables
    private String initialDealerCard;
    private int dealerAceValue = 0;

    // user instance variables
    private int aceValue = 0;

    // constructors
    public Blackjack(){ }

    public Blackjack(String player) { //creating initial hands
        if (player.equals("dealer")) {
            initialDealerCard = randomCard();
            hand.add("?"); //hidden card
            hand.add(randomCard());
        } else {
            hand.add(randomCard());
            hand.add(randomCard());
        }
    }

    // getters
    public int getHandValue() {
        return handValue;
    }

    // methods
    public String randomCard() {
        int randomIndex = (int) (Math.random() * 13); // random card
        while (cardAmt[randomIndex] == 0) { //cardAmt is zero -> rerolls random num
            randomIndex = (int) (Math.random() * 13);
        }
        handValue += cardValue[randomIndex]; // adds random card's value to total hand value
        cardAmt[randomIndex] -= 1; // reduces the amount of card amt
        return cards[randomIndex]; // returns the String value of the chosen card
    }

    public void printCurrentHand() { // displays the entire hand
        System.out.print("[");
        for (int i = 0; i < hand.size(); i++) {
            if (i != (hand.size() - 1)) { // if not the last card in hand
                System.out.print(hand.get(i));
                System.out.print(", ");
            } else {
                System.out.print(hand.get(i));
                System.out.println("]");
            }
        }
    }

    public void printDealerHand() { //prints dealer hand (only accessed when game's over)
        hand.set(0, initialDealerCard);
        printCurrentHand();
    }

    public void hitOrStand(String hitOrStand) {
        if (hitOrStand.equals("hit")) { // adds random card if player wants to hit
            hand.add(randomCard());
        }
    }

    // offers user the choice to assign a value to ace for n amount of times for n amount of aces in cards
    public void ifAce(Scanner scan) {
        handValue -= aceValue; //resets hand value back to value without aces
        aceValue = 0; //resets total ace value
        for (int i = 0; i < hand.size(); i++) { //checking for aces
            if (hand.get(i).equals("A")) {
                System.out.print("What value would you like \"A\" to be? (1 or 11): ");
                int choice = scan.nextInt();
                scan.nextLine();
                aceValue += choice; //updates total ace value with chosen ace #
                handValue += choice; //adding value to ace to hand value
                System.out.println("Your Current Value: " + getHandValue());
            }
        }
    }

    // does ace for dealer hand automatically
    public void ifAce(boolean dealer) {
        if (dealer) {
            hand.set(0, initialDealerCard); // temporarily replaces mystery card with actual card
            handValue -= dealerAceValue; //resets hand value back to value without aces
            dealerAceValue = 0; //resets total ace value
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).equals("A")) {
                    if (handValue + 11 <= 21) { // checks if 11 can fit into hand value without going over 21
                        dealerAceValue = 11;
                    } else {
                        dealerAceValue = 1; // if not defaults ace to 1
                    }
                    handValue += dealerAceValue; // updates hand value
                    dealerAceValue = 0; //resets ace value for when there is more than one
                }
            }
            hand.set(0, "?"); // replaces actual card back to mystery
        }
    }
}