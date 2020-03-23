package kr.co.tjoeun.finalproject_01_baseballgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.tjoeun.finalproject_01_baseballgame.R;
import kr.co.tjoeun.finalproject_01_baseballgame.datas.Message;

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

        if (row == null) {
            row = inf.inflate(R.layout.message_list_item, null);
        }

        Message data = mList.get(position);

        LinearLayout computerMessageLayout = row.findViewById(R.id.computerMessageLayout);
        LinearLayout userMessageLayout = row.findViewById(R.id.userMessageLayout);


        if (data.getSpeaker().equals("COMPUTER")) {


        } else {    //data.getSpeaker().equals("USER")

        }

        return row;
    }
}
