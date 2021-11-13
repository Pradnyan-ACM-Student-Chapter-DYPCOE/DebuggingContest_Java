package com.example.todoappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
        boolean isSuccess = myDB.insertData(text);
        if(isSuccess){
            Toast.makeText(this, "Todo added successfully!", Toast.LENGTH_SHORT).show();
            loadTodos();
            etTodo.getText().clear();    // Text is now clearing in edit text after successful save
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadTodos() {
        Cursor cursor = myDB.getAllData();
        if(cursor!=null){
            if (cursor.moveToFirst()) {
                String todoText;
                int todoId;
                do {
                    todoId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TODO_ID));
                    todoText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TODO_TEXT));
                    todoList.add(new TodoData(todoId, todoText));
                } while (cursor.moveToNext());
            }
            cursor.close();
            todoAdapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        rvTodos = findViewById(R.id.tvTodoText);
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
