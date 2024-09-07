package unipiloto.edu.co.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private int laps = 0;
    private int lastLap = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stopwatch);

        //revisar metodo onSaveInstanceState al final
//        if (savedInstanceState != null) {
//            seconds = savedInstanceState.getInt("seconds");
//            running = savedInstanceState.getBoolean("running");
//        }

        runTimer();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        laps = 0;
        lastLap = 0;
        TextView lapView = (TextView) findViewById(R.id.lap_view);
        lapView.setText("");

        TextView lapTimeView = (TextView) findViewById(R.id.lap_time_view);
        lapTimeView.setText("");

        TextView totalView = (TextView) findViewById(R.id.total_view);
        totalView.setText("");
    }

    public void onClickLap(View view) {
        if (!running) {
            return;
        }
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        laps++;

        TextView lapView = (TextView) findViewById(R.id.lap_view);
        lapView.setText(lapView.getText() + "\n" + laps);

        TextView lapTimeView = (TextView) findViewById(R.id.lap_time_view);
        if (laps == 1) {
            lapTimeView.setText("+"+time);
        } else {
            lastLap = seconds - lastLap;
            int hoursL = lastLap / 3600;
            int minutesL = (lastLap % 3600) / 60;
            int secsL = lastLap % 60;
            String timeL = String.format(Locale.getDefault(), "%d:%02d:%02d", hoursL, minutesL, secsL);
            lapTimeView.setText(lapTimeView.getText() + "\n" + "+" + timeL);
        }
        lastLap = seconds;

        TextView totalView = (TextView) findViewById(R.id.total_view);
        totalView.setText(totalView.getText() + "\n" + time);
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    //metodo mostrado y explicado en la presentacion, sin embargo no es necesario para el funcionamiento de la aplicacion y en la clase tampoco es usado.
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putInt("seconds", seconds);
//        savedInstanceState.putBoolean("running", running);
//    }
}