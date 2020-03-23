package kr.co.tjoeun.finalproject_01_baseballgame;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

//    백승훈의 개발 브런치

    public Context mContext = this;

    public abstract void setupEvents();
    public abstract void setValues();


}
