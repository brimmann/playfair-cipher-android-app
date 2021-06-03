package com.pentechnologies.playfair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pentechnologies.playfair.cryptography.Decrypto
import com.pentechnologies.playfair.cryptography.Encrypto

class MainActivity : AppCompatActivity() {

    val model: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    lateinit var switchButton: Button
    lateinit var actionbutton: Button
    lateinit var inputTextField: EditText
    lateinit var outputTextDisplay: TextView
    lateinit var keyTextField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        switchButton = findViewById(R.id.true_switch_button)
        actionbutton = findViewById(R.id.action_button)
        inputTextField = findViewById(R.id.text_editor)
        outputTextDisplay = findViewById(R.id.text_view)
        keyTextField = findViewById(R.id.key_input)

        switchButton.setOnClickListener {
            if(model.statIndicator == 0){
                actionbutton.text = "Decrypt"
                model.statIndicator = 1
            }
            else{
                actionbutton.text = "Encrypt"
                model.statIndicator = 0
            }
        }

        actionbutton.setOnClickListener {
            try {
                if(model.statIndicator == 0) encrypt() else decrypt()
            }catch (e: TooShortOrTooLongKeyException){
                keyTextField.text.clear()
                keyTextField.hint = e.message
                keyTextField.setHintTextColor(ContextCompat.getColor(this, R.color.red))

            }
        }

        if(model.outPutText != null){
            outputTextDisplay.text = model.outPutText
        }

        if(model.statIndicator == 1) actionbutton.text = "Decrypt"


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    private fun encrypt(){
        val key = keyTextField.text.toString()
        val plainText = inputTextField.text.toString()
        model.createEncrypto(plainText, key)
        val text = model.encrypto.encrypt()
        outputTextDisplay.text = text
        model.storeOutput(text)
    }

    private fun decrypt(){
        val key = keyTextField.text.toString()
        val cipherText = inputTextField.text.toString()
        model.createDecrypto(cipherText, key)
        val text = model.decrypto.decrypt()
        outputTextDisplay.text = text
        model.storeOutput(text)
    }

    private fun share(){
        var text = ""
        if(model.statIndicator == 0){
             text = getString(R.string.report, model.encrypto.plainText, model.outPutText, model.encrypto.key)
        }else{
             text = getString(R.string.report, model.decrypto.cipherText, model.outPutText, model.decrypto.key)
        }

        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }.also { intent ->
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_act){
            share()
            return true
        }else{
            return super.onOptionsItemSelected(item)
        }
    }
}