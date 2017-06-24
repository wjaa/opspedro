package opspedro.wjaa.com.br.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import opspedro.wjaa.com.br.R;

public class SplashActivity extends Activity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    public void run(){
        startActivity(new Intent(this, SettingsActivity.class));
        finish();
    }

}
