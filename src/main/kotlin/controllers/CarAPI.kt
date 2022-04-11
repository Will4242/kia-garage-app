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

    fun listAllCars(): String =
        if (cars.isEmpty()) "No cars stored"
        else formatListString(cars)

    fun numberOfCars(): Int {
        return cars.size
    }
}