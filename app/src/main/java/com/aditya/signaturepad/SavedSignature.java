package com.aditya.signaturepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aditya.signaturepad.Adapter.RVAdapter;

public class SavedSignature extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private RecyclerView objectRecyclerView;

    private RVAdapter objectRvAdapter;
    Button deleteSignbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_signature);

        deleteSignbtn=findViewById(R.id.deleteSignbtn);

        try{
            objectRecyclerView = findViewById(R.id.imageRV);
            databaseHandler=new DatabaseHandler(this);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{

            Log.d("YourResponse","image-- "+databaseHandler.getAllImageData());
            objectRvAdapter=new RVAdapter(databaseHandler.getAllImageData());
            objectRecyclerView.setHasFixedSize(true);

            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objectRvAdapter);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        deleteSignbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDeleteallBox();
            }
        });

    }

    private void alertDeleteallBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Setting message manually and performing action on button click
        builder.setMessage("Are you sure you want to delete all?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseHandler.deleteAllAtricles();
                        //startActivity(getIntent());
                        //finish();
                        Intent i = new Intent(SavedSignature.this, SavedSignature.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //your class
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(),"All Signature deleted successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
