package com.hesso.feelme

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast

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
        //pref6 = findViewById(R.id.pref6) as Spinner

        val cancelPref = findViewById(R.id.cancelPref) as Button

        var defpref1 = sharedPref.getString("pref1", "Select")
        var defpref2= sharedPref.getString("pref2", "Select")
        var defpref3 = sharedPref.getString("pref3", "Select")
        var defpref4 = sharedPref.getString("pref4", "Select")
        var defpref5 = sharedPref.getString("pref5", "Select")
        var defpref6 = sharedPref.getString("pref6", "Select")

        val pref11: String  = getString(R.string.pref11)
        val pref12: String  = getString(R.string.pref12)
        val pref13: String  = getString(R.string.pref13)
        val pref14: String  = getString(R.string.pref14)
        val pref15: String  = getString(R.string.pref15)
        val pref16: String  = getString(R.string.pref16)

        val pref21: String = getString(R.string.pref21)
        val pref22: String = getString(R.string.pref22)
        val pref23: String = getString(R.string.pref23)
        val pref24: String = getString(R.string.pref24)
        val pref25: String = getString(R.string.pref25)

        val pref31: String  = getString(R.string.pref31)
        val pref32: String  = getString(R.string.pref32)
        val pref33: String  = getString(R.string.pref33)
        val pref34: String  = getString(R.string.pref34)
        val pref35: String  = getString(R.string.pref35)
        val pref36: String  = getString(R.string.pref35)

        val pref41: String  = getString(R.string.pref41)
        val pref42: String  = getString(R.string.pref42)
        val pref43: String  = getString(R.string.pref43)
        val pref44: String  = getString(R.string.pref44)
        val pref45: String  = getString(R.string.pref45)
        val pref46: String  = getString(R.string.pref46)
        val pref47: String  = getString(R.string.pref47)
        val pref48: String  = getString(R.string.pref48)
        val pref49: String  = getString(R.string.pref49)
        val pref40: String  = getString(R.string.pref40)

        val pref51: String  = getString(R.string.pref51)
        val pref52: String  = getString(R.string.pref52)
        val pref53: String  = getString(R.string.pref53)

        val pref61: String  = getString(R.string.pref61)
        val pref62: String  = getString(R.string.pref62)
        val pref63: String  = getString(R.string.pref63)
        val pref64: String  = getString(R.string.pref64)
        val pref65: String  = getString(R.string.pref65)
        /*val pref66: String  = getString(R.string.pref66)
        val pref67: String  = getString(R.string.pref67)
        val pref68: String  = getString(R.string.pref68)
        val pref69: String  = getString(R.string.pref69)
        val pref60: String  = getString(R.string.pref60)
        val pref601: String  = getString(R.string.pref601)*/


        val listPref1 = arrayOf(defpref1,pref11,pref12,pref13,pref14,pref15,pref16)
        val listPref2 = arrayOf<String>(defpref2,pref21,pref22,pref23,pref24,pref25)
        val listPref3 = arrayOf<String>(defpref3,pref31,pref32,pref33,pref34,pref35,pref36)
        val listPref4 = arrayOf<String>(defpref4,pref41,pref42,pref43,pref44,pref45,pref46,pref47,pref48,pref49,pref40)
        val listPref5 = arrayOf<String>(defpref5,pref51,pref52,pref53)
        //val listPref6 = arrayOf<String>(defpref6,pref61,pref62,pref63,pref64,pref65,pref66,pref67,pref68,pref69,pref60,pref601)



        pref1.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listPref1)
        pref2.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref2)
        pref3.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref3)
        pref4.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref4)
        pref5.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref5)
       // pref6.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listPref6)

        pref1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref1 = ""

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@PreferenceActivity,"kkk",Toast.LENGTH_LONG)
                spref1 = listPref1.get(position).toString()
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

       /* pref6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var spref6 = ""
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                spinresult.text = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spref6 = listPref6.get(position)
                editor.putString("pref6",spref6)
                editor.apply()
            }
        }*/

        cancelPref.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

