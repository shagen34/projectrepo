public class checkingAccount extends bankAccount
{
  private double withdrawlFee = 1.00;
  private double monthlyWithdrawls;

  void withdrawl()
  {
    System.out.print("\nWithdrawl Amount: $");
    double newwithdrawl = s.nextDouble();
    monthlyWithdrawls++;

    if(newwithdrawl > getBalance())
    {
      System.out.print("\nBalanced exceeded. Current balance: $" + getBalance());
      withdrawl();
    }
    else
    {
      setBalance(getBalance() - newwithdrawl);
    }
    System.out.println("\nBalance: $" + getBalance());

    if(monthlyWithdrawls > 3)
    {
      double newBal = getBalance() - withdrawlFee;
      setBalance(newBal);
      System.out.print("\nFree withdrawls exceeded, applying fee. Balance: $" + getBalance());
    }
  }
}
