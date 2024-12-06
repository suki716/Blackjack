import java.util.Scanner;

public class BlackjackRound {

    //constructor
    public BlackjackRound() {}

    //methods
    public String playRound(Scanner scan) { // returns the result of the match
        Blackjack userHand = new Blackjack("user");
        Blackjack dealerHand = new Blackjack("dealer");
        String win = "lose";
        String answer = "";
        String dealerAnswer = "";
        System.out.println();
        System.out.println("Your Turn: ");

        playTurn(userHand, dealerHand, answer, scan);

        if (userHand.getHandValue() > 21) {// lose game automatically if user bust and if value is still above 21
            printInfo(userHand, dealerHand, true);
            System.out.println("You Busted!");
            win = "lose";
            return win;
        } else if (userHand.getHandValue() == 21){ // win game automatically if user 21
            printInfo(userHand, dealerHand, true);
            System.out.println("You have 21!");
            win = "win";
            return win;
        }

        if (!answer.equals("stand")) {
            printInfo(userHand, dealerHand);
        }

        System.out.println("Your turn ends. ");
        System.out.println("---------------------");
        System.out.println("Dealer's Turn: ");
        playTurn(userHand, dealerHand);

        while (dealerHand.getHandValue() <= 21 && !dealerAnswer.equals("stand")) {
            if (dealerHand.getHandValue() <= 16) {
                dealerAnswer = "hit";
                System.out.println("Dealer decided to hit.");
            } else {
                dealerAnswer = "stand";
                System.out.println("Dealer decided to stand.");
            }
            dealerHand.hitOrStand(dealerAnswer);
            dealerHand.ifAce(true);
            dealerHand.printCurrentHand();
        }

        System.out.println("Dealer's turn ends.");
        System.out.println("---------------------");

        printInfo(userHand, dealerHand, true);

        if (dealerHand.getHandValue() > 21){ // dealer busted
            System.out.println("Dealer Busted!");
            win = "win";
        } else if (dealerHand.getHandValue() == userHand.getHandValue()) { // if same value
            System.out.println("Tie!");
            win = "tie";
        } else if (dealerHand.getHandValue() == 21) { // if dealer 21
            System.out.println("Dealer Has 21!");
            win = "lose";
        } else if (userHand.getHandValue() > dealerHand.getHandValue()){ // if user and dealer both < 21 but user is closer
            System.out.println("You're closer to 21!");
            win = "win";
        } else { // if user and dealer both < 21 but dealer is closer
            System.out.println("Dealer's closer to 21!");
            win = "lose";
        }
        return win;
    }

    // helper methods
    private void playTurn(Blackjack userHand, Blackjack dealerHand, String answer, Scanner scan){
        while (userHand.getHandValue() <= 21 && !answer.equals("stand")) { //user plays until stand
            printInfo(userHand, dealerHand);
            if (userHand.getHandValue() != 21) {
                userHand.ifAce(scan);
            }
            System.out.print("Hit or Stand: ");
            answer = scan.nextLine();
            userHand.hitOrStand(answer); // adds random card to hand if user chose hit
            System.out.println();
        }
    }

    private void playTurn(Blackjack userHand, Blackjack dealerHand){
        String dealerAnswer = "";
        while (dealerHand.getHandValue() <= 21 && !dealerAnswer.equals("stand")) {
            if (dealerHand.getHandValue() <= 16) {
                dealerAnswer = "hit";
                System.out.println("Dealer decided to hit.");
            } else {
                dealerAnswer = "stand";
                System.out.println("Dealer decided to stand.");
            }
            dealerHand.hitOrStand(dealerAnswer);
            dealerHand.ifAce(true);
            dealerHand.printCurrentHand();
        }
    }

    private void printInfo(Blackjack userHand, Blackjack dealerHand) {
        System.out.print("Your Current Hand: ");
        userHand.printCurrentHand();
        System.out.println("Your Current Value: " + userHand.getHandValue());
        System.out.print("Dealer's Hand: ");
        dealerHand.printCurrentHand();
    }

    private void printInfo(Blackjack userHand, Blackjack dealerHand, boolean gameOver) {
        if (gameOver) {
            System.out.print("Your Current Hand: ");
            userHand.printCurrentHand();
            System.out.println("Your Current Value: " + userHand.getHandValue());


            System.out.print("Dealer's Hand: ");
            dealerHand.ifAce(true);
            dealerHand.printDealerHand();
            System.out.println("Dealer's Current Value: " + dealerHand.getHandValue());
        }
    }
}