case class Account(
  id: String,
  owner: Owner,
  money: Money,
  enabled: Boolean
)

case class InvalidId[A](id: String)


object Account {
  def validate(id: String,
               owner: Owner,
               money: Money,
               enabled: Boolean): Either[InvalidId[Account], Account] = {
    if (id.isEmpty) {
      Left(InvalidId[Account](id))
    } else {
      Right(new Account(id, owner, money, enabled))
    }
  }
}

object AccountOps {
  def debit(account: Account, amount: BigDecimal): Either[BankingError, (Account, Money)] = {
    if (account.enabled == false) Left(AccountDisabled)
    else if (account.money.amount < amount) Left(NotEnoughMoney)
    else {
      val newMoney = account.money.copy(amount = account.money.amount - amount)
      val newAccount = account.copy(money = newMoney)
      Right(newAccount, Money(amount, account.money.currency))
    }
  }
}