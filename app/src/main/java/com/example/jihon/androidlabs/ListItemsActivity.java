package com.example.jihon.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME="ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }
        );

        Switch onOffSwitch = (Switch)findViewById(R.id.onOffSwitch);

        onOffSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        CharSequence text;
                        int duration;
                        Toast toast;
                        if(compoundButton.isChecked()){
                          // text = "Switch is On";
                            duration = Toast.LENGTH_SHORT;
                            toast = Toast.makeText(getApplicationContext(), R.string.switch_on, duration);
                        }else{
                           // text = "Switch is Off";
                            duration = Toast.LENGTH_LONG;
                            toast = Toast.makeText(getApplicationContext(), R.string.switch_off, duration);
                        }

                        toast.show();
                    }
                }
        );

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(ListItemsActivity.this);
                        //chain together various setter methods to set the dialog characteristics
                        builder.setMessage(R.string.dialog_message)//add a dialog message to strings.xml
                                .setTitle(R.string.dialog_title)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("Response",
                                                getString(R.string.response_message));
                                        setResult(Activity.RESULT_OK, resultIntent);
                                        finish();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
        );

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageButton.setImageBitmap(imageBitmap);

        }

    }




    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}

