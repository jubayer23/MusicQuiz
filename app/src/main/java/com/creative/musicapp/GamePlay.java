package com.creative.musicapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.musicapp.appdata.AppConstant;
import com.creative.musicapp.appdata.AppController;
import com.creative.musicapp.musicvisualizer.renderer.BarGraphRenderer;
import com.creative.musicapp.musicvisualizer.renderer.CircleBarRenderer;
import com.creative.musicapp.musicvisualizer.renderer.CircleRenderer;
import com.creative.musicapp.musicvisualizer.renderer.LineRenderer;
import com.creative.musicapp.musicvisualizer.utils.TunnelPlayerWorkaround;
import com.creative.musicapp.musicvisualizer.visualizer.VisualizerView;
import com.creative.musicapp.service.MusicService;
import com.creative.musicapp.util.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by comsol on 10/30/2015.
 */
public class GamePlay extends Activity implements View.OnClickListener {

    private LinearLayout ll_keyboard_row_one, ll_keyboard_row_two, ll_Solvekeyboard_row_one, ll_Solvekeyboard_row_two;

    private ArrayList<LinearLayout> keyboard = new ArrayList<LinearLayout>();
    private ArrayList<LinearLayout> keyboard_solve = new ArrayList<LinearLayout>();

    private ImageView btn_help, btn_freecoins, btn_back;

    private TextView coin_remaining, level_num_tag;

    private String[] levelQuest;


    private String songName;

    private static Integer[] string_track;

    private char userWordCharArray[];

    private char keyboardCharArray[] = new char[20];

    char[] randCharArray;

    private int current_level = 0, max_level = AppConstant.startLevel;

    private static HashMap<Integer, Integer> keyboard_map = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> keyboard_inverse_map = new HashMap<Integer, Integer>();


    private Typeface tf_granda;


    public static boolean PRESS_LEVEL_SOLVE_FROM_HELP_DIALOG = false;

    private static final int TEXTVIEW_SIZE_FOR_SOLVE_KEYBOARD = 25;

    private static final int TEXTVIEW_SIZE_FOR_KEYBOARD = 25;

    private static boolean IS_REMOVE_LETTER_ALREADY_USED = false;

    private PrefManager saveData;

    // Instance of media player
    private MediaPlayer mPlayer;

    private MediaPlayer mSilentPlayer;  /* to avoid tunnel player issue */
    private VisualizerView mVisualizerView;

    // Saves the data buffer of the media player (used by MediaController).
    private int mBuffer = 0;

    private String music_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        init();


        initialLevelNumber();


        findLevelQuest();

        findCharForKeyboard();

        setupKeyboard();

        setupSolveKeyboard();

        setupRemainingCoin();

        //setupMusic();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initTunnelPlayerWorkaround();
        start(songName);

        mVisualizerView.link(mPlayer);

