
import cats.free.Free
import cats.{Id, ~>}

import scala.collection.mutable
import scala.concurrent.Future


sealed trait KVStoreADT[A]
case class Put[A](key: String, value: A) extends KVStoreADT[A]
case class Get(key: String) extends KVStoreADT[Any]
case class Delete(key: String) extends KVStoreADT[Unit]

object KVStore {
  type KVStoreF[A] = Free[KVStoreADT, A]

  def put[A](key: String, value: A): KVStoreF[A] =
    Free.liftF(Put(key, value))

  def get[A](key: String): KVStoreF[Any] =
    Free.liftF(Get(key))

  def delete(key: String): KVStoreF[Unit] =
    Free.liftF(Delete(key))
}

object MapKVStore extends (KVStoreADT ~> Id) {
  var store = mutable.Map.empty[String, Any]

  override def apply[A](fa: KVStoreADT[A]): Id[A] = fa match {
    case Put(key, value) =>
      store.put(key, value)
      value
    case Get(key) =>
      store.get(key).map(_.asInstanceOf[A])
    case Delete(key) =>
      store.remove(key)
      ()
  }
}

object SecondApp {

  import KVStore._

  def main(args: Array[String]): Unit = {
    val prg = for {
      _ <- put("toto", 5)
      toto <- get[Int]("toto")
      _ <- delete("toto")
    } yield toto

    println(prg.foldMap(MapKVStore))
  }
}
