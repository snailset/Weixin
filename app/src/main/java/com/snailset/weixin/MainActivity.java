package com.snailset.weixin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> datas;

    private TextView chatTextView;
    private TextView friendTextView;
    private TextView contactTextView;

    private LinearLayout chatLinearLayout;


    private int screen1_3;
    private ImageView tabline;

//    private int currentPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabView();

        initView();
    }

    private void initTabView() {
        tabline = (ImageView) findViewById(R.id.id_iv_tabline);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        screen1_3 = outMetrics.widthPixels / 3;
        ViewGroup.LayoutParams lp = tabline.getLayoutParams();
        lp.width = screen1_3;
        tabline.setLayoutParams(lp);
    }

    private BadgeView badgeView;

    private void initView()
    {
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        chatTextView = (TextView) findViewById(R.id.id_tv_chat);
        friendTextView = (TextView) findViewById(R.id.id_tv_friend);
        contactTextView = (TextView) findViewById(R.id.id_tv_contact);
        chatLinearLayout = (LinearLayout) findViewById(R.id.id_ll_chat);

        datas = new ArrayList<>();
        ChatMainTabFragment tab01 = new ChatMainTabFragment();
        ContactMainTabFragment tab03 = new ContactMainTabFragment();
        FriendMainTabFragment tab02 = new FriendMainTabFragment();


        datas.add(tab01);
        datas.add(tab02);
        datas.add(tab03);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return datas.get(position);
            }

            @Override
            public int getCount() {
                return datas.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 当页面滑动时会触发
                // position：如果向右移动，为当前页；向左移动，为当前页减一
                // positionOffset: 向右移动，从0-1，向左移动，从1-0
                // positionOffsetPixels 偏移的像素(暂时不是很理解)
                // 所以不管移动position+positionOffset就为当前精确的位置, 比如1.5
                Log.i("MSG", position + " : " + positionOffset + " : " + positionOffsetPixels);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabline.getLayoutParams();
               /* if (currentPageIndex == position) {
                    lp.leftMargin = (int) (positionOffset*screen1_3 + currentPageIndex*screen1_3);
                } else {
                    lp.leftMargin = (int) ((positionOffset-1)*screen1_3 + currentPageIndex*screen1_3);
                }*/

                lp.leftMargin = (int) (position * screen1_3 + positionOffset*screen1_3);

                tabline.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                // 当当前页面改变是会触发
                // position表示当前页
                resetTextView();
                switch (position){
                    case 0:
                        if (badgeView != null ) {
                            chatLinearLayout.removeView(badgeView);
                        }
                        badgeView = new BadgeView(MainActivity.this);
                        badgeView.setBadgeCount(7);
                        chatLinearLayout.addView(badgeView);
                        chatTextView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTopText));
                        break;
                    case 1:
                        friendTextView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTopText));
                        break;
                    case 2:
                        contactTextView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTopText));
                        break;
                }
//                currentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {
        chatTextView.setTextColor(Color.BLACK);
        friendTextView.setTextColor(Color.BLACK);
        contactTextView.setTextColor(Color.BLACK);
    }
}
