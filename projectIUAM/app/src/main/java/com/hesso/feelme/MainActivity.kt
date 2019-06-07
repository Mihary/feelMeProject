package com.hesso.feelme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val confidState: String = getValueString("confid").toString()


        startFeel.setOnClickListener(){
            val sharedPref = getSharedPreferences("feelMeData", Context.MODE_PRIVATE)
            //notification preferences
            val switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_NOTIF_SWITCH,true)
            Toast.makeText(this, switchPref.toString(),
                Toast.LENGTH_SHORT).show()
            val langPref = sharedPref.getString(SettingsActivity.KEY_PREF_LANGUE,"FR")

            val editor = sharedPref.edit()
            if(confidState=="OK"){
//                Toast.makeText(this,confidState, Context.MODE_PRIVATE).show()
                val intent = Intent(this, ConversationActivity::class.java)
                startActivity(intent);
            }else {
                Toast.makeText(this,R.string.acceptCondition, Context.MODE_PRIVATE).show()
                val intent = Intent(this, CCActivity::class.java)
                startActivity(intent);
            }
        }

        android.support.v7.preference.PreferenceManager
            .setDefaultValues(this, R.xml.preferences, false);

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun getValueString(KEY_NAME: String): String? {
        val sharedPref = getSharedPreferences("feelMeData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val ret : String? = sharedPref.getString(KEY_NAME, null)
        return ret
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_user -> {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
//                Toast.makeText(this, "Compte utilisateur", Toast.LENGTH_LONG).show()
            true
        }

            R.id.action_settings -> {
                val intent = Intent(this, ParamsActivity::class.java)
                startActivity(intent)
//                Toast.makeText(this, "parametres", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_confidentiality -> {
                val intent = Intent(this, CCActivity::class.java)
                startActivity(intent)
//                Toast.makeText(this, "parametres", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
//                Toast.makeText(this, "parametres", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
