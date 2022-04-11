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
    val carCategory = readNextLine("Enter a category for the car (Jeep, Saloon, Waggon, Sport, Super: ")
    val carCost = readNextDouble("Enter a price for the car: ")
    val carYear = readNextInt("Enter a year for the car (00-99): ")
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

fun updateCar(){
    println("You chose Update Note")
}

fun deleteCar(){
    println("You chose Delete Note")
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}