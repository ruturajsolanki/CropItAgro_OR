package com.cropitagro.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cropitagro.R;
import com.cropitagro.activities.ProductListActivity;
import com.cropitagro.databinding.CellProductBinding;
import com.cropitagro.models.ShopModel;
import com.google.gson.Gson;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private final Context context;
    private final List<ShopModel.Data> list;

    public ShopListAdapter(Context context, List<ShopModel.Data> list) {
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
        ShopModel.Data model = list.get(position);
        holder.binding.tvTitle.setText(model.name);
        holder.binding.tvDescription.setText(model.address);
        holder.binding.tvPrice.setText(model.area);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("shop", new Gson().toJson(model));
                context.startActivity(intent);
            }
        });
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
