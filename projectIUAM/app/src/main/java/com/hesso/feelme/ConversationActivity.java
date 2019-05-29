package com.hesso.feelme;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConversationActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = ConversationActivity.class.getName();

    private Gson gson = GsonFactory.getGson();

    private TextView resultTextView;
    private RecyclerView mRecyclerView;
    //private EditText contextEditText;
    private EditText queryEditText;
    private MessageAdapter mAdapter;
    private TextView queryTextView;

    private AIDataService aiDataService;
    private LinearLayoutManager mLayoutManager;

    private String botName;
    private String userPseudo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        mRecyclerView = (RecyclerView) findViewById(R.id.messageList);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        String messageDefaut = getResources().getString(R.string.defaultMessage);

        SharedPreferences sharedPref = getSharedPreferences("feelMeData", MODE_PRIVATE);
        String stockedUserName = sharedPref.getString("username", "User");
        String nomAmiConversation = sharedPref.getString("nomDeBot", "Bot");

        if (stockedUserName != null) {
//            Toast.makeText(this, stockedUserName, Toast.LENGTH_LONG).show();
            userPseudo = stockedUserName;
            botName = nomAmiConversation;
        }else {
            Toast.makeText(this,"Edit you information",Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(getBaseContext(), UserActivity.class);
            startActivity(myIntent);
        }

        userPseudo = "Miha";
        final ArrayList messages = new ArrayList<Message>();
        // Create the initial data list
        Message msg0 = new Message(
                botName,
                messageDefaut,
                Calendar.getInstance().getTimeInMillis());
        messages.add(msg0);

        mAdapter = new MessageAdapter(getApplicationContext(),messages);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addMessage(msg0);

        resultTextView = (TextView) findViewById(R.id.txtBotMessage);
        //contextEditText = (EditText) findViewById(R.id.contextEditText);
        // contextEditText.setVisibility(View.INVISIBLE);
        queryEditText = (EditText) findViewById(R.id.txtMessage);
        queryTextView = (TextView) findViewById(R.id.txtMyMessage);

        String connectionState = getConnectivityStatusString(this);

         // Check connexion status
        if(connectionState == "No internet is available") {
            Toast.makeText(this, R.string.noConnexion, Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(myIntent);
        }else Toast.makeText(this, connectionState, Toast.LENGTH_LONG).show();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {



        @Override
        public void onClick(View v) {


            if (R.id.fab==v.getId()) {
                String msg = queryEditText.getText().toString();
                if(!msg.equals("")) {

                    Message chatMessage = new Message(
                            userPseudo,
                            msg,
                            Calendar.getInstance().getTimeInMillis());
                    //Add themessage to the list
                   // messages.add(chatMessage);
                    mAdapter.addMessage(chatMessage);
                    int newMsgPosition = messages.size() - 1;

                    // Notify recycler view insert one new data.
                    mAdapter.notifyItemInserted(newMsgPosition);


                    // Scroll RecyclerView to the last message.
                    mRecyclerView.scrollToPosition(newMsgPosition);

                    sendRequest();
                    clearEditText();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Message should not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        }

        });

       Spinner spinner = (Spinner) findViewById(R.id.selectLanguageSpinner);
        final ArrayAdapter<LanguageConfig> languagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Config.languages);
        spinner.setAdapter(languagesAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void initService(final LanguageConfig selectedLanguage) {
        final AIConfiguration.SupportedLanguages lang = AIConfiguration.SupportedLanguages.fromLanguageTag(selectedLanguage.getLanguageCode());
        final AIConfiguration config = new AIConfiguration(selectedLanguage.getAccessToken(),
                lang,
                AIConfiguration.RecognitionEngine.System);


        aiDataService = new AIDataService(this, config);
    }


    private void clearEditText() {
        queryEditText.setText("");
    }

    /*
     * AIRequest should have query OR event
     */
    private void sendRequest() {

        final String queryString = String.valueOf(queryEditText.getText());

//        Toast.makeText(getApplicationContext(), queryString, Toast.LENGTH_SHORT).show();

// final String contextString = String.valueOf(contextEditText.getText());

        if (TextUtils.isEmpty(queryString) ) {
            onError(new AIError(getString(R.string.non_empty_query)));
            return;
        }

        final AsyncTask<String, Void, AIResponse> task = new AsyncTask<String, Void, AIResponse>() {

            private AIError aiError;

            @Override
            protected AIResponse doInBackground(final String... params) {
                final  AIRequest request = new AIRequest();
                String query = params[0];
                //String event = params[1];

                if (!TextUtils.isEmpty(query))
                    request.setQuery(query);

/*final String contextString = params[2];
        RequestExtras requestExtras = null;
        if (!TextUtils.isEmpty(contextString)) {
final List<AIContext> contexts = Collections.singletonList(new AIContext(contextString));
        requestExtras = new RequestExtras(contexts, null);
        }*/

                try {
                    return aiDataService.request(request);
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

// this is example how to get different parts of result object
                final Status status = response.getStatus();
                Log.i(TAG, "Status code: " + status.getCode());
                Log.i(TAG, "Status type: " + status.getErrorType());

                final Result result = response.getResult();
                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());

                Log.i(TAG, "Action: " + result.getAction());

                final String speech = result.getFulfillment().getSpeech();
                Log.i(TAG, "Speech: " + speech);
                //TTS.speak(speech);

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

                Message chatMessage = new Message(
                        botName,
                        speech,
                        Calendar.getInstance().getTimeInMillis());
                //Add themessage to the list
                mAdapter.addMessage(chatMessage);
                int newMsgPosition = mAdapter.getItemCount() - 1;

                // Notify recycler view insert one new data.
                mAdapter.notifyItemInserted(newMsgPosition);

                // Scroll RecyclerView to the last message.
                mRecyclerView.scrollToPosition(newMsgPosition);


            }

        });
    }


    private void onError(final AIError error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                resultTextView.setText(error.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_aibutton_sample, menu);
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
        final LanguageConfig selectedLanguage = (LanguageConfig) parent.getItemAtPosition(position);
        initService(selectedLanguage);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            return status;
        }
        return status;
    }

}
