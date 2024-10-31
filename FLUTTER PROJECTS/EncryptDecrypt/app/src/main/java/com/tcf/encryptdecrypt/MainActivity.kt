package com.tcf.encryptdecrypt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.tcf.encryptdecrypt.databinding.ActivityMainBinding
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
//    var key : String="mysecretkey12345"
    val secretKey = EncryptionUtil.generateKey()
//    var secretKeySpec = SecretKeySpec(key.toByteArray(),"AES")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    private fun getData()
    {

        binding.btDecrypt.setOnClickListener {
            var enValue = binding.etEncrypt.text.toString()
            val encryptedText = EncryptionUtil.encrypt(enValue, secretKey)
            binding.tvEncrypt.text = encryptedText
            println("Encrypted: $encryptedText")
        }

        binding.btDecrypt.setOnClickListener {
            var enValue = binding.etEncrypt.text.toString()
            val decryptedText = EncryptionUtil.decrypt(enValue, secretKey)
            binding.tvEncrypt.text = decryptedText
            println("Encrypted: $decryptedText")
        }

    }

//    private fun encrypt(string: String) : String{
//        var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") //Specifying which mode of AES is to be used
//        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec)// Specifying the mode wither encrypt or decrypt
//        var encryptBytes =cipher.doFinal(string.toByteArray(Charsets.UTF_8))//Converting the string that will be encrypted to byte array
//        return Base64.encodeToString(encryptBytes,Base64.DEFAULT) // returning the encrypted string
//    }
//
//    private fun decrypt(string : String) : String{
//        var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
//        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec)
//        var decryptedBytes = cipher.doFinal(Base64.decode(string,Base64.DEFAULT)) // decoding the entered string
//        return String(decryptedBytes,Charsets.UTF_8) // returning the decrypted string
//    }
}