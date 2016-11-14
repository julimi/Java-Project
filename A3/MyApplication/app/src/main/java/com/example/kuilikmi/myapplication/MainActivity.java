package com.example.kuilikmi.myapplication;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Drawing drawing;
    private ImageButton curPaint1, curPaint2, selectBtn, fillBtn, lineBtn, rectBtn, cirBtn, eraseBtn, newBtn, saveBtn, sizeBtn1, sizeBtn2, sizeBtn3;
    private float smSize, mdSize, lgSize;
    private static final int SELECT_PHOTO = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation != 1) setContentView(R.layout.activity_main);
        else setContentView(R.layout.activity_main_new);
        //get drawing view
        drawing = (Drawing)findViewById(R.id.drawing);

        //get the palette and first color button
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        drawing.setColor("#FF003300");
        curPaint1 = (ImageButton)paintLayout.getChildAt(0);
        curPaint1.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
        paintLayout = (LinearLayout)findViewById(R.id.paint_sizes);
        curPaint2 = (ImageButton)paintLayout.getChildAt(1);
        curPaint2.setImageDrawable(getResources().getDrawable(R.drawable.pressed));

        //sizes from dimensions
        smSize = getResources().getInteger(R.integer.small_size);
        mdSize = getResources().getInteger(R.integer.medium_size);
        lgSize = getResources().getInteger(R.integer.large_size);

        //curTool
        drawing.setTool(1);

        //select button
        selectBtn = (ImageButton)findViewById(R.id.btn1);
        selectBtn.setOnClickListener(this);

        //fill button
        fillBtn = (ImageButton)findViewById(R.id.btn6);
        fillBtn.setOnClickListener(this);

        //erase button
        eraseBtn = (ImageButton)findViewById(R.id.btn2);
        eraseBtn.setOnClickListener(this);

        //line button
        lineBtn = (ImageButton)findViewById(R.id.btn3);
        lineBtn.setOnClickListener(this);
        drawing.setdrawSize(mdSize);

        //rectangle button
        rectBtn = (ImageButton)findViewById(R.id.btn4);
        rectBtn.setOnClickListener(this);

        //circle button
        cirBtn = (ImageButton)findViewById(R.id.btn5);
        cirBtn.setOnClickListener(this);

        //new button
        newBtn = (ImageButton)findViewById(R.id.btn7);
        newBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton)findViewById(R.id.btn8);
        saveBtn.setOnClickListener(this);

        // small size button
        sizeBtn1 = (ImageButton)findViewById(R.id.btn9);
        sizeBtn1.setOnClickListener(this);

        // medium size button
        sizeBtn2 = (ImageButton)findViewById(R.id.btn10);
        sizeBtn2.setOnClickListener(this);

        // large size button
        sizeBtn3 = (ImageButton)findViewById(R.id.btn11);
        sizeBtn3.setOnClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(outState);
    }

    public void colorClick(View view) {
        drawing.setErase(false);
        if(view!=curPaint1){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawing.setColor(color);
            //update ui
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
            curPaint1.setImageDrawable(getResources().getDrawable(R.drawable.draw));
            curPaint1=(ImageButton)view;
        }
    }

    public void loadClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void onClick(View view) {

        if (view.getId()==R.id.btn3){
            drawing.setErase(false);
            drawing.setFill(false);
            drawing.setSelect(false);
            drawing.setTool(1);
        } else if (view.getId()==R.id.btn4) {
            drawing.setErase(false);
            drawing.setFill(false);
            drawing.setSelect(false);
            drawing.setTool(2);
        } else if (view.getId()==R.id.btn5) {
            drawing.setErase(false);
            drawing.setFill(false);
            drawing.setSelect(false);
            drawing.setTool(3);
        } else if (view.getId()==R.id.btn2){
            //drawing.setTool(1);
            drawing.setErase(true);
            drawing.setFill(false);
            drawing.setSelect(false);
        } else if (view.getId()==R.id.btn1) {
            drawing.setErase(false);
            drawing.setFill(false);
            drawing.setSelect(true);
        } else if (view.getId()==R.id.btn6) {
            drawing.setFill(true);
            drawing.setSelect(false);
            drawing.setErase(false);
        } else if(view.getId()==R.id.btn7){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New Drawing");
            newDialog.setMessage("Are you sure to start new drawing?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    drawing.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
        } else if(view.getId()==R.id.btn8){
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save drawing");
            saveDialog.setMessage("Save drawing to Gallery?");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    //save drawing
                    drawing.setDrawingCacheEnabled(true);
                    //attempt to save
                    String saved = MediaStore.Images.Media.insertImage(
                            getContentResolver(), drawing.getDrawingCache(),
                            UUID.randomUUID().toString()+".png", "drawing");
                    //feedback
                    if(saved!=null){
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                "Done! Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                        savedToast.show();
                    }
                    else{
                        Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                "Fail! Drawing cannot be saved.", Toast.LENGTH_SHORT);
                        unsavedToast.show();
                    }
                    drawing.destroyDrawingCache();
                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            saveDialog.show();

        } else if (view.getId() == R.id.btn9) {
            //drawing.setErase(false);
            if (view != curPaint2) {
                drawing.setdrawSize(smSize);

                ImageButton imgView = (ImageButton) view;
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
                curPaint2.setImageDrawable(getResources().getDrawable(R.drawable.sm));
                curPaint2 = (ImageButton) view;
            }
        } else if (view.getId() == R.id.btn10) {
            //drawing.setErase(false);
            if (view != curPaint2) {
                drawing.setdrawSize(mdSize);
                ImageButton imgView = (ImageButton) view;
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
                curPaint2.setImageDrawable(getResources().getDrawable(R.drawable.md));
                curPaint2 = (ImageButton) view;
            }
        } else if (view.getId() == R.id.btn11) {
            //drawing.setErase(false);
            if (view != curPaint1) {
                drawing.setdrawSize(lgSize);
                ImageButton imgView = (ImageButton) view;
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
                curPaint2.setImageDrawable(getResources().getDrawable(R.drawable.lg));
                curPaint2 = (ImageButton) view;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {

            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream stream = null;
                    try {
                        stream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);

                        BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);

                        if(Build.VERSION.SDK_INT >= 16)
                        {
                            drawing.setBackground(bd);
                        }else {
                            drawing.setBackgroundDrawable(bd);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

}
