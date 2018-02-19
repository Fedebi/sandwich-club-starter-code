package com.udacity.sandwichclub.utils;

        import com.udacity.sandwichclub.model.Sandwich;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jsonObj = new JSONObject(json);

            String placeOfOrigin = jsonObj.getString("placeOfOrigin");
            String description = jsonObj.getString("description");
            String image = jsonObj.getString("image");

            JSONObject jsonObjName = jsonObj.getJSONObject("name");
            String mainName = jsonObjName.getString("mainName");

            JSONArray jsonArray2 = new JSONArray(jsonObjName.getJSONArray("alsoKnownAs").toString());
            List<String> alsoKnownAs = new ArrayList<>();

            for (int i = 0; i < jsonArray2.length(); i++) {
                alsoKnownAs.add(jsonArray2.getString(i));
            }

            JSONArray jsonArray = new JSONArray(jsonObj.getJSONArray("ingredients").toString());
            List<String> ingredients = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                ingredients.add(jsonArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
