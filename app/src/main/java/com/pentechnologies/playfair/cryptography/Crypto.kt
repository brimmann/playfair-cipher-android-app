package com.pentechnologies.playfair.cryptography

import com.pentechnologies.playfair.TooShortOrTooLongKeyException

abstract class Crypto(key: String) {

    val key = key.toLowerCase()

    protected fun formKeyMatrix(): Array<CharArray>{
        val keyLength = key.length
        if(keyLength > 25 || keyLength < 8) throw TooShortOrTooLongKeyException()
        var remainingChars = ""


        for(ch1 in "abcdefghiklmnopqrstuvwxz"){
            var notFound = true
            for (ch2 in key){
                if(ch1 == ch2){
                    notFound = false
                    break
                }
            }
            if(notFound) remainingChars += ch1
        }

        val totalChars = key + remainingChars
        var matrix: Array<CharArray> = Array(5) { CharArray(5) }
        var index = 0;
        for(i in 0..4){
            for(j in 0..4){
                matrix[i][j] = totalChars[index]
                index += 1
            }
        }
        return matrix

    }



    protected fun formPairs(input: String): List<String> {
        var text = input
        if (text.length % 2 != 0) text = text + "z"
        return text.chunked(2)
    }

    protected fun find(pair: String, keyMatrix: Array<CharArray>): List<Int> {
        var i1 = 0
        var j1 = 0
        var i2 = 0
        var j2 = 0

        for(i in 0..4){
            for(j in 0..4){
                if(pair[0] == keyMatrix[i][j]){
                    i1 = i
                    j1 = j
                    break
                }
            }
        }

        for(i in 0..4){
            for(j in 0..4){
                if(pair[1] == keyMatrix[i][j]){
                    i2 = i
                    j2 = j
                }
            }
        }

        return listOf<Int>(i1, j1, i2, j2)
    }
}