public class savingsAccount extends bankAccount
{
  private double interestRate = 0.9;

  void addInterest()
  {
    double interest = getBalance() * Math.pow((1 + (interestRate / 12 )), (1/12)); //math is off
    setBalance(getBalance() + interest);
  }
}
