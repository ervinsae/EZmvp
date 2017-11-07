package com.ervin.mvp.ui.activity;

import android.ervin.mvp.R;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ervin.mvp.ui.adatper.FragmentNodeAdapter;
import com.ervin.mvp.ui.fragment.AllNodeFragment;
import com.ervin.mvp.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Ervin on 2017/11/7.
 */

public class AllNodeActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;

    public static String[] typeStr = {"技术", "创意", "好玩", "Apple", "酷工作", "交易", "城市", "问与答", "最热", "全部", "R2"};
    public static String[] type = {"tech", "creative", "play", "apple", "jobs", "deals", "city", "qna", "hot", "all", "r2"};

    List<AllNodeFragment> fragments = new ArrayList<>();

    @Override
    protected int setLayoutRsID() {
        return R.layout.activity_node_all;
    }
    @Override
    protected void initPresenter() {
        titleBar.setTitle("所有节点");

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(vpContainer);

        Observable.fromArray(type)
                .subscribe(s -> {
                    AllNodeFragment fragment = new AllNodeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", s);
                    fragment.setArguments(bundle);
                    tabLayout.addTab(tabLayout.newTab());
                    fragments.add(fragment);
                });

        FragmentNodeAdapter mAdapter = new FragmentNodeAdapter(getSupportFragmentManager(),fragments);
        vpContainer.setAdapter(mAdapter);
    }
}
