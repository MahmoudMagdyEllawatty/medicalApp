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
import entity.User;
import software.circles.pos.R;

/**
 * Created by Mahmoud Ellawatty on 20/10/2017.
 */

public class UserController {

    SimpleDateFormat dateFormat = new SimpleDateFormat(String.valueOf(R.string.date_format));

    public ArrayList<User> getUsers(Context context){
        ArrayList<User> users = new ArrayList<>();

        String url = context.getString(R.string.basic_path) + "getUsers.php";

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
                JSONArray jsonArray = jsonObject.getJSONArray("users");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    User user = new User();

                    user.setId(object.getInt("id"));
                    user.setCreated_at(object.getString("created_at"));

                    user.setAvatar(object.getString("avatar"));
                    user.setEmail(object.getString("email"));
                    user.setFirstname(object.getString("firstname"));
                    user.setHashed_password(object.getString("hashed_password"));
                    user.setLast_active(object.getString("last_active"));
                    user.setRole(object.getString("role"));
                    user.setLastname(object.getString("lastname"));
                    user.setStore_id(object.getInt("store_id"));
                    user.setUsername(object.getString("username"));

                    users.add(user);
                    
                }
            }catch (JSONException ex){

            }

        } catch (IOException e) {
        }



        return users;
    }

    public User getUserByMailandPassword(String mail,String hashedPass,Context context) throws IOException, ClassNotFoundException {
        ArrayList<User> users = Caching.getCachedData(context,User.class.getName());
        if(users.size() == 0){
            Caching.insertCach(context,User.class.getName(),getUsers(context));
            getUserByMailandPassword(mail,hashedPass,context);
        }

        return   users.stream()
                .filter(user -> user.getEmail().equals(mail) && user.getHashed_password().equals(hashedPass))
                .findFirst()
                .orElse(new User());
    }

    public User getUserById(int id,Context context) throws IOException, ClassNotFoundException {
        ArrayList<User> users = Caching.getCachedData(context,User.class.getName());
        if(users.size() == 0){
            Caching.insertCach(context,User.class.getName(),getUsers(context));
            getUserById(id,context);
        }

        return   users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(new User());
    }
}
