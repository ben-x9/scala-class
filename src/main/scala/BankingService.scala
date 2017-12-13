/**
  * Created by jalal on 17/10/25.
  */
/*
sealed trait BankingError
sealed trait CreditError extends BankingError

case object NegativeAmount extends CreditError
case object AccountDisabled extends BankingError

sealed trait DebitError extends BankingError
case object NotEnoughMoney extends DebitError

case class Transaction(from: CheckingAccount, money: Money, to: CheckingAccount)
case class AccountState(money: Money, enabled: Boolean)

object BankingService {
  def debit(account: CheckingAccount, amount: BigDecimal): Either[BankingError, (CheckingAccount, Money)] = {
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

  def credit(account: CheckingAccount, amount: BigDecimal): Either[BankingError, (CheckingAccount, Money)] = {
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

  // int => int => int
  // let f a b = a + b;
  // let g b = f 5;
  // Either.flatMap = Either[E, A] => (A => Either[E, B]) => | - Either[E, Either[E, B]]| Either[E, B]
  def transfer(from: CheckingAccount, amount: BigDecimal, to: CheckingAccount): Either[BankingError, Transaction] = {
    debit(from, amount).flatMap {
      case (account, _) => credit(to, amount)
    }.map {
      case (account, money) =>
        Transaction(from, money, to)
    }
  }

  def inquiry(account: CheckingAccount): AccountState = {
    AccountState(account.money, account.enabled)
  }
}

*/