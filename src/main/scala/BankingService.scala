sealed trait BankingError
sealed trait CreditError extends BankingError

case object NegativeAmount extends CreditError
case object AccountDisabled extends BankingError

sealed trait DebitError extends BankingError
case object NotEnoughMoney extends DebitError

case class Transaction(from: Account, to: Account, money: Money)
case class AccountState(money: Money, enabled: Boolean)

object BankingService {
  def debit(account: Account, amount: BigDecimal): Either[BankingError, (Account, Money)] = {
    if (!account.enabled) {
      Left(AccountDisabled)
    }
    else if (account.money.amount < amount) Left(NotEnoughMoney)
    else {
      val newMoney = account.money.copy(amount = account.money.amount - amount)
      val newAccount = account.copy(money = newMoney)
      Right(newAccount, Money(amount, account.money.currency))
    }
  }

  def credit(account: Account, amount: BigDecimal): Either[BankingError, (Account, Money)] = {
    if (!account.enabled) {
      Left(AccountDisabled)
    }
    else if (amount < 0) Left(NegativeAmount)
    else {
      val newMoney = account.money.copy(amount = account.money.amount + amount)
      val newAccount = account.copy(money = newMoney)
      Right(newAccount, Money(amount, account.money.currency))
    }
  }

  def transfer(from: Account, amount: BigDecimal, to: Account): Either[BankingError, Transaction] = {
    for {
      state1 <- debit(from, amount)
      state2 <- credit(to, amount)
    } yield Transaction(state1._1, state2._1, state1._2)
  }

  def inquiry(account: Account): AccountState = {
    AccountState(account.money, account.enabled)
  }
}