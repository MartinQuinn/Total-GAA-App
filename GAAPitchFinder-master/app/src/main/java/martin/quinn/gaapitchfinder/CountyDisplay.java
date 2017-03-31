package martin.quinn.gaapitchfinder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CountyDisplay extends AddClub {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county_display);

        final ListView listClubs = (ListView) findViewById(R.id.listView_counties);

        // This gets all the counties from the database.
        try {
            db.open();
            Cursor result = db.getAllCounties();
            MyCursorCountyAdapter cursorCountyAdapter = new MyCursorCountyAdapter(CountyDisplay.this, result);
            listClubs.setAdapter(cursorCountyAdapter);
            db.close();
        } catch (Exception ex) {
            Context context = getApplicationContext();
            CharSequence text = "Error opening database";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        listClubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {

                //intent to single club with details of all aspects of the club

                Cursor mycursor = (Cursor) av.getItemAtPosition(position);
                int countyQuery = mycursor.getInt(0);
                Log.d(String.valueOf(mycursor), "onItemClick ");
                Intent ClubInfoDisplay = new Intent(CountyDisplay.this, AllClubDisplay.class);
                ClubInfoDisplay.putExtra("countyID", countyQuery);
                startActivity(ClubInfoDisplay);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_club_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }


}