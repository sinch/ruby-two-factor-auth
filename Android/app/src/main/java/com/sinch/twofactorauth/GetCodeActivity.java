package com.sinch.twofactorauth;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class GetCodeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);
    }

    public void getCodeButtonClick(View v) {
        String phoneNumber = ((EditText) findViewById(R.id.phoneNumber)).getText().toString();
        (new GetCode()).execute(phoneNumber);
    }

    class GetCode extends AsyncTask<String, Void, Void> {

        private String phoneNumber;

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();

            phoneNumber = params[0];

            try {
                HttpPost request = new HttpPost("http://your-url.com/generate");
                StringEntity se = new StringEntity("{\"phone_number\":\"" + phoneNumber + "\"}");
                request.addHeader("Content-Type", "application/json");
                request.setEntity(se);
                httpClient.execute(request);
            } catch (Exception e) {
                Log.d("GetCode", "Request exception:" + e.getMessage());
            } finally {
                httpClient.getConnectionManager().shutdown();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
        }
    }

}
