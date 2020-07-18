package com.rakuten.assessment.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rakuten.assessment.R;
import com.rakuten.assessment.model.ImageModel;
import com.squareup.picasso.Picasso;

public class ImageListAdapter extends ListAdapter<ImageModel, ImageListAdapter.ViewHolder> {

  public interface OnItemClickListener {
    void onItemClick(View v, int position, ImageModel imageModel);
  }

  private OnItemClickListener onItemClickListener;
  private View.OnClickListener onImageClickListener = v -> {
    if (onItemClickListener != null) {
      int position = (int) v.getTag();
      onItemClickListener.onItemClick(v, position, getItem(position));
    }
  };

  public ImageListAdapter(OnItemClickListener onItemClickListener) {
    super(new DiffUtil.ItemCallback<ImageModel>() {
      @Override
      public boolean areItemsTheSame(@NonNull ImageModel oldItem, @NonNull ImageModel newItem) {
        return oldItem == newItem;
      }

      @SuppressLint("DiffUtilEquals")
      @Override
      public boolean areContentsTheSame(@NonNull ImageModel oldItem, @NonNull ImageModel newItem) {
        return oldItem == newItem;
      }
    });
    this.onItemClickListener = onItemClickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ImageModel imageModel = getItem(position);
    holder.titleView.setText(imageModel.title);
    holder.descView.setText(imageModel.owner);
    holder.imageView.setTag(position);
    holder.imageView.setOnClickListener(onImageClickListener);

    Picasso.get().load(imageModel.getUrl()).into(holder.imageView);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView titleView;
    public TextView descView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.image);
      titleView = itemView.findViewById(R.id.title);
      descView = itemView.findViewById(R.id.description);
    }
  }
}
