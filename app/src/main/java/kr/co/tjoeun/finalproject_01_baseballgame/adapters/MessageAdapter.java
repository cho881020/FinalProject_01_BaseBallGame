package kr.co.tjoeun.finalproject_01_baseballgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.R;
import kr.co.tjoeun.finalproject_01_baseballgame.data.Message;

public class MessageAdapter extends ArrayAdapter<Message> {

    Context mContext;
    List<Message> mList;
    LayoutInflater inf;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);

        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if(row == null){
            row = inf.inflate(R.layout.message_list_item, null);
        }

        Message data = mList.get(position);

        LinearLayout computerMessageLayout = row.findViewById(R.id.computerMessageLayout);
        LinearLayout userMessageLayout = row.findViewById(R.id.userMessageLayout);

        TextView computerTxt = row.findViewById(R.id.computerTxt);
        TextView userTxt = row.findViewById(R.id.userTxt);

        if(data.getSpeaker().equals("COMPUTER")){

            computerMessageLayout.setVisibility(View.VISIBLE);
            userMessageLayout.setVisibility(View.GONE);

            computerTxt.setText(data.getContent());

        }else{
            computerMessageLayout.setVisibility(View.GONE);
            userMessageLayout.setVisibility(View.VISIBLE);

            userTxt.setText(data.getContent());
        }

       return row;
    }
}
