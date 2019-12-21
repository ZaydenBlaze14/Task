package com.zayden.android.task;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
    }


    void saveTodo() {
        //obtain from app
        EditText nameEdtText = (EditText)findViewById(R.id.task_name);
        EditText descEditText = (EditText)findViewById(R.id.task_desc);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datepicker);
        Date date = new Date();
        date.setMonth(datePicker.getMonth());
        date.setYear(datePicker.getYear());
        date.setDate(datePicker.getDayOfMonth());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String dateString = format.format(date);



       //save to database db
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("todoList").push().getKey();

        Todo todo = new Todo();
        todo.setName(nameEdtText.getText().toString());
        todo.setMessage(descEditText.getText().toString());
        todo.setDate(dateString);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( key, todo.toFirebaseObject());
        database.getReference("todoList").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    finish();
                }
            }
        });
    }
}