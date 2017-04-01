package martin.quinn.gaapitchfinder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

public class ClubInfo extends AddClub {

    MyCursorInfoAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_info);
        final ListView listInfo = (ListView) findViewById(R.id.listView_Info);

        final Intent intent = getIntent();

        final String clubInfo = intent.getStringExtra("clubInfo");

        // when clicked will send you straight to the location of the Club through google maps.

        listInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long id) {
                //This Searches The Website of a certain club.
                Cursor cursor = (Cursor) cursorAdapter.getItem(position);
                final String website = cursor.getString(cursor.getColumnIndex("location"));
                String webURL = "";
                webURL += website;
                Log.d("location", webURL);
                Uri gmmIntentUri = Uri.parse(webURL);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(mapIntent);
            }
        });


        try {
            db.open();
            Cursor result = db.getClubNames(clubInfo);
            cursorAdapter = new MyCursorInfoAdapter(ClubInfo.this, result);
            listInfo.setAdapter(cursorAdapter);
            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_club_info, menu);
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
