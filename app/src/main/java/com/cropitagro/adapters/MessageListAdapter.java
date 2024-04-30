package com.cropitagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cropitagro.R;
import com.cropitagro.databinding.CellMessageBinding;
import com.cropitagro.models.MessageModel;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    public interface MessageListAdapterListener {
        void onReply(String phone);
    }

    private final MessageListAdapterListener listener;
    private final Context context;
    private final List<MessageModel.Data> list;

    public MessageListAdapter(Context context, List<MessageModel.Data> list, MessageListAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cell_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel.Data model = list.get(position);
        holder.binding.tvTitle.setText(model.phone_from);
        holder.binding.tvDescription.setText(model.message);
        holder.binding.tvDate.setText(model.date);
        holder.binding.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onReply(model.phone_from);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CellMessageBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CellMessageBinding.bind(itemView);
        }
    }
}
