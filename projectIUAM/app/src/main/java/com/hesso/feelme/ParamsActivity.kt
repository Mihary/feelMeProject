package com.hesso.feelme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ParamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_params)

        val setImage = findViewById(R.id.setImage) as ImageView
        //val feelMeImage = findViewById(R.id.feelMeImage) as ImageView
        val notifImage = findViewById(R.id.notifImage) as ImageView
        //val userImage = findViewById(R.id.userImage) as ImageView

        setImage.setOnClickListener {
            val intent = Intent(this, PreferenceActivity::class.java)
            startActivity(intent);

        }
        /*feelMeImage.setOnClickListener {
            val intent = Intent(this, ConversationActivity::class.java)
            startActivity(intent);
        }*/
        notifImage.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent);

        }
        /*userImage.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent);

        }*/


    }
}

