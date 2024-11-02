package com.example.mynews;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewsFullActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);

        // Lấy URL từ Intent đã gửi đến Activity này, với khóa là "url"
        String url = getIntent().getStringExtra("url");

        // Khởi tạo WebView bằng cách tìm theo ID trong layout
        webView = findViewById(R.id.web_view);

        // Lấy WebSettings của WebView để thiết lập các cài đặt
        WebSettings webSettings = webView.getSettings();

        // Cho phép JavaScript hoạt động trong WebView (cần thiết với một số trang web)
        webSettings.setJavaScriptEnabled(true);

        // Thiết lập WebViewClient để WebView không mở trình duyệt bên ngoài khi load URL
        webView.setWebViewClient(new WebViewClient());

        // Tải URL vào WebView để hiển thị nội dung của trang web
        webView.loadUrl(url);
    }

    // Phương thức xử lý khi người dùng nhấn nút "Back" trên thiết bị
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Nếu không thể quay lại (đã ở trang đầu tiên), gọi phương thức mặc định của hệ thống
            super.onBackPressed();
        }
    }
}
