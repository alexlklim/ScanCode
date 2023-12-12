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
import com.alex.scancode.models.Order;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OnItemClickListener onItemClickListener;
    // Constructor to initialize the orderList
    public OrdersAdapter(List<Order> orderList, OnItemClickListener onItemClickListener) {
        this.orderList = orderList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_orders, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Bind order data to views in the ViewHolder
        holder.titleOrderTextView.setText(order.getOrderNumber());
        holder.startTimeTextView.setText(order.getStartTime());

        // Set the ImageView based on the isSynch value
        if (order.getIsSynch() == 1) {
            holder.isSynchImageView.setImageResource(R.drawable.ic_success);
        } else {
            holder.isSynchImageView.setImageResource(R.drawable.ic_fail);
        }

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(order.getId());
                animateItemClicked(holder.itemView);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView titleOrderTextView, startTimeTextView;
        ImageView isSynchImageView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOrderTextView = itemView.findViewById(R.id.rv_orders_tv_titleOrder);
            startTimeTextView = itemView.findViewById(R.id.rv_orders_tv_startTime);
            isSynchImageView = itemView.findViewById(R.id.rv_orders_tv_isSynch);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int orderId);
    }


    private void animateItemClicked(View view) {
        int colorFrom = Color.argb(0, 255, 255, 255);
        int colorTo = Color.GRAY;

        // Duration for the initial animation
        long initialDuration = 300; // milliseconds

        // Delay before restoring the background color
        long delayBeforeRestore = 500; // milliseconds

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(initialDuration);

        colorAnimation.addUpdateListener(animator -> {
            view.setBackgroundColor((int) animator.getAnimatedValue());
        });

        colorAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Post a delayed action to restore the background color
                new Handler().postDelayed(() -> restoreBackgroundColor(view), delayBeforeRestore);
            }
        });

        colorAnimation.start();
    }

    private void restoreBackgroundColor(View view) {
        // Restore the background color to white
        int colorFrom = Color.GRAY;
        int colorTo = Color.argb(0, 255, 255, 255);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(300); // milliseconds

        colorAnimation.addUpdateListener(animator -> {
            view.setBackgroundColor((int) animator.getAnimatedValue());
        });

        colorAnimation.start();
    }


}
