package com.talix573.oasis.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.talix573.oasis.R;
import com.talix573.oasis.common.AppDatabase;
import com.talix573.oasis.common.Plant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPlantActivity extends AppCompatActivity {

    // Constants
    static final int REQUEST_IMG_CAPTURE = 1;
    static final int NEW_PLANT = 1;
    static final int EXISTING_PLANT = 2;

    // UI
    private ImageView plantPreview;
    private EditText plantName;
    private EditText irrigationPeriod;
    private Button submitButton;

    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        plantPreview = findViewById(R.id.plant_preview);
        plantName = findViewById(R.id.plant_name);
        irrigationPeriod = findViewById(R.id.irrigation_period);
        submitButton = findViewById(R.id.submit_plant_button);

        // Init values
        plantName.setText("");
        irrigationPeriod.setText("");

        Intent intent = getIntent();
        Integer purpose = intent.getIntExtra("PURPOSE", NEW_PLANT);
        switch (purpose) {
            case 1:
                this.dispatchTakePictureIntent();
                break;
            case 2:
                break;
            default:
                break;
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = plantName.getText().toString();
                String hours = irrigationPeriod.getText().toString();
                Long milliseconds = Long.parseLong(hours) * 3600000;
                if (purpose == NEW_PLANT) {
                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "plant").allowMainThreadQueries().fallbackToDestructiveMigration().build();

                    Plant plant = new Plant();
                    plant.name = name;
                    plant.irrigationPeriod = milliseconds;
                    Date date = new Date();
                    date.setTime(date.getTime() - milliseconds);
                    plant.lastWatered = date;
                    plant.imagePath = currentPhotoPath;
                    db.plantDao().insertOne(plant);
                    Log.i("PLANT SAVED:", String.valueOf(plant));

                    Intent redirectToHome = new Intent(AddPlantActivity.this,  MainActivity.class);
                    startActivity(redirectToHome);
                    finish();
                } else if (purpose == EXISTING_PLANT) {

                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error while creating the file. Try again later!", Toast.LENGTH_SHORT);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.talix573.oasis.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMG_CAPTURE);
                Log.i("HELLO", currentPhotoPath);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMG_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (currentPhotoPath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                plantPreview.setImageBitmap(bitmap);
            } else {
                Intent redirectToHome = new Intent(AddPlantActivity.this,  MainActivity.class);
                startActivity(redirectToHome);
                finish();
            }
        }
    }
}