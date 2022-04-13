package utils

object TransmissionUtility {

    @JvmStatic
    var transmission = listOf("manual", "automatic")

    @JvmStatic
    fun isValidTransmission(transmissions: String): Boolean {
        return transmission.contains(transmissions.lowercase())
    }
}