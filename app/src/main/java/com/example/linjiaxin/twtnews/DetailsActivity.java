package com.example.linjiaxin.twtnews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by linjiaxin on 2017/11/11.
 */

public class DetailsActivity extends AppCompatActivity {

    private WebView webView;
    private TextView subject;
    private TextView newscome;
    private TextView gonggao;
    private TextView shengao;
    private TextView sheying;
    private TextView visitcount;
    private final static int SHOW_RESPONSE=0;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_RESPONSE:
                    try {
                        String jsonData = (String) msg.obj;
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONObject json = jsonObject.getJSONObject("data");

                        String Subject = json.getString("subject");
                        String Content = json.getString("content");
                        String Newscome = "来源："+json.getString("newscome");
                        String Gonggao = "供稿人："+json.getString("gonggao");
                        String Shengao = "审稿人："+json.getString("shengao");
                        String Sheying = "摄影人："+json.getString("sheying");
                        String Visitcount = " /点击："+json.getInt("visitcount");

                        subject.setText(Subject);
                        newscome.setText(Newscome);
                        visitcount.setText(Visitcount);
                        webView.loadDataWithBaseURL(null,Content,"text/html","utf-8",null);
                        sheying.setText(Sheying);
                        gonggao.setText(Gonggao);
                        shengao.setText(Shengao);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        webView = (WebView) findViewById(R.id.web_view);
        subject = (TextView) findViewById(R.id.subject);
        newscome = (TextView) findViewById(R.id.newscome);
        gonggao = (TextView) findViewById(R.id.gonggao);
        shengao = (TextView) findViewById(R.id.shengao);
        sheying = (TextView) findViewById(R.id.sheying);
        visitcount = (TextView) findViewById(R.id.visitcount);
        String url="http://open.twtstudio.com/api/v1/news/"+getIntent().getStringExtra("index");
        sendRequestWithOKHttp(url);
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
                    Message message=new Message();
                    message.what=SHOW_RESPONSE;
                    message.obj=responseData;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}