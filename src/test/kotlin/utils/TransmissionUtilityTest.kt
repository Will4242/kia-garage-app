package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TransmissionUtilityTest {

    @Nested
    inner class TransmissionValid {
        @Test
        fun transmissionReturnsFullTransmissionSet() {
            Assertions.assertEquals(2, TransmissionUtility.transmission.size)
            Assertions.assertTrue(TransmissionUtility.transmission.contains("manual"))
            Assertions.assertTrue(TransmissionUtility.transmission.contains("automatic"))
            Assertions.assertFalse(TransmissionUtility.transmission.contains(""))
        }

        @Test
        fun isValidTransmissionTrueWhenTransmissionExists() {
            Assertions.assertTrue(TransmissionUtility.isValidTransmission("ManuaL"))
            Assertions.assertTrue(TransmissionUtility.isValidTransmission("auTomAtIc"))
        }

        @Test
        fun isValidTransmissionFalseWhenTransmissionDoesNotExist() {
            Assertions.assertFalse(TransmissionUtility.isValidTransmission("Manuals"))
            Assertions.assertFalse(TransmissionUtility.isValidTransmission("auto"))
            Assertions.assertFalse(TransmissionUtility.isValidTransmission(""))
        }
    }
}
