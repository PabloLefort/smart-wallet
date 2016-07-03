package com.example.pablo.smartwallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int SCAN_QR_ACTIVITY_REQUEST_CODE = 200;
    private static String[][] datos = new String[0][2];
    private static CardView card;
    private static TextView txtnombre;
    private static TextView txtprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        txtnombre = (TextView) findViewById(R.id.nombre);
        txtprecio = (TextView) findViewById(R.id.precio);
        card = (CardView) findViewById(R.id.cv);
        card.setVisibility(View.GONE);

        //RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);

        //recycler.setHasFixedSize(true);
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    }

    public void scanQr(View view){
        Intent scanIntent = new Intent(this, ScanCodeActivity.class);
        scanIntent.putExtra("nombre", "");
        scanIntent.putExtra("precio", 0);
        startActivityForResult(scanIntent, SCAN_QR_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if(requestCode == SCAN_QR_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                card.setVisibility(View.VISIBLE);
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
