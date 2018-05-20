package com.ajmalrasi.newsai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "APP";
    Utils utils;
    private RecyclerView recyclerView;
    VolleySingleton volley;
    RequestQueue requestQueue;
    ArrayList<News> newsList;
    private ViewAdapter adapter;
    private AppContext app = new AppContext();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_technology:
                    //sendRequest("TECHNOLOGY");
                    //adapter.setViewList(newsList);

                    return true;
                case R.id.navigation_sports:
                    //sendRequest("SPORTS");
                    //adapter.setViewList(newsList);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewAdapter(this);
        recyclerView.setAdapter(adapter);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        if (savedInstanceState == null) {
            volley = VolleySingleton.getInstance();
            requestQueue = volley.getRequestQueue();
            utils = new Utils(getAssets());
            sendRequest("WORLD");
        } else {
            newsList = new ArrayList<>();
            newsList = savedInstanceState.getParcelableArrayList("list");
            adapter.setViewList(newsList);
        }

        //Log.e(TAG, "onCreate: "+utils.predict("SpaceX: 'We thought someone shot the rocket'\", 'topic': 'SpaceX"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", newsList);
    }

    public void sendRequest(String categoty) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                app.getUrl(categoty),
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: Success");
                adapter.setViewList(parseRequest(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onError: " + error);
            }
        });
        requestQueue.add(request);
    }

    public ArrayList<News> parseRequest(JSONArray response) {
        newsList = new ArrayList<>();
        if (response == null || response.length() == 0) return null;
        Log.d(TAG, "parseRequest: parsing " + response.length() + " items");
        for (int i = 0; i < response.length(); i++) {
            try {

                JSONObject mainObject = (JSONObject) response.get(i);
                String link = mainObject.getString("link");
                String image = mainObject.getString("image");
                String date = mainObject.getString("date");
                String category = mainObject.getString("category");
                JSONArray titles = mainObject.getJSONArray("titles");
                String title = titles.getString(0);
                String description;
                if (!titles.isNull(1)) description = titles.getString(1);
                else {
                    description = title;
                }

                News list;
                list = new News();
                list.setTopic(utils.predict(title));
                list.setTitle(title);
                list.setLink(link);
                list.setThumbnails(image);
                list.setDescription(description);
                list.setCategory(category);
                list.setPublishedDate(date);

                newsList.add(list);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newsList;

    }


}
