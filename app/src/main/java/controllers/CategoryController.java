package controllers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Utilities.Caching;
import entity.Category;
import software.circles.pos.R;

/**
 * Created by Mahmoud Ellawatty on 20/10/2017.
 */

public class CategoryController {

    SimpleDateFormat dateFormat = new SimpleDateFormat(String.valueOf(R.string.date_format));

    public ArrayList<Category> getCategories(Context context){
        ArrayList<Category> categories = new ArrayList<>();

        String url = context.getString(R.string.basic_path) + "getCategories.php";

        try {


            URL insertURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) insertURL.openConnection();
            InputStreamReader resultStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader resultReader = new BufferedReader(resultStreamReader);

            StringBuilder final_text = new StringBuilder();
            String line;
            while((line =resultReader.readLine()) != null){
                final_text.append(line);
            }

            try {
                JSONObject jsonObject = new JSONObject(final_text.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("categories");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    Category category = new Category();

                    category.setId(object.getInt("id"));
                    category.setName(object.getString("name"));
                    category.setCreated_at(object.getString("created_at"));

                    categories.add(category);
                }
            }catch (JSONException ex){

            }

        } catch (IOException e) {
        }



        return categories;
    }

    public Category getCategoryByName(String categoryName,Context context) throws IOException, ClassNotFoundException {
        ArrayList<Category> categories = Caching.getCachedData(context,Category.class.getName());
        if(categories.size() == 0){
            Caching.insertCach(context,Category.class.getName(),getCategories(context));
            getCategoryByName(categoryName,context);
        }

        return   categories.stream()
                .filter(category1 -> category1.getName().equals(categoryName))
                .findFirst()
                .orElse(new Category());
    }

    public Category getCategoryById(int id,Context context) throws IOException, ClassNotFoundException {
        ArrayList<Category> categories = Caching.getCachedData(context,Category.class.getName());
        if(categories.size() == 0){
            Caching.insertCach(context,Category.class.getName(),getCategories(context));
            getCategoryById(id,context);
        }

        return   categories.stream()
                .filter(category1 -> category1.getId() == id)
                .findFirst()
                .orElse(new Category());
    }

}
