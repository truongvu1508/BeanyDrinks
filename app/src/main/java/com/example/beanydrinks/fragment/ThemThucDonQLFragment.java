package com.example.beanydrinks.fragment;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.beanydrinks.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ThemThucDonQLFragment extends Fragment {

    private ImageView imageMon; // ImageView để hiển thị ảnh món
    Button btn_addMon;
    EditText edt_nameMon;
    EditText edt_giaMon;
    Spinner spinner_loaiMon;
    ProgressBar progressBar;
    ImageView imageView_Mon;
    int MY_REQUEST_CODE = 1;

    Bitmap bitmap;
    String encodeImage;
    String names;
    String BASE_API_URL="";
    public ThemThucDonQLFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_thuc_don_q_l, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnbackthemttkhach);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        imageView_Mon=view.findViewById(R.id.image_mon);
        btn_addMon =view.findViewById(R.id.button_addMon);
        edt_nameMon=view.findViewById(R.id.edt_tenMon);
        edt_giaMon=view.findViewById(R.id.edt_giaTien);
        progressBar = view.findViewById(R.id.progessbar);
        imageView_Mon.setOnClickListener(v-> {
            Dexter.withContext(getContext())
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select image"),MY_REQUEST_CODE);

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();
        });
        btn_addMon.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_API_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String > myParams = new HashMap<>();
                    return super.getParams();
                }
            };
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == MY_REQUEST_CODE && requestCode==RESULT_OK && data!=null ){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView_Mon.setImageBitmap(bitmap);
                ImageStore(bitmap);
            } catch (FileNotFoundException e){
                throw new RuntimeException(e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ImageStore(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        encodeImage = android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);
    }
}
