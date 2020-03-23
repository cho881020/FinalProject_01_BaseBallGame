package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.adapters.MessageAdapter;
import kr.co.tjoeun.finalproject_01_baseballgame.data.Message;
import kr.co.tjoeun.finalproject_01_baseballgame.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    List<Message> messageList = new ArrayList<>();
    MessageAdapter adapter = null;

    int[] questionNumArr = new int[3];


    ActivityMainBinding binding = null;

//    박새영의 개발 브런치

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.oKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              입력한 숫자를 따와서 => 채팅내용으로 만들어서
//              => 리스트에 추가하고 => 새로고침

                String inputNumStr = binding.numInputEdt.getText().toString();

                messageList.add(new Message(inputNumStr, "USER"));
                adapter.notifyDataSetChanged();


                binding.numInputEdt.setText("");

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

//  컴퓨터가 문제 출제
//  1~9의 숫자를 랜덤으로 생성
//  문제 배열에 들어있나 검사 => 안들어있으면 집어넣자
//  => 들어있는걸 발견? 중복! 다시 뽑자.
//  => 세칸을 다 채울때 까지
   void makeQuestion() {

        for(int i=0; i<questionNumArr.length;i++){
//          각 자리에 조건에 맞는 숫자를 뽑을때까지
            while (true) {

//              1~9사이의 랜덤값 뽑아보자
//              1 <= (int) (Math.random()*9+1 < 10
                int randomNum = (int) (Math.random()*9+1);

//              배열 안에 랜덤값과 같은 값이 들어있나?
//              한번도 없으면 => 중복 X. 사용해도 O
//              한번이라도 찾으면 => 중복 O, 사용하면 X.

                boolean isNumOk = true;

//              문제배열안에 있는 숫자를 num로 하나씩 뽑아보자.
                for (int num : questionNumArr) {
                    if(randomNum == num) {
                        isNumOk = false;
                        break;
                    }
                }
//              isNumOk가 끝까지 true로 남아있나?
                if (isNumOk) {
                    questionNumArr[i] = randomNum;
                    Log.d("문제번호",randomNum+"");
                    break;
                }

            }
        }

    }
}