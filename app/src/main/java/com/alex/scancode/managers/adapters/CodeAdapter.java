package com.alex.scancode.managers.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.models.Code;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder> {

    private List<Code> codeList;
    private OnItemClickListener onItemClickListener;

    public CodeAdapter(List<Code> codeList, OnItemClickListener onItemClickListener) {
        this.codeList = codeList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_code, parent, false);
        return new CodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
        Code code = codeList.get(position);
        holder.textCode.setText("Code: " + code.getCode());
        holder.textTime.setText("Time: " + code.getTime());
        holder.textLabelType.setText("Type: " + code.getLabelType());

        holder.rv_iv_delete.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(code);
            }

            // Change the color of rv_iv_delete to black
            holder.rv_iv_delete.setColorFilter(Color.BLACK);
        });
    }

    @Override
    public int getItemCount() {
        return codeList.size();
    }

    public static class CodeViewHolder extends RecyclerView.ViewHolder {
        public TextView textCode, textTime, textLabelType;
        ImageView rv_iv_delete;

        public CodeViewHolder(@NonNull View itemView) {
            super(itemView);
            textCode = itemView.findViewById(R.id.textCode);
            textTime = itemView.findViewById(R.id.textTime);
            textLabelType = itemView.findViewById(R.id.textType);
            rv_iv_delete = itemView.findViewById(R.id.rv_iv_delete);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Code code);
    }


}
