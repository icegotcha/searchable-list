package com.icegotcha.searchlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataAdapter extends BaseAdapter {
    private List<Data> mDatas;
    private LayoutInflater mLayoutInflater;

    public DataAdapter(Context context, List<Data> aList) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.row_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.tvBookName = view.findViewById(R.id.b_name);
            holder.tvId = view.findViewById(R.id.b_id);
            holder.tvAuthor = view.findViewById(R.id.b_author);
            holder.tvTrans = view.findViewById(R.id.b_trans);
            holder.tvPublisher = view.findViewById(R.id.b_publisher);
            holder.tvPrice = view.findViewById(R.id.b_price);
            holder.tvDate = view.findViewById(R.id.b_date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvBookName.setText(mDatas.get(position).getBookname());
        holder.tvId.setText(String.valueOf(mDatas.get(position).getId()));
        holder.tvAuthor.setText(mDatas.get(position).getAuthor());
        holder.tvTrans.setText(mDatas.get(position).getTranslator());
        holder.tvPublisher.setText(mDatas.get(position).getPublisher());

        String priceStr = String.format(Locale.ENGLISH, "%.2f", mDatas.get(position).getPrice());
        holder.tvPrice.setText(priceStr);

        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd/MMM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String outputDate = null;

        try {
            date = inputFormat.parse(mDatas.get(position).getDate());
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvDate.setText(outputDate);

        return view;
    }

    public void updateList(List<Data> datas) {
        this.mDatas = datas;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvId;
        TextView tvBookName;
        TextView tvAuthor;
        TextView tvTrans;
        TextView tvPublisher;
        TextView tvPrice;
        TextView tvDate;
    }
}
