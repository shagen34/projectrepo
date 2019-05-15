public class main
{
  public static void main(String args[])
    {
      bankAccount testAccount = new bankAccount();
      System.out.println("*** New Account Created ***\n*** Testing Functions ***");
      testAccount.deposit();
      testAccount.withdrawl();
      System.out.print("Balance: $" + testAccount.getBalance());

      checkingAccount testChecking = new checkingAccount();
      System.out.println("\n*** New Checking Account Created ***\n*** Testing Functions ***");
      testChecking.deposit();
      testChecking.withdrawl();
      System.out.print("Checking Balance: $" + testChecking.getBalance());

      savingsAccount testSavings = new savingsAccount();
      System.out.println("\n*** New Savings Account Created ***\n*** Testing Functions ***");
      testSavings.deposit();
      testSavings.withdrawl();
      System.out.print("\nSavings Balance: $" + testSavings.getBalance());
      testSavings.addInterest();
      System.out.println("\nAdd 1 month interest: $" + testSavings.getBalance() + "\n");
    }
}
