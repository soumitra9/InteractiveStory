package com.saumitra.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saumitra.interactivestory.R;
import com.saumitra.interactivestory.model.Page;
import com.saumitra.interactivestory.model.Story;

import java.util.Stack;

import static android.R.attr.name;

public class StoryActivity extends AppCompatActivity {
    private Story story;
    private static final String TAG = "StoryActivity";
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;
    private String name;
    private Stack<Integer> pageStack = new Stack<Integer>();// why integer?? becoz that wat we r using to keep track of pageNumber

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        storyImageView = (ImageView) findViewById(R.id.storyImageView);
        storyTextView = (TextView) findViewById(R.id.storyTextView);
        choice1Button = (Button) findViewById(R.id.choice1Button);
        choice2Button = (Button) findViewById(R.id.choice2Button);
        Intent intent = getIntent(); // receiev the intent
        name = intent.getStringExtra(getString(R.string.key_name));// unbind the name attached to main activity
        // they key should be same as used to put the data on intent
        //else name will store null resulting in NullPoint Exception
        if (name == null || name.isEmpty()) {
            name = "Friend";
        }
        Log.d(TAG, name);
        story = new Story();//Why Sory object here???? Becoz we want the story class to create story as soon as we enter
        //our name and load page 0, after 0 where we go will determined by choice onClick lostener
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);// we want to push page Number wen we go to it pop everytime wen click Back Button
        final Page page = story.getPage(pageNumber);// page object has to be made final if you want Onclick to access it
        Drawable image = ContextCompat.getDrawable(this, page.getImageId());// converts the image into drawable type
        storyImageView.setImageDrawable(image);// thi function accepts image of Type Drawable, so u need to convert it
        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name); //this will replace name entered in the textview where "%1$s" is written
        storyTextView.setText(pageText);// to set Text always remember only this function is used, the param type may vary
        if (page.isFinalPage()) {
        choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText("PLAY AGAIN");
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  finish();// We could have set up a new intent but that is not needed, we still have access to it
                            // this method will finish current activity and tack us back to main activity
                            //But this will start the game again i.e u wil have to enter d name again
                            // I just want the game to start from page 0 and wid d same name dat i had eneterd
                    loadPage(0);
                }
            });
        }
        else{
            loadButtons(page); }
    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId()); //page.getChoice1 wil give Choice1 object, nd den use it to get Id
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = page.getChoice1().getNextPage();
                loadPage(n);
            }
        });
        choice2Button.setVisibility(View.VISIBLE);
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = page.getChoice2().getNextPage();
                loadPage(n);
            }
        });
    }
// this method is called each time press back button on ur phone
    @Override
    public void onBackPressed() {
        pageStack.pop();//But if we are on 1st page i.e page 0, and we click back button then stack is empty
        if(pageStack.isEmpty()) {
            super.onBackPressed();// so if we dont write any other code, this line will execute default back button feature i.e
            //switching to mainActivity no matter on what page We are.
            //So lets add some text becoz we want to switch between stage of Story Activity not Main Activity

        }// but if its not empty then we r on some further page and we need to go back to prev page
        else {
            loadPage(pageStack.pop());
                                        //pageStack.pop() actually returns the integer type of page we just popped
                            // suppose we were able to get prev page no. and call loadPage with it then
                            // then in above method pageStack.push() would have repushed the page, and we wud have ended
            //up on same page twice
        } //Suppose we r on page 1
        //pageStack.pop() removes page 1 from stack and we r left withh page0
        //stack is not empty
        // we go to lest part
        //pageStack.pop() again pops out page 0 and stack gets empty
        //but we repush page 0, wen we call pageStack.push()

    }
}
