package com.example.asm_ph38422;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterCar extends RecyclerView.Adapter<AdapterCar.CarViewHolder>{

    Context context;
    List<CarModel> listCar;

    public AdapterCar(Context context, List<CarModel> listCar){
        this.context = context;
        this.listCar = listCar;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String id = listCar.get(position).get_id();
        holder.tvName.setText(String.valueOf(listCar.get(position).getTen()));
        holder.tvNamSX.setText(String.valueOf(listCar.get(position).getNamSX()));
        holder.tvHang.setText(String.valueOf(listCar.get(position).getHang()));
        holder.tvGia.setText(String.valueOf(listCar.get(position).getGia()));
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.dialog_mess, null, false);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                AppCompatButton btnHuy, btnXacNhan;
                btnHuy = view.findViewById(R.id.btnHuy);
                btnXacNhan = view.findViewById(R.id.btnXacNhan);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIService.DOMAIN)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);

                        Call<CarModel> call = apiService.deleteCar(id);

                        call.enqueue(new Callback<CarModel>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                    MainActivity.CallAPI(retrofit);
                                    dialog.dismiss();
                                    notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onFailure(Call<CarModel> call, Throwable t) {
                                Log.e("cccccc", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });

            }
        });
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewCreateAndAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("titleEdit", "Update car");
                intent.putExtra("titleBtnEdit", "Update");
                intent.putExtra("ten",listCar.get(position).getTen());
                intent.putExtra("namSX",listCar.get(position).getNamSX());
                intent.putExtra("hang",listCar.get(position).getHang());
                intent.putExtra("gia",listCar.get(position).getGia());
                intent.putExtra("id",listCar.get(position).get_id());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listCar.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder{

        TextView tvID;
        ImageView imgAvatar;
        TextView tvName ;
        TextView tvNamSX ;
        TextView tvHang;
        TextView tvGia;

        ImageButton btndelete, btnupdate;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = (TextView) itemView.findViewById(R.id.tvId);
            imgAvatar = itemView.findViewById(R.id.imgAvatatr);
             tvName = itemView.findViewById(R.id.tvName);
             tvNamSX = itemView.findViewById(R.id.tvNamSX);
             tvHang = itemView.findViewById(R.id.tvHang);
             tvGia = itemView.findViewById(R.id.tvGia);

             btndelete = itemView.findViewById(R.id.btndelete);
             btnupdate = itemView.findViewById(R.id.btnupdate);
        }
    }
}
