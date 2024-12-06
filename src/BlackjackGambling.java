import java.util.Scanner;

public class BlackjackGambling {
    // private instance variables
    private int lost;
    private int gains;
    private int player;
    private int betAmount;
    private int loans;
    private int charity;

    // constructor
    public BlackjackGambling() {
        lost = 0; // money lost to dealer
        gains = 0; //how much gained
        player = 100; //current money
        betAmount = 0; //amt bet
        loans = 0; //loans held
        charity = 0; //payment of loans exceeds loan amt given to charity
    }

    //getters
    public int getLoans() {
        return loans;
    }

    // methods
    //placing bet logic
    public int placeBet(Scanner scan) {
        boolean properAmt = false;
        while (!properAmt) {
            //asking for value
            System.out.print("How much would you like to bet? $");
            betAmount = scan.nextInt();
            scan.nextLine();
            // special condition for fun
            if (betAmount == 11212914 && player >= 5 && player % 5 == 0) {
                System.out.println("ALL IN! :D");
                betAmount = player;
                return betAmount;
            } else if (betAmount == 11212914) {
                System.out.println("Conditions not met.");
            }
            // checking if proper value conditions are met
            if (betAmount < 5) {
                System.out.println("Too little. Try again.");
            } else if (betAmount % 5 != 0) {
                System.out.println("Not an increment of $5. Try again.");
            } else if (betAmount > player) {
                System.out.println("You don't have enough money.");
                //loans
                String loanChoice = yesOrNo(scan, "Would you like to take out a loan?");
                if (loanChoice.equals("y") && loans <= 10000) {
                    int borrowAmt = -1;
                    boolean proper = false;
                    System.out.println("You may borrow amounts only in increments of 5.");
                    while (!proper) {
                        System.out.print("How much would you like to borrow? $");
                        borrowAmt = scan.nextInt();
                        scan.nextLine();
                        //checking bet amt to fit conditions
                        if (borrowAmt <= 5) {
                            System.out.println("Too little. Try again.");
                        } else if (borrowAmt % 5 != 0) {
                            System.out.println("Not an increment of 5. Try again.");
                        } else if (loans + borrowAmt > 10000) {
                            System.out.println("Exceeded loan limit. Try again.");
                        } else {
                            proper = true;
                        }
                    }
                    System.out.println();
                    player += borrowAmt;
                    loans += borrowAmt;
                } else if (loanChoice.equals("y") && loans >= 100000) {
                    System.out.println("You reached your loan limit.");
                } else {
                    System.out.println("Maybe next time.");
                }
            }
            //checking if value meets conditions after loans
            if (betAmount >= 5 && betAmount <= player && betAmount % 5 == 0) {
                properAmt = true;
            }
        }
        //placing bets
        System.out.println("You bet $" + betAmount);
        return betAmount;
    }

    // to pay loans
    public void payLoan(Scanner scan) {
        if (loans > 0 && player > 0) {
            System.out.println();
            String choice = yesOrNo(scan, "Do you want to pay back your loans?");
            if (choice.equals("y")) {
                boolean proper = false; // check if input meets requirements
                int paybackAmt = 0; // random value

                //obtaining proper value for loan payment
                while (!proper) {
                    System.out.print("How much would you like to pay back? $");
                    paybackAmt = scan.nextInt();
                    if (paybackAmt > (player - 5)) {
                        System.out.println("You need as least $5 in your funds!");
                    } else if (paybackAmt % 5 != 0) {
                        System.out.println("Not an increment of 5.");
                    } else {
                        proper = true;
                    }
                }
                scan.nextLine();

                // paying loans back and printing info
                loans -= paybackAmt;
                player -= paybackAmt;
                if (loans <= 0) {
                    charity += Math.abs(loans);
                    loans = 0;
                }

                //printing info to user
                System.out.println("You paid $" + paybackAmt + " off your loans");
                System.out.println("Current Funds: $" + player);
                System.out.println("Loans Remaining: $" + loans);
                if (charity != 0) {
                    System.out.println("Donations to Charity: $" + charity);
                }
            }
        }
    }

    // calculating gains and loses after round
    public void calculate(String win, int betTotal) {
        if (win.equals("win")) {
            player += betTotal/2;
            gains += betTotal/2;
            System.out.println("Gained $" + betTotal/2);
        } else if (win.equals("lose")) {
            player -= betTotal/2;
            lost += betTotal/2;
            System.out.println("Lost $" + betTotal/2);
        }
        //printing info
        System.out.println("Current Funds: $" + player);
        System.out.println("Total Money Lost: $" + lost);
        System.out.println("Total Money Gained: $" + gains);
        System.out.println("Loans: $" + loans);
    }

    // when game ends
    public void gameEnd() {
        System.out.println("Starting Money: $100");
        System.out.println("Ending Money: $" + player);
        System.out.println("Net Change: $" + (player - 100));
        System.out.println("Debt Accrued: $" + loans);
    }

    //when debts aren't paid
    public void unpaidDebts() {
        System.out.println("You haven't paid back all your debts!");
        System.out.println("You receive a visit from loan sharks.");
        System.out.println("...");
    }

    //ensuring that the entered choice is acceptable
    public String yesOrNo(Scanner scan, String question) {
        String choice = "";
        while (choice.length() != 1 || (!choice.equals("y") && !choice.equals("n"))) {
            System.out.print(question + " (y/n): ");
            choice = scan.nextLine();
        }
        return choice;
    }
}