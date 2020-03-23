package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
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

    //    컴퓨터가 내는 숫자를 담는 배열
    int[] questionNumArr = new int[3];

    ActivityMainBinding binding = null;

    int tryCount = 0;

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
//                입력받은 숫자 String으로 변환하여 새 변수에 받기
                String inputNumStr = binding.numInputEdt.getText().toString();

//                받은 숫자로 새 메세지 출력하고 새로고침
                messageList.add(new Message(inputNumStr, "USER"));
                adapter.notifyDataSetChanged();

//                입력칸 비워주기
                binding.numInputEdt.setText("");

//                리스트 자동 스크롤(가장 마지막 메세지 보이도록)
                binding.messageListView.smoothScrollToPosition(messageList.size() - 1);


//                컴퓨터가 대답 (내가 입력한 숫자 분석)
                checkStrikeAndBall(Integer.parseInt(inputNumStr));
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

    //    컴퓨터가 문제 출제 (1~9의 숫자를 랜덤으로 생성 / 문제 배열에 들어있나 검사 / 안들어있으면 배열에 추가, 들어있으면 다시 랜덤으로 뽑기 / 세칸을 채울 때 까지 반복)
    void makeQuestion() {
        for (int i = 0; i < questionNumArr.length; i++) { //배열을 다 채울 때 까지 반복(index 3이기때문에 3번 반복될 것)
            while (true) {   //각 자리에 모든 조건을 만족할때까지 반복(모든 조건을 만족하는게 몇번일지 알 수 없음)

//                default : 0 <= Math.random() < 1 (실수) -> 가공 / 캐스팅 해주어야 함
                int randomNum = (int) (Math.random() * 9 + 1);

//                중복 검사
                boolean isNumOk = true;
                /*
                for(int j = 0 ; j < i ; j++) {
                    if(questionNumArr[j] == randomNum) {
                        isNumOk = false;
                        break;
                    }
                }
                */

                for (int num : questionNumArr) {
                    if (num == randomNum) {
                        isNumOk = false;
                        break;
                    }
                }

                if (isNumOk) {
                    questionNumArr[i] = randomNum;
                    Log.d("문제 번호", randomNum + "");
                    break;
                }

            }
        }
    }

    void checkStrikeAndBall(int inputNum) {

//        시도 횟수 1회 증가
        tryCount++;

        int[] userNumArr = new int[3];
        userNumArr[0] = inputNum / 100;
        userNumArr[1] = (inputNum / 10) % 10;
        userNumArr[2] = inputNum % 10;

        int strikeCount = 0;
        int ballCount = 0;

        for (int i = 0; i < userNumArr.length; i++) {
            for (int j = 0; j < questionNumArr.length; j++) {
                if (userNumArr[i] == questionNumArr[j]) {
                    if (i == j) {
                        strikeCount++;
                    } else {
                        ballCount++;
                    }
                }
            }
        }

        String content = String.format("%dS %dB 입니다.", strikeCount, ballCount);
        messageList.add(new Message(content, "COMPUTER"));
        adapter.notifyDataSetChanged();
        binding.messageListView.smoothScrollToPosition(messageList.size() - 1);

        if(strikeCount == 3) {
            messageList.add(new Message(String.format("축하합니다! %d회만에 맞췄습니다.", tryCount), "COMPUTER"));
            adapter.notifyDataSetChanged();
            binding.messageListView.smoothScrollToPosition(messageList.size() - 1);

//            더이상의 추가 입력을 막기위해 입력칸, 입력 버튼 enabled = false
            binding.numInputEdt.setEnabled(false);
            binding.okBtn.setEnabled(false);
        }

    }
}

