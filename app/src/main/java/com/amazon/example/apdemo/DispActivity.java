package com.amazon.example.apdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.amazon.example.apdemo.anotherpacakge.ExampleClass3;

public class DispActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillRandom();
    }

    public void onResetClicked(View v) {
        fillRandom();
    }

    private void fillRandom() {
        ((TextView) findViewById(R.id.random_object1_text)).setText(ExampleClass.random().toString());
        ((TextView) findViewById(R.id.random_object2_text)).setText(ExampleClass2.random().toString());
        ((TextView) findViewById(R.id.random_object3_text)).setText(ExampleClass3.random().toString());
    }
}
