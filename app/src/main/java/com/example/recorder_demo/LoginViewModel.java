package com.example.recorder_demo;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.http.Tag;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewModel extends ViewModel {
    private static final String LOGIN_URL = "https://cronj.com/birbal-test-api/v1/auth/login";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public interface AuthCallback {
        void onSuccess(String accessToken, String refreshToken);
        void onFailure(String message);
    }

    public void authenticate(User user, AuthCallback callback) {
        new LoginTask(callback).execute(user);
    }

    private class LoginTask extends AsyncTask<User, Void, String[]> {
        private AuthCallback callback;
        private String errorMessage;

        LoginTask(AuthCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String[] doInBackground(User... users) {
            JSONObject jsonuser = new JSONObject();

            User user = users[0];
            OkHttpClient client = new OkHttpClient();
            String[] tokens = new String[2];

            JSONObject json = new JSONObject();
            try {
                json.put("email", user.getUsername());
                json.put("password", user.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder()
                    .url(LOGIN_URL)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseData);
                    //JSONObject jsonObject = new JSONObject(jsonResponse);

                    JSONObject userObject = jsonResponse.getJSONObject("user");
                    JSONObject tokensObject = userObject.getJSONObject("tokens");

                    JSONObject accessObject = tokensObject.getJSONObject("access");
                    //String accessToken = accessObject.getString("token");

                    JSONObject refreshObject = tokensObject.getJSONObject("refresh");
                   // String refreshToken = refreshObject.getString("token");
                  // tokens[0] = jsonResponse.optString("accessToken", null);
                  // tokens[1] = jsonResponse.optString("refreshToken", null);
                    Log.d("User",userObject.toString());
                    Log.d("Access",accessObject.toString());
                    Log.d("Refresh",refreshObject.toString());
                    Log.d("Response",jsonResponse.toString());
                    //Log.d("LoginViewModel", "Access token: " + tokens[0] + ", Refresh token: " + tokens[1]);
                } else {
                    errorMessage = "Invalid username or password";
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                errorMessage = "An error occurred while logging in";
            }
            return tokens;
        }


        @Override
        protected void onPostExecute(String[] tokens) {
            if (tokens[0] != null && tokens[1] != null) {
                callback.onSuccess(tokens[0], tokens[1]);
            } else {
                callback.onFailure(errorMessage);
            }
        }
    }
}