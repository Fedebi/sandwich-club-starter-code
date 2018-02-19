package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView descriptionTv;
    TextView ingredientsTv;
    TextView also_knownTv;
    TextView placeOfOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        descriptionTv = (TextView) findViewById(R.id.description_tv);
        ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        also_knownTv = (TextView) findViewById(R.id.also_known_tv);
        placeOfOrigin = (TextView) findViewById(R.id.origin_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        descriptionTv.setText(sandwich.getDescription());

        String origin = sandwich.getPlaceOfOrigin();
        if (origin.length() != 0)
            placeOfOrigin.setText(origin);

        String ingredients = "";
        List<String> listIngredients = sandwich.getIngredients();
        int size = listIngredients.size();

        int i = 1;
        for (String s : listIngredients) {
            ingredients = ingredients.concat(s);

            if (i < size)
                ingredients = ingredients.concat(", ");
            else
                ingredients = ingredients.concat(".");
            i++;
        }

        ingredientsTv.setText(ingredients);

        String alsoKnownAs = "";
        List<String> listAlsoKnowAs = sandwich.getAlsoKnownAs();
        int j = 1;
        int len = listAlsoKnowAs.size();

        for (String t : listAlsoKnowAs) {
            alsoKnownAs = alsoKnownAs.concat(t);
            if (j < len)
                alsoKnownAs = alsoKnownAs.concat(", ");
            else
                alsoKnownAs = alsoKnownAs.concat(".");
            j++;
        }

        if (alsoKnownAs != "")
            also_knownTv.setText(alsoKnownAs);
    }
}
