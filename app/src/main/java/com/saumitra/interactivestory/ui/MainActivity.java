package com.saumitra.interactivestory.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.saumitra.interactivestory.R;

public class MainActivity extends AppCompatActivity {
        private EditText nameField;
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameField=(EditText) findViewById(R.id.nameEditText);
        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
               // Toast.makeText(MainActivity.this, "name", Toast.LENGTH_LONG).show();
                startStory(name);
                
            }
        });
    }
/* there is Up button creted that lets us go back to the Main activity and clears the text field itself, To activate
    Do into Manifest file and set parent activity to be Main Activity inside StoryActivity. So basically it lets u
    switch between activities, all u need is to define parent **/

/* OnCreate method and code inside it will always run when Back button is clicked. First the Main activity wil be recreated
   and then setText will be executed.  **/
    @Override
    protected void onResume() // always add it after onCreate, wen we pclick Back Button, we enter into a new lifeCycle called
                            //On resume, hence if we wane the text filed to be cleared after we click back button, then
                            //then define itnside onResume()
    {
        super.onResume();
        nameField.setText("");
    }

    private void startStory(String name) //intent function Aways make it private
    {
        Intent intent = new Intent(this,StoryActivity.class);
        Resources resources = getResources();// creating Resources objects inside Intent so that it can be used by
                                            //other activity too
        String key = resources.getString(R.string.key_name);
        intent.putExtra(key,name);
        startActivity(intent); //very very  imprtant fnction

    }
}
