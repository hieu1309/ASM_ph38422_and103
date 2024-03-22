package com.example.asm_ph38422;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewCreateAndAddActivity extends AppCompatActivity {

    private static final int MY_RES_CODE = 10;
    TextView tvTitle;
    ImageView ivBack,ivImageShoe;
    AppCompatButton btnNewAndEdit;
    EditText edTen, edNamSX, edHang, edGia;
    LinearLayout btnChooseImage;

    Uri mUri;//DEMO

    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {

                    Log.e("zzzzz", "onActivityResult: ");

                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            ivImageShoe.setImageBitmap(bitmap);

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create_and_add);

        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBackCreUp);
        btnNewAndEdit = findViewById(R.id.btnNewAndEdit);
        edTen = findViewById(R.id.edTen);
        edNamSX = findViewById(R.id.edNamSX);
        edHang = findViewById(R.id.edHang);
        edGia = findViewById(R.id.edGia);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        ivImageShoe = findViewById(R.id.ivImageShoe);

        ChangeUI();

        btnNewAndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String titleAdd = getIntent().getStringExtra("titleAdd");
                //Data intent update
                if (titleAdd == null) {
                    UpdateCar();
                } else {
                    CreateCar();
//                    CreateShoe2();//DEMO

                }

            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void ChooseImage() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_RES_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode  == MY_RES_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }
    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }

    private void CreateCar() {

        String ten = edTen.getText().toString();
        String namSX = edNamSX.getText().toString();
        String hang = edHang.getText().toString();
        String gia = edGia.getText().toString();

        if (CheckCreateCar()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiService = retrofit.create(APIService.class);

            Call<CarModel> call = apiService.createCar(new CarModel(ten, Integer.parseInt(namSX), hang, Double.parseDouble(gia), "url nè"));


            call.enqueue(new Callback<CarModel>() {
                @Override
                public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(NewCreateAndAddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewCreateAndAddActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CarModel> call, Throwable t) {
                    Log.e("zzzzz", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private boolean CheckCreateCar() {

        return true;
    }

    private void UpdateCar() {

        String ten = edTen.getText().toString();
        String namSX = edNamSX.getText().toString();
        String hang = edHang.getText().toString();
        String gia = edGia.getText().toString();
        String id = getIntent().getStringExtra("id");

        if (CheckCreateCar()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiService = retrofit.create(APIService.class);

            Call<CarModel> call = apiService.updateCar(id, new CarModel(ten, Integer.parseInt(namSX), hang, Double.parseDouble(gia), "url nè"));


            call.enqueue(new Callback<CarModel>() {
                @Override
                public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(NewCreateAndAddActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewCreateAndAddActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CarModel> call, Throwable t) {
                    Log.e("zzzzz", "onFailure: " + t.getMessage());
                }
            });


        }


    }

    private void ChangeUI() {
        //Data intent add
        String titleAdd = getIntent().getStringExtra("titleAdd");
        String titleBtnAdd = getIntent().getStringExtra("titleBtnAdd");
        //Data intent update
        String titleUpdate = getIntent().getStringExtra("titleEdit");
        String titleBtnUp = getIntent().getStringExtra("titleBtnEdit");
        String ten = getIntent().getStringExtra("ten");
        String namSX = getIntent().getStringExtra("namSX");
        String hang = getIntent().getStringExtra("hang");
        Double gia = getIntent().getDoubleExtra("gia", 0);

        if (titleUpdate == null) {
            tvTitle.setText(titleAdd);
            btnNewAndEdit.setText(titleBtnAdd);
        } else {
            tvTitle.setText(titleUpdate);
            edTen.setText(ten);
            edNamSX.setText(namSX);
            edHang.setText(hang);
            edGia.setText(gia + "");

            btnNewAndEdit.setText(titleBtnUp);
        }

        //set onClick nut back
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCreateAndAddActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}