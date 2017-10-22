package Utilities;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mahmoud Ellawatty on 20/10/2017.
 */

public class TestConnection {

    public static boolean isConnected(Context context){

        boolean state = false;

        try {

          //  String url = context.getString(R.string.basic_path) + "connection.php";
            String url ="http://www.moazsc.com/pos/php/connection.php";
            URL connectionURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) connectionURL.openConnection();
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            if (bufferedReader.readLine() == null)
                state = true;
        } catch (IOException e) {
            Log.e("Error Connection",e.toString());
            state = false;
        }

        return state;
    }
}
