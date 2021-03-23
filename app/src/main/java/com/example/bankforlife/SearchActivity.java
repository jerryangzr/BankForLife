package com.example.bankforlife;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String mType;
    private ChipGroup mChipGroup;
    private ListView mListView;
    private ArrayList<String> mTitleList;
    private ArrayList<String> mAddressList = new ArrayList<>();
    private ArrayList<Double> mRatingList = new ArrayList<>();
    private ArrayList<Integer> mDistanceList = new ArrayList<>();
    private ArrayList<MyListAdapter.SearchResult> mResultList = new ArrayList<>();
    private Spinner mSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int tmp = intent.getIntExtra("flag", 0);
        if (tmp == 1) {
            mTitleList = intent.getStringArrayListExtra("Input");          // Result got from user's Input
        } else {
            mTitleList = intent.getStringArrayListExtra("Result");         // Result got from user's Choices
        }

        mChipGroup = findViewById(R.id.chosenTag);
        for (int i = 0; i < mTitleList.size(); i++) {
            ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Widget_MaterialComponents_Chip_Entry);
            Chip chip = new Chip(newContext);
            String txt = mTitleList.get(i);
            chip.setText(txt);
            chip.setTextSize(15);
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#cccccc")));
            chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#5c5d5e")));
            chip.setChipStrokeWidth(3);
            chip.setCheckable(true);
            chip.setFocusable(true);
            chip.setRippleColor(ColorStateList.valueOf(Color.parseColor("#d3d3d3")));
            chip.setCloseIconVisible(true);
            chip.setCheckedIconVisible(false);
            mChipGroup.addView(chip);

