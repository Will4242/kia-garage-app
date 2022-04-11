package models

data class Car(var kiaModel: String,
               var kiaCategory: String,
               var kiaCost: Double,
               var kiaYear: Int,
               var kiaEngine: Double,
               var numberOfDoors: Int,
               var kiaTransmission: String,
               var isCarSold :Boolean){

    override fun toString(): String {
        return "Note(kiaModel='$kiaModel', kiaCategory='$kiaCategory', kiaCost=$kiaCost, kiaYear=$kiaYear, kiaEngine=$kiaEngine, numberOfDoors=$numberOfDoors, kiaTransmission='$kiaTransmission', isCarSold=$isCarSold)"
    }
}