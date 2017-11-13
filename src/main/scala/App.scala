

sealed trait Currency
case object Yen extends Currency
case object Eur extends Currency
case object USD extends Currency

case class Money(amount: BigDecimal, currency: Currency)

object App {
  def main(args: Array[String]): Unit = {
    val res = for {
      account <- CheckingAccount.validate(
        id = AccountId("toto"),
        owner = Owner(
          id = "test",
          address = Address(
            street = "112 blablabla",
            zipCode = "54321"
          ),
          name = "Hello"
        ),
        money = Money(
          amount = 15,
          currency = USD
        ),
        enabled = true
      )
      debit <- BankingService.debit(account, 14)
    } yield debit

      res.map {
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