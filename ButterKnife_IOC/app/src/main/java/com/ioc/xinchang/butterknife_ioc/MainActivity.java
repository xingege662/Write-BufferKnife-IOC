package com.ioc.xinchang.butterknife_ioc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BindView;
import com.ioc.xinchang.inject.InjectView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectView.bind(this);
        Toast.makeText(this, textView.toString(), Toast.LENGTH_SHORT).show();
    }
}
