package martin.quinn.gaapitchfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.SQLException;

public class AddClub extends AppCompatActivity {

    // passes the context
    DBManager db = new DBManager(this);
    EditText clubName;
    EditText countyName;
    EditText colours;
    EditText clubDesc;
    EditText clubLocation;
    EditText clubWebSite;
    Button setButton;
    ClubsClass clubsClass;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);

        // This is the edit text fields that the user inserts.

        setButton = (Button) findViewById(R.id.button_submit);
        Button getButton = (Button) findViewById(R.id.button_retrieve);

        clubName = (EditText) findViewById(R.id.editText_clubName);
        countyName = (EditText) findViewById(R.id.editText_countyName);
        colours = (EditText) findViewById(R.id.editText_colours);
        clubDesc = (EditText) findViewById(R.id.editText_clubDesc);
        clubLocation = (EditText) findViewById(R.id.editText_location);
        clubWebSite = (EditText) findViewById(R.id.editText_webSite);

        Intent intent = getIntent();
        if(intent != null) {
            int value = intent.getIntExtra("clubID", 0);

            Log.w("TEST",""+value);
            try {
                db.open();
                clubsClass = db.searchClubID(value);
                if(clubsClass != null) {

                    clubName.setText(clubsClass.getName());
                    countyName.setText(clubsClass.getCountyName());
                    colours.setText(clubsClass.getColors());
                    clubDesc.setText(clubsClass.getDecription());
                    clubLocation.setText(clubsClass.getLocaiton());
                    clubWebSite.setText(clubsClass.getWebsite());

                    setTitle("Update Club");
                    setButton.setText("Update");

                    db.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the button is clicke


                try {
                    addAlertMessage();
                    db.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Context context = getApplicationContext();
                    CharSequence text = "Error opening database";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the button is clicked
                Intent displayAllClubs = new Intent(AddClub.this, AllClubDisplay.class);
                startActivity(displayAllClubs);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddClub Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://martin.quinn.gaapitchfinder/http/host/path")
        );
        AppIndex.AppIndexApi.start(mClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddClub Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://martin.quinn.gaapitchfinder/http/host/path")
        );
        AppIndex.AppIndexApi.end(mClient, viewAction);
        mClient.disconnect();
    }


    // This is also a Dialog to ask the user whether they want to add a club

    public void addAlertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        try {
                            db.open();

                            //This is for the update it joins the two tables together to find
                            //the correct County and club details
                            int county_id = db.insertCounty(countyName.getText().toString());
                            if(setButton.getText().equals("Update")) {
                                db.updateClub(
                                        clubsClass.getId(),
                                        county_id,
                                        clubName.getText().toString(),
                                        colours.getText().toString(),
                                        clubDesc.getText().toString(),
                                        clubLocation.getText().toString(),
                                        clubWebSite.getText().toString()
                                );

                            } else {
                                db.insertClub(
                                        county_id,
                                        clubName.getText().toString(),
                                        colours.getText().toString(),
                                        clubDesc.getText().toString(),
                                        clubLocation.getText().toString(),
                                        clubWebSite.getText().toString()
                                );
                            }
                            db.close();
                            Intent displayAllClubs = new Intent(AddClub.this , AllClubDisplay.class);
                            startActivity(displayAllClubs);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to add this Club?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
