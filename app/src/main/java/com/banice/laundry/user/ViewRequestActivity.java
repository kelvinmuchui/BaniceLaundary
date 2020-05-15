package com.banice.laundry.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banice.laundry.R;
import com.banice.laundry.user.Models.ModelUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewRequestActivity extends AppCompatActivity {

    TextView providerName,plocation,pTime,pDate,pyDay,pyMode;
    String providername;
    Button pdfbtn, editbtn;
    private Bitmap bitmap;
    private RelativeLayout llPdf;


    String providerId,pickloaction,picktime,pickdate,paydate,paymode;
    DatabaseReference reference;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        providerId = getIntent().getStringExtra("provider_id");
        pickdate =getIntent().getStringExtra("deliverydate");
        pickloaction =getIntent().getStringExtra("Picklocation");
        picktime =getIntent().getStringExtra("picktime");
        paydate =getIntent().getStringExtra("paydate");
        paymode =getIntent().getStringExtra("paymode");

        providerName = findViewById(R.id.selectedprovidername);
        plocation = findViewById(R.id.selectedplocationname);
        pTime = findViewById(R.id.selectedptimename);
        pDate = findViewById(R.id.selectedddatename);
        pyDay = findViewById(R.id.selectedpaydatename);
        pyMode = findViewById(R.id.selectedpmodename);
        editbtn = findViewById(R.id.editbtn);
        pdfbtn = findViewById(R.id.downnloadbtn);
        llPdf = findViewById(R.id.llPdf);

        pd= new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference();
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditRequestDialog();
            }
        });

        reference.child("Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ModelUser user =  dataSnapshot.getValue(ModelUser.class);

                    providername = user.getProvider_Name();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        providerName.setText(providername);
        plocation.setText(pickloaction);
        pTime.setText(picktime);
        pDate.setText(pickdate);
        pyDay.setText(paydate);
        pyMode.setText(paymode);


        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.d("size"," "+llPdf.getWidth() +"  "+llPdf.getWidth());
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                pdfbtn.setVisibility(View.INVISIBLE);
                editbtn.setVisibility(View.INVISIBLE);
                createPdf();
            }
        });

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(ViewRequestActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void showEditRequestDialog(){
        String options [] = {"Edit provider", "Edit Pick up time","Edit location", "Edit Payment Mode", "Edit Payment Date", "Edit delivery Date"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose Action");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0){
                    showEdit("provider", providerName);
                }else  if(which == 1){
                    showEdit("Delivery date",pDate);

                }else  if(which == 2){
                    showEdit("PayDate",pyDay);
                }else  if(which == 3){
                    showEdit("PickTime",pTime);
                }else  if(which == 4){
                    showEdit("Location",plocation);
                }else  if(which == 5) {
                    showEdit("PayMode",pyMode);
                }

            }
        });

        builder.create().show();
    }

    private void showEdit(final String key, final TextView data) {

        //Custom dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update" + key);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);


        final EditText editText = new EditText(this);
        editText.setText("Enter" + key);

        linearLayout.addView(editText);

        builder.setView(linearLayout);


        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)){
                    pd.show();

                    data.setText(value);
                }else{
                    Toast.makeText(ViewRequestActivity.this, "Enter" +key , Toast.LENGTH_SHORT).show();

                }

                pd.dismiss();
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
