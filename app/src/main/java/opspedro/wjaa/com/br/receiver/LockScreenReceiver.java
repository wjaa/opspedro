package opspedro.wjaa.com.br.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.drive.DriveFile;

import opspedro.wjaa.com.br.activity.LockScreenAppActivity;
import opspedro.wjaa.com.br.service.LockScreenService;

public class LockScreenReceiver extends DeviceAdminReceiver {
	 public static boolean wasScreenOn = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("LockScreenReceiver","onReceive");
		Intent intent2 = new Intent(context, LockScreenService.class);
		intent2.putExtra("extra_quick_lock", true);
		intent2.setFlags(DriveFile.MODE_READ_ONLY);
		context.startService(intent2);
		DevicePolicyManager dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);

		ComponentName deviceAdminReceiver = new ComponentName(context,LockScreenReceiver.class);
		try{

			if (dpm.isAdminActive(deviceAdminReceiver)) {
				Log.i("LockScreenReceiver","isAdmin iniciando force lock");
				dpm.lockNow();
			} else {
				Log.i("LockScreenReceiver","isNotAdmin abrindo um intent");
				Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,deviceAdminReceiver);
				context.startActivity(intent);
			}
		}catch(Exception ex){
			Log.e("LockScreenReceiver", "Erro ao tentar dar lock na tela", ex);
		}
        /*if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

        	wasScreenOn=false;
        	Intent intent11 = new Intent(context,LockScreenAppActivity.class);
        	intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        	context.startActivity(intent11);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        	wasScreenOn=true;
        	Intent intent11 = new Intent(context,LockScreenAppActivity.class);
        	intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
       else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
        	Intent intent11 = new Intent(context, LockScreenAppActivity.class);
        	intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           	context.startActivity(intent11);
       }*/

    }


}
