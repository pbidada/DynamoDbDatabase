package com.zybooks.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.view.ViewGroup;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    private static final String COGNITO_POOL_ID = "us-east-1:43a009ff-e4ec-422c-a9ed-dd8fb281c198";
//    private static final Regions MY_REGION =Regions.US_EAST_1;
    //public Table dbTable;
//    private AmazonDynamoDBClient dbClient;
//    private Table dbTable;
//    private Context context;
//    private final String DYNAMODB_TABLE = "Signup";
//    CognitoCachingCredentialsProvider credentialsProvider;
//    private static final String TABLE_NAME = "Signup" ;
    private EditText editTextUsername, editTextName, editTextAddress, editTextPhone, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button signUpButton;
    //  private CheckBox termsCheckBox;
    View view;
    private String TAG = "DynamoDb_Demo";
    DatabaseAccess databaseAccess;
    private static final String COGNITO_POOL_ID = "us-east-1:43a009ff-e4ec-422c-a9ed-dd8fb281c198";
    private static final Regions MY_REGION = Regions.US_EAST_1;
    // public Table dbTable;
//    private AmazonDynamoDBClient dbClient;
//    public Table dbTable;
//    private Context context;
//    private final String DYNAMODB_TABLE = "Signup";
//    CognitoCachingCredentialsProvider credentialsProvider;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        //  termsCheckBox = view.findViewById(R.id.termsCheckBox);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //view= inflater.inflate(R.layout.activity_main,fa)
        //  editTextName = findViewById(R.id.fullNameEditText);
      //  editTextUsername= findViewById(R.id.username);
        editTextName = findViewById(R.id.fullNameEditText);
        editTextAddress = findViewById(R.id.addressEditText);
        editTextPhone = findViewById(R.id.phoneEditText);
        editTextEmail = findViewById(R.id.emailEditText);
        editTextPassword = findViewById(R.id.passwordEditTextCreateAccount);
        editTextConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        final String name = editTextName.getText().toString().trim();

        signUpButton = findViewById(R.id.signUpBtnSignUpFrag);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Getting all devices...");
                //             textView.setText("Getting all devices...");

                GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask();
                getAllDevicesTask.execute();
//                PostAllItemsAsyncTask postAllItemsAsyncTask = new PostAllItemsAsyncTask();
//                postAllItemsAsyncTask.doInBackground(editTextName.getText().toString(),editTextAddress.getText().toString());

              //  registerUser();

                Document newMemo = new Document();
                newMemo.put("Name", editTextName.getText().toString());
                newMemo.put("address",editTextAddress.getText().toString());
                newMemo.put("email",editTextEmail.getText().toString());
                newMemo.put("password",editTextPassword.getText().toString());
                newMemo.put("phoneno",editTextPhone.getText().toString());
                CreateItemAsyncTask task = new CreateItemAsyncTask();
                task.execute(newMemo);
            }

        });
    }

    private class GetAllItemsAsyncTask extends AsyncTask<Void, Void, List<Document>> {
        @Override
        protected List<Document> doInBackground(Void... params) {
            String Name= editTextName.getText().toString().trim();
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);
            Log.d(TAG, "databases content"+databaseAccess.getAllItems().toString());
            return databaseAccess.getAllItems();
        }

        }

    private class CreateItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);
            databaseAccess.create(documents[0]);
            return null;
        }


    }


//    private class PostAllItemsAsyncTask extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            System.out.println(strings[0]);
//            System.out.println(strings[1]);
//
//            return null;
//        }
//
    //registerUser() method contains code for Amazon Cognito
//    private void registerUser() {
//        final String username= editTextUsername.getText().toString().trim();
//        final String name = editTextName.getText().toString().trim();
//        final String address = editTextAddress.getText().toString().trim();
//        final String phone = editTextPhone.getText().toString().trim();
//        final String email = editTextEmail.getText().toString().trim();
//        final String password = editTextPassword.getText().toString().trim();
//        final String confirmPassword= editTextConfirmPassword.getText().toString().trim();
//        // String confirmPassword = editTextConfirmPassword.getText().toString().trim();
//
//        if(username.isEmpty()){
//            editTextUsername.setError("Username Required");
//            editTextUsername.requestFocus();
//            return;
//        }
//
//        if (name.isEmpty()) {
//            editTextName.setError("Name required");
//            editTextName.requestFocus();
//            return;
//        }
//
//        if (address.isEmpty()) {
//            editTextAddress.setError("Address required");
//            editTextAddress.requestFocus();
//            return;
//        }
//
//        if (phone.isEmpty()) {
//            editTextPhone.setError("Phone number required");
//            editTextPhone.requestFocus();
//            return;
//        }
//
////        if (phone.length() != 10) {
////            editTextPhone.setError("Enter a valid phone number");
////            editTextPhone.requestFocus();
////            return;
////        }
//
//        if (email.isEmpty()) {
//            editTextEmail.setError("Email required");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (password.isEmpty()) {
//            editTextPassword.setError("Password required");
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            editTextPassword.setError("Passwords do not match");
//            editTextConfirmPassword.requestFocus();
//            return;
//        }
//
//        if (password.length() < 6) {
//            editTextPassword.setError("Password must be at least 6 characters long");
//            editTextPassword.requestFocus();
//            return;
//        }

//
//        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                getApplicationContext(),
//                "us-east-1:8164814f-7cb9-4787-8df3-340372512e52", // Identity pool ID
//                Regions.US_EAST_1 // Region
//        );
//
//            final SignUpHandler signupCallback = new SignUpHandler(){
//
//                @Override
//                public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
//                    Log.i(TAG,"sign up successfull " + signUpConfirmationState);
//                    if(!signUpConfirmationState){
//                        Log.i(TAG,"sign up success....not confirmed. Verification code sent to:" + cognitoUserCodeDeliveryDetails.getDestination());
//                    }
//                    else{
//                        Log.i(TAG,"Sign up success confirmed:");
//                    }
//                }
//
//                @Override
//                public void onFailure(Exception exception) {
//                    Log.i(TAG,"sign up failure" + exception.getLocalizedMessage());
//                }
//            };



//        CognitoSettings cognitoSettings= new CognitoSettings(MainActivity.this);
//
//        final CognitoUserAttributes userAttributes = new CognitoUserAttributes();
//        userAttributes.addAttribute("name",name);
//        userAttributes.addAttribute("address",address);
//        userAttributes.addAttribute("phone_number",phone);
//        userAttributes.addAttribute("email", email);
//
//        cognitoSettings.getUserPool().signUpInBackground(username, password,userAttributes,null,signupCallback);
//        Intent intent = new Intent(this,VerifyActivity.class);
//        startActivity(intent);

    }

