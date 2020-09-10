package com.oving3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;
import java.util.Calendar;

public class AddPersonActivity extends AppCompatActivity{

    private String name = "";
    private String date = "";
    private int index = -1;
    private EditText nameEditor;
    private EditText dateEditor;
    private Calendar calendar = Calendar.getInstance();
    private int day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);
        nameEditor = (EditText)findViewById(R.id.nameEditor);
        dateEditor = (EditText)findViewById(R.id.dateEditor);
        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        index = getIntent().getIntExtra("index", index);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dateEditor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog();
            }
        });
        try{
            nameEditor.setText(name, TextView.BufferType.EDITABLE);
            dateEditor.setText(date, TextView.BufferType.EDITABLE);
        }catch (NullPointerException npe){
            Log.i("FloatingActionButton","creating new entry in list");
        }
    }

    public void onSubmitClick(View v){
        name = nameEditor.getText().toString();
        date = dateEditor.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("index", index);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void DateDialog(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
            {
                monthOfYear++; // Måneden er en for liten av en eller annen grunn. hmmm
                dateEditor.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }};
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dpDialog.show();
    }
}
