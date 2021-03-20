package com.example.yandexmobileapplication;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;
public class MainClass extends AppCompatActivity {
        @SuppressLint("ResourceType")
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            RecyclerView recyclerView = findViewById(R.layout.rv_item);
        }

}
