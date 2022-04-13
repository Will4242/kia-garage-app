package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Utilities.isValidNoDoors
import utils.Utilities.isValidYear

class UtilitiesTest {

    @Nested
    inner class NoDoorsValid {

        @Test
        fun isValidNoDoorsTrueWhenNoDoorsExists() {
            Assertions.assertTrue(isValidNoDoors(2))
            Assertions.assertTrue(isValidNoDoors(3))
            Assertions.assertTrue(isValidNoDoors(4))
            Assertions.assertTrue(isValidNoDoors(5))
        }

        @Test
        fun isValidNoDoorsFalseWhenNoDoorsDoesNotExist() {
            Assertions.assertFalse(isValidNoDoors(1))
            Assertions.assertFalse(isValidNoDoors(-2))
            Assertions.assertFalse(isValidNoDoors(6))
        }
    }

    @Nested
    inner class YearValid {

        @Test
        fun isValidYearTrueWhenYearExists() {
            Assertions.assertTrue(isValidYear(1))
            Assertions.assertTrue(isValidYear(12))
            Assertions.assertTrue(isValidYear(80))
            Assertions.assertTrue(isValidYear(99))
        }

        @Test
        fun isValidYearFalseWhenYearDoesNotExist() {
            Assertions.assertFalse(isValidYear(2011))
            Assertions.assertFalse(isValidYear(-1))
            Assertions.assertFalse(isValidYear(100))
        }
    }
}