package com.tcf.encryptdecrypt

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import android.util.Base64
import java.security.SecureRandom

object EncryptionUtil {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance(ALGORITHM)
        keyGen.init(256) // You can use 128 or 192 bits as well
        return keyGen.generateKey()
    }

    fun encrypt(plainText: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = ByteArray(16) // AES block size
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(iv)
        val ivParams = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())

        val combined = ByteArray(iv.size + encryptedBytes.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encryptedBytes, 0, combined, iv.size, encryptedBytes.size)

        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String, secretKey: SecretKey): String {
        val combined = Base64.decode(encryptedText, Base64.DEFAULT)

        val iv = combined.copyOfRange(0, 16) // Extract IV
        val encryptedBytes = combined.copyOfRange(16, combined.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

}
