package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.testepicpic.R;
import com.example.testepicpic.fragment.AddAlimentacaoFragment;
import com.example.testepicpic.fragment.AddBemEstarFragment;
import com.example.testepicpic.fragment.AddExercicioFragment;
import com.example.testepicpic.fragment.AddGlicemiaFragment;
import com.example.testepicpic.fragment.AddInsulinaFragment;

public class AddInfosActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private final AddGlicemiaFragment addGlicemiaFragment = new AddGlicemiaFragment();
    private final AddInsulinaFragment addInsulinaFragment = new AddInsulinaFragment();
    private final AddAlimentacaoFragment addAlimentacaoFragment = new AddAlimentacaoFragment();
    private final AddBemEstarFragment addBemEstarFragment = new AddBemEstarFragment();
    private final AddExercicioFragment addExercicioFragment = new AddExercicioFragment();

    private static final String TAG = "swipe position";
    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infos);

        Bundle extras = getIntent().getExtras();

        assert extras != null;
        int position = extras.getInt("position");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        gestureDetector = new GestureDetector(AddInfosActivity.this, this);

        switch (position) {
            case 0:
                transaction.replace(R.id.frameAddInfos, addBemEstarFragment);
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.frameAddInfos, addAlimentacaoFragment);
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.frameAddInfos, addExercicioFragment);
                transaction.commit();
                break;
            case 3:
                transaction.replace(R.id.frameAddInfos, addInsulinaFragment);
                transaction.commit();
                break;
            case 4:
                transaction.replace(R.id.frameAddInfos, addGlicemiaFragment);
                transaction.commit();
                break;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueY = y2 - y1;

                if(Math.abs(valueY) > minDistance) {
                    if (y2 > y1) {
                        finish();
                    }
                }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}