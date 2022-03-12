package com.gourabsingha.preferencedatastoreexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.saveKeyvalue).setOnClickListener {
            lifecycleScope.launch{
                saveKeyValue(findViewById<EditText>(R.id.keyvalueInput).text.toString(),findViewById<EditText>(R.id.insertkeyvalue).text.toString())
               Toast.makeText(applicationContext,"Data Saved",Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.getsavedValue).setOnClickListener {
            lifecycleScope.launch{

                findViewById<TextView>(R.id.showKeyvalueLabel).text =  getKeyValue(findViewById<EditText>(R.id.savekeydata).text.toString())
                Toast.makeText(applicationContext,"Saved Value: ${findViewById<TextView>(R.id.showKeyvalueLabel).text}",Toast.LENGTH_SHORT).show()

            }
        }
    }

    suspend fun saveKeyValue(key:String, value:String){
        val preferencekey = stringPreferencesKey(key)
        dataStore.edit { phonebooks->
            phonebooks[preferencekey] = value
        }
    }

    suspend fun getKeyValue(key:String):String?{
        val preferencekey = stringPreferencesKey(key)
       return dataStore.data.first()[preferencekey]
    }
}