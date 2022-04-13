package controllers

import models.Car
import persistence.Serializer
import utils.ScannerInput

class CarAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        cars = serializer.read() as ArrayList<Car>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(cars)
    }

    private var cars = ArrayList<Car>()

    fun formatListString(carsToFormat: List<Car>): String =
        carsToFormat
            .joinToString(separator = "\n") { car ->
                cars.indexOf(car).toString() + ": " + car.toString()
            }

    fun add(car: Car): Boolean {
        return cars.add(car)
    }

    fun deleteCar(indexToDelete: Int): Car? {
        return if (isValidListIndex(indexToDelete, cars)) {
            cars.removeAt(indexToDelete)
        } else null
    }

    fun updateCar(indexToUpdate: Int, car: Car?): Boolean {
        // find the car object by the index number
        val foundCar = findCar(indexToUpdate)

        // if the car exists, use the car details passed as parameters to update the found car in the ArrayList.
        if ((foundCar != null) && (car != null)) {
            foundCar.carModel = car.carModel
            foundCar.carCategory = car.carCategory
            foundCar.carCost = car.carCost
            foundCar.carYear = car.carYear
            foundCar.carEngine = car.carEngine
            foundCar.numberOfDoors = car.numberOfDoors
            foundCar.carTransmission = car.carTransmission
            return true
        }

        // if the car was not found, return false, indicating that the update was not successful
        return false
    }

    fun updateCost(indexToCost: Int, cost: Double): Boolean {
        // find the car object by the index number
        val foundCar = findCar(indexToCost)

        // if the car exists, use the car details passed as parameters to update the found car in the ArrayList.
        if ((foundCar != null)) {
            foundCar.carCost = cost
            return true
        }

        // if the car was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, cars)
    }

    fun numberOfCars(): Int {
        return cars.size
    }

    fun findCar(index: Int): Car? {
        return if (isValidListIndex(index, cars)) {
            cars[index]
        } else null
    }

    fun sellCar(indexToSell: Int): Boolean {
        if (isValidIndex(indexToSell)) {
            val carToSold = cars[indexToSell]
            if (!carToSold.isCarSold) {
                carToSold.isCarSold = true
                return true
            }
        }
        return false
    }

    fun listAllCars(): String =
        if (cars.isEmpty()) "No cars stored"
        else formatListString(cars)

    fun listCarsOnSale(): String =
        if (numberOfCarsOnSale() == 0) "No cars on sale stored"
        else formatListString(cars.filter { car -> !car.isCarSold })

    fun listSoldCars(): String =
        if (numberOfSoldCars() == 0) "No sold cars stored"
        else formatListString(cars.filter { car -> car.isCarSold })

    fun numberOfSoldCars(): Int {
        return cars.count({ it.isCarSold })
    }

    fun numberOfCarsOnSale(): Int {
        return cars.count({ !it.isCarSold })
    }

    fun carsSortedByCost(): String {
        cars.sortBy { it.carCost }
        return listCarsOnSale()
    }

    fun carsSortedByModel(): String {
        cars.sortBy { it.carModel }
        return listCarsOnSale()
    }

    fun carsSortedByCategory(): String {
        cars.sortBy { it.carCategory }
        return listCarsOnSale()
    }

    fun carsSortedByEngine(): String {
        cars.sortBy { it.carEngine }
        return listCarsOnSale()
    }

    fun carsSortedByYear(): String {
        cars.sortBy { it.carYear }
        return listCarsOnSale()
    }

    fun listCarsBySelectedNoDoors(carNumberOfDoors: Int): String =
        formatListString(
            cars.filter { car -> car.numberOfDoors == carNumberOfDoors }
        )

    fun numberOfCarsByNoDoors(carNumberOfDoors: Int): Int {
        return cars.count({ it.numberOfDoors == carNumberOfDoors })
    }

    fun searchCarsByModel(model: String) =
        formatListString(
            cars.filter { car -> car.carModel.contains(model, ignoreCase = true) }.sortedBy { it.carYear }
        )

    fun numberOfCarsByModel(model: String): Int {
        return cars.count { it.carModel == model }
    }

    fun searchCarsByCategory(category: String) =
        formatListString(
            cars.filter { car -> car.carCategory.contains(category, ignoreCase = true) }.sortedBy { it.carCost }
        )

    fun numberOfCarsByCategory(category: String): Int {
        return cars.count { it.carCategory == category }
    }

    fun searchCarsByTransmission(transmission: String) =
        formatListString(
            cars.filter { car -> car.carTransmission.contains(transmission, ignoreCase = true) }.sortedBy { it.carCost }
        )

    fun numberOfCarsByTransmission(transmission: String): Int {
        return cars.count { it.carTransmission == transmission }
    }

}
