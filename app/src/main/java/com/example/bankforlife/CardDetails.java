package com.example.bankforlife;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class CardDetails extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mCard, mPromotion, mPromotion1, mPromotion2;
    private TextView mContent1, mContent2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mTitle = findViewById(R.id.info_Title);
        mCard = findViewById(R.id.cardImage);
        mContent1 = findViewById(R.id.period);
        mPromotion = findViewById(R.id.promotion);
        mPromotion1 = findViewById(R.id.promotion1);
//        mPromotion2 = findViewById(R.id.promotion2);
        mContent2 = findViewById(R.id.details);
//        mContent3 = findViewById(R.id.title3);


        Intent intent = getIntent();
        final int position = intent.getIntExtra("cardNum", -1);

        switch (position) {
            case 0:
                mTitle.setText("現金回饋PLUS卡");
                mCard.setBackgroundResource(R.drawable.card1);
                mContent1.setText(" 即日起 ~ 2019/12/31");
                mPromotion.setImageResource(R.drawable.promotion1_1);
                mPromotion1.setImageResource(R.drawable.promotion1_2);
                mContent2.setText("現金回饋御璽卡新戶指定網購享最高6.88%現金回饋，係指指定網路購物消費金額可享原1.88%現金回饋+新戶額外5%現金回饋，新戶額外5%現金回饋之上限為NT$500。\n");
//                tv2.append(" Jackson Wu");
//                tv3.append(" NTD$ 17,500");
                break;
            case 1:
                mTitle.setText("RCBC電子數位卡");
                mCard.setBackgroundResource(R.drawable.card2);
                mContent1.setText(" 2019/09/01 ~ 2019/12/31");
                mPromotion.setImageResource(R.drawable.promotion2_1);
                mPromotion1.setImageResource(R.drawable.promotion2_2);
                mContent2.setText("1. 本優惠案限於中國東方航空台灣官網，持星展炫晶御璽卡刷卡購買由台灣始發並符合指定艙等及航點，即享優惠折扣，促銷代碼優惠僅限機票價格，稅費除外。（註：支付方式請點選臺灣地區發行信用卡）\n" +
                        "2. 本優惠案促銷代碼不得與中國東方航空台灣官網之其他促銷活動合併使用，優惠僅能擇一使用，包含其他促銷代碼折抵優惠票價。\n" +
                        "3. 本優惠案活動限制皆須於『促銷代碼欄位』輸入適用之促銷代碼（半形大寫英文），逾期作廢。如未輸入者，完成付款後，亦不得追溯過往，要求享有其優惠。如有去回程訂購不同艙位混合之情形，促銷折扣將從嚴適用，例如：兩岸直航【去程S艙+回程M艙】僅能適用S艙優惠享機票價格10%。\n" +
                        "4. 本優惠案商務艙、頭等艙適用『暢行專享接送機服務』，實際申請辦法請依照相關網頁說明為準。\n" +
                        "5. 本優惠案適用『機上WIFI』和『增值服務』和『隔夜中轉飯店服務』，實際申請辦法請依照相關網頁說明為準。");
//                tv1.append(" 896361413731");
//                tv2.append(" Takashi Matsumoto");
//                tv3.append(" NTD$ 82,500");
                break;
            case 2:
                mTitle.setText("商旅御璽卡");
                mCard.setBackgroundResource(R.drawable.card3);
                mContent1.setText(" 2019/03/01 ~ 2019/12/31");
                mPromotion.setImageResource(R.drawable.promotion3_1);
                mPromotion1.setImageResource(R.drawable.promotion3_2);
                mContent2.setText("1. 台北北海高爾夫鄉村俱樂部\t\n" +
                        "平日早鳥優惠價新台幣2,180元，每月限前10名使用。\n" +
                        "平日優惠價新台幣2,475元，假日優惠價新台幣3,380。每月限前20名使用。\n" +
                        "2. 桃園大溪高爾夫俱樂部\n" +
                        "平日早球優惠價新台幣3,100元，每月限前10名使用。" +
                        "平日優惠價新台幣3,600元，假日優惠價新台幣6,250元，每月限前20名使用。\n" +
                        "本優惠僅限星展飛行世界卡持卡人本人使用，前月不限金額刷卡1筆，並於前月入帳(核卡當月恕不適用本優惠)。符合上述資格之持卡人逢週六及週日每日可停1次(正、附卡合併計算消費金額及使用次數)，跨日取車需取車日於週六及週日始可適用取車日當日一次停車優惠，每次最高4小時停車優惠，每日每次實際停車時數若不足4小時，不得要求遞延使用該次未完全使用之停車優惠時數，且不得併入他次優惠時數計算。");
//                tv1.append(" 931245664897");
//                tv2.append(" Maggie Tsai");
//                tv3.append(" NTD$ 37,450");
                break;
            case 3:
                mTitle.setText("VISA一卡通御璽卡");
                mCard.setBackgroundResource(R.drawable.card4);
                mContent1.setText(" 2019/02/01 ~ 2019/09/31");
                mPromotion.setImageResource(R.drawable.promotion4_1);
                mPromotion1.setImageResource(R.drawable.promotion4_2);
                mContent2.setText("1. 本活動適用於萬事達卡國際組織台灣、香港、澳門及中國發卡機構所發行之World Elite Mastercard世界之極卡、World Mastercard世界卡、世界商務卡正卡及附卡持卡人。\n" +
                        "2. 本優惠不接受持卡人於飯店直接使用且不可與其他優惠一起使用。" +
                        "舉凡海內外住房安排、餐廳預約、緊急醫療救援等事項，無論您身在何處，只須致電全球禮賓尊榮秘書團隊，我們便會立即為您提供諮詢與安排。" +
                        "全天候24 小時服務專線：04-2206-7802。");

//                tv1.append(" 899248604451");
//                tv2.append(" Kelly Wang");
//                tv3.append(" NTD$ 37,450");
                break;
            case 4:
                mTitle.setText("MayBank無限悠遊卡");
                mCard.setBackgroundResource(R.drawable.card5);
                mContent1.setText(" 即日起 ~ 2019/10/31");
                mPromotion.setImageResource(R.drawable.promotion5_1);
                mPromotion1.setImageResource(R.drawable.promotion5_2);
                mContent2.setText("1. 國際機場停車8折起優惠\n" +
                        "2. 康是美刷花旗信用以Apple Pay支付 單筆滿888現折100元 至2019/12/31止 各活動之細節及相關限制請詳各活動頁面。");
//                tv1.append(" 107539443876");
//                tv2.append(" James Liang");
//                tv3.append(" NTD$ 27,450");
                break;
            case 5:
                mTitle.setText("TCU 白金卡");
                mCard.setBackgroundResource(R.drawable.card6);
                mContent1.setText(" 2019/06/15 ~ 2019/11/01");
                mPromotion.setImageResource(R.drawable.promotion6_1);
                mPromotion1.setImageResource(R.drawable.promotion6_2);
                mContent2.setText("當月新增消費滿1筆NT$888(含)以上，次月即享電影票價最優75折優惠(取票時，影城櫃台將收取每張20元訂票手續費，每人每月購票限購8張)。");
                mContent2.append("\n出國刷當次國際線機票全額或國外旅遊團費80%以上，可享公共運輸工具期間最高NT$3000萬旅行平安險，讓您外出旅遊更安心！");
//                tv1.append(" 543791349074");
//                tv2.append(" Harry Potter");
//                tv3.append(" NTD$ 90,765");
                break;
            case 6:
                mTitle.setText("閃付Prime聯名卡");
                mCard.setBackgroundResource(R.drawable.card7);
                mContent1.setText(" 2019/04/20 ~ 2019/10/24");
                mPromotion.setImageResource(R.drawable.promotion7_1);
                mPromotion1.setImageResource(R.drawable.promotion7_2);
                mContent2.setText("1. 購買國際線機票全額或國外團費總金額8成\n" +
                        "以上，且付款成功者，一年享8次國際機場接或送優惠價格單趟$588起 即日起至~2019/12/31\n" +
                        "2. 精選美饌最優5折起\n" +
                        "刷花旗讓您天天享美味 處處享優惠 即日起至 2019/12/31");
//                tv1.append(" 384895402461");
//                tv2.append(" Allen Kao");
//                tv3.append(" NTD$ 3,450");
                break;
            case 7:
                mTitle.setText("i&M無限暢遊卡");
                mCard.setBackgroundResource(R.drawable.card8);
                mContent1.setText(" 2019/08/08 ~ 2019/12/25");
                mPromotion.setImageResource(R.drawable.promotion8_1);
                mPromotion1.setImageResource(R.drawable.promotion8_2);
                mContent2.setText("出國刷當次國際線機票全額或國外旅遊團費80%以上，即享一年最高15天、不限次數的免費桃園國際機場週邊停車服務（正、附卡合併計算，若持有本行其它卡別亦須合併計算）。" +
                        "\n網路預約：www.freeliving.com.tw/visa\n" + "電話預約：04-2206-4655");
//                tv1.append(" 739473927626");
//                tv2.append(" Matthew Ma");
//                tv3.append(" NTD$ 225,200");
                break;
            case 8:
                mTitle.setText("UOB鈦金桂冠卡");
                mCard.setBackgroundResource(R.drawable.card9);
                mContent1.setText(" 即日起 ~ 2019/12/31");
                mPromotion.setImageResource(R.drawable.promotion9_1);
                mPromotion1.setImageResource(R.drawable.promotion9_2);
                mContent2.setText("汽油最優降1.2元/公升\n" +
                        "全國加油站汽油最優每公升降1.2元 至2019/12/31止\n" +
                        "平日6折起、假日85折至 2019/12/31止\n" +
                        "各活動之細節及相關限制請詳各活動頁面。");
//                tv1.append(" 997462629573");
//                tv2.append(" Sharron Lu");
//                tv3.append(" NTD$ 179,00");
                break;
            case 9:
                mTitle.setText("RBC現金回饋卡");
                mCard.setBackgroundResource(R.drawable.card10);
                mContent1.setText(" 2019/03/20 ~ 2019/08/31");
                mPromotion.setImageResource(R.drawable.promotion10_1);
                mPromotion.setPadding(1, 1, 1, 1);
                mPromotion1.setImageResource(R.drawable.promotion10_2);
                mPromotion1.setPadding(1, 0, 1, 0);
                mContent2.setText("1. 本優惠不可轉讓，僅限持卡人使用，僅對所示商品或服務有效，不可兌換現金。\n" +
                        "2. 本優惠不可與KKBOX其他優惠一起使用。\n" +
                        "3. 本優惠每人每月限購買一次，每月數量有限，售完為止。\n" +
                        "4. 本優惠服務之使用流程、結帳及退款等等相關之使用辦法，皆以活動網站 規定為準。\n" +
                        "5. KKBOX另外之特殊限制條件，依KKBOX規定辦理，KKBOX並保有更改優惠之權利。");
//                tv1.append(" 245873694790");
//                tv2.append(" Vincent Lin");
//                tv3.append(" NTD$ 327,290");
                break;
        }

        ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(500);
        fade_in.setFillAfter(true);
        mPromotion.startAnimation(fade_in);
        mPromotion1.startAnimation(fade_in);

        mPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No Thanks", Snackbar.LENGTH_LONG)
                        .setAction("Show more", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String website = "";
                                switch (position) {
                                    case 0:
                                        website = "https://dbs.com.tw/";
                                        break;
                                    case 1:
                                        website = "https://o-bank.com/";
                                        break;
                                    case 2:
                                        website = "https://card.ubot.com.tw/";
                                        break;
                                    case 3:
                                        website = "https://www.citibank.com.tw/global_docs/chi/cc/190812_eztravel/index.html?icid=TWCCUTKZHMAINHBKM";
                                        break;
                                    case 4:
                                        website = "https://cathaybk.com.tw/";
                                        break;
                                    case 5:
                                        website = "https://service.standardchartered.com.tw/ssl/campaign/credit-cards/signature-card.asp?utm_source=GoogleSearch&utm_medium=cpc&gclsrc=aw.ds&&gclid=CjwKCAjw1_PqBRBIEiwA71rmtbjtRgE_s11tKWGd_Cex0jvC2XgOf94-ucq67AGO9y6nahVqPkPgMhoCIIwQAvD_BwE";
                                        break;
                                    case 6:
                                        website = "https://visa.com.tw/pay-with-visa/visa-offers-and-perks/offer-details.html?offerId=126670";
                                        break;
                                    case 7:
                                        website = "https://www.esunbank.com.tw/bank/personal/credit-card/discount/shopinfo?sno=9995";
                                        break;
                                    case 8:
                                        website = "https://www.feib.com.tw/creditCardMgm/p2?empbranch=VD2047";
                                        break;
                                    case 9:
                                        website = "https://www.esunbank.com.tw/bank/personal/credit-card/intro/co-branded-card/c4-card";
                                        break;
                                }
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                                startActivity(browserIntent);
                            }
                        })
                        .setActionTextColor(Color.YELLOW)
                        .show();
            }
        });

        mPromotion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "No Thanks", Snackbar.LENGTH_LONG);
                snackbar.setAction("Show more", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String website = "";
                        switch (position) {
                            case 0:
                                website = "https://dbs.com.tw/";
                                break;
                            case 1:
                                website = "https://o-bank.com/";
                                break;
                            case 2:
                                website = "https://card.ubot.com.tw/";
                                break;
                            case 3:
                                website = "https://www.citibank.com.tw/global_docs/chi/cc/190812_eztravel/index.html?icid=TWCCUTKZHMAINHBKM";
                                break;
                            case 4:
                                website = "https://cathaybk.com.tw/";
                                break;
                            case 5:
                                website = "https://service.standardchartered.com.tw/ssl/campaign/credit-cards/signature-card.asp?utm_source=GoogleSearch&utm_medium=cpc&gclsrc=aw.ds&&gclid=CjwKCAjw1_PqBRBIEiwA71rmtbjtRgE_s11tKWGd_Cex0jvC2XgOf94-ucq67AGO9y6nahVqPkPgMhoCIIwQAvD_BwE";
                                break;
                            case 6:
                                website = "https://visa.com.tw/pay-with-visa/visa-offers-and-perks/offer-details.html?offerId=126670";
                                break;
                            case 7:
                                website = "https://www.esunbank.com.tw/bank/personal/credit-card/discount/shopinfo?sno=9995";
                                break;
                            case 8:
                                website = "https://www.feib.com.tw/creditCardMgm/p2?empbranch=VD2047";
                                break;
                            case 9:
                                website = "https://www.esunbank.com.tw/bank/personal/credit-card/intro/co-branded-card/c4-card";
                                break;
                        }
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                        startActivity(browserIntent);
                    }
                })
                        .setActionTextColor(Color.YELLOW)
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CardDetails.this, MainActivity.class);
                intent.putExtra("update", false);
                setResult(RESULT_OK, intent);
                System.out.println("User checking Card details.......");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
