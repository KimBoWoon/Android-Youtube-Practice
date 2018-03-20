package com.dabang.byul.byuldabang;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.dabang.byul.byuldabang.adapter.RecyclerAdapter;
import com.dabang.byul.byuldabang.data.DataManager;
import com.dabang.byul.byuldabang.data.StreamerType;
import com.dabang.byul.byuldabang.http.HttpServiceProvider;
import com.dabang.byul.byuldabang.http.ProviderImplement;
import com.dabang.byul.byuldabang.http.VolleyCallback;
import com.dabang.byul.byuldabang.http.VolleyManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HttpServiceProvider.registerDefaultProvider(new ProviderImplement());
        VolleyManager.getInstance().setRequestQueue(getApplicationContext());

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setAutoMeasureEnabled(false);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        HttpServiceProvider.newInstance().requestYoutubeVideosList(
                new VolleyCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        Log.i("onSuccess", result.toString());
                        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), DataManager.getInstance().getVideoEntry());
                        recyclerView.setAdapter(recyclerAdapter);
                    }

                    @Override
                    public void onFail() {
                        Log.i("onFail", "onFail");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        StreamerType type = StreamerType.ALL;

        if (id == R.id.bomdalsae) {
            type = StreamerType.BOMDALSAE;
        } else if (id == R.id.whitepang) {
            type = StreamerType.WHITEPANG;
        } else if (id == R.id.minga) {
            type = StreamerType.MINGA;
        } else if (id == R.id.sook) {
            type = StreamerType.SOOK;
        } else if (id == R.id.bomi) {
            type = StreamerType.BOMI;
        }

        DataManager.getInstance().getVideoEntry().get(0).getItems().clear();

        HttpServiceProvider.newInstance().requestYoutubeVideosList(
                type,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        Log.i("onSuccess", result.toString());
                        Log.i("dataSize", String.valueOf(DataManager.getInstance().getVideoEntry().get(0).getItems().size()));
                        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), DataManager.getInstance().getVideoEntry());
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail() {
                        Log.i("onFail", "onFail");
                    }
                });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
