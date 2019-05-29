package com.hesso.feelme

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast

class CCActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cc)

        val sharedPref = getSharedPreferences("feelMeData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val conditionalSwitch = findViewById(R.id.conditionSwitch) as Switch

        val currentSwitchSt: String =  getValueString("confid").toString()
        if(currentSwitchSt=="OK"){
            conditionalSwitch.setChecked(true)
        }else
            conditionalSwitch.setChecked(false)

        conditionalSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                Toast.makeText(this,"Condition accepte", Context.MODE_PRIVATE).show()
                editor.putString("confid","OK")
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // The switch is disabled
                Toast.makeText(this,"Condition refuse", Context.MODE_PRIVATE).show()
                editor.putString("confid","NOK")
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun getValueString(KEY_NAME: String): String? {
        val sharedPref = getSharedPreferences("feelMeData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val ret : String? = sharedPref.getString(KEY_NAME, null)
        return ret
    }
}
