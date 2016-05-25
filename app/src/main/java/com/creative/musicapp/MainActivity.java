package com.creative.musicapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.creative.musicapp.appdata.AppConstant;
import com.creative.musicapp.appdata.AppController;
import com.creative.musicapp.util.PrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout btn_play;

    private ImageView btn_setting;

    private PrefManager saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        saveData = new PrefManager(this);

        new LoadViewTask().execute();


    }


    // To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        // Before running code in separate thread
        @Override
        protected void onPreExecute() {
            // Create a new progress dialog
            //progressbar.setVisibility(View.VISIBLE);

            // load_animation();
        }

        // The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params) {

            AppConstant.loadAllLevelData();


            AppConstant.levelIntArray = saveData.getIntArray();

            if (AppConstant.levelIntArray.length <= 1) {
                AppConstant.levelIntArray = AppConstant.makeLevelRandomization();

                saveData.setIntArray(AppConstant.levelIntArray);
            }


            return null;
        }

        // Update the progress

        // after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {

            init();
        }

    }

    private void init() {

        btn_play = (LinearLayout) findViewById(R.id.btn_play);

        btn_play.setOnClickListener(this);

        btn_setting = (ImageView) findViewById(R.id.btn_setting);

        btn_setting.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();


        if (id == R.id.btn_play) {



            Intent intent = new Intent(MainActivity.this, GamePlay.class);
            startActivity(intent);

        }
        if (id == R.id.btn_setting) {
            showSettingDialog();
        }
    }

    private void showSettingDialog() {

        final Dialog dialog_start = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(true);
        dialog_start.setContentView(R.layout.dialog_setting);


        LinearLayout reset_data = (LinearLayout) dialog_start.findViewById(R.id.dialog_setting_reset_data);
        reset_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.levelIntArray = AppConstant.makeLevelRandomization();

                saveData.setIntArray(AppConstant.levelIntArray);

                saveData.setCurrentLevel(0);
                saveData.setRemainingCoin(60);

                Toast.makeText(MainActivity.this,"All Data Reset",Toast.LENGTH_SHORT).show();

                dialog_start.dismiss();
            }
        });


        dialog_start.show();
    }
    
}
