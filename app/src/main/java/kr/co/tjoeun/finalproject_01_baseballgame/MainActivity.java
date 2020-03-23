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
//    컴퓨터가 내는 숫자 3자리가 담길 배열
    int[] questionNumArr = new int[3];


    ActivityMainBinding binding = null;

//    조경진의 개발 브런치

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
        makeQuestion();
    }

    @Override
    public void setupEvents() {

        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                입력할 숫자를 따와서 => 채팅내용으로 만들어서
//                 => 리스트에 추가하고 => 새로고침

                String inputNumStr = binding.numInputEdt.getText().toString();

                messageList.add(new Message(inputNumStr, "USER"));
                adapter.notifyDataSetChanged();

//                입력칸 비우기
                binding.numInputEdt.setText("");

//              리스트뷰 끌어내림
                binding.messageListView.smoothScrollToPosition(messageList.size());

//                ?S?B인지 컴퓨터가 대답하게 해주기
//                입력값을 => int로 바꿔서 => 메쏘드에 전달.


                checkStrikeAndBall(Integer.parseInt(inputNumStr));

            }
        });


    }

    @Override
    public void setValues() {

        messageList.add(new Message("숫자 야구를 시작합니다.","COMPUTER"));
        messageList.add(new Message("세자리 숫자를 맞춰주세요.","COMPUTER"));
        messageList.add(new Message("0은 사용되지 않고, 중복된 숫자도없습니다.","COMPUTER"));


        adapter = new MessageAdapter(mContext, R.layout.message_list_item, messageList);
        binding.messageListView.setAdapter(adapter);

    }
//    세칸을 다 채울때 까지.
//    문제 배열엥 들어있나 검사. 안들어있으면 집어 넣자 => 들어있는걸 발견? 중복! 다시뽑자.
//    컴퓨터가 1~9숫자 랜덤 생성
//    컴퓨터 문제 출제.
    void makeQuestion() {
//        3자리 배열을 다 채울때까지.
        for (int i = 0; i < questionNumArr.length; i++) {

//        각 자리에 조건에 맞는 숫자를 뽑을때 까지.
            while (true) {


//                1~9사이의 랜덤값 뽑아 보자.
//                1 <= (int) (Math.random()*9 + 1) < 10
                int randomNum = (int) (Math.random() * 9 + 1);

//                배열안에 랜덤값과 같은값이 들어있나?
//                한번도 없으면 중복이 아니여서 사용해도 o
//                한번이라도 찾으면 중복o,사용하면 x
                boolean isNumOK = true;
//                문제 배열안에있는 숫자를 num로 하나씩 뽑아보자
                for (int num : questionNumArr) {
                    if (randomNum == num) {
                        isNumOK = false;
                        break;
                    }
                }

//                isNumOK가 끝까지 true로 남아있나?


                if (isNumOK) {
                    questionNumArr[i] = randomNum;
                    Log.d("문제번호", randomNum + "");
                    break;
                }

            }

        }
    }

//    ?S?B 판정하기.

    void checkStrikeAndBall(int inputNum) {

//        123 => {1,2,3} 배열로 분리.
        int[] userNumArr = new int[3];
//        0번칸? 100의자리
        userNumArr[0] = inputNum / 100;
//        1번칸? 10의자리
        userNumArr[1] = inputNum / 10 % 10;
//        2번칸?1의 자리
        userNumArr[2] = inputNum % 10;

//        S가 몇개 , B이 몇개인지 카운팅.
        int strikeCount = 0;
        int ballCount = 0;

//        숫자 비교 for 중첩
        for (int i =0 ; i < userNumArr.length ; i++) {

            for (int j =0; j < questionNumArr.length; j++) {

                if (userNumArr[i] == questionNumArr[j]) {

                    if (i == j) {
                        strikeCount ++;
                    }
                    else {
                        ballCount++;
                    }


                }

            }

        }

        String content = String.format("%dS %dB 입니다.", strikeCount, ballCount);
        messageList.add(new Message(content, "COMPUTER"));
        adapter.notifyDataSetChanged();

        binding.messageListView.smoothScrollToPosition(messageList.size()-1);



    }


}
