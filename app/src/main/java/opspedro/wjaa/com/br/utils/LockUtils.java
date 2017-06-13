package opspedro.wjaa.com.br.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.haisom.workspace.lib.HaisomWorkspace;

import opspedro.wjaa.com.br.R;

/**
 * Created by wagner on 6/10/17.
 */
//C1386a
public final class LockUtils {

    private WindowManager windowsManager;
    private View view;
    private FrameLayout frameLayout;
    private WindowManager.LayoutParams layoutParams;
    private boolean f267e;
    private boolean f268f;
    private HaisomWorkspace haisomWorkspace;

    public LockUtils(Context context) {
        this.haisomWorkspace = new HaisomWorkspace();
        this.f268f = m343i(context);
        this.f267e = false;
        this.windowsManager = (WindowManager) context.getSystemService("window");
        this.layoutParams = new WindowManager.LayoutParams();
        this.layoutParams.screenOrientation = 1;
        if (this.f268f) {
            this.layoutParams.windowAnimations = 16973828;
        }
        this.layoutParams.width = -1;
        this.layoutParams.height = -1;
    }


    public static boolean m343i(Context context) {
        /*if (android.support.v7.internal.view.menu..m40a(context, "key_haisom_work", false)) {
            return true;
        }*/
        try {
            String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
            /*if (simOperator != null && (simOperator.startsWith(new HaisomWorkspace().reflectNativeDip4(context)) || "".equals(simOperator))) {
                return false;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //C0154d.m45b(context, "key_haisom_work", true);
        return true;
    }

    public final synchronized void start(Context context) {
        if (!this.f267e) {
            if (this.view == null) {
                this.frameLayout = new FrameLayout(context);
                this.view = View.inflate(context, R.layout.activity_main, null);
                    if (this.haisomWorkspace.reflectNativeDip0(context) > 0) {
                       this.layoutParams.format = 1;
                     } else {
                    this.layoutParams.format = -1;
                }
                try {
                    this.layoutParams.flags = getFlags(context, this.haisomWorkspace);//Intent.FLAG_ACTIVITY_NEW_TASK;//
                    this.layoutParams.type = getType();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //((LockView) this.view.findViewById(2131230870)).m398a(this);

                Button b = (Button) this.view.findViewById(R.id.btnUnlock);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);
                    }
                });


                this.frameLayout.addView(this.view);
            }

            this.windowsManager.addView(this.frameLayout, this.layoutParams);
            //Window
        }
        m210a(0);
        this.f267e = true;
    }


    public static int getType() {
        return WindowManager.LayoutParams.TYPE_SYSTEM_ERROR | WindowManager.LayoutParams.FLAG_FULLSCREEN; //2010;
    }

    public final synchronized void m209a() {
        if (this.windowsManager != null && this.f267e) {
            this.windowsManager.removeView(this.frameLayout);
            this.view = null;
            this.frameLayout = null;
        }
        this.f267e = false;
    }

    public static int getFlags(Context context, HaisomWorkspace haisomWorkspace) {
        boolean z = true;
        /*if (!C0154d.m40a(context, "key_statu_bar", false)) {
            z = true;
        }*/
        //1280
        int value = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 14 ? z ? WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE : 0 : z ? haisomWorkspace.reflectNativeDip1(context) : haisomWorkspace.reflectNativeDip2(context);
        return value;
    }

    public final synchronized boolean m213b() {
        return this.f267e;
    }

    public final void m210a(int i) {
        if (this.frameLayout != null) {
            this.frameLayout.setVisibility(i);
        }
        if (this.view != null) {
            this.view.setVisibility(i);
        }
    }

    public final void m212b(int i) {
        try {
            this.layoutParams.screenBrightness = ((float) i) / 255.0f;
            this.windowsManager.updateViewLayout(this.frameLayout, this.layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