//            chip.setOnCloseIconClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mChipGroup.removeView(view);
//                    System.out.println("Remove the tag...");
//                }
//            });
        }

        Log.d("searchResult", "Result list from Main:" + mTitleList);
        mType = mTitleList.get(0);
        prepList(mTitleList);

        mListView = findViewById(R.id.listView);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyListAdapter adapter = new MyListAdapter(mResultList, inflater);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(onClickListView);


        mSpinner = findViewById(R.id.filter);
        final List<String> list = new ArrayList<>();
        list.add(0, "Choose...");
        list.add(1, "距離最近");
        list.add(2, "評分最高");
        list.add(3, "價格最低");
        list.add(4, "最多瀏覽");
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, list);
        mSpinner.setAdapter(filterAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(SearchActivity.this, "Sort By...", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(SearchActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                        sortByDistance();
                        break;
                    case 2:
                        Toast.makeText(SearchActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                        sortByRating();
                        break;
                    case 3:
                        Toast.makeText(SearchActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(SearchActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(SearchActivity.this, "Click the " + (position + 1) + " Item \nContent：" + mTitleList.get(position), Toast.LENGTH_SHORT).show();
            String Item = mTitleList.get(position);
            System.out.println(Item);
            Intent intent = new Intent(SearchActivity.this, ResultDetails.class);
            intent.putExtra("Title", mResultList.get(position).getTitle());
            intent.putExtra("Type", mType);
            startActivity(intent);
        }
    };

    private void prepList(ArrayList<String> resultList) {

        if (resultList.size() == 1) {
            switch (resultList.get(0)) {            // get the selected tag...
                case "Gym":
                case "GYM":
                    mTitleList.add("World Gym");
                    mTitleList.add("Pure Gym");
                    mTitleList.add("健身工廠");
                    mTitleList.add("體育課");
                    mTitleList.add("運動中心");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "ATM":
                    mTitleList.add("彰化銀行ATM");
                    mTitleList.add("國泰世華銀行ATM");
                    mTitleList.add("元大商業銀行ATM");
                    mTitleList.add("台北富邦銀行ATM");
                    mTitleList.add("上海商業銀行ATM");
                    mTitleList.add("台北敦南郵局ATM");
                    mTitleList.add("台新銀行ATM");


                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "Cafe":
                    mTitleList.add("Starbucks");
                    mTitleList.add("老咖啡館");
                    mTitleList.add("Costa");
                    mTitleList.add("丹提咖啡");
                    mTitleList.add("莫凡彼咖啡館");
                    mTitleList.add("Mr. Brown");
                    mTitleList.add("西雅圖極品咖啡");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "Parking":
                    mTitleList.add("星屋停車場");
                    mTitleList.add("雷鋒停車場");
                    mTitleList.add("大安行政中心停車場");
                    mTitleList.add("UPARK");
                    mTitleList.add("千里亭停車場");
                    mTitleList.add("敦和停車場");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "Airport":
                    mTitleList.add("台北松山機場");
                    mTitleList.add("臺灣桃園國際機場");
                    mTitleList.add("桃園機場捷運");
                    mTitleList.add("松山機場站");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "Mall":
                    mTitleList.add("京華城");
                    mTitleList.add("微風廣場");
                    mTitleList.add("台北地下街");
                    mTitleList.add("SOGO");
                    mTitleList.add("新光山越");
                    mTitleList.add("台北 101");
                    mTitleList.add("大元百貨");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "Pool":
                    mTitleList.add("榮興游泳池");
                    mTitleList.add("台北市中山運動中心");
                    mTitleList.add("松山國小游泳池");
                    mTitleList.add("台北市立三民游泳池");
                    mTitleList.add("龍門國中游泳池");
                    mTitleList.add("新生公園游泳池");
                    mTitleList.add("臺大游泳館");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "College":
                    mTitleList.add("台灣大學醫學院");
                    mTitleList.add("國立臺灣師範大學");
                    mTitleList.add("臺北醫學大學");
                    mTitleList.add("輔仁大學");
                    mTitleList.add("臺北科技大學");
                    mTitleList.add("國立政治大學");
                    mTitleList.add("中國科技大學");
                    mTitleList.add("世新大學");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
                case "Barber":
                    mTitleList.add("台北油頭TPE.Barber");
                    mTitleList.add("小林髮廊");
                    mTitleList.add("N Hair");
                    mTitleList.add("ZENO.hair");
                    mTitleList.add("MOD'S HAIR");
                    mTitleList.add("QB HOUSE 忠孝店");
                    mTitleList.add("費加洛造型髮廊");

                    Collections.shuffle(mTitleList);
                    Log.d("searchResult", "Title list: " + mTitleList);
                    break;
            }
        }else{
            // do nothing..
        }

        mRatingList.add(3.7);
        mRatingList.add(4.7);
        mRatingList.add(3.9);
        mRatingList.add(4.2);
        mRatingList.add(3.0);
        mRatingList.add(5.0);
        mRatingList.add(2.5);
        mRatingList.add(3.5);
        mRatingList.add(4.6);
        mRatingList.add(2.9);
        mRatingList.add(1.8);
        mRatingList.add(3.9);
        mRatingList.add(4.4);
        mRatingList.add(4.1);


        Collections.shuffle(mRatingList);    // Randomize the number's order....
        Log.d("searchResult", "Rating list: " + mRatingList);

        mAddressList.add("台北市中山區八德路二段45號");
        mAddressList.add("台北市敦化南路一段232巷6號");
        mAddressList.add("台北市忠孝東路四段54號3樓");
        mAddressList.add("台北市忠孝東路四段218號2樓");
        mAddressList.add("台北市新店區河州街二段30號");
        mAddressList.add("台北市中和區安和街ㄧ段17號");
        mAddressList.add("新北市三重光復路四段222號");
        mAddressList.add("新北市板橋區安寧路100號");
        mAddressList.add("台北市大安區重慶路ㄧ段49號");
        mAddressList.add("台北市文山區指南路二段64號");
        mAddressList.add("台北市文山區試院路156號");
        mAddressList.add("台北市大安區羅斯福路四段");
        mAddressList.add("新北市中和區工專路111號");


        Collections.shuffle(mAddressList);
        Log.d("searchResult", "Address list: " + mAddressList);

        mDistanceList.add(500);
        mDistanceList.add(372);
        mDistanceList.add(120);
        mDistanceList.add(700);
        mDistanceList.add(240);
        mDistanceList.add(90);
        mDistanceList.add(30);
        mDistanceList.add(260);
        mDistanceList.add(75);
        mDistanceList.add(205);
        mDistanceList.add(310);
        mDistanceList.add(160);
        mDistanceList.add(730);

        Collections.shuffle(mDistanceList);


        for (int i = 0; i < mTitleList.size(); i++) {                       // store each row as one object in mResultList .....
            mResultList.add(new MyListAdapter.SearchResult(mTitleList.get(i), mRatingList.get(i), mAddressList.get(i), mDistanceList.get(i)));
        }
    }

    private void sortByRating() {                   // Called when user click the spinner...
        Log.d("searchResult", "mResultList : " + mResultList);

        Collections.sort(mResultList, new Comparator<MyListAdapter.SearchResult>() {
            @Override
            public int compare(MyListAdapter.SearchResult searchResult, MyListAdapter.SearchResult t1) {        // Descending order
                if (t1.getRating() > searchResult.getRating()) return 1;
                if (t1.getRating() < searchResult.getRating()) return -1;
                return 0;
            }
        });
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyListAdapter adapter = new MyListAdapter(mResultList, inflater);
        mListView.setAdapter(adapter);

        showResult();
    }

    private void sortByDistance() {
        Collections.sort(mResultList, new Comparator<MyListAdapter.SearchResult>() {
            @Override
            public int compare(MyListAdapter.SearchResult searchResult, MyListAdapter.SearchResult t1) {           // Ascending order
                if (t1.getDistance() > searchResult.getDistance()) return -1;
                if (t1.getDistance() < searchResult.getDistance()) return 1;
                return 0;
            }
        });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyListAdapter adapter = new MyListAdapter(mResultList, inflater);
        mListView.setAdapter(adapter);

        showResult();
    }

    private void showResult() {
        for (int i = 0; i < mResultList.size(); i++) {
            Log.d("searchResult", "sortByRating Title:" + mResultList.get(i).getTitle());
            Log.d("searchResult", "sortByRating Rating:" + mResultList.get(i).getRating());
            Log.d("searchResult", "sortByRating Address:" + mResultList.get(i).getAddress());
            Log.d("searchResult", "sortByRating Distance:" + mResultList.get(i).getDistance());
        }
    }
}