        // Start with just line renderer
        //addLineRenderer();
        addBarGraphRenderers();
    }

    private void init() {


        saveData = new PrefManager(this);

        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);

        ll_keyboard_row_one = (LinearLayout) findViewById(R.id.layout_keybaord_row_one);
        ll_keyboard_row_two = (LinearLayout) findViewById(R.id.layout_keyboard_row_two);
        ll_Solvekeyboard_row_one = (LinearLayout) findViewById(R.id.layout_Solvekeybaord_row_one);
        ll_Solvekeyboard_row_two = (LinearLayout) findViewById(R.id.layout_Solvekeybaord_row_two);


        btn_help = (ImageView) findViewById(R.id.btn_help);
        btn_help.setOnClickListener(this);
        btn_freecoins = (ImageView) findViewById(R.id.btn_freecoins);
        btn_freecoins.setOnClickListener(this);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        coin_remaining = (TextView) findViewById(R.id.coin_remaining);

        level_num_tag = (TextView) findViewById(R.id.level_num_tag);


        tf_granda = Typeface.createFromAsset(getAssets(), "MJ Granada.ttf");
    }


    private void initialLevelNumber() {
        current_level = saveData.getCurrentLevel();

        level_num_tag.setText("LEVEL " + String.valueOf(current_level + 1));
    }


    private void findLevelQuest() {

        // TODO Auto-generated method stub

        songName = AppConstant.levelQuest_map.get(AppConstant.levelIntArray[current_level]);

       // Log.d("DEBUG_currentlevel", String.valueOf(current_level));


        levelQuest = songName.split("_");


        string_track = new Integer[getLevelQuest().length()];
        userWordCharArray = new char[getLevelQuest().length()];
        // Log.d("DEBUG", String.valueOf(temp.length));
        for (int i = 0; i < getLevelQuest().length(); i++) {
            string_track[i] = 0;
            userWordCharArray[i] = '0';
        }


    }

    private String getLevelQuest() {
        String temp = "";
        for (int i = 0; i < levelQuest.length; i++) {
            temp = temp + levelQuest[i];
        }
        return temp.trim();
    }

    private void findCharForKeyboard() {
        // TODO Auto-generated method stub

        int needRandomChar = 16 - (getLevelQuest().length());

        char[] levelQuestCharArray = getLevelQuest().toCharArray();

        randCharArray = new char[needRandomChar];

        for (int i = 0; i < needRandomChar; i++) {
            Random ran = new Random();

            randCharArray[i] = (char) (96 + (ran.nextInt(26) + 1));
        }

        StringBuilder sb = new StringBuilder(14);

        sb.append(levelQuestCharArray);
        sb.append(randCharArray);

        keyboardCharArray = sb.toString().toCharArray();

        String shuffledWord = shuffle(String.copyValueOf(keyboardCharArray));

        keyboardCharArray = shuffledWord.toCharArray();

    }

    public String shuffle(String input) {
        List<Character> characters = new ArrayList<Character>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }


    private void setupKeyboard() {

        keyboard.clear();

        int margin = AppController.getInstance().getPixelValue(4);

// removing child blank diamond
        if (ll_keyboard_row_one.getChildCount() > 0)
            ll_keyboard_row_one.removeAllViews();
        // removing child blank diamond
        if (ll_keyboard_row_two.getChildCount() > 0)
            ll_keyboard_row_two.removeAllViews();

        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);

        LLParams.setMargins(margin, margin, margin, margin);

        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //Adding Button in 1st row of the keyboard
        for (int i = 0; i < 8; i++) {

            LinearLayout LL = new LinearLayout(this);

            LL.setLayoutParams(LLParams);
            LL.setGravity(Gravity.CENTER);

            LL.setBackgroundResource(R.drawable.layout_bg_rounded);

            TextView tv = new TextView(this);

            tv.setLayoutParams(tvParams);
            tv.setText(String.valueOf(keyboardCharArray[i]).toUpperCase());
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setTextSize(TEXTVIEW_SIZE_FOR_KEYBOARD);
            //tv.setTextColor(Color.parseColor("#333"));

            LL.addView(tv);

            LL.setOnClickListener(this);


            keyboard.add(LL);

            ll_keyboard_row_one.addView(LL);
        }
        //Adding Button in 1st row of the keyboard
        for (int i = 0; i < 8; i++) {

            LinearLayout LL = new LinearLayout(this);

            LL.setLayoutParams(LLParams);
            LL.setGravity(Gravity.CENTER);
            LL.setClipChildren(false);
            LL.setBackgroundResource(R.drawable.layout_bg_rounded);

            TextView tv = new TextView(this);

            tv.setLayoutParams(tvParams);
            tv.setText(String.valueOf(keyboardCharArray[i + 8]).toUpperCase());
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setTextSize(TEXTVIEW_SIZE_FOR_KEYBOARD);

            LL.addView(tv);

            LL.setOnClickListener(this);


            keyboard.add(LL);

            ll_keyboard_row_two.addView(LL);
        }


    }

    private void setupSolveKeyboard() {

        keyboard_solve.clear();

        int margin = AppController.getInstance().getPixelValue(1);


        String[] row_string = AppConstant.solveKeyboardformat_map.get(AppConstant.levelIntArray[current_level]).split(",");


        int first_row = Integer.parseInt(row_string[0]);
        int second_row = Integer.parseInt(row_string[1]);

        //Log.d("DEBUG_row",String.valueOf(row_string[0]) + " " + String.valueOf(row_string[1]));

        String space_pos[] = AppConstant.solveKeyboardformatSpace_map.get(AppConstant.levelIntArray[current_level]).split(",");
        HashMap<Integer, Boolean> space_pos_map = new HashMap<Integer, Boolean>();

        for (int i = 0; i < space_pos.length; i++) {
            space_pos_map.put(Integer.parseInt(space_pos[i]), true);

            //Log.d("DEBUG_space",space_pos[i]);
        }

        if (ll_Solvekeyboard_row_one.getChildCount() > 0)
            ll_Solvekeyboard_row_one.removeAllViews();
        // removing child blank diamond
        if (ll_Solvekeyboard_row_two.getChildCount() > 0)
            ll_Solvekeyboard_row_two.removeAllViews();


        if (first_row > 10) {
            ll_Solvekeyboard_row_one.setWeightSum(13f);
        }
        if (second_row > 10) {
            ll_Solvekeyboard_row_two.setWeightSum(13f);
        }
        if (first_row > 11) {
            ll_Solvekeyboard_row_one.setWeightSum(14f);
        }
        if (second_row > 11) {
            ll_Solvekeyboard_row_two.setWeightSum(14f);
        }


        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        LLParams.setMargins(margin, margin, margin, margin);


        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int space_pos_counter = 0;
        for (int i = 0; i < first_row; i++, space_pos_counter++) {
            LinearLayout LL = new LinearLayout(this);

            LL.setLayoutParams(LLParams);
            LL.setGravity(Gravity.CENTER);


            ImageView img = new ImageView(this);
            img.setLayoutParams(imgParams);
            if (space_pos_map.get(space_pos_counter) != null) {

                img.setImageResource(0);
            } else {

                img.setImageResource(R.drawable.line);
            }


            LL.addView(img);


            if (space_pos_map.get(space_pos_counter) == null) {
                LL.setOnClickListener(this);
                keyboard_solve.add(LL);
            }


            ll_Solvekeyboard_row_one.addView(LL);


        }
        for (int i = 0; i < second_row; i++, space_pos_counter++) {
            LinearLayout LL = new LinearLayout(this);

            LL.setLayoutParams(LLParams);
            LL.setGravity(Gravity.CENTER);


            ImageView img = new ImageView(this);
            img.setLayoutParams(imgParams);
            if (space_pos_map.get(space_pos_counter) != null) {

                img.setImageResource(0);
            } else {

                img.setImageResource(R.drawable.line);
            }

            LL.addView(img);
            if (space_pos_map.get(space_pos_counter) == null) {
                LL.setOnClickListener(this);
                keyboard_solve.add(LL);
            }

            ll_Solvekeyboard_row_two.addView(LL);
        }
    }


    private void setupRemainingCoin() {

        coin_remaining.setText(String.valueOf(saveData.getRemainingCoin()));
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (keyboard.contains(view)) {
            // Toast.makeText(this,
            //         String.valueOf(keyboardCharArray[keyboard.indexOf(view)]),
            //         Toast.LENGTH_SHORT).show();
            updateSolveKeyboard(keyboardCharArray[keyboard.indexOf(view)],
                    keyboard.indexOf(view), view, 0);


            // checkarray(userword);

        }
        if (keyboard_solve.contains(view)) {
            if (string_track[keyboard_solve.indexOf(view)] == 1) {
                manageChangeInSolveKeyboard(keyboard_solve.indexOf(view));
            }

        }


        if (id == R.id.btn_freecoins) {
            dialogFreeCoins();
        }
        if (id == R.id.btn_help) {
            dialogHelp();
        }
        if (id == R.id.btn_back) {
            onBackPressed();
        }


    }

    private void updateSolveKeyboard(char onSolveChar, int keyboard_pos,
                                     View view, int index_start) {
        // TODO Auto-generated method stub

        for (int index = index_start; index < getLevelQuest().length(); index++) {

            if (string_track[index] == 0) {

                if (keyboard_solve.get(index).getChildCount() > 0)
                    keyboard_solve.get(index).removeAllViews();


                // TextView tv = (TextView)keyboard.get(keyboard_pos).getChildAt(0);

                TextView tv = new TextView(this);

                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setText(String.valueOf(keyboardCharArray[keyboard_pos]).toUpperCase());
                //tv.setTextSize(20);
                tv.setTextColor(getResources().getColor(R.color.black));
                tv.setTypeface(tf_granda);
                tv.setTextSize(TEXTVIEW_SIZE_FOR_SOLVE_KEYBOARD);
                //keyboard_solve.get(index).setBackgroundResource(R.drawable.layout_bg_rounded);

                keyboard_solve.get(index).addView(tv);

                userWordCharArray[index] = onSolveChar;

                string_track[index] = 1;

                keyboard_map.put(index, keyboard_pos);
                keyboard_inverse_map.put(keyboard_pos, index);

                view.setVisibility(View.INVISIBLE);


                String userword = String.copyValueOf(userWordCharArray);

                if (userword.equalsIgnoreCase(getLevelQuest())) {

                    Toast.makeText(this, "MATCHED", Toast.LENGTH_LONG).show();

                    //mServ.stop();
                    //  setupSolveBoard();
                    cleanUp();

                    if (PRESS_LEVEL_SOLVE_FROM_HELP_DIALOG) {
                        //Handler handler = new Handler();
                        //handler.postDelayed(new Runnable() {
                        //   public void run() {
                        saveData.setRemainingCoin(saveData.getRemainingCoin() + 5);

                        dialogNextLevel();
                        //    }
                        // }, 3000);
                    } else {
                        dialogNextLevel();
                    }
                    break;

                }

                break;
            }

        }
    }

    private void manageChangeInSolveKeyboard(int onSolveKeyboard_pos) {

        Log.d("onSolveKeyboard_pos", String.valueOf(onSolveKeyboard_pos));

        string_track[onSolveKeyboard_pos] = 0;

        keyboard_solve.get(onSolveKeyboard_pos).setBackgroundResource(
                0);
        if (keyboard_solve.get(onSolveKeyboard_pos).getChildCount() > 0)
            keyboard_solve.get(onSolveKeyboard_pos).removeAllViews();


        ImageView img = new ImageView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        img.setImageResource(R.drawable.line);

        keyboard_solve.get(onSolveKeyboard_pos).addView(img);

        keyboard.get(keyboard_map.get(onSolveKeyboard_pos)).setVisibility(
                View.VISIBLE);

    }


    private void dialogNextLevel() {

        final Dialog dialog_start = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(false);
        dialog_start.setContentView(R.layout.dialog_next_level);

        TextView song_name = (TextView) dialog_start.findViewById(R.id.dialog_nextlevel_song_name);
        song_name.setTypeface(tf_granda);

        String temp = levelQuest[0];
        for (int i = 1; i < levelQuest.length; i++) {
            temp = temp + " " + levelQuest[i];
        }

        song_name.setText(temp);


        ImageView btn_play = (ImageView) dialog_start
                .findViewById(R.id.dialog_btn_next_level);

        btn_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog_start.dismiss();


                startNextLevel();

            }
        });
        dialog_start.show();
    }

    private void startNextLevel() {

        clearingData();

        modifyLevelNumber();

        //changeUIlevelBar_and_coinBar();
        if (current_level == AppConstant.startLevel) {
            saveData.setCurrentLevel(0);

            AppConstant.levelIntArray = AppConstant.makeLevelRandomization();

            saveData.setIntArray(AppConstant.levelIntArray);

            Toast.makeText(this, "Hurray!!All Level Completed", Toast.LENGTH_LONG).show();

            return;


        }


        findLevelQuest();

        findCharForKeyboard();

        setupKeyboard();


        setupSolveKeyboard();

        playSound();
    }


    private void clearingData() {

        keyboard.clear();
        keyboard_solve.clear();
        keyboard_map.clear();
        keyboard_inverse_map.clear();

        PRESS_LEVEL_SOLVE_FROM_HELP_DIALOG = false;
        IS_REMOVE_LETTER_ALREADY_USED = false;


        AppConstant.musicVisualizerHeightBottom = 40;
        AppConstant.musicVisualizerHeightTop = 10;
    }

    private void modifyLevelNumber() {
        current_level++;

        saveData.setCurrentLevel(current_level);

        level_num_tag.setText("LEVEL " + String.valueOf(current_level + 1));


    }


    private void playSound() {
        // Log.d("DEBUG_music",songName);

        initTunnelPlayerWorkaround();

        start(songName);

        mVisualizerView.link(mPlayer);

        // Start with just line renderer


        Random ran = new Random();

        int ran_value = ran.nextInt(4);

        if (ran_value == 0) {

            AppConstant.musicVisualizerHeightBottom = 15;

            addLineRenderer();
            addBarGraphRenderersOnlyBottom();


        } else if (ran_value == 1) {
            addCircleBarRenderer();
        } else if (ran_value == 2) {
            addCircleRenderer();
        } else if (ran_value == 3) {
            addBarGraphRenderers();
        } else {
            addBarGraphRenderers();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onDestroy() {
        cleanUp();
        super.onDestroy();


    }


    @Override
    protected void onPause() {
        cleanUp();
        super.onPause();
    }

    private void cleanUp() {
        if (mPlayer != null) {
            mVisualizerView.release();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        if (mSilentPlayer != null) {
            mSilentPlayer.release();
            mSilentPlayer = null;
        }
        mVisualizerView.clearRenderers();

    }


    private void dialogFreeCoins() {

        final Dialog dialog_start = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(true);
        dialog_start.setContentView(R.layout.dialog_freecoins);


        dialog_start.show();
    }

    private void dialogHelp() {

        final Dialog dialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_help);

        LinearLayout dialog_btn_solveLevel = (LinearLayout) dialog.findViewById(R.id.dialog_help_solve_level);
        LinearLayout dialog_btn_removeLetter = (LinearLayout) dialog.findViewById(R.id.dialog_help_remove_letter);
        LinearLayout dialog_btn_showLetter = (LinearLayout) dialog.findViewById(R.id.dialog_help_show_letter);


        dialog_btn_removeLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IS_REMOVE_LETTER_ALREADY_USED && saveData.getRemainingCoin() >= 50) {

                    saveData.setRemainingCoin(saveData.getRemainingCoin() - Integer.parseInt(GamePlay.this.getResources().getString(R.string.coin_remove_letters)));

                    IS_REMOVE_LETTER_ALREADY_USED = true;
                    removeLetter();

                    setupRemainingCoin();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(GamePlay.this, "Not Enough Coin", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog_btn_showLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveData.getRemainingCoin() >= 15) {
                    saveData.setRemainingCoin(saveData.getRemainingCoin() - Integer.parseInt(GamePlay.this.getResources().getString(R.string.coin_show_single_letter)));
                    showLetter();
                    setupRemainingCoin();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(GamePlay.this, "Not Enough Coin", Toast.LENGTH_LONG).show();
                }


            }
        });

        dialog_btn_solveLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveData.getRemainingCoin() >= 100) {
                    PRESS_LEVEL_SOLVE_FROM_HELP_DIALOG = true;

                    saveData.setRemainingCoin(saveData.getRemainingCoin() - Integer.parseInt(GamePlay.this.getResources().getString(R.string.coin_solve_level)));
                    solveLevel();

                    setupRemainingCoin();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(GamePlay.this, "Not Enough Coin", Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog.show();
    }

    private void removeLetter() {

        int key_pos = 0;


        char temp[] = keyboardCharArray;

        for (int i = 0; i < randCharArray.length; i++) {
            for (int j = 0; j < temp.length; j++) {


                if (randCharArray[i] == temp[j]) {
                    key_pos = j;
                    temp[j] = '0';
                    break;
                }


            }

            if (keyboard.get(key_pos).getVisibility() == View.INVISIBLE) {
                manageChangeInSolveKeyboard(keyboard_inverse_map.get(key_pos));
            } else {
                keyboard.get(key_pos).setVisibility(View.INVISIBLE);
            }
        }


    }


    private void showLetter() {

        int random_index = 0;
        Random ran = new Random();
        do {


            random_index = ran.nextInt(getLevelQuest().length());

            Log.d("ran", String.valueOf(random_index));

        } while (userWordCharArray[random_index] == getLevelQuest().charAt(random_index));


        if (string_track[random_index] == 1) {
            if (userWordCharArray[random_index] != getLevelQuest().charAt(random_index)) {
                manageChangeInSolveKeyboard(random_index);


                readyToRevealLetter(getLevelQuest().charAt(random_index), random_index);


            }
        } else {
            readyToRevealLetter(getLevelQuest().charAt(random_index), random_index);


        }

    }

    private void readyToRevealLetter(char onSolveLetter, int random_index) {

        for (int key_pos = 0; key_pos < keyboardCharArray.length; key_pos++) {

            if (keyboardCharArray[key_pos] == onSolveLetter) {
                updateSolveKeyboard(keyboardCharArray[key_pos],
                        key_pos, keyboard.get(key_pos), random_index);
                break;
            }

        }
    }


    private void solveLevel() {
        for (int index = 0; index < getLevelQuest().length(); index++) {


            if (string_track[index] == 1) {
                if (userWordCharArray[index] != getLevelQuest().charAt(index)) {
                    manageChangeInSolveKeyboard(index);


                    readyToRevealLetter(getLevelQuest().charAt(index), 0);


                    // break;

                }
            } else {
                readyToRevealLetter(getLevelQuest().charAt(index), 0);

                //break;
            }
        }
    }

    public void start(String music_name) {
        // Log.d("debug", "it's run");
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(this, this.getResources()
                    .getIdentifier(music_name, "raw", getPackageName()));
            //mPlayer.setOnErrorListener(this);
            mPlayer.setLooping(true);

            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    onError(mPlayer, what, extra);
                    return true;
                }
            });
        } else {
            mPlayer.stop();
            mPlayer.release();

            mPlayer = null;

            mPlayer = MediaPlayer.create(this, this.getResources()
                    .getIdentifier(music_name, "raw", getPackageName()));
            // mPlayer.setOnErrorListener(this);
            mPlayer.setLooping(true);

            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    onError(mPlayer, what, extra);
                    return true;
                }
            });
        }
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                mPlayer.start();
            }
        });
        th.start();
    }


    public void pressMusicOnOff(View view) {
        if (mPlayer.isPlaying()) {
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        } else {
            mPlayer.seekTo(mPlayer.getCurrentPosition());
            mPlayer.start();
        }

        Log.d("DEBUG_music", "press");
    }

    //
    private void initTunnelPlayerWorkaround() {
        // Read "tunnel.decode" system property to determine
        // the workaround is needed
        if (TunnelPlayerWorkaround.isTunnelDecodeEnabled(this)) {
            mSilentPlayer = TunnelPlayerWorkaround.createSilentMediaPlayer(this);
        }
    }

    private void addLineRenderer() {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(6f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(88, 114, 94, 26));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(4f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mVisualizerView.addRenderer(lineRenderer);
    }


    // Methods for adding renderers to visualizer
    private void addBarGraphRenderers() {
        Paint paint = new Paint();
        paint.setStrokeWidth(50f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 69, 65, 23));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        mVisualizerView.addRenderer(barGraphRendererBottom);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.argb(200, 134, 131, 111));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
        mVisualizerView.addRenderer(barGraphRendererTop);
    }

    // Methods for adding renderers to visualizer
    private void addBarGraphRenderersOnlyBottom() {
        Paint paint = new Paint();
        paint.setStrokeWidth(40f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 69, 65, 23));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        mVisualizerView.addRenderer(barGraphRendererBottom);

    }

    private void addCircleBarRenderer() {
        Paint paint = new Paint();
        paint.setStrokeWidth(8f);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        paint.setColor(Color.argb(255, 222, 92, 143));
        CircleBarRenderer circleBarRenderer = new CircleBarRenderer(paint, 32, true);
        mVisualizerView.addRenderer(circleBarRenderer);
    }

    private void addCircleRenderer() {
        Paint paint = new Paint();
        paint.setStrokeWidth(6f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 222, 92, 143));
        CircleRenderer circleRenderer = new CircleRenderer(paint, true);
        mVisualizerView.addRenderer(circleRenderer);
    }


}