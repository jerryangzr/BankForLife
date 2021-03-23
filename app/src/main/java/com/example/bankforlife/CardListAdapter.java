package com.example.bankforlife;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class CardListAdapter extends BaseAdapter {

    private ArrayList<CardData> cardList;
    private LayoutInflater inflater;

    public class ViewHolder {
        protected TextView cardTitle;
        protected TextView cardContent;
        private ImageView image;
    }


    public CardListAdapter(ArrayList<CardData> cardList,LayoutInflater inflater) {
        this.cardList = cardList;
//        this.cardContent = cardContent;
        this.inflater = inflater;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getCount() {
        return cardList.size();
    }

    //get the item
    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.view_cardlist, null, true);
            holder.cardTitle = convertView.findViewById(R.id.title_cardlist);
            holder.cardContent = convertView.findViewById(R.id.content_cardlist);
            holder.image = convertView.findViewById(R.id.image_cardlist);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (cardList.get(position) != null) {
            holder.cardTitle.setText(cardList.get(position).getTitle());
            holder.cardContent.setText(cardList.get(position).getContent());
            holder.image.setImageResource(cardList.get(position).getImage());
            holder.cardTitle.setTextColor(Color.parseColor("#000000"));
            holder.cardContent.setTextColor(Color.parseColor("#696969"));
            holder.cardContent.setTextSize(15);

        } else {
            holder.cardTitle.setText("No Title...");
            holder.cardContent.setText("Empty Content");
            holder.image.setImageResource(R.drawable.card0);
        }
        return convertView;
    }
}