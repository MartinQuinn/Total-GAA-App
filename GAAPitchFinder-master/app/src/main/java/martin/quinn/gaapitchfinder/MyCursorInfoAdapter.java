package martin.quinn.gaapitchfinder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class MyCursorInfoAdapter extends CursorAdapter {
    public MyCursorInfoAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.info_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView clubName = (TextView) view.findViewById(R.id.TextView_club_name);
        TextView countyName = (TextView) view.findViewById(R.id.TextView_county_name);
        TextView locationLongLat = (TextView) view.findViewById(R.id.TextView_location);


        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("club_name"));
        String cname = cursor.getString(cursor.getColumnIndexOrThrow("county_name"));
        String col = cursor.getString(cursor.getColumnIndexOrThrow("colours"));
        String desc = cursor.getString(cursor.getColumnIndexOrThrow("club_description"));
        String loc = cursor.getString(cursor.getColumnIndexOrThrow("location"));
        String website = cursor.getString(cursor.getColumnIndexOrThrow("website"));

        // Populate fields with extracted properties
        clubName.setText(name);
        countyName.setText(cname);

        locationLongLat.setText(loc);

    }
}