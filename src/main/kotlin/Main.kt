import controllers.CarAPI
import models.Car
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val carAPI = CarAPI(XMLSerializer(File("cars.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a Car                 |
         > |   2) List Cars                 |
         > |   3) Update a Car              |
         > |   4) Update a Price            |
         > |   5) Delete a Car              |
         > |   6) Sell a Car                |
         > |   7) Save                      |
         > |   8) Load                      |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addCar()
            2 -> listCars()
            3 -> updateCar()
            4 -> updateCost()
            5 -> deleteCar()
            6 -> sellCar()
            7 -> save()
            8 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: $option")
        }
    } while (true)
}

fun listCars() {
    if (carAPI.numberOfCars() > 0) {
        val option = ScannerInput.readNextInt(
            """
                  > ----------------------------------
                  > |   1) View ALL Cars             |
                  > |   2) View Cars on Sale         |
                  > |   3) View Sold Cars            |
                  > |   4) Number of Cars on Sale    |
                  > |   5) Number of Sold Cars       |
                  > |   6) View ordered by Price     |
                  > |   7) View ordered by Model     |
                  > |   8) View ordered by Category  |
                  > |   9) View ordered by Engine    |
                  > |  10) View ordered by Year      |
                  > |  11) View Cars by Price Range  |
                  > |  12) View Cars by No. Doors    |
                  //searches sorted by price and number of cars in field given
                  > |  13) Search Model              |
                  > |  14) Search Category           |
                  > |  15) Search Transmission       |
                  > ----------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1  -> listAllCars()
            2  -> listCarsOnSale()
            3  -> listSoldCars()
            4  -> numberOfCarsOnSale()
            5  -> numberOfSoldCars()
            6  -> carsSortedByCost()
            7  -> carsSortedByModel()
            8  -> carsSortedByCategory()
            9  -> carsSortedByEngine()
            10 -> carsSortedByYear()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun save() {
    try {
        carAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        carAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun listAllCars() {
    // logger.info { "listNotes() function invoked" }
    println(carAPI.listAllCars())
}

fun addCar(){
    //logger.info { "addNote() function invoked" }
    val carModel = readNextLine("Enter the model of the car: ")
    val carCategory = readNextLine("Enter a category for the car (Jeep, Saloon, Hatchback, Sport, Super: ")
    val carCost = readNextDouble("Enter a price for the car: ")
    val carYear = readNextInt("Enter the year of the car (00-99): ")
    val carEngine = readNextDouble("Enter the engine size of the car: ")
    val numberOfDoors = readNextInt("Enter the number of doors for the car (2-5): ")
    val carTransmission = readNextLine("Enter the transmission of the car (Manual or Automatic): ")
    val isAdded = carAPI.add(Car(carModel, carCategory, carCost, carYear, carEngine, numberOfDoors, carTransmission, false))
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun updateCar() {
    //logger.info { "updateCars() function invoked" }
    listAllCars()
    if (carAPI.numberOfCars() > 0) {
        //only ask the user to choose the car if cars exist
        val indexToUpdate = readNextInt("Enter the index of the car to update: ")
        if (carAPI.isValidIndex(indexToUpdate)) {
            val carModel = readNextLine("Enter the model of the car: ")
            val carCategory = readNextLine("Enter a category for the car (Jeep, Saloon, Hatchback, Sport, Super: ")
            val carCost = readNextDouble("Enter a price for the car: ")
            val carYear = readNextInt("Enter the year of the car (00-99): ")
            val carEngine = readNextDouble("Enter the engine size of the car: ")
            val numberOfDoors = readNextInt("Enter the number of doors for the car (2-5): ")
            val carTransmission = readNextLine("Enter the transmission of the car (Manual or Automatic): ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (carAPI.updateCar(indexToUpdate, Car(carModel, carCategory, carCost, carYear, carEngine, numberOfDoors, carTransmission, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no cars for this index number")
        }
    }
}

fun updateCost() {
    listAllCars()
    if (carAPI.numberOfCars() > 0) {
        val indexToCost = readNextInt("Enter the index of the car to update: ")
        if (carAPI.isValidIndex(indexToCost)) {
            var carCost = readNextDouble("Enter a Price for the Car: ")
            if (carAPI.updateCost(indexToCost, carCost)) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteCar() {
    // logger.info { "deleteCars() function invoked" }
    listAllCars()
    if (carAPI.numberOfCars() > 0) {
        // only ask the user to choose the car to delete if cars exist
        val indexToDelete = readNextInt("Enter the index of the car to delete: ")
        // pass the index of the car to CarAPI for deleting and check for success.
        val carToDelete = carAPI.deleteCar(indexToDelete)
        if (carToDelete != null) {
            println("Delete Successful! Deleted car: ${carToDelete.carModel}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun sellCar() {
    listCarsOnSale()
    if (carAPI.numberOfCarsOnSale() > 0) {
        val indexToSold = readNextInt("Enter the index of the car to sell: ")
        if (carAPI.sellCar(indexToSold)) {
            println("Sale Successful!")
        } else {
            println("Sale NOT Successful")
        }
    }
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

fun listCarsOnSale() {
    println(carAPI.listCarsOnSale())
}

fun listSoldCars() {
    println(carAPI.listSoldCars())
}

fun numberOfSoldCars() {
    println(carAPI.numberOfSoldCars())
}

fun numberOfCarsOnSale() {
    println(carAPI.numberOfCarsOnSale())
}

fun carsSortedByCost() {
    println(carAPI.carsSortedByCost())
}

fun carsSortedByModel() {
    println(carAPI.carsSortedByModel())
}

fun carsSortedByCategory() {
    println(carAPI.carsSortedByCategory())
}

fun carsSortedByEngine() {
    println(carAPI.carsSortedByEngine())
}

fun carsSortedByYear() {
    println(carAPI.carsSortedByYear())
}
