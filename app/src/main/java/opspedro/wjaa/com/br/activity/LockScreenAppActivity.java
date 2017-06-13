package opspedro.wjaa.com.br.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import opspedro.wjaa.com.br.R;
import opspedro.wjaa.com.br.receiver.LockScreenReceiver;
import opspedro.wjaa.com.br.service.LockScreenService;

/**
 *
 */
public class LockScreenAppActivity extends Activity {


	class ReceiverLock extends BroadcastReceiver {
		private String f226a;
		private String f227b;
		private /* synthetic */ LockScreenAppActivity screenLockActivity;

		ReceiverLock(LockScreenAppActivity screenLockActivity) {
			this.screenLockActivity = screenLockActivity;
			this.f226a = "reason";
			this.f227b = "homekey";
		}

		public final void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
				Toast.makeText(context,String.valueOf(this.f227b.equals(intent.getStringExtra(this.f226a)))
						,Toast.LENGTH_LONG).show();
				this.screenLockActivity.onlock();
				LockScreenService.getInstance().m354a(0);
				//LockUtils l = new LockUtils(LockScreenAppActivity.this);
				//l.start(LockScreenAppActivity.this);
			}
		}
	}

	private void onlock() {
		finish();
	}

	private ReceiverLock receiver;
	/** Called when the activity is first created. */
	  KeyguardManager.KeyguardLock k1;
	   boolean inDragMode;
 	   int selectedImageViewX;
 	   int selectedImageViewY;
 	   int windowwidth;
 	   int windowheight;
 	   ImageView droid,phone,home;
 	  //int phone_x,phone_y;
 	   int home_x,home_y;
 	   int[] droidpos;

 	  private LayoutParams layoutParams;

	  @Override
	 public void onAttachedToWindow() {
		// TODO Auto-generated method stub


          super.onAttachedToWindow();
	 }

    public void onCreate(Bundle savedInstanceState) {

    	   super.onCreate(savedInstanceState);
		receiver = new ReceiverLock(this);
		registerReceiver(receiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    	   getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG|WindowManager.LayoutParams.FLAG_FULLSCREEN);

    	   setContentView(R.layout.main);
    	   droid =(ImageView)findViewById(R.id.droid);



    	   System.out.println("measured width"+droid.getMeasuredWidth());
    	   System.out.println(" width"+droid.getWidth());


    	   if(getIntent()!=null&&getIntent().hasExtra("kill")&&getIntent().getExtras().getInt("kill")==1){
    	      // Toast.makeText(this, "" + "kill activityy", Toast.LENGTH_SHORT).show();
    	        	finish();
    	    	}

        try{

        startService(new Intent(this,LockScreenService.class));




  /*      KeyguardManager km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        k1 = km.newKeyguardLock("IN");
        k1.disableKeyguard();*/
        StateListener phoneStateListener = new StateListener();
        TelephonyManager telephonyManager =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

        windowwidth=getWindowManager().getDefaultDisplay().getWidth();
        System.out.println("windowwidth"+windowwidth);
        windowheight=getWindowManager().getDefaultDisplay().getHeight();
        System.out.println("windowheight"+windowheight);

        MarginLayoutParams marginParams2 = new MarginLayoutParams(droid.getLayoutParams());

        marginParams2.setMargins((windowwidth/24)*10,((windowheight/32)*8),0,0);

        //marginParams2.setMargins(((windowwidth-droid.getWidth())/2),((windowheight/32)*8),0,0);
        LayoutParams layoutdroid = new LayoutParams(marginParams2);

        droid.setLayoutParams(layoutdroid);

        /* phone =(ImageView)findViewById(R.id.phone);
        MarginLayoutParams marginParams = new MarginLayoutParams(phone.getLayoutParams());
         marginParams.setMargins(0,windowheight/32,windowwidth/24,0);
         LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(marginParams);
         phone.setLayoutParams(layoutParams1);
*/

         LinearLayout homelinear = (LinearLayout)findViewById(R.id.homelinearlayout);
         homelinear.setPadding(0,0,0,(windowheight/32)*3);
         home =(ImageView)findViewById(R.id.home);

         MarginLayoutParams marginParams1 = new MarginLayoutParams(home.getLayoutParams());

         marginParams1.setMargins((windowwidth/24)*10,0,(windowheight/32)*8,0);
        // marginParams1.setMargins(((windowwidth-home.getWidth())/2),0,(windowheight/32)*10,0);
         LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(marginParams1);

         home.setLayoutParams(layout);

        }catch (Exception e) {
			// TODO: handle exception
		}

    }
    class StateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("call Activity off hook");
                	finish();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss.
        return;
    }

    //only used in lockdown mode
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
		startService(new Intent(this, LockScreenReceiver.class));
    }


	public final static int REQUEST_CODE = -1010101; //*(see edit II)*

	public void checkDrawOverlayPermission() {
		/** check if we already  have permission to draw over other apps */
		if (!Settings.canDrawOverlays(this)) {
			/** if not construct intent to request permission */
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					Uri.parse("package:" + getPackageName()));
			/** request permission via start activity for result */
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
		/** check if received result code
		 is equal our requested code for draw permission  */
		if (requestCode == REQUEST_CODE) {
       /** if so check once again if we have permission */
			if (Settings.canDrawOverlays(this)) {
				// continue here - permission was granted
			}
		}
	}




    @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {

    	if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)||(keyCode == KeyEvent.KEYCODE_POWER)||(keyCode == KeyEvent.KEYCODE_VOLUME_UP)||(keyCode == KeyEvent.KEYCODE_CAMERA)) {
    	    //this is where I can do my stuff
    	    return true; //because I handled the event
    	}
       if((keyCode == KeyEvent.KEYCODE_HOME)){
    	   return true;
        }

	return false;

    }

    public boolean dispatchKeyEvent(KeyEvent event) {
    	if (event.getKeyCode() == KeyEvent.KEYCODE_POWER ||(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)||(event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
    	    return false;
    	}
    	if((event.getKeyCode() == KeyEvent.KEYCODE_HOME)){
           	System.out.println("alokkkkkkkkkkkkkkkkk");
      		return true;
        }
   		return false;
    }

    public void onDestroy(){
		try {
			unregisterReceiver(this.receiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
        super.onDestroy();

    }

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (Build.VERSION.SDK_INT >= 23){
			checkDrawOverlayPermission();
		}
	}
}