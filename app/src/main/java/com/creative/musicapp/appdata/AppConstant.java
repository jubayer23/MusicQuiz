package com.creative.musicapp.appdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by comsol on 10/27/2015.
 */

public class AppConstant {

    public static HashMap<Integer, String> levelQuest_map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> solveKeyboardformat_map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> solveKeyboardformatSpace_map = new HashMap<Integer, String>();
    public static int startLevel = 0;

    public static int[] levelIntArray;

    public static int musicVisualizerHeightBottom = 40;
    public static int musicVisualizerHeightTop = 40;

    public static void loadAllLevelData() {
        startLevel = 0;
        levelQuest_map.clear();

        levelQuest_map.put(startLevel, "in_da_club");
        solveKeyboardformatSpace_map.put(startLevel, "2");
        solveKeyboardformat_map.put(startLevel++, "5,4");


        levelQuest_map.put(startLevel, "walk_this_way");
        solveKeyboardformatSpace_map.put(startLevel, "8");
        solveKeyboardformat_map.put(startLevel++, "4,8");

        levelQuest_map.put(startLevel, "american_idiot");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "8,5");

        levelQuest_map.put(startLevel, "beautiful_day");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "9,3");

        levelQuest_map.put(startLevel, "cant_stop");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,4");

        levelQuest_map.put(startLevel, "cant_touch_this");
        solveKeyboardformatSpace_map.put(startLevel, "4");
        solveKeyboardformat_map.put(startLevel++, "10,4");

        levelQuest_map.put(startLevel, "billy_jean");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,4");

        levelQuest_map.put(startLevel, "changes");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "7,0");

        levelQuest_map.put(startLevel, "crazy_in_love");
        solveKeyboardformatSpace_map.put(startLevel, "5");
        solveKeyboardformat_map.put(startLevel++, "8,4");

        levelQuest_map.put(startLevel, "eye_of_the_tiger");
        solveKeyboardformatSpace_map.put(startLevel, "3,9");
        solveKeyboardformat_map.put(startLevel++, "6,9");


        levelQuest_map.put(startLevel, "final_countdown");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,9");

        levelQuest_map.put(startLevel, "fly_away");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "3,4");

        levelQuest_map.put(startLevel, "have_a_nice_day");
        solveKeyboardformatSpace_map.put(startLevel, "4,10");
        solveKeyboardformat_map.put(startLevel++, "6,8");


        levelQuest_map.put(startLevel, "here_without_you");
        solveKeyboardformatSpace_map.put(startLevel, "11");
        solveKeyboardformat_map.put(startLevel++, "4,11");


        levelQuest_map.put(startLevel, "hey_ya");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "3,2");


        levelQuest_map.put(startLevel, "hung_up");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,2");

        levelQuest_map.put(startLevel, "i_feel_good");
        solveKeyboardformatSpace_map.put(startLevel, "1");
        solveKeyboardformat_map.put(startLevel++, "6,4");


        levelQuest_map.put(startLevel, "i_love_rock_n_roll");
        solveKeyboardformatSpace_map.put(startLevel, "1,6,12");
        solveKeyboardformat_map.put(startLevel++, "11,6");


        levelQuest_map.put(startLevel, "insomnia");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "8,0");

        levelQuest_map.put(startLevel, "jailhouse_rock");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "9,4");

        levelQuest_map.put(startLevel, "jump");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,0");

        levelQuest_map.put(startLevel, "karma_chameleon");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,9");


        levelQuest_map.put(startLevel, "labamba");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "7,0");

        levelQuest_map.put(startLevel, "layla");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,0");

        levelQuest_map.put(startLevel, "light_my_fire");
        solveKeyboardformatSpace_map.put(startLevel, "7");
        solveKeyboardformat_map.put(startLevel++, "5,7");

        levelQuest_map.put(startLevel, "love_generation");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,10");

        levelQuest_map.put(startLevel, "mamma_mia");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,3");


        levelQuest_map.put(startLevel, "rebel_rebel");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,5");

        levelQuest_map.put(startLevel, "seven_nation_army");
        solveKeyboardformatSpace_map.put(startLevel, "11");
        solveKeyboardformat_map.put(startLevel++, "5,11");

        levelQuest_map.put(startLevel, "sexual_healing");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "6,7");

        levelQuest_map.put(startLevel, "smoke_on_the_water");
        solveKeyboardformatSpace_map.put(startLevel, "5,11");
        solveKeyboardformat_map.put(startLevel++, "8,9");

        levelQuest_map.put(startLevel, "still_dre");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,3");


        levelQuest_map.put(startLevel, "sweet_child_of_mine");
        solveKeyboardformatSpace_map.put(startLevel, "5,13");
        solveKeyboardformat_map.put(startLevel++, "11,7");

        levelQuest_map.put(startLevel, "toxic");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,0");

        levelQuest_map.put(startLevel, "umbrella");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "8,0");

        levelQuest_map.put(startLevel, "what_is_love");
        solveKeyboardformatSpace_map.put(startLevel, "4");
        solveKeyboardformat_map.put(startLevel++, "7,4");

        levelQuest_map.put(startLevel, "who_are_you");
        solveKeyboardformatSpace_map.put(startLevel, "3");
        solveKeyboardformat_map.put(startLevel++, "7,3");

        levelQuest_map.put(startLevel, "beat_it");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,2");

        levelQuest_map.put(startLevel, "born_slippy");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,6");

        levelQuest_map.put(startLevel, "call_on_me");
        solveKeyboardformatSpace_map.put(startLevel, "4");
        solveKeyboardformat_map.put(startLevel++, "7,2");

        levelQuest_map.put(startLevel, "candy_shop");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "5,4");

        levelQuest_map.put(startLevel, "in_the_end");
        solveKeyboardformatSpace_map.put(startLevel, "2");
        solveKeyboardformat_map.put(startLevel++, "6,3");

        levelQuest_map.put(startLevel, "is_this_love");
        solveKeyboardformatSpace_map.put(startLevel, "2");
        solveKeyboardformat_map.put(startLevel++, "7,4");

        levelQuest_map.put(startLevel, "take_me_out");
        solveKeyboardformatSpace_map.put(startLevel, "4");
        solveKeyboardformat_map.put(startLevel++, "7,3");

        levelQuest_map.put(startLevel, "wake_up");
        solveKeyboardformatSpace_map.put(startLevel, "-5");
        solveKeyboardformat_map.put(startLevel++, "4,2");

    }


    public static int[] makeLevelRandomization() {
        List<Integer> dataList = new ArrayList<Integer>();
        for (int i = 0; i < AppConstant.startLevel; i++) {
            dataList.add(i);
        }
        Collections.shuffle(dataList);
        int[] num = new int[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            num[i] = dataList.get(i);
        }


        return num;
    }
}
