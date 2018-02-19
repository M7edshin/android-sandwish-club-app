package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //JSON Constants Keys
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static String LOG_TAG = JsonUtils.class.getName(); // For log messages
    private static Sandwich sandwich;

    public static Sandwich parseSandwichJson(String json) {

        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        if (TextUtils.isEmpty(json)) return null;

        try {
            JSONObject base = new JSONObject(json);
            JSONObject nameObj = base.getJSONObject(KEY_NAME);
            String name = nameObj.optString(KEY_MAIN_NAME);

            JSONArray alsoKnownAsArray = nameObj.getJSONArray(KEY_KNOWN_AS);
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }

            String placeOfOrigin = base.optString(KEY_ORIGIN);
            String description = base.optString(KEY_DESCRIPTION);
            String image = base.optString(KEY_IMAGE);

            JSONArray ingredientsArray = base.getJSONArray(KEY_INGREDIENTS);
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.optString(i));
            }

            sandwich = new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.v(LOG_TAG, "Error: Problem in parsing JSON data");
        }

        return sandwich;
    }


}
