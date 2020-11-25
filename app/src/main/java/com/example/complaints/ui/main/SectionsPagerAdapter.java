package com.example.complaints.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.complaints.R;
import com.example.complaints.fragment.ComplaintsFragment;
import com.example.complaints.fragment.MyComplaintsFragment;
import com.example.complaints.fragment.NewComplaintFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_newComplaint, R.string.tab_myComplaint, R.string.tab_complaints
    };
    private final Context context;

    public SectionsPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new NewComplaintFragment();
            case 1: return new MyComplaintsFragment();
            case 2: return new ComplaintsFragment();
        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}