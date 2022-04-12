package controllers

import models.Car

class CarAPI {

    private var cars = ArrayList<Car>()

    fun formatListString(carsToFormat: List<Car>): String =
        carsToFormat
            .joinToString(separator = "\n") { car ->
                cars.indexOf(car).toString() + ": " + car.toString()
            }

    fun add(car: Car): Boolean {
        return cars.add(car)
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