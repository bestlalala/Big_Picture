package smwu.mobileprogramming.termprj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {

    TextView welcome, welcomeText;
    ImageView logoImage;
    Button startBtn;

    Handler animHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimThread animThread = new AnimThread();
        animThread.start();

        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    class AnimThread extends Thread {
        public void run() {
            animHandler.post(new Runnable() {
                @Override
                public void run() {
                    welcome = findViewById(R.id.welcome);
                    welcomeText = findViewById(R.id.welcomeText);
                    logoImage = findViewById(R.id.logo);

                    Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome);
                    Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo);
                    Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcometext);

                    welcome.startAnimation(anim1);
                    logoImage.startAnimation(anim2);
                    welcomeText.startAnimation(anim3);
                }
            });
        }
    }

}