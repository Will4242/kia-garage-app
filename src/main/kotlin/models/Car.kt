package models

data class Car(
    var carModel: String,
    var carCategory: String,
    var carCost: Double,
    var carYear: Int,
    var carEngine: Double,
    var numberOfDoors: Int,
    var carTransmission: String,
    var isCarSold: Boolean
) {

    override fun toString(): String {
        return "Car(carModel='$carModel', carCategory='$carCategory', carCost=$carCost, carYear=$carYear, carEngine=$carEngine, numberOfDoors=$numberOfDoors, carTransmission='$carTransmission', isCarSold=$isCarSold)"
    }
}
