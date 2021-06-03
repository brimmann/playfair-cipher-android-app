package com.pentechnologies.playfair.cryptography

class Decrypto(cipherText: String, key: String): Crypto(key) {
    val cipherText = cipherText.toLowerCase()

    fun decrypt(): String{
        val matrix = formKeyMatrix()
        var plainText = ""
        val cipherTextPairs = formPairs(cipherText)
        var decryptedLocation = emptyList<Int>()
        var i1 = 0
        var j1 = 0
        var i2 = 0
        var j2 = 0

        for(pair in cipherTextPairs){
            val location = find(pair, matrix)

            decryptedLocation = if(location[1] == location[3]){
                i1 = (location[0] - 1)%5
                i2 = (location[2] - 1)%5
                if(i1 < 0){
                    i1 = 4
                }
                if(i2 < 0){
                    i2 = 4
                }
                listOf(i1 , location[1], i2 , location[3])
            }
            else if(location[0] == location[2]){
                i1 = (location[1] - 1)%5
                i2 = (location[3] - 1)%5
                if(i1 < 0){
                    i1 = 4
                }
                if(i2 < 0){
                    i2 = 4
                }
                listOf(location[0],j1 , location[2], j2)
            }
            else{
                listOf(location[0], location[3], location[2], location[1])
            }

            plainText += matrix[decryptedLocation[0]][decryptedLocation[1]]
            plainText += matrix[decryptedLocation[2]][decryptedLocation[3]]

        }

        return  plainText

    }
}