package lbt.com.manager.customAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.R;

public class aExplvLichSu extends BaseExpandableListAdapter {

    Context context;
    List<objLichSu> mList;

    public aExplvLichSu(Context context, List<objLichSu> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getLichsu().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getLichsu().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String header = mList.get(groupPosition).getMamay();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.row_group,null);

        TextView tvMaMay = convertView.findViewById(R.id.tvmamayexplist);
        tvMaMay.setText(header);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Long time = mList.get(groupPosition).getLichsu().get(childPosition).getNgaybaocao();
        boolean isDaSua = mList.get(groupPosition).getLichsu().get(childPosition).isDasuachua();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.row_child,null);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - hh:mm a");
        calendar.setTimeInMillis(time);

        TextView tvNgay = convertView.findViewById(R.id.tvNgayBaoCaoExplist);

        if(isDaSua)
            tvNgay.setTextColor(context.getResources().getColor(R.color.colorGreen));
        else
            tvNgay.setTextColor(context.getResources().getColor(R.color.colorRed));

        tvNgay.setText(" - "+format.format(calendar.getTime()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
