package com.oving3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAddButton();
        initList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == request_code && resultCode == RESULT_OK){
            int index = data.getIntExtra("index", -1);
            if(index == -1){
                names.add(data.getStringExtra("name"));
                dates.add(data.getStringExtra("date"));
                friends.add(data.getStringExtra("name") +  "\n" + data.getStringExtra("date"));
            }
            else{
                names.set(index, data.getStringExtra("name"));
                dates.set(index, data.getStringExtra("date"));
                friends.set(index, data.getStringExtra("name") +  "\n" + data.getStringExtra("date"));
            }
            initList();
        }
    }

    private void initAddButton(){
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add);
        addButton.setOnClickListener( new OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent("com.oving3.AddPersonActivity");
                startActivityForResult(i, request_code);
            }
        });
    }

    private void initList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, friends);
        ListView listView = (ListView)findViewById(R.id.friendlist);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("com.oving3.AddPersonActivity");
                intent.putExtra("name", names.get(i));
                intent.putExtra("date", dates.get(i));
                intent.putExtra("index", i);
                startActivityForResult(intent, request_code);
            }
        });
    }
}