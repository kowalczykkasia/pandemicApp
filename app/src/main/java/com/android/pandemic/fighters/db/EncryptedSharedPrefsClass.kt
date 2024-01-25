package com.android.pandemic.fighters.db

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.SecureRandom

class EncryptedSharedPrefsClass(context: Context) {

    private var spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private val masterKey = MasterKey
        .Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private val encryptedSharedPrefs = EncryptedSharedPreferences.create(
        context,
        ENCRYPTED_PREFERENCES_KEY,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getDBEncryptionPhrase(): String {
        val encryptionCode = encryptedSharedPrefs.getString(ENCRYPTION_CODE, null)
        encryptionCode?.let {
            return it
        } ?: kotlin.run {
            createEncryptionKey()
            return encryptedSharedPrefs.getString(ENCRYPTION_CODE, null)!!
        }
    }

    private fun createEncryptionKey() {
        val random = SecureRandom().nextInt(100).toString()
        encryptedSharedPrefs.edit().putString(ENCRYPTION_CODE, random).apply()
    }

    fun getSharedPreferences() = encryptedSharedPrefs
}