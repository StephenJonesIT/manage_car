package vn.edu.stu.thanhsang.managecar.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.stu.thanhsang.managecar.databinding.ItemBranchBinding;
import vn.edu.stu.thanhsang.managecar.model.Branch;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder>{
    private final List<Branch> listBranch;
    private final OnItemClickListener onClickListener;
    private final OnItemLongClickListener longClickListener;

    public BranchAdapter(List<Branch> listBranch, OnItemClickListener onClickListener, OnItemLongClickListener longClickListener) {
        this.listBranch = listBranch;
        this.onClickListener = onClickListener;
        this.longClickListener = longClickListener;
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClick(int position);
    }
    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBranchBinding binding = ItemBranchBinding.inflate(LayoutInflater.from(
                parent.getContext()
        ), parent, false);

        return new BranchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        Branch branch = listBranch.get(position);
        holder.binding.tvName.setText(branch.getName());
        holder.binding.tvBase.setText(branch.getBase());
        Bitmap bitmap = BitmapFactory.decodeByteArray(branch.getImage(),0,branch.getImage().length);
        holder.binding.imgBranch.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if(listBranch!=null){
            return listBranch.size();
        }
        return 0;
    }

    public class BranchViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        final ItemBranchBinding binding;
        public BranchViewHolder(@NonNull ItemBranchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                onClickListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                return longClickListener.onItemLongClick(position);
            }
            return false;
        }

    }
}
