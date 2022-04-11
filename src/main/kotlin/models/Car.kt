package models

data class Car(var kiaModel: String,
               var kiaEngine: Double,
               var kiaCategory: String,
               var isCarSold :Boolean,
               var kiaYear: String,
               var numberOfDoors: Int,
               var kiaCost: Double,
               var kiaTransmission: String){
}