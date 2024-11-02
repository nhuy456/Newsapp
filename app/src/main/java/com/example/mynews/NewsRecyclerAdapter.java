package com.example.mynews;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter  extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{
    List<Article> articleList;// danh sach cac bai viết
    // Constructor nhận vào danh sách bài viết và gán vào biến articleList
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }
    // Phương thức onCreateViewHolder được gọi khi cần tạo ViewHolder mới
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Khởi tạo view cho từng hàng của RecyclerView từ layout news_recycler_row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row, parent, false);
        // Trả về một đối tượng NewsViewHolder, giữ view này
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // Lấy bài viết tại vị trí position trong danh sách
        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.ic_no_image) // hình ảnh mặc định
                .placeholder(R.drawable.ic_no_image) // hình ảnh tạm lúc tải
                .into(holder.imageView);
        // thiếtlapajp sự kiện click cho mỗi phần tử
        holder.itemView.setOnClickListener((v->{
            // Khi click vào bài viết, mở NewsFullActivity và truyền URL của bài viết qua intent
            Intent intent= new Intent(v.getContext(), NewsFullActivity.class);
            intent.putExtra("url",article.getUrl());
            v.getContext().startActivity(intent);
        }));
    }
    // phương thức update cập nhật danh sách bài viết mới
    void updateData(List<Article> data){
        // xóa phần tử cũ và thêm danh sách mới vào
        articleList.clear();
        articleList.addAll(data);
    }
    // phương thức trả về số lượng bài viết trong danh sách
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        // khai báo các thành phần giao diện của mỗi phần tử
        TextView titleTextView, sourceTextView;
        ImageView imageView;
        // Constructor của ViewHolder, nhận vào một view và liên kết các thành phần giao diện
        public NewsViewHolder(@NonNull View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);
        }
    }
}
