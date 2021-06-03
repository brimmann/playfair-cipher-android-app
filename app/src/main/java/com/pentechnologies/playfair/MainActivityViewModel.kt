package com.pentechnologies.playfair

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pentechnologies.playfair.cryptography.Crypto
import com.pentechnologies.playfair.cryptography.Decrypto
import com.pentechnologies.playfair.cryptography.Encrypto
import javax.crypto.Cipher

class MainActivityViewModel : ViewModel() {
    var statIndicator = 0
    lateinit var encrypto: Encrypto
    lateinit var decrypto: Decrypto
    var outPutText: String? = null

    fun createEncrypto(plainText: String, key: String){
        encrypto = Encrypto(plainText, key)
    }

    fun createDecrypto(cipherText: String, key: String){
        decrypto = Decrypto(cipherText, key)
    }

    fun storeOutput(text: String){
        outPutText = text
    }

    fun createReport(context: Context){

    }
}