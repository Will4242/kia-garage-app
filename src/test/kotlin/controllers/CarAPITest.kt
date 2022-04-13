package controllers

import models.Car
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class CarAPITest {

    private var kiaRio: Car? = null
    private var kiaK900: Car? = null
    private var kiaStinger: Car? = null
    private var kiaNiro: Car? = null
    private var kiaEV6: Car? = null
    private var populatedCars: CarAPI? = CarAPI(XMLSerializer(File("notes.xml")))
    private var emptyCars: CarAPI? = CarAPI(XMLSerializer(File("notes.xml")))

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

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty cars.XML file.
            val storingCars = CarAPI(XMLSerializer(File("notes.xml")))
            storingCars.store()

            // Loading the empty Cars.xml file into a new object
            val loadedCars = CarAPI(XMLSerializer(File("notes.xml")))
            loadedCars.load()

            // Comparing the source of the cars (storingCars) with the XML loaded cars (loadedCars)
            assertEquals(0, storingCars.numberOfCars())
            assertEquals(0, loadedCars.numberOfCars())
            assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 cars to the cars.XML file.
            val storingCars = CarAPI(XMLSerializer(File("cars.xml")))
            storingCars.add(kiaRio!!)
            storingCars.add(kiaK900!!)
            storingCars.add(kiaStinger!!)
            storingCars.store()

            // Loading cars.xml into a different collection
            val loadedCars = CarAPI(XMLSerializer(File("cars.xml")))
            loadedCars.load()

            // Comparing the source of the cars (storingCars) with the XML loaded cars (loadedCars)
            assertEquals(3, storingCars.numberOfCars())
            assertEquals(3, loadedCars.numberOfCars())
            assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
            assertEquals(storingCars.findCar(0), loadedCars.findCar(0))
            assertEquals(storingCars.findCar(1), loadedCars.findCar(1))
            assertEquals(storingCars.findCar(2), loadedCars.findCar(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty cars.json file.
            val storingCars = CarAPI(JSONSerializer(File("cars.json")))
            storingCars.store()

            // Loading the empty cars.json file into a new object
            val loadedCars = CarAPI(JSONSerializer(File("cars.json")))
            loadedCars.load()

            // Comparing the source of the cars (storingCars) with the json loaded cars (loadedCars)
            assertEquals(0, storingCars.numberOfCars())
            assertEquals(0, loadedCars.numberOfCars())
            assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 cars to the notes.json file.
            val storingCars = CarAPI(JSONSerializer(File("cars.json")))
            storingCars.add(kiaRio!!)
            storingCars.add(kiaK900!!)
            storingCars.add(kiaStinger!!)
            storingCars.store()

            // Loading cars.json into a different collection
            val loadedCars = CarAPI(JSONSerializer(File("cars.json")))
            loadedCars.load()

            // Comparing the source of the cars (storingCars) with the json loaded cars (loadedCars)
            assertEquals(3, storingCars.numberOfCars())
            assertEquals(3, loadedCars.numberOfCars())
            assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
            assertEquals(storingCars.findCar(0), loadedCars.findCar(0))
            assertEquals(storingCars.findCar(1), loadedCars.findCar(1))
            assertEquals(storingCars.findCar(2), loadedCars.findCar(2))
        }
    }

    @Nested
    inner class ListCarsOnSale {

        @Test
        fun `listCarsOnSale returns No Cars on Sale Stored message when no Cars are on Sale but ArrayList is not empty`() {
            assertEquals(0, emptyCars!!.numberOfCarsOnSale())
            val testSellCar = Car("Kia Rio", "Hatchback", 30_000.00, 20, 1.2, 5, "Manual", true)
            emptyCars!!.add(testSellCar)
            kotlin.test.assertTrue(emptyCars!!.listCarsOnSale().lowercase().contains("no cars on sale"))
        }

        @Test
        fun `listCarsOnSale returns Cars when ArrayList has cars on sale stored`() {
            assertEquals(5, populatedCars!!.numberOfCarsOnSale())
            val carsString = populatedCars!!.listCarsOnSale().lowercase()
            kotlin.test.assertTrue(carsString.contains("kia rio"))
            kotlin.test.assertTrue(carsString.contains("kia k900"))
            kotlin.test.assertTrue(carsString.contains("kia stinger"))
            kotlin.test.assertTrue(carsString.contains("kia niro"))
            kotlin.test.assertTrue(carsString.contains("kia ev6"))
        }
    }

    @Nested
    inner class ListSoldCars {

        @Test
        fun `listSoldCars returns No Sold Cars Stored message when no Sold Cars but ArrayList is not empty`() {
            assertEquals(0, emptyCars!!.numberOfSoldCars())
            val testSoldCars = Car("Kia Rio", "Hatchback", 30_000.00, 20, 1.2, 5, "Manual", false)
            emptyCars!!.add(testSoldCars)
            kotlin.test.assertTrue(emptyCars!!.listSoldCars().lowercase().contains("no sold cars"))
        }

        @Test
        fun `listSoldCars returns Cars when ArrayList has Sold Cars stored`() {
            assertEquals(0, populatedCars!!.numberOfSoldCars())
            populatedCars!!.findCar(0)!!.isCarSold = true
            val carsString = populatedCars!!.listSoldCars().lowercase()
            kotlin.test.assertTrue(carsString.contains("kia rio"))
            kotlin.test.assertTrue(!carsString.contains("kia k900"))
            kotlin.test.assertTrue(!carsString.contains("kia stinger"))
            kotlin.test.assertTrue(!carsString.contains("kia niro"))
            kotlin.test.assertTrue(!carsString.contains("kia ev6"))
        }
    }

    @Nested
    inner class NumberOfSoldCars {

        @Test
        fun `numberSoldCars returns No Sold Cars Stored message when no Sold Cars but ArrayList is not empty`() {
            assertEquals(0, emptyCars!!.numberOfSoldCars())
            val testSoldCar = Car("Kia Rio", "Hatchback", 30_000.00, 20, 1.2, 5, "Manual", false)
            emptyCars!!.add(testSoldCar)
            kotlin.test.assertTrue(emptyCars!!.listSoldCars().lowercase().contains("no sold cars"))
        }

        @Test
        fun `numberOfSoldCars returns Cars when ArrayList has sold cars stored`() {
            assertEquals(0, populatedCars!!.numberOfSoldCars())
            populatedCars!!.findCar(0)!!.isCarSold = true
            assertEquals(1, populatedCars!!.numberOfSoldCars())
        }
    }

    @Nested
    inner class NumberOfCarsOnSale {

        @Test
        fun `listCarsOnSale returns No Cars on Sale Stored message when no Cars on Sale but ArrayList is not empty`() {
            assertEquals(0, emptyCars!!.numberOfCarsOnSale())
            val testCarsOnSale = Car("Kia Rio", "Hatchback", 30_000.00, 20, 1.2, 5, "Manual", true)
            emptyCars!!.add(testCarsOnSale)
            kotlin.test.assertTrue(emptyCars!!.listCarsOnSale().lowercase().contains("no cars on sale"))
        }

        @Test
        fun `listCarsOnSale returns Cars when ArrayList has cars on sale stored`() {
            assertEquals(5, populatedCars!!.numberOfCarsOnSale())
            populatedCars!!.findCar(0)!!.isCarSold = true
            assertEquals(4, populatedCars!!.numberOfCarsOnSale())
        }
    }

    @Nested
    inner class SellCar {
        @Test
        fun `selling a car that does not exist returns false`() {
            assertFalse(populatedCars!!.sellCar(6))
            assertFalse(populatedCars!!.sellCar(-1))
            assertFalse(emptyCars!!.sellCar(0))
        }

        @Test
        fun `selling an already sold car returns false`() {
            assertFalse(populatedCars!!.findCar(2)!!.isCarSold)
            kotlin.test.assertTrue(populatedCars!!.sellCar(2))
        }

        @Test
        fun `selling a car on sale that exists returns true and sells`() {
            assertFalse(populatedCars!!.findCar(1)!!.isCarSold)
            kotlin.test.assertTrue(populatedCars!!.sellCar(1))
            kotlin.test.assertTrue(populatedCars!!.findCar(1)!!.isCarSold)
        }
    }

}