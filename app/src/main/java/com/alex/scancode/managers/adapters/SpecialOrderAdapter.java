package com.alex.scancode.managers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.managers.DateTimeMan;
import com.alex.scancode.models.Code;

import java.util.List;
public class SpecialOrderAdapter extends RecyclerView.Adapter<SpecialOrderAdapter.ViewHolder> {

    private List<Code> codeList;

    private OnItemClickListener onItemClickListener;

    public SpecialOrderAdapter(List<Code> codeList, OnItemClickListener onItemClickListener) {
        this.codeList = codeList;
        this.onItemClickListener = onItemClickListener;
    }

    public boolean newCodeListAfterDeleting(Code code){
        codeList.remove(code);
        if (codeList.isEmpty()){
            return false;
        }
        return true;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_special_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Code code = codeList.get(position);

        // Set data to views in your item layout
        holder.rvSoCode.setText(code.getCode());
        holder.rvSoTime.setText(DateTimeMan.extractHoursAndMinutes(code.getTime()));
        if (code.getGps().equalsIgnoreCase("none")) holder.rvSoIvGps.setImageResource(R.drawable.ic_gps_not);
        else holder.rvSoIvGps.setImageResource(R.drawable.ic_gps);

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(code);
            }
        });
        // Handle clicks or other interactions here if needed
    }

    @Override
    public int getItemCount() {
        return codeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rvSoCode, rvSoTime;
        ImageView rvSoIvGps, rvSoIvDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvSoCode = itemView.findViewById(R.id.rv_so_code);
            rvSoTime = itemView.findViewById(R.id.rv_so_time);
            rvSoIvGps = itemView.findViewById(R.id.rv_so_iv_gps);
            rvSoIvDel = itemView.findViewById(R.id.rv_so_iv_del);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Code code);
    }

}
