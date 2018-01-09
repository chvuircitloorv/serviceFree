package com.example.chvui.siamservicebasic.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.chvui.siamservicebasic.R;
import com.example.chvui.siamservicebasic.service.SensorService;
import com.example.chvui.siamservicebasic.ui.about.AboutActivity;
import com.example.chvui.siamservicebasic.ui.base.BluetoothActivity;
import com.example.chvui.siamservicebasic.ui.settings.SettingsActivity;
import com.example.chvui.siamservicebasic.utils.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BluetoothActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setupNavMenu();
    }

    void setupNavMenu() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_item_make:
                                //mPresenter.onDrawerOptionAboutClick();
                                return true;
                            case R.id.nav_item_measurements:
                                //mPresenter.onDrawerRateUsClick();
                                return true;
                            case R.id.nav_item_settings:
                                startActivity(SettingsActivity.getCallingActivity(MainActivity.this));
                                return true;
                            case R.id.nav_item_about:
                                startActivity(AboutActivity.getCallingIntent(MainActivity.this));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }
}
