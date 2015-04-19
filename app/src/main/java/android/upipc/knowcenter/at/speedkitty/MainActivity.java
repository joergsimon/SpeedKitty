package android.upipc.knowcenter.at.speedkitty;

import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements KittyMoodUpdater {

    KittyMoodReceiver receiver;
    MotivationLogic logic;

    TextView kittySpeak;
    TextView speedCounterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kittySpeak = (TextView)findViewById(R.id.kittySpeak);
        speedCounterNumber = (TextView)findViewById(R.id.speedCounterNumber);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(MotivationLogic.BROADCAST_ACTION);

        receiver = new KittyMoodReceiver(this);
        registerReceiver(receiver, filter);

        logic = new MotivationLogic(this, false);
        logic.startMonitorMoticationUpdates();

        updateSpeedCounter();
        updateKittyMood(logic.getKittiesMood());
    }

    @Override
    protected void onPause() {
        super.onPause();
        logic.stopMonitorMoticationUpdates();
        // TODO: transfer to background processing
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void updateKittyMood(KittyMood mood) {
        updateSpeedCounter();
        if (mood == KittyMood.RUNNING) {
            displayRunning();
        }
        else if (mood == KittyMood.SCARED) {
            displayScared();
        }
        else if (mood == KittyMood.NEUTRAL) {
            displayNeutral();
        }
        else if (mood == KittyMood.MOTIVATED) {
            displayMotivated();
        }
        else {
            // Log wtf
        }
    }

    private void updateSpeedCounter() {
        speedCounterNumber.setText(""+logic.getSpeed());
    }

    private void displayRunning() {
        kittySpeak.setText("running");
    }

    private void displayScared() {
        kittySpeak.setText("scared");
    }

    private void displayNeutral() {
        kittySpeak.setText("neutral");
    }

    private void displayMotivated() {
        kittySpeak.setText("motivated");
    }
}
