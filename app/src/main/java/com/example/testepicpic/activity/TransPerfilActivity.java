package com.example.testepicpic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.TrafficStats;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.testepicpic.R;
import com.example.testepicpic.fragment.ConfigAjudaFragment;
import com.example.testepicpic.fragment.ConfigNotificacaoFragment;
import com.example.testepicpic.fragment.ConfigPerfilFragment;
import com.example.testepicpic.fragment.ConfigTratamentoFragment;

public class TransPerfilActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{


    private float x1, y1, x2, y2;
    private static int minDistance = 150;
    private GestureDetector gestureDetector;

    private ConfigPerfilFragment perfilFragment = new ConfigPerfilFragment();
    private ConfigTratamentoFragment tratamentoFragment = new ConfigTratamentoFragment();
    private ConfigNotificacaoFragment notificacaoFragment = new ConfigNotificacaoFragment();
    private ConfigAjudaFragment ajudaFragment = new ConfigAjudaFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_perfil);

        Bundle extras  = getIntent().getExtras();

        assert extras != null;
        int position = extras.getInt("position");
        gestureDetector = new GestureDetector(TransPerfilActivity.this,this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position){
            case 0:
                transaction.replace(R.id.frameTransPerfil, perfilFragment);
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.frameTransPerfil, tratamentoFragment);
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.frameTransPerfil, notificacaoFragment);
                transaction.commit();
                break;
            case 3:
                transaction.replace(R.id.frameTransPerfil, ajudaFragment);
                transaction.commit();
                break;

        }

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
}