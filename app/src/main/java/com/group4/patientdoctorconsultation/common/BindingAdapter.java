package com.group4.patientdoctorconsultation.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

public abstract class BindingAdapter<L, B extends ViewDataBinding>
        extends RecyclerView.Adapter<BindingViewHolder<B>> {

    protected final ClickListener<L> clickListener;
    private List<L> listItems;

    protected BindingAdapter(final ClickListener<L> listener) {
        this.clickListener = Objects.requireNonNull(listener);
    }

    @Override
    public final BindingViewHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        B binding = createBinding(inflater, parent);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public final void onBindViewHolder(BindingViewHolder<B> holder, int position) {
        bind(holder.binding, listItems.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
    }

    public void replaceListItems(List<L> newList){
        listItems = newList;
        notifyDataSetChanged();
    }

    public List<L> getListItems(){
        return listItems;
    }

    public void removeAt(int position){
        listItems.remove(position);
        notifyItemRemoved(position);
    }

    protected abstract B createBinding(LayoutInflater inflater, ViewGroup parent);
    protected abstract void bind(B binding, L item);

}
