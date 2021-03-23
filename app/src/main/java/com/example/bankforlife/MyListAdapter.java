package com.example.bankforlife;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MyListAdapter extends BaseAdapter {

    private ArrayList<SearchResult> mResultList;
    private LayoutInflater inflater;   // Load the layout
    private int count = 0;

    static class ViewHolder {
        LinearLayout Result;
        TextView Order;
        TextView Title;
        TextView Rating;
        TextView Address;
        TextView Distance;
        ImageView Stars;
    }

    public static class SearchResult {                  // Create searchResult object for each row ....
        private String title;
        private double rating;
        private String address;
        private int distance;

        public SearchResult(String name, double rating, String address , int distance) {
            this.title = name;
            this.rating = rating;
            this.address = address;
            this.distance = distance;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String name) {
            this.title = name;
        }


        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

    }

    //Initialization
    public MyListAdapter(ArrayList<SearchResult> mResultList, LayoutInflater inflater) {
        this.mResultList = mResultList;
        this.inflater = inflater;
    }

    //get the size of arrayList
    @Override
    public int getCount() {
        return mResultList.size();
    }

    //get the item
    @Override
    public Object getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //當ListView被拖拉時會不斷觸發getView，為了避免重複加載必須加上這個判斷
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.view_result, null);
            holder.Order = (TextView) convertView.findViewById(R.id.order);
            holder.Title = (TextView) convertView.findViewById(R.id.title);
            holder.Rating = (TextView) convertView.findViewById(R.id.rating);
            holder.Stars = (ImageView) convertView.findViewById(R.id.ratingstar);
            holder.Address = (TextView) convertView.findViewById(R.id.address);
            holder.Distance = (TextView) convertView.findViewById(R.id.distance);
            holder.Result = (LinearLayout) convertView.findViewById(R.id.resultBorder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


//        if(count < mResultList.size()){
//            count++;
//            String order = count + "";
            holder.Order.setText(position+1+"");
//            Log.d("order", "Order: " + order);
//        }

        if (mResultList.get(position) != null) {
            holder.Title.setText(mResultList.get(position).getTitle());
            System.out.println("Title :" + mResultList.get(position).getTitle());

//            holder.Rating.setText(mResultList.get(position).getRating() + "");
            holder.Rating.setText(String.valueOf(mResultList.get(position).getRating()));

            if (mResultList.get(position).getRating() > 4 && mResultList.get(position).getRating() <= 5) {
                holder.Stars.setImageResource(R.drawable.stars4);
            } else if (mResultList.get(position).getRating() > 3 && mResultList.get(position).getRating() <= 4) {
                holder.Stars.setImageResource(R.drawable.stars3);
            } else if (mResultList.get(position).getRating() > 2 && mResultList.get(position).getRating() <= 3) {
                holder.Stars.setImageResource(R.drawable.stars2);
            } else if (mResultList.get(position).getRating() > 1 && mResultList.get(position).getRating() <= 2) {
                holder.Stars.setImageResource(R.drawable.stars1);
            } else if (mResultList.get(position).getRating() > 0 && mResultList.get(position).getRating() <= 1) {
                holder.Stars.setImageResource(R.drawable.stars0);
            }

            holder.Address.setText(mResultList.get(position).getAddress());
            holder.Distance.setText(mResultList.get(position).getDistance() + " m");
            holder.Result.setBackgroundColor(Color.parseColor("#dcdff2"));
        } else {
            holder.Title.setText("Null");
            holder.Order.setText("Nothing...");
            holder.Rating.setText("No Rating...");
            holder.Address.setText("No Address...");
            holder.Stars.setImageResource(R.drawable.stars4);
            holder.Distance.setText("0 m");
        }
        return convertView;
    }

}
