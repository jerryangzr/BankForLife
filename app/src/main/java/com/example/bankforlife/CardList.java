package com.example.bankforlife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CardList extends AppCompatActivity {

    private ListView mListView;
    private int[] myCardImage = new int[]{R.drawable.card1, R.drawable.card2, R.drawable.card3,
            R.drawable.card4, R.drawable.card5, R.drawable.card6,
            R.drawable.card7, R.drawable.card8, R.drawable.card9,
            R.drawable.card10};
    private String[] myCardTitle = new String[]{"現金回饋PLUS卡", "TCU 白金卡", "商旅御璽卡",
            "VISA一卡通御璽卡", "MayBank無限悠遊卡", "RCBC電子數位卡",
            "閃付Prime聯名卡", "i&M無限暢遊卡", "UOB鈦金桂冠卡",
            "RBC現金回饋卡"};
    private String[] myCardContent = new String[]{"即日起 ~ 2019/12/31", "2019/09/01 ~ 2019/12/31", "2019/03/01 ~ 2019/07/09",
            "2019/02/01 ~ 2019/09/31", "即日起 ~ 2019/10/31", "2019/06/15 ~ 2019/11/01",
            "2019/04/20 ~ 2019/10/24", "2019/08/08 ~ 2019/12/25", "即日起 ~ 2019/12/31",
            "2019/03/20 ~ 2019/08/31"};
    private ArrayList<CardData> cardList = null;
    private ArrayList<Integer> currentList;
    private int tmp = -1;
    private boolean Update = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardlist);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mListView = findViewById(R.id.card_list);
        cardList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CardData data = new CardData();
            data.setImage(myCardImage[i]);
            data.setTitle(myCardTitle[i]);
            data.setContent(myCardContent[i]);
            cardList.add(data);
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardListAdapter myAdapter = new CardListAdapter(cardList, inflater);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(onClickListView);
    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            int image = cardList.get(position).getImage();
//            ArrayList<Integer> tmpList = new ArrayList<>();
//            tmpList.add(position);
//            chosenList = tmpList;
            tmp = position;         // tmp 存下positon , 給下面去做判斷...
            System.out.println("Chosen card: " + tmp);
            Toast.makeText(CardList.this, "你選擇了 "+ myCardTitle[position] + " 卡!", Toast.LENGTH_SHORT).show();
//            mListView.getChildAt(position).setBackgroundColor();
//            view.setBackgroundColor(R.drawable.selector_cardlist_color);
//            Log.d("cardselection", "View isselected: " + view.isSelected());

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                int flag = 0;
                if (tmp != -1) {
                    Intent data = getIntent();
                    currentList = data.getIntegerArrayListExtra("currentList");
                    System.out.println("Previous List: " + currentList);
                    for (int i = 0; i < currentList.size(); i++) {
                        if (currentList.get(i).equals(tmp)) {
                            System.out.println("Repeated selection....");
                            Toast.makeText(CardList.this, "你已選過此卡 !!!", Toast.LENGTH_SHORT).show();
                            flag = -1;
                        }
                    }
                    if (flag != -1) {
                        System.out.println(currentList+ "#########");
                        Intent intent = new Intent(CardList.this, MainActivity.class);
//                        intent.putExtra("cardList", currentList);
                        intent.putExtra("newCard", tmp);
                        System.out.println("Chosen card: " + tmp);
                        setResult(RESULT_OK, intent);
                        this.finish();
                    }
                } else {
                    Update = false;
                    Intent intent = new Intent(CardList.this, MainActivity.class);
                    intent.putExtra("update", Update);
                    setResult(RESULT_OK, intent);
                    System.out.println("User Ain't clicking any card.......");
                    System.out.println("isUpdate:" + Update);
                    this.finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
