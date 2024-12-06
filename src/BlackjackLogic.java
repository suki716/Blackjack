import java.util.Scanner;

public class BlackjackLogic {
    // private instance variables
    Scanner scan;
    int startingMoney;
    BlackjackGambling gamble;

    // constructor
    public BlackjackLogic() {
        scan = new Scanner(System.in);
    }

    //methods
    //method to start game
    public void start() {
        introductions();
        gamble = new BlackjackGambling();
        String quit = "n";
        int roundNum = 1; // var to keep track of rounds
        while (quit.equals("n")) {
            // start round
            System.out.println("Round " + roundNum + ":");
            System.out.println();
            System.out.println("Place your bets: ");
            int bet = gamble.placeBet(scan); // ask for and return gamble amount

            //playing round
            BlackjackRound round = new BlackjackRound(); //start new round every time
            String win = round.playRound(scan); // plays and returns boolean value of win

            //round end
            System.out.println("---------------------");
            gamble.calculate(win, bet*2);
            gamble.payLoan(scan);
            System.out.println("---------------------");

            //continue or no?
            roundNum++; // increasing rounds played
            quit = gamble.yesOrNo(scan, "Do you want to quit?");
            System.out.println();
        }
        //printing out info
        gamble.gameEnd();
        //checking for loan situation
        if (gamble.getLoans() > 0) {
            gamble.unpaidDebts();
        }
        //game end
        System.out.println("Goodbye! Thanks for playing!");
    }

    //private helper methods
    private void introductions() {
        System.out.println("Welcome to Blackjack!");
        System.out.println("---------------------");
        System.out.println("Instructions:");
        System.out.println("Blackjack is a card game consisting of two players: you and the dealer");
        System.out.println("Your goal is to get as close to 21 without going over");
        System.out.println();
        System.out.println("Additional Notes: ");
        System.out.println("You may take out loans up to a limit of $10,000");
        System.out.println("Make sure to pay back your loans. There may be consequences...");
        System.out.println();
        System.out.println("Fun Fact: Try entering \"11212914\" when betting :D");
        System.out.println();
        System.out.println("You start off with $100");
        System.out.println("---------------------");
    }
}