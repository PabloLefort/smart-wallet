package com.example.pablo.smartwallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int SCAN_QR_ACTIVITY_REQUEST_CODE = 200;
    private static CardView card;
    private static TextView txtnombre;
    private static TextView txtprecio;
    private static Button finalizar;
    private static Bitmap photo;
    private static String SCAN_FACE_REQUEST_URL = "http://localhost:8000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        txtnombre = (TextView) findViewById(R.id.nombre);
        txtprecio = (TextView) findViewById(R.id.precio);
        finalizar = (Button) findViewById(R.id.finalizar);
        card = (CardView) findViewById(R.id.cv);

        card.setVisibility(View.GONE);
        finalizar.setVisibility(View.GONE);


    }

    public void scanQr(View view){
        Intent scanIntent = new Intent(this, ScanCodeActivity.class);
        scanIntent.putExtra("nombre", "");
        scanIntent.putExtra("precio", 0);
        startActivityForResult(scanIntent, SCAN_QR_ACTIVITY_REQUEST_CODE);
    }

    public void finishBuy(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, AppConstant.URI_IMAGE_CAPTURED);
        //intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.camera2.);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        /*try {
            //create a file to write bitmap data
            //File f = new File(context.getCacheDir(), filename);
            File f = new File("testing.png");
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            MultipartUtility multipart = new MultipartUtility(SCAN_FACE_REQUEST_URL, "utf-8");
            multipart.addFilePart("fileee", f);
            List<String> response = multipart.finish();
            System.out.println("SERVER REPLIED:");
            for (String line : response) {
                System.out.println("Upload Files Response:::" + line);
                System.out.println(line);
            }

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                //Toast.makeText(this, "Saved to:\n" +data.getData(), Toast.LENGTH_LONG).show();
                //photo = (Bitmap) data.getExtras().get("data");
                System.out.println("PHOTO: "+photo);
                setContentView(R.layout.success);

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if(requestCode == SCAN_QR_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                card.setVisibility(View.VISIBLE);
                finalizar.setVisibility(View.VISIBLE);
                txtnombre.setText("Prenda: "+data.getStringExtra("nombre"));
                txtprecio.setText("Precio: "+data.getStringExtra("precio"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
