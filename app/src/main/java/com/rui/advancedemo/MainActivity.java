package com.rui.advancedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.rui.advancedemo.ioc.OnClick;
import com.rui.advancedemo.ioc.ViewById;
import com.rui.advancedemo.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.ioc_test)
    private Button mIocTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        mIocTest.setText("demo");
    }

    @OnClick(values = {R.id.ioc_test})
    public void click(){
        mIocTest.setText("click");
    }
}
