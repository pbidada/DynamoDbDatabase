package com.zybooks.helloworld;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

//THis activity was created to use Amazon Cognito
public class CognitoSettings {

    private String userPoolId="us-east-1_UuRDgi8hE";
    private String clientId="5hti16q50k0mk28t1k0pu76kl4";
    private String clientSecret="1i3rf4r6eebf4imcjag0kdb8fbbprt16rmn0ce4mqpcdiucgl7b5";
    private Regions cognitoRegion= Regions.US_EAST_1;

    private Context context;

    public CognitoSettings(Context context){
        this.context=context;
    }

    public String getUserPoolId(){
        return userPoolId;
    }

    public String getClientId(){
        return clientId;
    }

    public String getClientSecret(){
        return clientSecret;
    }

    public Regions getCognitoRegion(){
        return cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
    }
}
