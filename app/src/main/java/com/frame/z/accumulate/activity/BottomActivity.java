package com.frame.z.accumulate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.frame.z.accumulate.R;
import com.frame.z.accumulate.fragment.BlankFragment1;
import com.frame.z.accumulate.fragment.BlankFragment2;
import com.frame.z.accumulate.fragment.BlankFragment3;
import com.frame.z.accumulate.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 底部导航栏
 */
public class BottomActivity extends AppCompatActivity {

    @BindView(R.id.vp_page)
    NoScrollViewPager vpPage;
    @BindView(R.id.bnb)
    BottomNavigationBar bnb;

    List<Fragment> fragmentList;

    BlankFragment1 fragment1;
    BlankFragment2 fragment2;
    BlankFragment3 fragment3;

    //当前选中的项
    int currenttab = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        ButterKnife.bind(this);

        initPage();
        initBottom();

    }

    private void initPage() {

        fragmentList = new ArrayList<>();
        fragment1 = new BlankFragment1();
        fragment2 = new BlankFragment2();
        fragment3 = new BlankFragment3();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        vpPage.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bnb.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initBottom() {

        bnb.setMode(BottomNavigationBar.MODE_DEFAULT);
        bnb.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bnb.setBarBackgroundColor(R.color.white);
        bnb.addItem(new BottomNavigationItem(R.mipmap.main_account_select, "第一页面").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.main_fight_select, "第二页面").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.main_sign_select, "第三页面").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成


        bnb.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {//这里也可以使用SimpleOnTabSelectedListener
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                vpPage.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });

    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


}
