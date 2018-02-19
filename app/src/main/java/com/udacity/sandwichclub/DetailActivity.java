package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @BindView(R.id.ingredients_iv) ImageView ingredients_iv;
    @BindView(R.id.origin_tv) TextView origin_tv;
    @BindView(R.id.also_known_tv) TextView alsoKnownAs_tv;
    @BindView(R.id.ingredients_tv) TextView ingredients_tv;
    @BindView(R.id.description_tv) TextView description_tv;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredients_iv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAs_tv.setText(getString(R.string.detail_also_known_as_label) + getString(R.string.not_applicable));
        } else {
            alsoKnownAs_tv.setText(getString(R.string.detail_also_known_as_label) + split(sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            origin_tv.setText(getString(R.string.detail_place_of_origin_label) + getString(R.string.not_applicable));
        } else {
            origin_tv.setText(getString(R.string.detail_place_of_origin_label) + sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredients_tv.setText(getString(R.string.detail_ingredients_label) + getString(R.string.not_applicable));
        } else {
            ingredients_tv.setText(getString(R.string.detail_ingredients_label) + split(sandwich.getIngredients()));
        }

        if (sandwich.getDescription().isEmpty()) {
            description_tv.setText(getString(R.string.detail_description_label) + getString(R.string.not_applicable));
        } else {
            description_tv.setText(getString(R.string.detail_description_label) + sandwich.getDescription());
        }

    }

    private String split(List<String> list) {
        String output = "";
        String strList = TextUtils.join(",", list);
        String[] strArray = strList.split(",");

        for (String rate : strArray) {
            output += rate + ", ";
        }
        return output;
    }
}
