/*import scalaz._
import scalaz.Scalaz._

trait AccountRepository[StoreType, Account] {
  def query(accountNo: AccountId): Reader[StoreType, Account]
  def write(accounts: Seq[CheckingAccount]): Boolean
  def delete(account: CheckingAccount): Boolean
}*/

/*object AccountRepositoryImpl extends AccountRepository {
  var accounts: Map[AccountId, CheckingAccount] = Map()

  def query(accountNo: AccountId): Option[CheckingAccount] = accounts.get(accountNo)

  def write(newAccounts: Seq[CheckingAccount]): Boolean = {
    accounts = newAccounts.foldLeft(accounts){
      case (results, newAccount) => results + (newAccount.id -> newAccount)
    }

    true
  }

  def delete(accountToDelete: CheckingAccount): Boolean = {
    accounts -= accountToDelete.id
    true
  }


}*/