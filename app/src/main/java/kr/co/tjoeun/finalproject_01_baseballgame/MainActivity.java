package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.adapters.MessageAdapter;
import kr.co.tjoeun.finalproject_01_baseballgame.databinding.ActivityMainBinding;
import kr.co.tjoeun.finalproject_01_baseballgame.datas.Message;

public class MainActivity extends BaseActivity {


    List<Message> messageList = new ArrayList<>();
    MessageAdapter adapter = null;
    ActivityMainBinding binding = null;

//    조경진의 개발 브런치

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                입력받은 숫자 String으로 변환하여 새 변수에 받기
                String inputNumStr = binding.numInputEdt.getText().toString();

//
                messageList.add(new Message(inputNumStr, "USER"));
                adapter.notifyDataSetChanged();

//                입력칸 비워주기
                binding.numInputEdt.setText("");

//                리스트 자동 스크롤(가장 마지막 메세지 보이도록)
                binding.messageListView.smoothScrollToPosition(messageList.size()-1);
            }
        });



    }

    @Override
    public void setValues() {

        messageList.add(new Message("숫자 야구를 시작합니다.", "COMPUTER"));
        messageList.add(new Message("세자리 숫자를 맞춰주세요.", "COMPUTER"));
        messageList.add(new Message("0은 사용되지 않고, 중복된 숫자도 없습니다.", "COMPUTER"));

        adapter = new MessageAdapter(mContext, R.layout.message_list_item, messageList);
        binding.messageListView.setAdapter(adapter);
    }
}
