package software.circles.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import Utilities.BackgroundTasks;
import Utilities.SharedData;

public class Splash extends Activity {

    BackgroundTasks backgroundTasks;
    ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mProgressView =(ProgressBar) findViewById(R.id.splash_progress);

        mProgressView.setVisibility( View.VISIBLE);


        cacheData(1);


    }



    private  void cacheData(int which){

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function

                if(!SharedData.isCached) {
                    backgroundTasks = new BackgroundTasks(which, getApplicationContext());
                    SharedData.isCached = false;
                    backgroundTasks.execute((Void) null);
                }else{
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
                }
                ha.postDelayed(this, 1000);
            }

        }, 1000);

    }
}
