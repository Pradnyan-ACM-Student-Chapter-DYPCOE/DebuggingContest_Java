package com.example.todoappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoappjava.db.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnItemClickListener {

    private RecyclerView rvTodos;
    private EditText etTodo;
    private Button btnAddTodo;

    private final ArrayList<TodoData> todoList = new ArrayList<>();
    private final TodoAdapter todoAdapter = new TodoAdapter(todoList, this);
    private final DatabaseHelper myDB = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        rvTodos.setLayoutManager(new LinearLayoutManager(this));
        rvTodos.setAdapter(todoAdapter);
        loadTodos();
        btnAddTodo.setOnClickListener(v -> addTodo());
    }

    private void addTodo() {
        String text = etTodo.getText().toString();
        if(text.isEmpty())
        {
            // Bug 1 fixed
            Toast.makeText(MainActivity.this, "Empty TODO can't be saved", Toast.LENGTH_SHORT).show();
        }
        else{
            boolean isSuccess = myDB.insertData(text);
            //App Not Crashing
            Toast.makeText(MainActivity.this, "Todo added successfully!", Toast.LENGTH_SHORT).show();
            loadTodos();
            //Solved bug 2
            etTodo.setText("");
        }
    }
    private TextView tt;
    @SuppressLint("NotifyDataSetChanged")
    private void loadTodos() {
        Cursor cursor = myDB.getAllData();
        if(cursor!=null){
            tt=findViewById(R.id.textView);
            if (cursor.moveToFirst()) {
                tt.setText("");
                String todoText;
                int todoId;
                // bug 3 Fixed
                do {
                    todoId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TODO_ID));
                    todoText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TODO_TEXT));
                } while (cursor.moveToNext());
                todoList.add(new TodoData(todoId, todoText));
            }
            else{
                // bug 7 fixed
                tt.setText("TODO LIST Empty  !! \n ADD A Task to see the LIST .");
            }
            cursor.close();
            todoAdapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        rvTodos = findViewById(R.id.rvTodos);
        etTodo = findViewById(R.id.etTodo);
        btnAddTodo = findViewById(R.id.btnAddTodo);
    }
    @Override
    public void onDeleteClick(int position) {
        String itemIdToDelete = String.valueOf(todoList.get(position).getId());
        Toast.makeText(this, "This item should be deleted "+itemIdToDelete, Toast.LENGTH_SHORT).show();
        loadTodos();
    }
}
