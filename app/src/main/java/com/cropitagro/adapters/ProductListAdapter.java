package com.cropitagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cropitagro.R;
import com.cropitagro.databinding.CellProductBinding;
import com.cropitagro.models.ProductModel;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductModel.Data> list;

    public ProductListAdapter(Context context, List<ProductModel.Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cell_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel.Data model = list.get(position);
        holder.binding.tvTitle.setText(model.pname);
        holder.binding.tvDescription.setText(model.pdesc);
        holder.binding.tvPrice.setText("Rs. " + model.price);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CellProductBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CellProductBinding.bind(itemView);
        }
    }
}
