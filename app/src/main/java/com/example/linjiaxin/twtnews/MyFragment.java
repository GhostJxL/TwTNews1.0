package com.example.linjiaxin.twtnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by linjiaxin on 2017/11/13.
 */

public class MyFragment extends Fragment {

    private List<NewsBean> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private String url="http://open.twtstudio.com/api/v1/news/1/page/1";
    public static final String ARG_OBJECT="object";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment,container,false);
        Bundle bundle = getArguments();
        int data = bundle.getInt(ARG_OBJECT);
        url="http://open.twtstudio.com/api/v1/news/"+data+"/page/1";
        Log.d("Data", "onCreate");
        sendRequestWithOKHttp(url);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(newsAdapter);
        return view;
    }

    private void sendRequestWithOKHttp(final String url) {
        new Thread(new Runnable() //子线程
        {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.optJSONObject(i);
                String index = json.optString("index");
                String subject = json.optString("subject");
                String pic = json.optString("pic");
                String visitcount = json.optString("visitcount");
                String comments = json.optString("comments");
                String summary = json.optString("summary");

                NewsBean newsBean = new NewsBean(index, subject, pic, visitcount, comments, summary);
                newsList.add(newsBean);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}