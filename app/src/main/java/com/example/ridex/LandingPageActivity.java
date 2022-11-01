package com.example.ridex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class LandingPageActivity extends AppCompatActivity {


    Button getStartedBtn;
    ImageSwitcher imageSwitcher;
    private int position = 0;
    private int[] image = {R.drawable.infocard1_formatter, R.drawable.infocard2_formatter, R.drawable.infocard3_formatter};
    //for swipe gesture
    ConstraintLayout myLayout;
    SwipeListener swipeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getStartedBtn = findViewById(R.id.getStartedButton);
        imageSwitcher = findViewById(R.id.imageSwitcher);
        myLayout = findViewById(R.id.myLayout);

        swipeListener = new SwipeListener(myLayout);

        //set default view
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(LandingPageActivity.this);
                imageView.setImageResource(image[position]);

                return imageView;
            }
        });

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;

        //constructor
        SwipeListener(View view){
            //threshold value
            int threshold = 100;
            int velocity_threshold = 100;
            imageSwitcher = findViewById(R.id.imageSwitcher);
            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float xDiff = e2.getX() - e1.getX();
                    float yDiff = e2.getY() - e1.getY();
                    try{
                        //check condition
                        if(Math.abs(xDiff) > Math.abs(yDiff)){ //when x is greater than y
                            if (Math.abs(xDiff) > threshold
                                && Math.abs(velocityX) > velocity_threshold ){

                                if(xDiff>0){
                                    //when swiped right
                                    imageSwitcher.setInAnimation(getApplicationContext(),R.anim.slide_in_right);
                                    imageSwitcher.setOutAnimation(getApplicationContext(),R.anim.slide_out_right);
                                    if (position>=0 && position<3){
                                        position--;
                                        if (position<0){position =2;}
                                        imageSwitcher.setImageResource(image[position]);
                                    }

                                }else{
                                    //when swiped left
                                    imageSwitcher.setInAnimation(getApplicationContext(),R.anim.slide_in_left);
                                    imageSwitcher.setOutAnimation(getApplicationContext(),R.anim.slide_out_left);
                                    if (position>=0 && position<3){
                                        position++;
                                        if(position>2){position = 0;}
                                        imageSwitcher.setImageResource(image[position]);
                                    }

                                }
                                return true;
                            }
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    return false;
                }
            };

            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }


}