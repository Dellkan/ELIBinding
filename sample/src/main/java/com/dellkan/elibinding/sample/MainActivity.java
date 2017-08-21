package com.dellkan.elibinding.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dellkan.elibinding.ELIBindingInflater;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new ELIBindingInflater(this).inflate(new TestModel(), R.layout.activity_main, null));
	}
}
