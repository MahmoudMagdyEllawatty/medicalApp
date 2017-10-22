package Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import controllers.CategoryController;
import controllers.UserController;
import entity.Category;
import entity.User;

/**
 * Created by Mahmoud Ellawatty on 20/10/2017.
 */

/**
 * This class used to cache objects in background
 * objects to cache is (Users , Categories and products)
 */
public class BackgroundTasks extends AsyncTask<Void,Void,Boolean> {

    private int whichObject = 0;
    private Context context;

    public  BackgroundTasks(int whichObject,Context context){
        this.whichObject = whichObject;
        this.context = context;
    }


    @Override
    protected Boolean doInBackground(Void... params) {
      if(TestConnection.isConnected(this.context)) {
               //Users
                  UserController userController = new UserController();
                  try {
                      Caching.insertCach(this.context, User.class.getName(), userController.getUsers(this.context));
                  } catch (IOException e) {
                      Log.e("Error Caching Users",e.toString());
                      return false;
                  }

            //Categories
                  CategoryController categoryController = new CategoryController();
                  try {
                      Caching.insertCach(this.context, Category.class.getName(), categoryController.getCategories(this.context));
                  } catch (IOException e) {
                      Log.e("Error Category Caching",e.toString());
                      return false;
                  }

                  SharedData.isCached = true;
                  return true;


      }else{
          return false;
      }
    }



    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Log.e("State",String.valueOf(aBoolean));

        super.onPostExecute(aBoolean);
    }
}
