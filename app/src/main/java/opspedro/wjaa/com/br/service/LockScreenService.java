package opspedro.wjaa.com.br.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.TelephonyManager;

import com.google.android.gms.wallet.WalletConstants;

import java.util.ArrayList;

import opspedro.wjaa.com.br.activity.LockScreenAppActivity;
import opspedro.wjaa.com.br.utils.LockScreenHelper;
import opspedro.wjaa.com.br.utils.LockUtils;

public class LockScreenService extends Service{

	private static LockScreenService lockScreenService;
	private NotificationManager notificationManager;
	private TelephonyManager telephonyManager;
	private LockScreenBroadcastReceiver broadcastReceiver;
	private LockUtils lockUtils;
	private PowerManager powerManager;
	private boolean telefoneInativo;
	private boolean apiGreether22;
	private LockScreenHelper helper1;
	private boolean endCommand;
	private boolean apiLesser18;


	public class LockScreenServiceBinder extends Binder {
		private LockScreenService lockScreenService;

		public LockScreenServiceBinder(LockScreenService screenLockService) {
			this.lockScreenService = screenLockService;
		}

		public final LockScreenService getLockScreenService() {
			return this.lockScreenService;
		}
	}


	class LockScreenBroadcastReceiver extends BroadcastReceiver {
		private LockScreenService myService;

		private LockScreenBroadcastReceiver(LockScreenService screenLockService) {
			this.myService = screenLockService;
		}

		public final void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (!"android.intent.action.SCREEN_ON".equals(action)) {
				if ("android.intent.action.PHONE_STATE".equals(action)) {
					if (this.myService.telephonyManager.getCallState() == 0) {
						if (this.myService.telefoneInativo) {
							this.myService.telefoneInativo = false;
							LockScreenHelper.startLockScreen(this.myService, this.myService.lockUtils, this.myService.apiGreether22);
						}
					} else if (this.myService.lockUtils.m213b()) {
						this.myService.lockUtils.m209a();
						this.myService.telefoneInativo = true;
					}
				} else if ("android.intent.action.SCREEN_OFF".equals(action)) {
					this.myService.helper1.startLockStreen(this.myService.telephonyManager, true, context, this.myService.lockUtils, this.myService.apiGreether22);
				} else {
					"android.intent.action.USER_PRESENT".equals(action);
				}
			}
		}
	}

	public LockScreenService() {
		Handler handler = new Handler();
		ArrayList arrayList = new ArrayList();
		this.helper1 = new LockScreenHelper();
		this.endCommand = false;
	}


	static {
		Integer.valueOf(Build.VERSION.SDK).intValue();
	}

	public static LockScreenService getInstance() {
		return lockScreenService;
	}

	public IBinder onBind(Intent intent) {
		return new LockScreenServiceBinder(this);
	}

	public int onStartCommand(Intent intent, int i, int i2) {
		LockScreenHelper c1360a = this.helper1;
		PowerManager powerManager = this.powerManager;
		TelephonyManager telephonyManager = this.telephonyManager;
		LockUtils lockUtils = this.lockUtils;
		boolean z = this.apiGreether22;
		if (intent != null) {
			if (intent.hasExtra("extra_quick_lock") || intent.hasExtra("preview_cmd")) {
				c1360a.startLockStreen(telephonyManager, intent.hasExtra("preview_cmd"), this, lockUtils, z);
			} else if (intent.hasExtra("screen_cmd") && !powerManager.isScreenOn()) {
				c1360a.startLockStreen(telephonyManager, false, this, lockUtils, z);
			}
		}
		try {
			if (!this.endCommand && this.apiLesser18) {
				c1360a = this.helper1;
				NotificationManager notificationManager = this.notificationManager;
				if (notificationManager != null) {
					Notification build;
					if (Integer.valueOf(Build.VERSION.SDK).intValue() >= 18) {
						PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, LockScreenAppActivity.class), 134217728);
						CharSequence string = "Ops Pedro Running";
						android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);
						if (Build.VERSION.SDK_INT >= 21) {
							builder.setContentIntent(activity).setColor(6323595).setSmallIcon(2130837735).setWhen(System.currentTimeMillis()).setAutoCancel(false).setContentTitle(string).setContentText("Touch to open").setPriority(-2);
						} else {
							builder.setContentIntent(activity).setSmallIcon(2130837675).setWhen(System.currentTimeMillis()).setAutoCancel(false).setContentTitle(string).setContentText("Touch to open").setPriority(-2);
						}
						build = builder.build();
						build.flags = 64;
					} else {
						build = new Notification.Builder(this).setPriority(-2).build();
					}
					startForeground(2131492940, build);
					notificationManager.notify(2131492940, build);
				}
				this.endCommand = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void onCreate() {
		boolean z = true;
		super.onCreate();
		this.telephonyManager = (TelephonyManager) getSystemService("phone");
		getSystemService("activity");
		this.powerManager = (PowerManager) getSystemService("power");
		this.lockUtils = new LockUtils(this);
		lockScreenService = this;
		this.apiGreether22 = Integer.valueOf(Build.VERSION.SDK_INT).intValue() >= 23;
		this.broadcastReceiver = new LockScreenBroadcastReceiver(this);
		BroadcastReceiver broadcastReceiver = this.broadcastReceiver;
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.PHONE_STATE");
		intentFilter.addAction("android.intent.action.SCREEN_ON");
		intentFilter.addAction("android.intent.action.SCREEN_OFF");
		intentFilter.addAction("android.intent.action.USER_PRESENT");
		intentFilter.setPriority(WalletConstants.CardNetwork.OTHER);
		registerReceiver(broadcastReceiver, intentFilter);
		if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() >= 18) {
			z = false;
		}
		this.apiLesser18 = z;
		if (this.apiLesser18) {
			this.notificationManager = (NotificationManager) getSystemService("notification");
		}
	}

	public void onDestroy() {
		super.onDestroy();
		try {
			this.endCommand = false;
			try {
				if (this.endCommand) {
					NotificationManager notificationManager = this.notificationManager;
					if (notificationManager != null) {
						stopForeground(true);
						notificationManager.cancel(2131492940);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.endCommand = false;
			unregisterReceiver(this.broadcastReceiver);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}


	public final void m354a(int i) {
		if (this.lockUtils != null) {
			this.lockUtils.m210a(i);
		}
	}
}
