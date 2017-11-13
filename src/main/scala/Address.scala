/**
  * Created by jalal on 17/11/01.
  */
case class Country(
  name: String
)

case class Address(
  street: String,
  zipCode: String,
  country: Country
)
