package com.rohitsharma.moviesnew.basemodules;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;

public class GenericRecyclerAdapter<T> extends RecyclerView.Adapter<GenericRecyclerAdapter.ViewHolder> {

    private final int layout;
    private BindListener<T> bindListener;
    private List<T> items;
    private ItemClick<T> itemClick;

    /**
     * Constructor
     *
     * @param layout passing in the layout you want to use with the recycler
     */
    public GenericRecyclerAdapter(int layout) {
        this.layout = layout;
    }


    @NonNull
    @Override
    public GenericRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GenericRecyclerAdapter.ViewHolder viewHolder, int i) {
        if (bindListener != null) bindListener.bind(items.get(i), viewHolder);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }


    /**
     * Setting the callback for onBindViewHolder
     *
     * @param bindListener anonymous class or the class which implements the interface BindListener
     */
    public void setBindListener(BindListener<T> bindListener) {
        this.bindListener = bindListener;
    }

    /**
     * method to set data in Recycler View
     *
     * @param items list of items to add
     */
    public void addData(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    /**
     * method to set click on items
     *
     * @param itemClick anonymous class or the class which implements the itemClick interface
     */
    public void setItemClickListener(ItemClick<T> itemClick) {
        this.itemClick = itemClick;
    }


    public void clear() {
        if (items != null) {
            items.clear();
            notifyDataSetChanged();
        }
    }


    public interface BindListener<T> {
        void bind(T obj, GenericRecyclerAdapter.ViewHolder viewHolder);
    }

    public interface ItemClick<T> {
        void onClick(T obj, GenericRecyclerAdapter.ViewHolder holderObject);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
