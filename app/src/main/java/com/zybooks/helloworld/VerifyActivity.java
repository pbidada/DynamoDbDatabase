package com.zybooks.helloworld;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

//THis activity was created to use Amazon Cognito
public class VerifyActivity extends AppCompatActivity {
    private static final String TAG = "Cognito";
    private CognitoUserPool userPool;
    @Override
    protected void onCreate(Bundle savedInstaceState) {

        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_verify);

        final EditText editTextCode = findViewById(R.id.verifycode);
        final EditText givenusername = findViewById(R.id.givenusername);

        Button buttonVerify = findViewById(R.id.verifybutton);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmTask().execute(String.valueOf(editTextCode.getText()), String.valueOf(givenusername.getText()));
                login();
            }


        });
    }


    private void login(){
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
    }
    private class ConfirmTask extends AsyncTask<String , Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            final String[] result= new String[1];

            final GenericHandler confirmationCallback = new GenericHandler() {
                @Override
                public void onSuccess() {
                    result[0]= "Succeeded";
                }

                @Override
                public void onFailure(Exception exception) {
                    result[0]="failed: "+exception.getMessage();
                }
            };

            CognitoSettings cognitoSettings= new CognitoSettings(VerifyActivity.this);

            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strings[1]);
            thisUser.confirmSignUp(strings[0], false, confirmationCallback);
            return result[0];
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.i(TAG, "Confirmation Result: "+result);
        }
    }
}
