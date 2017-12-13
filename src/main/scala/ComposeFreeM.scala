
import CatsApp.CatsAppADT
import cats.data.EitherK
import cats.free.Free
import cats.{Id, InjectK, ~>}

import scala.collection.mutable.ListBuffer


sealed trait Interact[A]
case class Ask(prompt: String) extends Interact[String]
case class Tell(msg: String) extends Interact[Unit]

sealed trait DataOp[A]
case class AddCat(a: String) extends DataOp[Unit]
case class GetAllCats() extends DataOp[List[String]]

object CatsApp {
  type CatsAppADT[A] = EitherK[DataOp, Interact, A]
}

class Interacts[F[_]](implicit I: InjectK[Interact, F]) {
  def tell(msg: String): Free[F, Unit] = Free.inject[Interact, F](Tell(msg))
  def ask(prompt: String): Free[F, String] = Free.inject[Interact, F](Ask(prompt))
}

object Interacts {
  implicit def interacts[F[_]](implicit I: InjectK[Interact, F]): Interacts[F] = new Interacts[F]
}

class DataSource[F[_]](implicit I: InjectK[DataOp, F]) {
  def addCat(a: String): Free[F, Unit] = Free.inject[DataOp, F](AddCat(a))
  def getAllCats: Free[F, List[String]] = Free.inject[DataOp, F](GetAllCats())
}

object DataSource {
  implicit def dataSource[F[_]](implicit I: InjectK[DataOp, F]): DataSource[F] = new DataSource[F]
}

object ConsoleCatsInterpreter extends (Interact ~> Id) {
  def apply[A](i: Interact[A]) = i match {
    case Ask(prompt) =>
      println(prompt)
      readLine()
    case Tell(msg) =>
      println(msg)
  }
}

object InMemoryDatasourceInterpreter extends (DataOp ~> Id) {

  private[this] val memDataSet = new ListBuffer[String]

  def apply[A](fa: DataOp[A]) = fa match {
    case AddCat(a) => memDataSet.append(a); ()
    case GetAllCats() => memDataSet.toList
  }
}

object ComposeFreeMApp {
  val dataSourceOp = implicitly[DataSource[CatsAppADT]]
  val interactOp = implicitly[Interacts[CatsAppADT]]

  import dataSourceOp._
  import interactOp._

  def main(args: Array[String]): Unit = {
    val prg = for {
      cat <- ask("What's the name of the kitty ?")
      _ <- addCat(cat)
      cats <- getAllCats
      _ <- tell(cats.toString)
    } yield ()

    val interpreter = InMemoryDatasourceInterpreter or ConsoleCatsInterpreter

    prg.foldMap(interpreter)
  }
}