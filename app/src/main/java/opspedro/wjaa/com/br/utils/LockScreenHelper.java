package opspedro.wjaa.com.br.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

/**
 * Created by wagner on 6/10/17.
 */
public class LockScreenHelper {
        public final void startLockStreen(TelephonyManager telephonyManager, boolean start, Context context, LockUtils lockUtils, boolean fork) {

            //se estiver em ligacao nao coloca a tela de bloqueio.
            if (telephonyManager.getCallState() != 0) {
                return;
            }
            if (start) {
                LockScreenHelper.startLockScreen(context, lockUtils, fork);
            }
        }

        public static void startLockScreen(Context context, LockUtils lockUtils, boolean fork) {
            if (fork) {
                try {
                    lockUtils.start(context);
                    return;
                } catch (SecurityException e) {
                    e.printStackTrace();
                    Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
                    context.startActivity(intent);
                    return;
                }
            }
            lockUtils.start(context);
        }

}
