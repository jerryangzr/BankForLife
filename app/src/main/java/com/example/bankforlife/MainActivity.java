package com.example.bankforlife;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SearchView mSearch = null;
    private ViewPager m_viewPager;
    private MyPagerAdapter myPagerAdapter;
    private Button mButton;
    private MapView mMapView;
    private ChipGroup mPopularGroup;
    private ChipGroup mChosenGroup;
    private ImageView Imv;
    private TextView mShow;
    private Chip mChip;

    private ArrayList<Integer> mList = new ArrayList<>();            // ViewPage imageId cardList
    private ArrayList<String> Chosen;           // the ArrayList got from MoreDetails..
    private ArrayList<String> mFinalList = null;       // Final ArrayList for SearchActivity
    private ArrayList<String> mSearchList = null;
//    private ArrayList<String> tmpList = new ArrayList<>();

    private int flag = 0;
    private boolean gps = false, isPopular = false, update = true;
    private String result = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChosenGroup = findViewById(R.id.chosen);

        mSearch = findViewById(R.id.search_tool);
        mSearch.setIconifiedByDefault(false);       // hide the original search icon
        mSearch.clearFocus();
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchList = new ArrayList<>();
                if (query.equals("gym")) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    mSearchList.add("Gym");
                    flag = 1;
                    System.out.println("<<Main>> List: " + mSearchList);
                    intent.putExtra("Input", mSearchList);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "查無結果 ！", Toast.LENGTH_SHORT).show();
                    flag = -1;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 1) {
                    System.out.println("<<Main>> User input some text ......");
                }
                return false;
            }
        });

        mMapView = findViewById(R.id.location);
        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationChoice.class);
                startActivity(intent);
            }
        });


        Imv = findViewById(R.id.profile);
        Imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileDetails.class);
                startActivity(intent);
            }
        });

        mChip = findViewById(R.id.more);
        mChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChosenGroup.removeAllViews();
                Intent intent = new Intent(MainActivity.this, MoreDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 100);
            }
        });

        mPopularGroup = findViewById(R.id.popular);
        mPopularGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                mChosenGroup.removeAllViews();
            }
        });

        m_viewPager = findViewById(R.id.viewpage);
        m_viewPager.setPageMargin(100);
        m_viewPager.setOffscreenPageLimit(10);

        mButton = findViewById(R.id.search_Button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = mPopularGroup.getCheckedChipId();
                mFinalList = new ArrayList<>();

                System.out.println("<<Main>> " + Chosen);
                System.out.println("<<Main>> " + flag);

                if (id != -1) {
                    if (id == R.id.more) {
                        if (Chosen.size() == 0) {
                            flag = -1;
                        }
                        mFinalList = Chosen;
                    } else {
                        Chip chip = findViewById(id);
                        mFinalList.add(chip.getText().toString());
                    }
                } else {
                    mFinalList = Chosen;
                    if (mFinalList == null) {
                        flag = -1;
                    } else {
                        if (mFinalList.size() != 0) {
                            mFinalList = Chosen;
                        } else {
                            flag = -1;
                        }
                    }
                }

                System.out.println("<<Main>> Final List: " + mFinalList);

                if (flag == -1) {
                    mButton.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Please select at least one type !", Toast.LENGTH_SHORT).show();
                    mButton.setEnabled(true);
                    flag = 0;
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    System.out.println("<<Main>> Final List: " + mFinalList);
                    intent.putExtra("Result", mFinalList);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                Chosen = data.getStringArrayListExtra("Chosen Tag");
                for (int i = 0; i < Chosen.size(); i++) {
                    ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Widget_MaterialComponents_Chip_Entry);
                    final Chip chip = new Chip(newContext);
                    chip.setText(Chosen.get(i));
                    chip.setTextSize(15);
                    chip.setCloseIconVisible(true);
                    chip.setCheckedIconVisible(false);
                    chip.setCheckable(true);
                    chip.setChipBackgroundColor(getResources().getColorStateList(R.color.common_google_signin_btn_text_light_disabled));
//                    chip.setChipStrokeColor(getResources().getColor());
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mChosenGroup.removeView(view);
                            System.out.println("<<Main>> Remove: " + chip.getText());
                            for (int j = 0; j < Chosen.size(); j++) {
                                if (Chosen.get(j) == chip.getText()) {
                                    Chosen.remove(j);
                                }
                            }
                            System.out.println("New Chosen list: " + Chosen);
                            if (Chosen.size() == 0) {
                                flag = -1;
                            }
                        }
                    });
                    mChosenGroup.addView(chip);
                }
            }
        } else if (requestCode == 110 && resultCode == RESULT_OK) {
            System.out.println("<<Main>> Checking ~~~~~~");
            Intent intent = getIntent();
            update = intent.getBooleanExtra("update", false);
            System.out.println("Need to Update? :" + update);
            if (data != null) {
                int newCard = data.getIntExtra("newCard", -1);
                if (newCard != -1) {
                    System.out.println("<<Main>> New Card from Cardlist: " + newCard);
                    System.out.println("<<Main>> New added card: " + newCard);
                    System.out.println("<<Main>> mList: " + mList);
                    Collections.reverse(mList);
                    mList.add(newCard);
                    Collections.reverse(mList);
                    System.out.println("<<Main>> Final list: " + mList);

                    ArrayList<String> tmpList = new ArrayList<>();              // ArrayList<Integer>: mList to ArrayList<String>: tmpList ....
                    for (int i = 0; i < mList.size(); i++) {
                        tmpList.add(mList.get(i).toString());
                    }
                    // Update the new cardList to SharedPreferences...
                    SharedPreferences pref = getSharedPreferences("cardData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    // Or SharedPreferences editor = getSharedPreferences("cardData",MODE_PRIVATE).edit();
                    String cardListString = TextUtils.join(",", mList);
                    editor.putString("cardList", cardListString);              // Use String instead of StringSet ...   (cuz SharedPreferences supports Set<String>, which is unordered>
                    //            editor.putStringSet("cardList", set);
                    editor.commit();

                    m_viewPager.setAdapter(myPagerAdapter);
                    System.out.println("New String: " + cardListString);
                } else {
                    Toast.makeText(this, "User didnt choose any card...", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 120 && resultCode == RESULT_OK) {
            Intent intent = getIntent();
            update = intent.getBooleanExtra("update", false);
            System.out.println("Update : " + update);
            System.out.println("User just checking the card details....");
        }
    }

    protected void onResume() {
        super.onResume();
        mSearch.clearFocus();
        mSearch.clearAnimation();

        mShow = findViewById(R.id.show);
        SharedPreferences pref = getSharedPreferences("locationData", MODE_PRIVATE);
        gps = pref.getBoolean("status", false);
        result = pref.getString("result", "location");
        isPopular = pref.getBoolean("isPopular", false);
//        set = pref.getStringSet("chosenList",new HashSet<String>());

        Log.d("main", "<<Main>> Status in main: " + gps);
        Log.d("main", "<<Main>> Result :" + result);
        Log.d("main", "<<Main>> isPopular :" + isPopular);
        Log.d("main", "<<Main>> !!!!!!!!!!");

        if (gps) {
            mMapView.setBackground(getDrawable(R.drawable.gpson));    // gps on, result = ... m
            System.out.println("<<Main>> RESUlt: " + result);
            mShow.setText("\t " + result + " m");
            mShow.setTextColor(Color.parseColor("#DC143C"));
        } else {                                                       // gps off, result = location.
            mMapView.setBackground(getDrawable(R.drawable.gpsoff));
            if (result != null) {
                mShow.setText(result);
            }
            mShow.setTextColor(Color.BLACK);
        }

//        Set<String> set = pref1.getStringSet("cardList", new LinkedHashSet<String>());      // Shared Preferences will still use HashSet when reading them back in ...
//        List<String> cardList = new ArrayList<>(set);               // Set to list
//        ArrayList<Integer> showList = new ArrayList<>();         // ArrayList<String> to ArrayList<Integer>
//        for (int i = 0; i < cardList.size(); i++) {
//            showList.add(Integer.parseInt(cardList.get(i)));
//        }

        System.out.println("isUpdate: " + update);
        if (update) {
            SharedPreferences pref1 = getSharedPreferences("cardData", MODE_PRIVATE);
            final String cardListString = pref1.getString("cardList", "-1");                // "-1" as default card
            System.out.println("String : " + cardListString);
            ArrayList<Integer> cardList = new ArrayList<>();         // tmpList to cardList ==> ArrayList<String> to ArrayList<Integer>
            if (!cardListString.isEmpty()) {
                ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(cardListString.split(",")));
                for (int i = 0; i < tmpList.size(); i++) {
                    cardList.add(Integer.parseInt(tmpList.get(i)));
                }
                mList = cardList;
            }

            CardUpdate(mList, "User Deleted One Card :", -1);

            if (mList.size() == 0) {
                mList.add(-1);
                m_viewPager.setAdapter(myPagerAdapter);
            }

            System.out.println("Showing list : " + cardList);

        } else {
            System.out.println("NO Update Needed....");
            System.out.println("Current List: " + mList);
        }
    }


    public void CardUpdate(ArrayList<Integer> List, final String msg, int flag) {

        System.out.println("Calling CardUpdate....." + flag);
        System.out.println("Ready to Updated list :" + List);

        if (flag == -1) {            //  When User manually chose to delete the card, Update the list...
            System.out.println("Updated the CardList !!!");

            myPagerAdapter = new MyPagerAdapter(this, List, new MyPagerAdapter.CardUpdateListener() {       // Called when User deleted the Card
                @Override
                public void onUpdate(List<Integer> data) {
                    System.out.println("User Deleted One Card...");
                    Log.d("card", msg + data);
                    mList = (ArrayList<Integer>) data;
                    SharedPreferences pref = getSharedPreferences("cardData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    String cardListString = TextUtils.join(",", mList);
                    editor.putString("cardList", cardListString);
                    editor.commit();
                }
            });
        }
        m_viewPager.setAdapter(myPagerAdapter);
    }
}

//            myPagerAdapter = new MyPagerAdapter(this, mList, new MyPagerAdapter.CardUpdateListener() {
//                @Override
//                public void onUpdate(List<Integer> updateData) {
//                    System.out.println("Called by User going back to menu: " + updateData);
//                }
//            });
//            m_viewPager.setAdapter(myPagerAdapter);