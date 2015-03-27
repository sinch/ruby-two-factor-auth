package com.sinch.twofactorauth;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class VerifyCodeActivity extends ActionBarActivity {

    private String phoneNumber;
    private Boolean verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    public void verifyCodeButtonClick(View v) {
        String code = ((EditText) findViewById(R.id.code)).getText().toString();
        (new VerifyCode()).execute(phoneNumber, code);
    }

    class VerifyCode extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();

            try {
                HttpPost request = new HttpPost("http://your-url.com/verify");
                StringEntity se = new StringEntity("{ \"phone_number\":\""+ params[0] +"\", \"code\":\"" + params[1] + "\"}");
                request.addHeader("Content-Type", "application/json");
                request.setEntity(se);
                HttpResponse response = httpClient.execute(request);
                ResponseHandler<String> handler = new BasicResponseHandler();
                String httpResponse = handler.handleResponse(response);
                JSONObject responseJSON = new JSONObject(httpResponse);
                if (responseJSON.getString("verified").toString().equals("true")) {
                    verified = true;
                } else {
                    verified = false;
                }
            } catch (Exception e) {
                Log.d("Exception", "Request exception:" + e.getMessage());
            } finally {
                httpClient.getConnectionManager().shutdown();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Toast.makeText(getApplicationContext(), "Verified: " + verified, Toast.LENGTH_LONG).show();
        }
    }
}
