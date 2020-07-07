package com.zybooks.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

//THis activity was created to use Amazon Cognito
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextUsername = findViewById(R.id.editText);
        final EditText editTextPassword = findViewById(R.id.passwordEditText);

        final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                    Log.i("Cognito", "Login Successfull");
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {

                    Log.i("Cognito", "in getAuthenticationDetails.....");

                AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, String.valueOf(editTextPassword.getText()), null);

                authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {
                    Log.i("COgnito", "in AuthenticationChallenge....");
            }

            @Override
            public void onFailure(Exception exception) {
                    Log.i("Cognito", "Login Failed " +exception.getLocalizedMessage());
            }
        };

        Button buttonLogin = findViewById(R.id.signInBtnInFrag);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CognitoSettings cognitoSettings = new CognitoSettings(LoginActivity.this);
                CognitoUser thisUser = cognitoSettings.getUserPool().getUser(String.valueOf(editTextUsername.getText()));

                Log.i("Cognito", "in button clicked");

                thisUser.getSessionInBackground(authenticationHandler);
            }
        });
    }
}