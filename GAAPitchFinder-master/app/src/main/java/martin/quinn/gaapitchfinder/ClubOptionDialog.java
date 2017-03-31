package martin.quinn.gaapitchfinder;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

/**
 * Created by marto_000 on 27/11/2015.
 */
public class ClubOptionDialog extends Dialog {

    Activity mActivity;
    public Button editButton, deleteButton, locButton, webButton;

    public ClubOptionDialog(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_club_options);

        // Buttons for the pop up in long press
        editButton = (Button)findViewById(R.id.btn_view);
        deleteButton = (Button)findViewById(R.id.btn_delete);
        locButton = (Button)findViewById(R.id.btn_location);
        webButton = (Button)findViewById(R.id.btn_website);

    }
}
