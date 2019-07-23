package Applications.abfahrt2019;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button startButton = findViewById(R.id.startButton);

        /*
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });
        */
        startButton.setOnClickListener(view ->  {
            Log.d("debug", "Button clicked");
            // Hier kommt der Aufruf unserer Anfangsmethode rein
        });
    }
}
