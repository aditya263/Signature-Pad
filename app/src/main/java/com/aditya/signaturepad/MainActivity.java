package com.aditya.signaturepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.aditya.signaturepad.Adapter.RVAdapter;
import com.aditya.signaturepad.Model.ModelClass;

public class MainActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    Button saveButton, clearButton;
    Bitmap bitmap;
    String path;
    EditText etSignatureName;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    DatabaseHandler objectDatabaseHandler;
    private RecyclerView objectRecyclerView;

    private RVAdapter objectRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        etSignatureName=findViewById(R.id.etSignatureName);

        try{
            objectDatabaseHandler=new DatabaseHandler(this);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);
        etSignatureName.setEnabled(false);

        //change screen orientation to landscape mode
        /*setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);*/

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                etSignatureName.setEnabled(true);
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                etSignatureName.setText("");
                etSignatureName.setEnabled(false);
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        /*saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for saving the signature here
                bitmap = signaturePad.getSignatureBitmap();
                //path = saveImage(bitmap);
            }
        });*/

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
    }

    public void storeImage(View view){

        //write code for saving the signature here
        bitmap = signaturePad.getSignatureBitmap();
        //path = saveImage(bitmap);
        try {

            if (!etSignatureName.getText().toString().isEmpty() && bitmap!=null){
                objectDatabaseHandler.storeImage(new ModelClass(etSignatureName.getText().toString(),bitmap));
                Toast.makeText(MainActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();

                signaturePad.clear();
                etSignatureName.setText("");
            }
            else {
                Toast.makeText(this, "Please Insert Signature's Name", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void moveToSavedSignature(View view){
        Intent intent = new Intent(MainActivity.this,SavedSignature.class);
        startActivity(intent);
    }



    /*public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY *//*iDyme folder*//*);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh",wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(MainActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }*/

}
