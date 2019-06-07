package com.hesso.feelme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {


    public static final String
            KEY_PREF_NOTIF_SWITCH = "notif_switch";
    public static final String
            KEY_PREF_LANGUE = "lang_preference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
