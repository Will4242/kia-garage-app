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
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(6, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfNotes())
            assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
        }
    }

    @Nested
    inner class ListNotes {

        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
        }
    }

}