package com.example.bankforlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MoreDetails extends AppCompatActivity {
    private ChipGroup mCategoryGroup;
    private ChipGroup mChoiceGroup;

    private String[] Foods = {"Cafe", "Restaurant", "FastFood", "Beef Noodle", "Boba Shop", "Night Market", "Supermarket", "菜市場", "Costo"};
    private String[] Clothes = {"ZARA", "Uniqulo", "H&M", "GQ", "Nike", "Adidas"};
    private String[] Living = {"Hotel", "Airbnb", "Park", "B&Q", "IKEA", "Bank", "Barber", "ATM"};
    private String[] Transportation = {"MRT", "Bus", "Airport", "Train", "UBike", "Boat", "Parking Lot"};
    private String[] Edu = {"Library", "書局", "補習班", "College", "Exhibition"};
    private String[] Entertainment = {"Mall", "Movie", "Pool", "KTV", "遊樂場", "Snooker", "Bowling", "網吧", "Bar", "GYM", "Club"};

    private ArrayList<String> chosenList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mChoiceGroup = findViewById(R.id.choices);
        mCategoryGroup = findViewById(R.id.category);

        mCategoryGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {         // Click Six Categories ...
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int checkedId) {
                System.out.println("Clicked.....");
                mChoiceGroup.removeAllViews();

                if (checkedId == R.id.eat) {
                    addFoodChip();
                } else if (checkedId == R.id.cloth) {
                    addClothChip();
                } else if (checkedId == R.id.living) {
                    addLivingChip();
                } else if (checkedId == R.id.commute) {
                    addTransportationChip();
                } else if (checkedId == R.id.education) {
                    addEduChip();
                } else if (checkedId == R.id.entertainment) {
                    addEntertainmentChip();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                int count = 0;
                chosenList = new ArrayList<>();
                for (int i = 0; i < mChoiceGroup.getChildCount(); i++) {
                    Chip chip = (Chip) mChoiceGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        chosenList.add(chip.getText().toString());
                        count++;
                    }
                }
                if(count <= 5){
                    Intent data = new Intent();
                    data.putExtra("Chosen Tag", chosenList);
                    setResult(RESULT_OK, data);
                    this.finish();
                    return true;
                }else{
                    Toast.makeText(MoreDetails.this, "最多選 5 種!", Toast.LENGTH_SHORT).show();
                }

                System.out.println("Child: "+count);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addFoodChip() {
        chipSetting(Foods);
    }

    private void addClothChip() {
        chipSetting(Clothes);
    }

    private void addLivingChip() {
        chipSetting(Living);
    }

    private void addTransportationChip() {
        chipSetting(Transportation);
    }

    private void addEduChip() {
        chipSetting(Edu);
    }

    private void addEntertainmentChip() {
        chipSetting(Entertainment);
    }

    private void chipSetting(String type[]) {
        for (int i = 0; i < type.length; i++) {
            ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.Widget_MaterialComponents_Chip_Entry);
            Chip chip = new Chip(newContext);
            chip.setText(type[i]);
            chip.setCheckedIconVisible(false);
            chip.setCloseIconVisible(false);
            chip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_stroke_color));
            chip.setCheckable(true);
            chip.setChipStrokeWidth(3);
            chip.setTextSize(18);
            mChoiceGroup.addView(chip);
        }
    }
}
