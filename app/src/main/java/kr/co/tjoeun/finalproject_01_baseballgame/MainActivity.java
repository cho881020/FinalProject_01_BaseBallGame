package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.adapters.MessageAdapter;
import kr.co.tjoeun.finalproject_01_baseballgame.data.Message;
import kr.co.tjoeun.finalproject_01_baseballgame.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    List<Message> messageList = new ArrayList<>();
    ActivityMainBinding binding = null;
    MessageAdapter adapter = null;


//    권오경의 개발 브런치

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

        messageList.add(new Message("숫자 야구를 시작합니다", "COMPUTER"));
        messageList.add(new Message("세자리 숫자를 맞춰주세요", "COMPUTER"));
        messageList.add(new Message("0은 사용되지 않고, 중복된 숫자도 없습니다.", "COMPUTER"));


        adapter = new MessageAdapter(mContext, R.layout.message_list_item, messageList);

        binding.messageListView.setAdapter(adapter);
    }
}
