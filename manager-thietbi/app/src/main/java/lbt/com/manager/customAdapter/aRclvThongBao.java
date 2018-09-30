package lbt.com.manager.customAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.R;

public class aRclvThongBao extends RecyclerView.Adapter<aRclvThongBao.ViewHolder> {

    List<objThietBi> mList;
    Context context;

    private static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onClick(View v, int pos);
    }

    public void setOnClickListener(OnItemClickListener ls){
        this.listener = ls;
    }

    public aRclvThongBao(List<objThietBi> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.row_thongbao,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaThietBi.setText(mList.get(position).getMathietbi());

        //LẤY THỜI GIAN 2 phút trước
        long time = mList.get(position).getLichsusuachua().getNgaybaocao();
        long now = System.currentTimeMillis();
        CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

        holder.tvThoiGian.setText(ago);
        holder.tvNguoiThongBao.setText(mList.get(position).getLichsusuachua().getEmailnguoibaocao());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMaThietBi,tvThoiGian,tvNguoiThongBao;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvMaThietBi = itemView.findViewById(R.id.tvmamaythongbao);
            tvNguoiThongBao = itemView.findViewById(R.id.tvnguoibaocaothongbao);
            tvThoiGian = itemView.findViewById(R.id.tvthoigianthongbao);

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
