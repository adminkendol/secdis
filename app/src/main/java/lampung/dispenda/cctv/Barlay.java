package lampung.dispenda.cctv;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.fragments.OneFragment;
import lampung.dispenda.cctv.fragments.ThreeFragment;
import lampung.dispenda.cctv.fragments.TwoFragment;

public class Barlay extends DrawerActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_action_live,
            R.drawable.ic_action_recording,
            R.drawable.ic_action_action_assessment
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barlay);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager,group_type_id,loc_id);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        /*Snackbar snackbar = Snackbar.make(coordinatorLayout, "CCTV Dispeda Lampung", Snackbar.LENGTH_LONG);
        snackbar.show();*/
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager, String group_type_id, String loc_id) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(group_type_id,loc_id), "Online");
        adapter.addFrag(new TwoFragment(group_type_id,loc_id), "Arsip");
        adapter.addFrag(new ThreeFragment(), "Dashboard");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        /*@Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }*/
        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }
}
