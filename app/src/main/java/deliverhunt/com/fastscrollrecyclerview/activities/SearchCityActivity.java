package deliverhunt.com.fastscrollrecyclerview.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import deliverhunt.com.fastkroll.RecyclerViewFastScroller;
import deliverhunt.com.fastscrollrecyclerview.R;
import deliverhunt.com.fastscrollrecyclerview.adapters.SearchCityAdapter;
import deliverhunt.com.fastscrollrecyclerview.listeners.RecyclerViewClickListener;
import deliverhunt.com.fastscrollrecyclerview.utils.SimpleDividerItemDecoration;


public class SearchCityActivity extends AppCompatActivity
{
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private Context context;
    private SearchCityAdapter mAdapter;
    private List<String> cityList = new ArrayList<String>();
    private List<String> filteredCityList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SearchCityAdapter(filteredCityList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
            context
        ));
        prepareCityList();
        filterCity(null);

        final RecyclerViewFastScroller fastScroller =
            (RecyclerViewFastScroller) findViewById(R.id.fastscroller);
        recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            {
                @Override
                public void onLayoutChildren(final RecyclerView.Recycler recycler,
                                             final RecyclerView.State state)
                {
                    super.onLayoutChildren(recycler, state);
                    final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition != 0)
                    {
                        if (firstVisibleItemPosition == -1)
                        {
                            fastScroller.setVisibility(View.GONE);
                        }
                        return;
                    }
                    final int lastVisibleItemPosition = findLastVisibleItemPosition();
                    int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                    //if all items are shown, hide the fast-scroller
                    fastScroller.setVisibility(
                        mAdapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE
                    );
                }
            }
        );

        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.recycler_view_fast_scroller,
                                   R.id.fastscroller_bubble, R.id.fastscroller_handle);

        mAdapter.SetOnItemClickListener(new RecyclerViewClickListener()
        {
            @Override
            public void onItemClick(View v, int position)
            {
                String city = filteredCityList.get(position);
                processSelectedCity(city);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // for search view in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);

        // Retrieve the SearchView and plug it into SearchManager
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
            (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));

        searchView.findViewById(
            android.support.v7.appcompat.R.id.search_src_text
        ).setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha);
        searchView.onActionViewExpanded();

        searchView.setQueryHint(getResources().getString(R.string.search_bar_hint));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                filterCity(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                if (filteredCityList.size() == 1)
                {
                    processSelectedCity(filteredCityList.get(0));
                }
                else
                {
                    Toast.makeText(context, "Please choose city",
                                   Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return super.onCreateOptionsMenu(menu);
    }

    private void processSelectedCity(String city)
    {
        Toast.makeText(context, city, Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent();
        intent.putExtra("Keys", city);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void prepareCityList()
    {
        cityList.add("Pune");
        cityList.add("Dhanbad");
        cityList.add("Kota");
        cityList.add("Nagpur");
        cityList.add("Bangalore");
        cityList.add("Jaipur");
        cityList.add("Bokaro");
        cityList.add("Mumbai");
        cityList.add("Noida");
        cityList.add("Patna");
        cityList.add("Delhi");

        cityList.add("Bokaro");
        cityList.add("Mumbai");
        cityList.add("Noida");
        cityList.add("Patna");
        cityList.add("Delhi");
        cityList.add("Bokaro");
        cityList.add("Mumbai");
        cityList.add("Noida");
        cityList.add("Patna");
        cityList.add("Delhi");

        cityList = sort(cityList);
    }

    public static List<String> sort(List<String> list)
    {
        Collections.sort(list, new Comparator<String>()
        {
            @Override
            public int compare(String firstString, String secondString)
            {
                return firstString.compareToIgnoreCase(secondString);
            }
        });
        return list;
    }

    private void filterCity(String cityName)
    {
        if(cityName != null && !cityName.isEmpty())
        {
            filteredCityList.clear();
            cityName = cityName.toLowerCase();
            for (String city: cityList)
            {
                if (city.toLowerCase().contains(cityName))
                {
                    filteredCityList.add(city);
                }
            }
        }
        else
        {
            filteredCityList.addAll(cityList);
        }
        filteredCityList = sort(filteredCityList);
        mAdapter = new SearchCityAdapter(filteredCityList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
