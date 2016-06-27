package de.ahlfeld.breminale.app.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.adapters.BrefunkAdapter;
import de.ahlfeld.breminale.app.caches.FontCache;
import de.ahlfeld.breminale.app.databinding.FragmentBrefunkBinding;
import de.ahlfeld.breminale.app.viewmodel.BrefunkViewModel;



/**
 * Created by bjornahlfeld on 06.06.16.
 */
public class BrefunkFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private BrefunkViewModel viewModel;
    private FragmentBrefunkBinding binding;

    public static BrefunkFragment newInstance() {
        return new BrefunkFragment();
    }

    public BrefunkFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_brefunk,container,false);
        BrefunkAdapter adapter = new BrefunkAdapter(getChildFragmentManager());
        viewModel = new BrefunkViewModel();
        binding.setViewModel(viewModel);

        setupTablayout(binding.tabLayout, inflater);

        binding.viewpager.setAdapter(adapter);
        binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));


        return binding.getRoot();
    }


    public void setupTablayout(@NonNull TabLayout tablayout, LayoutInflater inflater) {

        View v = inflater.inflate(R.layout.brefunk_tab,null,false);
        TextView headerTwitter = (TextView) v.findViewById(R.id.tab_header);

        headerTwitter.setTextColor(getResources().getColorStateList(R.color.tab_colors));
        headerTwitter.setTypeface(FontCache.getInstance().get("roboto-medium"));
        headerTwitter.setText(R.string.twitter);
        headerTwitter.setCompoundDrawablesWithIntrinsicBounds(R.drawable.twitter_drawable,0,0,0);

        View v1 = inflater.inflate(R.layout.brefunk_tab,null,false);
        TextView headerInstagram = (TextView) v1.findViewById(R.id.tab_header);
        headerInstagram.setTextColor(getResources().getColorStateList(R.color.tab_colors));
        headerInstagram.setTypeface(FontCache.getInstance().get("roboto-medium"));
        headerInstagram.setText(R.string.instagram);
        headerInstagram.setCompoundDrawablesWithIntrinsicBounds(R.drawable.instagram_drawable,0,0,0);

        tablayout.addTab(tablayout.newTab().setCustomView(v));
        tablayout.addTab(tablayout.newTab().setCustomView(v1));
        tablayout.addTab(tablayout.newTab().setText(R.string.liveblog));
        tablayout.setTabTextColors(getResources().getColorStateList(R.color.tab_colors));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
