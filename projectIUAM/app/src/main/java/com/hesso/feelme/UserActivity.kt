package com.hesso.feelme

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast

class UserActivity : AppCompatActivity() {


    lateinit var sex : Spinner
    lateinit var name: EditText
    lateinit var age: EditText
    lateinit var email: EditText
    lateinit var nomDeBot: EditText
    lateinit var userNameTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val sharedPref = getSharedPreferences("feelMeData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        name = findViewById(R.id.name) as EditText
        age = findViewById(R.id.age) as EditText
        email = findViewById(R.id.email) as EditText
        nomDeBot = findViewById(R.id.nomDeBot) as EditText
        sex = findViewById(R.id.sex) as Spinner
        val cancelInfo = findViewById(R.id.cancelInfo) as Button
        val saveInfo = findViewById(R.id.saveInfo) as Button
        val userNameTitle = findViewById(R.id.userNameTitle) as TextView

        var ssex = ""
        var defname = sharedPref.getString("username", "Tape your name")
        var defage = sharedPref.getString("userage", "Tape your age")
        var defemail = sharedPref.getString("useremail", "Tape your email")
        var defsex = sharedPref.getString("usersex", "Select your sex")
        var defnomDeBots = sharedPref.getString("nomDeBot", "Bot")

        userNameTitle.setText(defname)

        val listSex = arrayOf(defsex, "Male","Female")

        name.setText(defname)
        age.setText(defage)
        email.setText(defemail)
        nomDeBot.setText(defnomDeBots)

        sex.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listSex)

        sex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ssex = listSex.get(position)
            }
        }

        cancelInfo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveInfo.setOnClickListener {
            editor.putString("usersex",ssex)
            editor.putString("username", name.text.toString())
            editor.putString("userage", age.text.toString())
            editor.putString("useremail", email.text.toString())
            editor.putString("nomDeBot", nomDeBot.text.toString())
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }

        fun saveInfo() {
            editor.putString("name", name.text.toString())
            editor.putString("age", age.text.toString())
            editor.putString("email", email.text.toString())
            editor.apply()
        }
    }
}
