package kr.co.tjoeun.finalproject_01_baseballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.adapters.MessageAdapter;
import kr.co.tjoeun.finalproject_01_baseballgame.datas.Message;
import kr.co.tjoeun.finalproject_01_baseballgame.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    List<Message> messageList = new ArrayList<>();
    MessageAdapter adapter = null;

    //    컴퓨터가 내는 숫자 세자리가 담길 배열.
    int[] questionNumArr = new int[3];

//    몇번만에 맞췄는지 카운팅 변수.
    int tryCount = 0;

    ActivityMainBinding binding = null;

//    김주형의 개발 브런치

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
//                입력한 숫자를 따와서 채팅내용으로 만들어서
//                리스트에 추가하고 새로고침

                String inputNumStr = binding.numInputEdt.getText().toString();

                if (inputNumStr.length() != 3) {
                    Toast.makeText(mContext, "입력은 3자리여야 합니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                messageList.add(new Message(inputNumStr, "USER"));
                adapter.notifyDataSetChanged();

//                입력칸을 비워주자.
                binding.numInputEdt.setText("");

//                리스트뷰 끌어 내림
                binding.messageListView.smoothScrollToPosition(messageList.size()-1);

//                ?S ?B인지 컴퓨터가 대답해주게 하자.
//                입력값을 => int로 바꿔서 (Wrapper) => 메쏘드에 전달
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

//    컴퓨터가 문제 출제
//    1~9의 숫자를 랜덤으로 생성
//    문제 배열에 들어있나 검사. => 만들었으면 집어넣자
//    들어있는걸 발견? 중복 다시 불자
//    => 세칸을 다 채울때 까지.
    void makeQuestion() {
//        3자리 다 채울때까지
        for (int i = 0; i < questionNumArr.length; i++){
//            각자리에 조건에 맞는숫자를 뽑을때까지
            while (true) {
//                1~9
                int randomNum = (int) (Math.random()*9+1);

//                배열안에 랜덤값과 같은 값이 들어있나?
//                한번도 없으면 => 중복x 사용 ㅇ
//                한번이라도 찾으면 중복 ㅇ 사용x
                boolean isNumOk = true;

//                문제 배열안에 있는 숫자를 num로 하나씩 뽑아본다
                for (int num : questionNumArr) {
                    if (randomNum == num) {
                        isNumOk = false;
                        break;
                    }
                }

//                isNumOk가 끝까지 true로 남아있나?
                if (isNumOk) {
                    questionNumArr[i] = randomNum;
                    Log.d("문제번호", randomNum+"");
                    break;
                }
            }
        }

    }

//    ?s ?b 판정하기
    void checkStrikeAndBall (int inputNum) {

        tryCount++;

//        123 => {1,2,3} 배열로 분리

        int[] userNumArr = new int[3];
//        0번칸 100의자리
        userNumArr[0] = inputNum / 100;
//        1번칸 10의자리 => 10으로 나누고 그 값의 1의자리.
        userNumArr[1] = inputNum / 10 % 10;
//        2번칸 1의자리
        userNumArr[2] = inputNum % 10;

//        S가 몇개, B이 몇개인지 카운팅.
        int strikeCount = 0;
        int ballCount = 0;

//        숫자 비교 for 중첩
//        i = 사용자 입력값을 뽑는 index
        for (int i=0; i < userNumArr.length; i++) {

//            j= 문제의 각 자리를 뽑는 index
            for (int j= 0; j < questionNumArr.length; j++) {

                if (userNumArr[i] == questionNumArr[j]) {

//                    위치가 같은가? => index가 서로 같나?
//                    i == j ?
                    if (i == j) {
                        strikeCount++;
                    }
                    else {
                        ballCount++;
                    }

                }

            }
        }

        String content = String.format("%dS %dB입니다.", strikeCount, ballCount);
        messageList.add(new Message(content, "COMPUTER"));
        adapter.notifyDataSetChanged();

        binding.messageListView.smoothScrollToPosition(messageList.size()-1);

//        만약 3S면 축하메세지 + 입력 막자. (종료)

        if (strikeCount == 3) {
            messageList.add(new Message(String.format("ㅊㅋㅊㅋㅊㅋ %d회 만에 맞췄으니 저리가셈", tryCount), "COMPUTER"));
            adapter.notifyDataSetChanged();
            binding.messageListView.smoothScrollToPosition(messageList.size()-1);

//            입력 막자
            binding.numInputEdt.setEnabled(false);
            binding.okBtn.setEnabled(false);
        }

    }
}
