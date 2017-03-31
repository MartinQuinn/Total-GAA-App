package martin.quinn.gaapitchfinder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AddClub {

    private Button searchClubButton;
    ArrayAdapter<String> mArrayAdapter;
    List<ClubsClass> mClubsClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // This is my array list of clubs
        mClubsClasses = new ArrayList<>();

        mArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, android.R.id.text1);

        final ListView listClubs = (ListView) findViewById(R.id.listView);
        listClubs.setAdapter(mArrayAdapter);

        clubName = (EditText) findViewById(R.id.club_search);
        searchClubButton = (Button)findViewById(R.id.button);
        searchClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start getClubActivity
                if(clubName == null){
                    try{

                        db.open();
                        Cursor result = db.getAll();
                        MyCursorAdapter cursorAdapter = new MyCursorAdapter(SearchActivity.this, result);

                        db.close();

                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    try {
                        // This searches the database of clubs and returns the closest results
                        db.open();
                        mArrayAdapter.clear();
                        mArrayAdapter.notifyDataSetChanged();
                        listClubs.setAdapter(mArrayAdapter);
                        mClubsClasses = db.searchLikeName(clubName.getText().toString());

                        for(int i = 0; i < mClubsClasses.size(); i++) {
                           mArrayAdapter.add(mClubsClasses.get(i).getName());
                        }

                        db.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }




            }
        });

        listClubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {

                //intent to single club with details of all aspects of the club

                ClubsClass clubsClass = mClubsClasses.get(position);

                Intent ClubInfoDisplay = new Intent(SearchActivity.this, ClubInfo.class);
                ClubInfoDisplay.putExtra("clubInfo",clubsClass.getName());
                ClubInfoDisplay.putExtra("locInfo",clubsClass.getLocaiton());
                startActivity(ClubInfoDisplay);
            }
        });
    }


}