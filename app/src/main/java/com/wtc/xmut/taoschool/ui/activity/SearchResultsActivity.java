package com.wtc.xmut.taoschool.ui.activity;

import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lapism.searchview.SearchView;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.ToastUtils;

public class SearchResultsActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtils.showToast(getApplicationContext(),query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

    }
}
