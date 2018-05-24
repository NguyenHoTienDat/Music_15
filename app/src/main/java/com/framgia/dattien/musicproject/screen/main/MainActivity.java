package com.framgia.dattien.musicproject.screen.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.screen.BaseFragment;
import com.framgia.dattien.musicproject.screen.BasicActivity;
import com.framgia.dattien.musicproject.screen.main.homefragment.HomeFragment;
import com.framgia.dattien.musicproject.screen.main.inforfragment.InforFragment;
import com.framgia.dattien.musicproject.screen.main.mefragment.MeFragment;
import com.framgia.dattien.musicproject.utils.Constant;

public class MainActivity extends BasicActivity
        implements MainContract.View, AHBottomNavigation.OnTabSelectedListener {

    private AHBottomNavigation mBottomNavigation;

    private MainContract.Presenter mPresenter;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private MeFragment mMeFragment;
    private InforFragment mInforFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initBottomNavigationBar();
        initPresenter();

        mHomeFragment = HomeFragment.getInstance();
        mMeFragment = MeFragment.getInstance();
        mInforFragment = InforFragment.getInstance();
        findOrCreateFragment(mHomeFragment, HomeFragment.HOME_FRG_TAG);
    }

    @Override
    public void bindView() {
        mBottomNavigation = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter();
        mPresenter.onStart();
    }

    /**
     * BottomNavigation setup
     */
    @Override
    public void initBottomNavigationBar() {
        // Create items
        AHBottomNavigationItem homeTab = new AHBottomNavigationItem(
                getString(R.string.bottomnavigation_home_tab),
                R.drawable.ic_home,
                R.color.colorAccent);
        AHBottomNavigationItem myMusicTab = new AHBottomNavigationItem(
                getString(R.string.bottomnavigation_me_tab),
                R.drawable.ic_me,
                R.color.colorAccent);
        AHBottomNavigationItem infoTab = new AHBottomNavigationItem(
                getString(R.string.bottomnavigation_infor_tab),
                R.drawable.ic_info,
                R.color.colorAccent);

        // Add item
        mBottomNavigation.addItem(homeTab);
        mBottomNavigation.addItem(myMusicTab);
        mBottomNavigation.addItem(infoTab);

        // Listen event
        mBottomNavigation.setOnTabSelectedListener(this);
    }

    /**
     * This method handle change tab event
     *
     * @param tabPosition
     */
    @Override
    public void onChangeTabClick(int tabPosition) {
        switch (tabPosition) {
            case Constant.TAB_HOME_POSITION: {
                findOrCreateFragment(mHomeFragment, HomeFragment.HOME_FRG_TAG);
                return;
            }
            case Constant.TAB_ME_POSITION: {
                findOrCreateFragment(mMeFragment, MeFragment.ME_FRG_TAG);
                return;
            }
            case Constant.TAB_INFORMATION_POSITION: {
                findOrCreateFragment(mInforFragment, InforFragment.INFOR_FRG_TAG);
                return;
            }

        }
    }

    /**
     * Depend on fragmentState to decide create new fragment or get exist instance
     *
     * @param baseFragment
     * @param fragmentTag
     */
    @Override
    public void findOrCreateFragment(BaseFragment baseFragment, String fragmentTag) {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (!findExistFragment(fragmentTag)) {
            transaction.add(R.id.frame_contain, baseFragment, fragmentTag);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();
        } else {
            transaction.replace(R.id.frame_contain,
                    mFragmentManager.findFragmentByTag(fragmentTag));
            transaction.commit();
        }

    }

    /**
     * This method find fragment in backstack
     *
     * @param fragmentTag
     * @return true if has an instance of fragment with given tag
     */
    @Override
    public boolean findExistFragment(String fragmentTag) {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }

        if (mFragmentManager.findFragmentByTag(fragmentTag) == null) return false;

        return true;
    }

    /**
     * @param position
     * @param wasSelected
     * @return
     */
    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected) {
            onChangeTabClick(position);
        }
        return true;
    }
}
