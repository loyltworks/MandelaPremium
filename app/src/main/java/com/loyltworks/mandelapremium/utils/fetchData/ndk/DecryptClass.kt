package com.loyltworks.mandelapremium.utils.fetchData.ndk

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object DecryptClass {

    // AES Decrypt
    @Throws(Exception::class)
    fun decrypt(encryptedHex: String): String {
        val keyBytes = md5Hash("@1Mandela183*25")
        val initVector = byteArrayOf(
            0x00, 0x01, 0x02, 0x03,
            0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0a, 0x0b,
            0x0c, 0x0d, 0x0e, 0x0f
        )
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val ivSpec = IvParameterSpec(initVector)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
        val decryptedBytes = cipher.doFinal(hexToBytes(encryptedHex))
        return String(decryptedBytes, charset("UTF-8"))
    }

    // MD5 Hash
    @Throws(Exception::class)
    fun md5Hash(input: String): ByteArray {
        val md = MessageDigest.getInstance("MD5")
        return md.digest(input.toByteArray(charset("UTF-8")))
    }

    // Convert hex to bytes
    fun hexToBytes(hex: String): ByteArray {
        val len = hex.length
        val result = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            result[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
        }
        return result
    }
}