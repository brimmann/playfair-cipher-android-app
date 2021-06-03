package com.pentechnologies.playfair.cryptography

class Encrypto(plainText: String, key: String): Crypto(key) {

    val plainText = refinePlainText(plainText)

    fun encrypt(): String {
        val matrix = formKeyMatrix()
        var cipherText = ""
        val plainTextPairs = formPairs(plainText)
        var encryptedLocation = emptyList<Int>()

        for(pair in plainTextPairs){
            val location = find(pair, matrix)
            encryptedLocation = if(location[1] == location[3]) listOf((location[0] + 1)%5, location[1], (location[2] + 1)%5, location[3])
            else if(location[0] == location[2]) listOf(location[0], (location[1] + 1)%5, location[2], (location[3] + 1)%5)
            else listOf(location[0], location[3], location[2], location[1])

            cipherText += matrix[encryptedLocation[0]][encryptedLocation[1]]
            cipherText += matrix[encryptedLocation[2]][encryptedLocation[3]]
        }

        return cipherText
    }


    private fun refinePlainText(plainText: String): String{
        plainText.replace("j", "i");
        return plainText.toLowerCase()
    }
}