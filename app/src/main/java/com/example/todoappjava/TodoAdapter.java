package com.example.todoappjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    OnItemClickListener mListener;
    List<TodoData> list;
    public TodoAdapter(List<TodoData> list, OnItemClickListener listener){
        this.list = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoData todoData = list.get(position);
        holder.tvTodoId.setText(String.valueOf(todoData.getId()));
        holder.tvTodoText.setText(todoData.getTodoText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTodoId, tvTodoText;
        private ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTodoText = itemView.findViewById(R.id.tvTodoText);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(getAdapterPosition() != RecyclerView.NO_POSITION){
                mListener.onDeleteClick(getAdapterPosition());
            }
        }
    }

    interface OnItemClickListener{
        void onDeleteClick(int position);
    }
}
