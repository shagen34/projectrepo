import java.util.*;

public class bankAccount
{
  private double balance;
  private double accountNumber;

  bankAccount() //would retrieve number/balance from database
  {
      accountNumber = 0000000;
      balance = 0;
  }

  Scanner s = new Scanner(System.in);

  void deposit()
  {
    System.out.print("\nDeposit Amount: $");
    double newdeposit = s.nextDouble();
    balance += newdeposit;
    System.out.print("\nBalance: $" + balance);
  }

  void withdrawl()
  {
    System.out.print("\nWithdrawl Amount: $");
    double newwithdrawl = s.nextDouble();

    if(newwithdrawl > balance)
    {
      System.out.print("\nBalanced Exceeded. Current Balance: $" + balance);
      withdrawl();
    }
    else
    {
      setBalance(getBalance() - newwithdrawl);
    }
  }
  double getBalance()
  {
      return balance;
  }
  void setBalance(double newBalance)
  {
    balance = newBalance;
  }
}
