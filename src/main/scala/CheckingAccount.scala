import scala.util.{Failure, Success, Try}
/*
case class AccountId(value: String) extends AnyVal

trait Account {
  def id : AccountId
  def owner : Owner
  def money : Money
  def enabled : Boolean
}

case class CheckingAccount(
                           id: AccountId,
                           owner: Owner,
                           money: Money,
                           enabled: Boolean) extends Account

trait InterestBearingAccount extends Account {
  def rateOfInterest: BigDecimal
}

case class SavingsAccount(id: AccountId,
                          owner: Owner,
                          money: Money,
                         rateOfInterest: BigDecimal,
                          enabled: Boolean) extends InterestBearingAccount


case class InvalidId[A](id: String)



object CheckingAccount {

  def validate(id: AccountId,
            owner: Owner,
            money: Money,
            enabled: Boolean): Either[InvalidId[CheckingAccount], CheckingAccount] = {
    if (id.value.isEmpty) {
      Left(InvalidId[CheckingAccount](id.value))
    } else {
      Right(new CheckingAccount(id, owner, money, enabled))
    }
  }
}
*/