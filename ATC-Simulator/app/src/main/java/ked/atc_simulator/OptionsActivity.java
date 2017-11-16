package ked.atc_simulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ked.atc_simulator.Utils.Options;

public class OptionsActivity extends AppCompatActivity {

    private int difficulty; // 0 facile, 1 moyen, 2 diffcile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        difficulty = 1; //par défaut

        findViewById(R.id.easierButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty--;
                if(difficulty < 0)
                    difficulty = 0;
                setDifficultyText(difficulty);
                Options.setDifficulty(difficulty);
                Log.i("Difficulty","Difficulty is now "+difficulty);
            }
        });

        findViewById(R.id.harderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty++;
                if(difficulty > 2)
                    difficulty = 2;
                setDifficultyText(difficulty);
                Options.setDifficulty(difficulty);
                Log.i("Difficulty","Difficulty is now "+difficulty);
            }
        });

        findViewById(R.id.resetSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().deleteFile("save.xml");
                Toast.makeText(OptionsActivity.this, getResources().getString(R.string.options_reset_save_toast_text), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Setter pour le texte indiquand la difficulté
     * @param d le niveau de difficulté
     */
    void setDifficultyText(int d){
        TextView text = ((TextView) findViewById(R.id.difficultyText));
        Button easyButton = ((Button) findViewById(R.id.easierButton));
        Button hardButton = ((Button) findViewById(R.id.harderButton));

        switch(d){
            case 0:
                text.setText(getResources().getString(R.string.options_difficulty_easy));
                easyButton.setText(getResources().getString(R.string.options_difficulty_easy));
                hardButton.setText(getResources().getString(R.string.options_difficulty_normal));
                break;
            case 1:
                text.setText(getResources().getString(R.string.options_difficulty_normal));
                easyButton.setText(getResources().getString(R.string.options_difficulty_easy));
                hardButton.setText(getResources().getString(R.string.options_difficulty_hard));
                break;
            case 2:
                text.setText(getResources().getString(R.string.options_difficulty_hard));
                easyButton.setText(getResources().getString(R.string.options_difficulty_normal));
                hardButton.setText(getResources().getString(R.string.options_difficulty_hard));
                break;
            default:
                text.setText(getResources().getString(R.string.options_difficulty_error));
                break;
        }
    }

}
