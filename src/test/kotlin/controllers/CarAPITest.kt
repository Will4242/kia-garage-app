package controllers

import models.Car
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CarAPITest {

    private var kiaRio: Car? = null
    private var kiaK900: Car? = null
    private var kiaStinger: Car? = null
    private var kiaNiro: Car? = null
    private var kiaEV6: Car? = null
    private var populatedCars: CarAPI? = CarAPI()
    private var emptyCars: CarAPI? = CarAPI()

    @BeforeEach
    fun setup(){
        kiaRio = Car("Kia Rio", "Hatchback", 30_000.00, 20, 1.2, 5, "Manual",false)
        kiaK900 = Car("Kia K900", "Saloon", 60_000.00, 14, 2.0, 5, "Automatic", false)
        kiaStinger = Car("Kia Stinger", "Sport", 80_000.00, 21, 2.0, 3, "Manual", false)
        kiaNiro = Car("Kia Niro", "Jeep", 40_000.00, 18, 1.6, 5, "Automatic", false)
        kiaEV6 = Car("Kia EV6", "Super", 200_000.00, 22, 4.0, 2, "Manual", false)

        //adding 5 Note to the notes api
        populatedCars!!.add(kiaRio!!)
        populatedCars!!.add(kiaK900!!)
        populatedCars!!.add(kiaStinger!!)
        populatedCars!!.add(kiaNiro!!)
        populatedCars!!.add(kiaEV6!!)
    }

    @AfterEach
    fun tearDown(){
        kiaRio = null
        kiaK900 = null
        kiaStinger = null
        kiaNiro = null
        kiaEV6 = null
        populatedCars = null
        emptyCars = null
    }

    @Nested
    inner class AddCars {
        @Test
        fun `adding a Car to a populated list adds to ArrayList`() {
            val newCar = Car("Kia Vanatge", "Super", 150_000.00, 22, 3.2, 3, "Automatic", false)
            assertEquals(5, populatedCars!!.numberOfCars())
            assertTrue(populatedCars!!.add(newCar))
            assertEquals(6, populatedCars!!.numberOfCars())
            assertEquals(newCar, populatedCars!!.findCar(populatedCars!!.numberOfCars() - 1))
        }

        @Test
        fun `adding a Car to an empty list adds to ArrayList`() {
            val newCar = Car("Kia Vanatge", "Super", 150_000.00, 22, 3.2, 3, "Automatic", false)
            assertEquals(0, emptyCars!!.numberOfCars())
            assertTrue(emptyCars!!.add(newCar))
            assertEquals(1, emptyCars!!.numberOfCars())
            assertEquals(newCar, emptyCars!!.findCar(emptyCars!!.numberOfCars() - 1))
        }
    }

    @Nested
    inner class ListAllCars {

        @Test
        fun `listAllCars returns No Cars Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCars!!.numberOfCars())
            assertTrue(emptyCars!!.listAllCars().lowercase().contains("no cars"))
        }

        @Test
        fun `listAllCars returns Cars when ArrayList has cars stored`() {
            assertEquals(5, populatedCars!!.numberOfCars())
            val notesString = populatedCars!!.listAllCars().lowercase()
            assertTrue(notesString.contains("kia rio"))
            assertTrue(notesString.contains("kia k900"))
            assertTrue(notesString.contains("kia stinger"))
            assertTrue(notesString.contains("kia niro"))
            assertTrue(notesString.contains("kia ev6"))
        }
    }

}