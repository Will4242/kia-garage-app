package controllers

import models.Car
import persistence.Serializer

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

    fun listAllCars(): String =
        if (cars.isEmpty()) "No cars stored"
        else formatListString(cars)

}