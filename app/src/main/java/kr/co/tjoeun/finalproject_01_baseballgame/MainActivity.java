package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import kr.co.tjoeun.finalproject_01_baseballgame.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {


    ActivityMainBinding binding = null;

//  박새영의 개발 브런치

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }
}
