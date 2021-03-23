package com.example.bankforlife;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<Integer> viewLists;
    private Activity activity;
    private ImageView img;
    private CardUpdateListener listener;

    public MyPagerAdapter(Activity activity, ArrayList<Integer> viewLists, CardUpdateListener listener) {
        super();
        this.viewLists = viewLists;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        View v = LayoutInflater.from(activity).inflate(R.layout.view_card, null, false);
        System.out.println("Putting Image: " + viewLists.get(position));
        final int index = viewLists.get(position);
        switch (index) {
            case -1:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card0_2);
                break;
            case 0:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card1);
                break;
            case 1:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card2);
                break;
            case 2:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card3);
                break;
            case 3:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card4);
                break;
            case 4:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card5);
                break;
            case 5:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card6);
                break;
            case 6:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card7);
                break;
            case 7:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card8);
                break;
            case 8:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card9);
                break;
            case 9:
                img = v.findViewById(R.id.imageView);
                img.setImageResource(R.drawable.card10);
                break;
        }


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    Intent intent = new Intent(activity, CardDetails.class);
                    intent.putExtra("cardNum", index);
                    System.out.println("Card: " + (index + 1) + " !!!!");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivityForResult(intent,120);
                } else {
                    Intent intent = new Intent(activity, CardList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("currentList", viewLists);
                    Toast.makeText(activity, "Empty Card!", Toast.LENGTH_SHORT).show();
                    activity.startActivityForResult(intent, 110);
                    System.out.println("List from Menu !!!: " + viewLists);
                }
            }
        });

        if(viewLists.get(position) !=-1){
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    new AlertDialog.Builder(activity)
                            .setMessage("確認刪除此卡 ? ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    viewLists.remove(position);
                                    if(listener!=null) {
                                        listener.onUpdate(viewLists);
                                    }
                                    if (viewLists != null) {
                                        notifyDataSetChanged();
                                    }
                                    System.out.println("ViewList: " + viewLists);
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                    return true;
                }
            });
        }

        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    interface CardUpdateListener {
        void onUpdate(List<Integer> data);
    }
}
