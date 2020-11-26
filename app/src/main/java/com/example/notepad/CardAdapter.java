package com.example.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.notepad.getdb.GetDBData;

import java.util.ArrayList;

/**
 * @author boukyuan
 */
public class CardAdapter extends BaseAdapter {
    private final Context context;
    private final LayoutInflater layoutInflater;
    ArrayList<ArrayList<String>> cardList;

    CardAdapter(Context content, ArrayList<ArrayList<String>> cardList) {
        this.context = content;
        layoutInflater = LayoutInflater.from(content);
        this.cardList = cardList;
    }

    @Override
    public int getCount() {
        //数组长度
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
//        getItem : 根据一个索引（位置）获得该位置的对象
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
//        getItemId : 获取条目的id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.card_list, null);
            viewHolder = new ViewHolder();
            viewHolder.text = convertView.findViewById(R.id.card_text);
            viewHolder.title = convertView.findViewById(R.id.card_title);
            viewHolder.buttonText = convertView.findViewById(R.id.card_button);
            viewHolder.deleteButton = convertView.findViewById(R.id.card_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(cardList.get(position).get(1));
        viewHolder.title.setText(cardList.get(position).get(2));
        viewHolder.buttonText.setText(cardList.get(position).get(1).substring(0, 1));
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库上删除
                GetDBData getDBData = new GetDBData();
                getDBData.deleteData(context, cardList.get(position).get(0));
                //视图上删除
                cardList.remove(getItem(position));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        public TextView text;
        public TextView title;
        public Button buttonText;
        public Button deleteButton;
    }
}
