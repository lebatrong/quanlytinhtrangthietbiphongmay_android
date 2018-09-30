package lbt.com.manager.customAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.R;

public class aRclvDanhSachMayTinh extends RecyclerView.Adapter<aRclvDanhSachMayTinh.ViewHolder>{
    ArrayList<objthietbimaytinh_app> mList;
    Context context;

    private static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onClick(View v, int pos);
    }

    public void setOnClickListener(OnItemClickListener ls){
        this.listener = ls;
    }


    public aRclvDanhSachMayTinh(ArrayList<objthietbimaytinh_app> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.row_danhsachmay,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaThietBi.setText(mList.get(position).getMathietbi());
        //MÁY BÌNH THƯỜNG
        if(mList.get(position).getLichsusuachua() == null || (mList.get(position).getLichsusuachua().isDasuachua() && mList.get(position).getLichsusuachua() != null)){
            holder.lnlNgungHoadDong.setVisibility(View.GONE);
            holder.lnlBinhThuong.setVisibility(View.VISIBLE);
        }else{ //MÁY HƯ
            holder.lnlNgungHoadDong.setVisibility(View.VISIBLE);
            holder.lnlBinhThuong.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMaThietBi;
        LinearLayout lnlBinhThuong,lnlNgungHoadDong;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvMaThietBi = itemView.findViewById(R.id.tvmamayrclv);
            lnlBinhThuong = itemView.findViewById(R.id.lnldanghoatdong);
            lnlNgungHoadDong = itemView.findViewById(R.id.lnlngunghoatdong);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_recyclerview);
            itemView.setAnimation(animation);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        listener.onClick(itemView,getLayoutPosition());
                    }
                }
            });

        }
    }
}
