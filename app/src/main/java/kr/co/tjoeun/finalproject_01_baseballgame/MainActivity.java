package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.data.Message;
import kr.co.tjoeun.finalproject_01_baseballgame.data.adapters.MessageAdapter;
import kr.co.tjoeun.finalproject_01_baseballgame.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

//    백승훈의 개발 브런치

    List<Message> messageList = new ArrayList<>();
    MessageAdapter adapter = null;

    //    컴퓨터가 내는 숫자 세자리가 담길 배열
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
//                입력한 숫자를 따와서 => 채팅내용으로 만들어서
//                => 리스트에 추가 => 새로고침

                String inputNumStr = binding.numInputEdt.getText().toString();

                messageList.add(new Message(inputNumStr, "USER"));
                adapter.notifyDataSetChanged();

//                입력칸을 배워주자
                binding.numInputEdt.setText("");

//                자동으로 리스트뷰 끌어내림
                binding.messageListView.smoothScrollToPosition(messageList.size() - 1);
//                ?S ?B인지 컴퓨터가 대답해주게 하자
//                입력값을 => int로 바꿔서 (wrapper)메소드에 전달
                checkStrikeAndBall(Integer.parseInt(inputNumStr));


            }
        });

    }

    @Override
    public void setValues() {

        messageList.add(new Message("숫자 야구를 시작합니다", "COMPUTER"));
        messageList.add(new Message("세자리 숫자를 맞춰주세요", "COMPUTER"));
        messageList.add(new Message("0은 사용되지 않고 중복된 숫자도 없습니다", "COMPUTER"));

        adapter = new MessageAdapter(mContext, R.layout.message_list_item, messageList);
        binding.messageListView.setAdapter(adapter);

    }

    //    컴퓨터가 문제 출제
//    1~9의 숫자를 랜덤으로 생성
//    문제 배열에 들어있나 검사 = 안들어있으면 집어넣자
//    => 들어있는걸 발견? 중복! 다시뽑자
//    => 세칸을 다 채울때 까지
    void makeQuestion() {

//        3자리 배열을 다 채울때 까지
        for (int i = 0; i < questionNumArr.length; i++) {
//            각 자리에 조건에 맞는 숫자를 뽑을때 까지
            while (true) {
//                1~9사이의 랜덤값을 뽑아보자
//                0 <= Math.random < 1
//                0 <= (int) (Math.random*9+1) < 10
                int randomNum = (int) (Math.random() * 9 + 1);

//                배열 안에 랜덤값과 같은 값이 들어있나?
//                한번도 없으면 => 중복 X . 사용 O
//                한번이라도 찾으면 => 중복 O, 사용하면 O

                boolean isNumOk = true;
//                문제 배열안에 있는 숫자를 num로 하나씩 뽑아보자
                for (int num : questionNumArr) {
                    if (randomNum == num) {
                        isNumOk = false;
                        break;
                    }
                }

//                isNumOk가 끝까지 true로 남아있나?
                if (isNumOk) {
                    questionNumArr[i] = randomNum;
                    Log.d("문제번호", randomNum + "");
                    break;
                }

            }
        }

    }

//    ?S ?B 판정하기

    void checkStrikeAndBall(int inputNum) {

//        123 => { 1,2,3 } 배열로 분리
        int[] userNumArr = new int[3];
//        0번칸? 100의자리 1번칸? 10의자리 2번칸? 1의자리
        userNumArr[0] = inputNum / 100;
        userNumArr[1] = inputNum / 10 % 10;
        userNumArr[2] = inputNum % 10;

//        S가 몇개? B이 몇개인지 카운팅
        int strikeCount = 0;
        int ballCount = 0;

//        숫자 비교 for 중펍
//        i : 사용자 입력값을 뽑는 index
        for (int i = 0; i < userNumArr.length; i++) {

//            j: 문제의 각 자리를 뽑는 index
            for (int j = 0; j < questionNumArr.length; j++) {

//                같은숫자인지? 같으면 위치 같은지 추가검사
                if (userNumArr[i] == questionNumArr[j]) {

//                    위치가 같은가? => index가 서로 같나? i==j?
                    if (i == j) {
                        strikeCount++;
                    } else {
                        ballCount++;
                    }
                }
            }

        }String content = String.format("%dS %dB입니다", strikeCount,ballCount);
        messageList.add(new Message(content, "COMPUTER"));
        adapter.notifyDataSetChanged();

        binding.messageListView.smoothScrollToPosition(messageList.size()-1);


    }


}
