package com.hesso.feelme

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class PreferenceActivity : AppCompatActivity() {

    lateinit var pref1 : Spinner
    lateinit var pref2 : Spinner
    lateinit var pref3 : Spinner
    lateinit var pref4 : Spinner
    lateinit var pref5 : Spinner
    lateinit var pref6 : Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val sharedPref = getSharedPreferences("feelMeData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        pref1 = findViewById(R.id.pref1) as Spinner
        pref2 = findViewById(R.id.pref2) as Spinner
        pref3 = findViewById(R.id.pref3) as Spinner
        pref4 = findViewById(R.id.pref4) as Spinner
        pref5 = findViewById(R.id.pref5) as Spinner
        pref6 = findViewById(R.id.pref6) as Spinner
        val cancelPref = findViewById(R.id.cancelPref) as Button

        var defpref1 = sharedPref.getString("pref1", "Select")
        var defpref2= sharedPref.getString("pref2", "Select")
        var defpref3 = sharedPref.getString("pref3", "Select")
        var defpref4 = sharedPref.getString("pref4", "Select")
        var defpref5 = sharedPref.getString("pref5", "Select")
        var defpref6 = sharedPref.getString("pref6", "Select")

        val listPref1 = arrayOf(defpref1,"Soft","Walking","Sleeping","Singing","Reading","Watching TV","Go to beach")
        val listPref2 = arrayOf(defpref2,"Hard outside","Running","Playing Basket","Playing Foot","Playing Hockey","Swimming")
        val listPref3 = arrayOf(defpref3,"Hard inside","Walking","Sleeping","Singing")
        val listPref4 = arrayOf(defpref4,"Mental","Playing game","Reading")
        val listPref5 = arrayOf(defpref5,"Old","None if you are not old","listen to music","reading","walking")
        val listPref6 = arrayOf(defpref6,"At work","Take a break","Drink water","Drink coffee")



        pref1.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref1)
        pref2.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref2)
        pref3.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref3)
        pref4.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref4)
        pref5.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref5)
        pref6.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref6)

        pref1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref1 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spref1 = listPref1.get(position)
                editor.putString("pref1",spref1)
                editor.apply()
            }
        }

        pref2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref2 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spref2 = listPref2.get(position)
                editor.putString("pref2",spref2)
                editor.apply()
            }
        }

        pref3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref3 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spref3 = listPref3.get(position)
                editor.putString("pref3",spref3)
                editor.apply()
            }
        }

        pref4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref4 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                spinresult.text = listPref4.get(position)
                spref4 = listPref4.get(position)
                editor.putString("pref4",spref4)
                editor.apply()
            }
        }

        pref5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref5 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spref5 = listPref5.get(position)
                editor.putString("pref5",spref5)
                editor.apply()
            }
        }

        pref6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref6 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spref6 = listPref6.get(position)
                editor.putString("pref6",spref6)
                editor.apply()
            }
        }

        cancelPref.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

