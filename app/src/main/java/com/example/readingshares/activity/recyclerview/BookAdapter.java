package com.example.readingshares.activity.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingshares.R;
import com.example.readingshares.activity.model.Book;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.bookviewholder> {


    List<Book> mdata;

    public BookAdapter(List<Book> mdata) {
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public bookviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.activity_framehome,parent,false);
       return new bookviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookviewholder holder, int position) {


        //bind book item data here
        //I just going to bind the book image
        //load book image using glide
        Glide.with(holder.itemView.getContext())
                .load(mdata.get(position).getDrawableResource())
                .transforms(new CenterCrop(),new RoundedCorners(16))
        .into(holder.imgBook);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class bookviewholder extends RecyclerView.ViewHolder{


        ImageView imgBook,imgFav;
        TextView title,description,author,pages,rate;
        RatingBar ratingBar;
        CardView cardview;

        public bookviewholder(@NonNull View itemView) {
            super(itemView);

            imgBook =itemView.findViewById(R.id.item_book_img);
            title =itemView.findViewById(R.id.item_book_title);
            author =itemView.findViewById(R.id.item_book_author);
            pages =itemView.findViewById(R.id.item_book_page);
            ratingBar =itemView.findViewById(R.id.item_book_ratingbar);
            cardview =itemView.findViewById(R.id.cardview);
        }
    }
}
