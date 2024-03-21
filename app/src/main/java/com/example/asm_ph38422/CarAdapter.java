package com.example.asm_ph38422;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CarAdapter extends BaseAdapter {
    List<CarModel> carModelList;
    Context context;

    private Activity mActivity;

    public CarAdapter(Activity activity, List<CarModel> carModelList){
        this.mActivity = activity;
        this.carModelList = carModelList;
    }

    public CarAdapter(Context context, List<CarModel> carModelList){
        this.context = context;
        this.carModelList = carModelList;
    }
    @Override
    public int getCount() {
        return carModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return carModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_car, parent, false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
        ImageView imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatatr);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvNamSX = (TextView) rowView.findViewById(R.id.tvNamSX);
        TextView tvHang = (TextView) rowView.findViewById(R.id.tvHang);
        TextView tvGia = (TextView) rowView.findViewById(R.id.tvGia);

        tvName.setText(String.valueOf(carModelList.get(position).getTen()));
        tvNamSX.setText(String.valueOf(carModelList.get(position).getNamSX()));
        tvHang.setText(String.valueOf(carModelList.get(position).getHang()));
        tvGia.setText(String.valueOf(carModelList.get(position).getGia()));

        ImageButton btnDelete = rowView.findViewById(R.id.btndelete);
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDeleteConfirmationDialog(carModelList.get(position).get_id());
//            }
//        });

        return rowView;
    }

    private void showDeleteConfirmationDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            CarDeletionManager deletionManager = new CarDeletionManager();
            deletionManager.deleteCar(id);
            dialog.dismiss();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
