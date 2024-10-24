package com.tcf.encryptdecrypt

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import android.util.Base64
import java.security.SecureRandom


object EncryptionUtil
{

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    // Generate a new AES key
    fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance(ALGORITHM)
        keyGen.init(256) // You can use 128 or 192 bits as well
        return keyGen.generateKey()
    }

    fun encrypt(plainText: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

        // Generate a random IV (Initialization Vector)
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivParams = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())

        // Combine IV and encrypted bytes and encode in Base64
        val combined = iv + encryptedBytes
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }


    // Decrypt the given encrypted text
    fun decrypt(encryptedText: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

        // Decode Base64 to get the combined IV and encrypted bytes
        val combined = Base64.decode(encryptedText, Base64.DEFAULT)

        // Extract IV and encrypted bytes
        val iv = combined.copyOfRange(0, 16)
        val encryptedBytes = combined.copyOfRange(16, combined.size)

        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)

        // Decrypt the bytes
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

}

