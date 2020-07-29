package com.example.readingshares.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingshares.R;
import com.example.readingshares.activity.model.Book;
import com.example.readingshares.activity.recyclerview.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Fragment {
    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private List<Book> mdata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_framehome,container,false);
        initViews(view);
        initmdataBook();
        setupBookAdapter();
        return view;

    }
    private void setupBookAdapter() {
        bookAdapter =new BookAdapter(mdata);
        rvBooks.setAdapter(bookAdapter);
    }

    private void initmdataBook(){
        //testing .. create radom set book
        //get data to web server or frie base
        mdata =new ArrayList<>();
        mdata .add(new Book(R.drawable.a));
        mdata .add(new Book(R.drawable.b));
        mdata .add(new Book(R.drawable.c));
        mdata .add(new Book(R.drawable.d));
        mdata .add(new Book(R.drawable.e));
        mdata .add(new Book(R.drawable.f));
        mdata .add(new Book(R.drawable.g));
    }

    private void initViews(View view){

        rvBooks = view.findViewById(R.id.rv_book);
        rvBooks .setLayoutManager(new LinearLayoutManager(getContext()));
        rvBooks.setHasFixedSize(true);
    }
}
