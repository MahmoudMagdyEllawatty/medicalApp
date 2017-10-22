package Utilities;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 18/10/2017.
 */

public class Caching {

    public static <T> void insertCach(Context context, String key, ArrayList<T> data) throws IOException {

        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(data);
        oos.close();
        fos.close();
    }



    public static <T> ArrayList<T> getCachedData(Context context,String key)throws IOException,ClassNotFoundException {

        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<T> data = new ArrayList<>() ;

        try{
            data =(ArrayList<T>)(ois.readObject());
        }catch (Exception ex){
            data.clear();
        }

        return data;
    }
}
