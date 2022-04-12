import controllers.CarAPI
import models.Car
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit

private val logger = KotlinLogging.logger {}

private val carAPI = CarAPI()

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
         > |   4) Delete a Car              |
         > |   5) Sell a Car                |
         > |   6) Save                      |
         > |   7) Load                      |
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
            4 -> deleteCar()
            //5 -> sellCar()
            //7 -> save()
            //8 -> load()
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
                  > ----------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllCars()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
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

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}