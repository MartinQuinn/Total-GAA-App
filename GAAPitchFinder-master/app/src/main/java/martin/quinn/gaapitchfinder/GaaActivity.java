package martin.quinn.gaapitchfinder;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.SQLException;

public class GaaActivity extends AddClub {

    private Button searchClubButton;
    private Button addClubButton;
    private Button getClubButton;
    private Button countyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaa);

        // Search Club Button to start the search activity
        searchClubButton = (Button)findViewById(R.id.searchclub_button);
        searchClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start getClubActivity

                Intent searchClubActivity = new Intent(GaaActivity.this, SearchActivity.class);
                startActivity(searchClubActivity);

            }
        });

        // Get Club Button to start the Display club activity
        getClubButton = (Button)findViewById(R.id.getclub_button);
        getClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start getClubActivity

                Intent getClubActivity = new Intent(GaaActivity.this , AllClubDisplay.class);
                startActivity(getClubActivity);

            }
        });

        // Search The counties Button to start the County list activity
        countyButton = (Button) findViewById(R.id.button_county);
        countyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getCountyActivity = new Intent(GaaActivity.this , CountyDisplay.class);
                startActivity(getCountyActivity);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gaa, menu);
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


