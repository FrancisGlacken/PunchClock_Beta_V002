package co.codemaestro.punchclockv002;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {


    TextView timeView;
    Button start, pause, reset, lap;
    Long millisecondsTime, StartTime, TimeBuff = 0L, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    String currentTime;
    String category, timeBankName, savedTime; // Fran added "savedTime" to simplify the code on timeDataBase.java  - 10/9/2018

    //Required for putExtras
    public final static String TIME_MAIN = "co.codemaestro.punchclockv002.MESSAGE";
    public final static String CATEGORY_NAME = "co.codemaestro.punchclockv002.MESSAGE";
    public final static String TIME_BANK_NAME = "co.codemaestro.punchclockv002.MESSAGE";
    public final static String CURRENT_TIME = "co.codemaestro.punchclockv002.MESSAGE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning reference variables for interface
        timeView = findViewById(R.id.timerView);
        start = findViewById(R.id.startButton);
        pause = findViewById(R.id.pauseButton);
        reset = findViewById(R.id.resetButton);
        //Assigning Handler
        handler = new Handler() ;


    }

    //Starts timeMain value counting
    public void startButton(View view) {

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        reset.setEnabled(false);
    }

    //Stops the timeMain as is
    //Changes startButton text to "Resume"
    public void pauseButton(View view) {

        TimeBuff += millisecondsTime;
        handler.removeCallbacks(runnable);
        reset.setEnabled(true);
    }

    //Asks the User if they are sure they want to reset
    //If yes reset timeMain to 00:00:00
    //If no, back to MainActivity
    public void resetButton(View view) {

        millisecondsTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;
        timeView.setText(R.string.time_main_at_zero);
    }

    //StopWatch Logic
    public Runnable runnable = new Runnable() {

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {

            millisecondsTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + millisecondsTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            timeView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            currentTime = timeView.getText().toString();

            handler.postDelayed(this, 0);
        }
    };

//    Save Time Button
//    Starts timeDataBase Activity Class
//    Passes the CATEGORY_NAME, TIME_BANK_NAME, TIME_MAIN extras to the next activity
//    TimeDataBase must be able to accept these extras for a RecyclerView
    public void startTimeDatabase(View view) {

        /*Code for passing information to RecyclerView*/
//        Intent startTimeDataBase = new Intent(this, TimeDataBase.class);
//        startTimeDataBase.putExtra("CATEGORY_NAME", category);
//        startTimeDataBase.putExtra("TIME_BANK_NAME", timeBankName);
//        savedTime = timeView.getText().toString();  // Fran added this line to get the timer value - 10/9/2018
//        startTimeDataBase.putExtra("CURRENT_TIME", savedTime); //Fran edited this line to take var "savedTime"
//        startActivity(startTimeDataBase);


//        DialogFragment newFragment = new CommitTimeFragment();
//        newFragment.show(getSupportFragmentManager(), "CommitTimeFragment");

//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
//        View mView = getLayoutInflater().inflate(R.layout.commit_time_dialog, null);

//        TextView commitTimeDialogHeader = mView.findViewById(R.id.commit_time_header);
//        TextView currentTime = mView.findViewById(R.id.current_time);
//        currentTime.setText((CharSequence) currentTime);
//        Button commitTimeConfirm = mView.findViewById(R.id.confirm_commit_button);
//        Button declineTimeCommit = mView.findViewById(R.id.decline_commit_time_button);

//        mBuilder.setView(mView);
//        AlertDialog dialog = mBuilder.create();
//        dialog.show();

//        openDialog(currentTime);

        CommitTimeDialog commitTimeDialog = CommitTimeDialog.newInstance();

//        FragmentManager commitTimeManager = getSupportFragmentManager();
//        FragmentTransaction commitTimeTransaction = commitTimeManager.beginTransaction();
//        commitTimeTransaction.add(R.id.commit_time_dialog_viewgroup, commitTimeDialog);
//        commitTimeTransaction.addToBackStack(null);
//        commitTimeTransaction.commit();

        commitTimeDialog.show(getSupportFragmentManager(), "commit time dialog");


    }

//    public void openDialog(String currentTime) {
//        CommitTimeDialog commitTimeDialog = new CommitTimeDialog();
//        commitTimeDialog.show(getSupportFragmentManager(), "commit time dialog");
//
//    }


}
