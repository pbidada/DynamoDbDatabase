package com.zybooks.helloworld;

import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;
import java.util.jar.Attributes;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;

public class DatabaseAccess{
    private static final String COGNITO_POOL_ID = "us-east-1:43a009ff-e4ec-422c-a9ed-dd8fb281c198";
    private static final Regions MY_REGION =Regions.US_EAST_1;
    //public Table dbTable;
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;
    private Context context;
    //private final String DYNAMODB_TABLE = "Signup";
    private final String DYNAMODB_TABLE="test_database1";
    CognitoCachingCredentialsProvider credentialsProvider;

    private static volatile DatabaseAccess instance;
    private DatabaseAccess (Context context) {
        this.context =context;
        credentialsProvider = new CognitoCachingCredentialsProvider (context, COGNITO_POOL_ID, MY_REGION);
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.US_EAST_1));
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);

    }
    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public Document getItem (String user_id){
        Document result = dbTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(user_id));
        return result;
    }

    //fetch all the items from database
    public List<Document> getAllItems() {

        return dbTable.query(new Primitive("email")).getAllResults();

    }
//    public void putName(String s) {
//
//        dbTable.putItem(s);
//
//    }

    //create the items in Dynamodb database
    public void create(Document document) {
     //   Document memo;
        if(!document.containsKey("email")){
            document.put("email",credentialsProvider.getCachedIdentityId());
        }
        dbTable.putItem(document);
    }
}
