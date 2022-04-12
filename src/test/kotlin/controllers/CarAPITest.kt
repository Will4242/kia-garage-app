package controllers

import models.Car
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

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

    @Nested
    inner class DeleteCars {

        @Test
        fun `deleting a Car that does not exist, returns null`() {
            assertNull(emptyCars!!.deleteCar(0))
            assertNull(populatedCars!!.deleteCar(-1))
            assertNull(populatedCars!!.deleteCar(5))
        }

        @Test
        fun `deleting a car that exists delete and returns deleted object`() {
            assertEquals(5, populatedCars!!.numberOfCars())
            assertEquals(kiaEV6, populatedCars!!.deleteCar(4))
            assertEquals(4, populatedCars!!.numberOfCars())
            assertEquals(kiaRio, populatedCars!!.deleteCar(0))
            assertEquals(3, populatedCars!!.numberOfCars())
        }
    }

    @Nested
    inner class UpdateCars {
        @Test
        fun `updating a car that does not exist returns false`() {
            assertFalse(populatedCars!!.updateCar(6, Car("Updating Car", "Sport", 80_000.00, 21, 2.0, 3, "Manual", false)))
            assertFalse(populatedCars!!.updateCar(-1, Car("Updating Car", "Sport", 80_000.00, 21, 2.0, 3, "Manual", false)))
            assertFalse(emptyCars!!.updateCar(0, Car("Updating Car", "Sport", 80_000.00, 21, 2.0, 3, "Manual", false)))
        }

        @Test
        fun `updating a car that exists returns true and updates`() {
            // check car 5 exists and check the contents
            assertEquals(kiaEV6, populatedCars!!.findCar(4))
            assertEquals("Kia EV6", populatedCars!!.findCar(4)!!.carModel)
            assertEquals("Super", populatedCars!!.findCar(4)!!.carCategory)
            assertEquals(200_000.00, populatedCars!!.findCar(4)!!.carCost)
            assertEquals(22, populatedCars!!.findCar(4)!!.carYear)
            assertEquals(4.0, populatedCars!!.findCar(4)!!.carEngine)
            assertEquals(2, populatedCars!!.findCar(4)!!.numberOfDoors)
            assertEquals("Manual", populatedCars!!.findCar(4)!!.carTransmission)

            // update car 5 with new information and ensure contents updated successfully
            kotlin.test.assertTrue(populatedCars!!.updateCar(4, Car("Updating Car", "Sport", 80_000.00, 21, 2.0, 3, "Manual", false)))
            assertEquals("Updating Car", populatedCars!!.findCar(4)!!.carModel)
            assertEquals("Sport", populatedCars!!.findCar(4)!!.carCategory)
            assertEquals(80_000.00, populatedCars!!.findCar(4)!!.carCost)
            assertEquals(21, populatedCars!!.findCar(4)!!.carYear)
            assertEquals(2.0, populatedCars!!.findCar(4)!!.carEngine)
            assertEquals(3, populatedCars!!.findCar(4)!!.numberOfDoors)
            assertEquals("Manual", populatedCars!!.findCar(4)!!.carTransmission)
        }
    }

}