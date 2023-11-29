package com.alex.scancode.managers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.models.Code;

import java.util.List;

//public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder> {
//
//    private List<Code> codeList;
//
//    public CodeAdapter(List<Code> codeList) {
//        this.codeList = codeList;
//    }
//
//    @NonNull
//    @Override
//    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_code, parent, false);
//        return new CodeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
//        Code code = codeList.get(position);
//        holder.textCode.setText("Code: " + code.getCode());
//        holder.textTime.setText("Time: " + code.getTime());
//        holder.textType.setText("Type: " + code.getType());
//    }
//
//    @Override
//    public int getItemCount() {
//        return codeList.size();
//    }
//
//    public static class CodeViewHolder extends RecyclerView.ViewHolder {
//        public TextView textCode;
//        public TextView textTime;
//        public TextView textType;
//
//        public CodeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textCode = itemView.findViewById(R.id.textCode);
//            textTime = itemView.findViewById(R.id.textTime);
//            textType = itemView.findViewById(R.id.textType);
//        }
//    }
//}
