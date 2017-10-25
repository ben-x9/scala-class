sealed trait Currency
case object Yen extends Currency
case object Eur extends Currency
case object USD extends Currency

case class Money(amount: BigDecimal, currency: Currency)

sealed trait DebitError
case object NotEnoughMoney extends DebitError
case object AccountDisabled extends DebitError

case class Owner(
                  id: String,
                  address: String,
                  zipCode: String,
                  name: String
                )

case class Account(
                    id: String,
                    owner: Owner,
                    money: Money,
                    enabled: Boolean
                  )

object AccountOps {
  def debit(account: Account, amount: BigDecimal): Either[DebitError, (Account, Money)] = {
    if (account.enabled == false) Left(AccountDisabled)
    else if (account.money.amount < amount) Left(NotEnoughMoney)
    else {
      val newMoney = account.money.copy(amount = account.money.amount - amount)
      val newAccount = account.copy(money = newMoney)
      Right(newAccount, Money(amount, account.money.currency))
    }
  }
}

object App {
  def main(args: Array[String]): Unit = {
    val account = Account(
      id = "toto",
      owner = Owner(
        id = "test",
        address = "My super address",
        zipCode = "My super zip code",
        name = "Hello"
      ),
      money = Money(
        amount = BigDecimal(15),
        currency = USD
      ),
      enabled = true
    )

    AccountOps.debit(account, 10).map {
      case (_, money) =>
        println(s"Successfully debitted money $money")
    }.left.map {
      case NotEnoughMoney =>
        println("Not enough money")
      case AccountDisabled =>
        println("Account disabled")
    }
  }
}

