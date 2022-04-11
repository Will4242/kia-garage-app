import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit

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
         > |   4) Delete a Car              |
         > |   5) Archive a Car             |
         > |   6) save                      |
         > |   7) load                      |
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
            //5 -> archiveCar()
            //7 -> save()
            //8 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: $option")
        }
    } while (true)
}

fun listCars() {
    if (carAPI.numberOfNotes() > 0) {
        val option = ScannerInput.readNextInt(
            """
                  > ----------------------------------
                  > |   1) View ALL Cars             |
                  > ----------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            //1 -> listAllCars()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun addCar(){
    println("You chose Add Note")
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