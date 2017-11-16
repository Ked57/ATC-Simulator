package ked.atc_simulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ked.atc_simulator.Utils.Options;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Options.setDifficulty(1);

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tutorialButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.optionsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.creditButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });
    }
}
