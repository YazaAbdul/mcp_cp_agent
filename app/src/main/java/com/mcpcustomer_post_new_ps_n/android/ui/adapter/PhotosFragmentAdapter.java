package com.mcpcustomer_post_new_ps_n.android.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mcpcustomer_post_new_ps_n.android.ui.activities.user.Photos_Fragment;

public class PhotosFragmentAdapter extends FragmentStateAdapter {
    public PhotosFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return new Photos_Fragment();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
