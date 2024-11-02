package com.example.mynews;
import com.example.mynews.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator; //  hiển thị trạng thái tải dữ liệu
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hiển thị ở chế độ toàn màn hình
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        searchView = findViewById(R.id.search_view);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);

        // Thiết lập OnClickListener cho các nút để xử lý click
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 getNews("GENERAL", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setupRecycleView(); // Tải dữ liệu
        getNews("GENERAL", null); // Lấy tin tức mặc định
    }

    void setupRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show) {
        if (show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    // Phương thức để lấy dữ liệu tin tức từ API dựa trên danh mục và truy vấn tìm kiếm
    void getNews(String category, String query) {
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("yourkey");
         newsApiClient.getTopHeadlines(new TopHeadlinesRequest.Builder()
                .language("en")
                .category(category)
                .q(query)
                .build(), new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse response) {
                runOnUiThread(() -> {
                    changeInProgress(false);
                    articleList = response.getArticles();
                    adapter.updateData(articleList);
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i("Lỗi", throwable.getMessage());
                changeInProgress(false);
            }
        });
    }

    // Xử lý sự kiện click cho các danh mục
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String category = btn.getText().toString().toUpperCase(); // Lấy danh mục từ văn bản nút
        getNews(category, null);
    }

    // Inflate các mục menu trong toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}