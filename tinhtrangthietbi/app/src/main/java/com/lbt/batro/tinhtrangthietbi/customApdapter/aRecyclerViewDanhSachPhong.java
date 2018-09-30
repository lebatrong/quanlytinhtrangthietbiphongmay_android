package com.lbt.batro.tinhtrangthietbi.customApdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lbt.batro.tinhtrangthietbi.R;
import com.lbt.batro.tinhtrangthietbi.models.clsFireBase.objphongmay;

import java.util.ArrayList;

public class aRecyclerViewDanhSachPhong extends RecyclerView.Adapter<aRecyclerViewDanhSachPhong.ViewHolder> {

    ArrayList<objphongmay> lisphong;
    Context context;
    int Layout;

    private static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public aRecyclerViewDanhSachPhong(ArrayList<objphongmay> lisphong, Context context, int layout) {
        this.lisphong = lisphong;
        this.context = context;
        Layout = layout;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(this.Layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        objphongmay item = lisphong.get(position);

        holder.tvkhu.setText(item.getKhu());
        holder.tvphong.setText(item.getPhong());
        holder.tvlau.setText(item.getLau());
        holder.tvtenphongmay.setText(item.getTenphong());

    }

    @Override
    public int getItemCount() {
        return lisphong.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView tvtenphongmay, tvkhu,tvlau,tvphong;
        public ViewHolder(View itemView) {
            super(itemView);

            tvkhu = itemView.findViewById(R.id.tvkhu);
            tvlau = itemView.findViewById(R.id.tvlau);
            tvphong = itemView.findViewById(R.id.tvphongrow);
            tvtenphongmay = itemView.findViewById(R.id.tvtenphongmay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                        listener.onItemClick(v,getLayoutPosition());
                }
            });

        }



    }
}
