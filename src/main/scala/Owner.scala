import scalaz._
import Scalaz._

case class Owner(
  id: String,
  address: Address,
  name: String
)

object Owner {
  val addressLens = Lens.lensu[Owner, Address](
    (owner, address) => owner.copy(address = address),
    (owner) => owner.address
  )
  val countryLens = Lens.lensu[Address, Country](
    (address, country) => address.copy(country = country),
    (address) => address.country
  )

  val owner = Owner(
    id = "toto",
    address = Address(
      street = "test",
      zipCode = "124",
      country = Country(
        name = "Japan"
      )
    ),
    name = "test"
  )
  val ownerCountryLens = addressLens andThen countryLens
  val owner2 = ownerCountryLens.set(owner, Country("Indonesia"))

}
