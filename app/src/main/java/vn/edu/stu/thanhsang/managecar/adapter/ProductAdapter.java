package vn.edu.stu.thanhsang.managecar.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.stu.thanhsang.managecar.databinding.ItemProductBinding;
import vn.edu.stu.thanhsang.managecar.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private final List<Product> list;
    private final onItemClickListener onItemClickListener;
    private final onItemLongClickListener onItemLongClickListener;

    public ProductAdapter(
            List<Product> list,
            onItemClickListener onItemClickListener,
            onItemLongClickListener onItemLongClickListener
    ) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public interface onItemLongClickListener{
        boolean onItemLongClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(
                parent.getContext()
                ),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.binding.tvIdEdit.setText(product.getIdProduct());
        holder.binding.tvNameEdit.setText(product.getNameProduct());
        holder.binding.tvBranchEdit.setText(product.getBranchProduct());
        Bitmap image = BitmapFactory.decodeByteArray(
                product.getImageProduct(),
                0,
                product.getImageProduct().length
        );
        holder.binding.imgProduct.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ItemProductBinding binding;
        public ViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
                return onItemLongClickListener.onItemLongClick(position);
            return false;
        }
    }
}
