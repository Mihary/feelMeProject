package com.hesso.feelme;

import ai.api.model.*;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIConfiguration.RecognitionEngine;
import ai.api.android.AIDataService;
import ai.api.android.GsonFactory;


public class ConversationActivity<queryString> extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = ConversationActivity.class.getName();
    private Gson gson = GsonFactory.getGson();
    private TextView resultTextView;
    private EditText queryEditText;


    private AIDataService aiDataService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultTextView = (TextView) findViewById(R.id.txtBotMessage);
        queryEditText = (EditText) findViewById(R.id.txtMessage);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void initService(final LanguageConfig selectedLanguage) {
        final AIConfiguration.SupportedLanguages lang = AIConfiguration.SupportedLanguages.fromLanguageTag(selectedLanguage.getLanguageCode());
        final AIConfiguration config = new AIConfiguration(selectedLanguage.getAccessToken(),
                lang,
                RecognitionEngine.System);


        aiDataService = new AIDataService(this, config);
    }


    private void clearEditText() {
        queryEditText.setText("");
    }



    private void sendRequest() {
        final String queryString = String.valueOf(queryEditText.getText());
        if (TextUtils.isEmpty(queryString)){
            onError(new AIError(getString(R.string.non_empty_query)));
        return;
         }


        final AsyncTask<String, Void, AIResponse> task = new AsyncTask<String, Void, AIResponse>() {
            private AIError aiError;

            @Override
            protected AIResponse doInBackground(final String... params) {
                final AIRequest request = new AIRequest();
                String query = params[0];

                if (!TextUtils.isEmpty(query))
                    request.setQuery(query);
                RequestExtras requestExtras = null; // When we get context we should put it here
                try {
                    return aiDataService.request(request, requestExtras);
                } catch (final AIServiceException e) {
                    aiError = new AIError(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(final AIResponse response) {
                if (response != null) {
                    onResult(response);
                } else {
                    onError(aiError);
                }
            }
        };
        task.execute(queryString);
    }


    private void onResult(final AIResponse response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onResult");

                resultTextView.setText(gson.toJson(response));

                Log.i(TAG, "Received success response");

                // this is example how to get different parts of result object
                final Status status = response.getStatus();
                Log.i(TAG, "Status code: " + status.getCode());
                Log.i(TAG, "Status type: " + status.getErrorType());

                final Result result = response.getResult();
                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());

                Log.i(TAG, "Action: " + result.getAction());

                final String speech = result.getFulfillment().getSpeech();
                Log.i(TAG, "Speech: " + speech);
                // TTS.speak(speech);

                final Metadata metadata = result.getMetadata();
                if (metadata != null) {
                    Log.i(TAG, "Intent id: " + metadata.getIntentId());
                    Log.i(TAG, "Intent name: " + metadata.getIntentName());
                }

                final HashMap<String, JsonElement> params = result.getParameters();
                if (params != null && !params.isEmpty()) {
                    Log.i(TAG, "Parameters: ");
                    for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                        Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
                    }
                }
            }

        });
    }

    private void onError(final AIError error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultTextView.setText(error.toString());
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); //TO CHECK
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();

            //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
               // startActivity(AISettingsActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivity(Class<?> cls) {
        final Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //final LanguageConfig selectedLanguage = (LanguageConfig) parent.getItemAtPosition(position);
        //initService(selectedLanguage);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            sendRequest();
        }

    }
}
