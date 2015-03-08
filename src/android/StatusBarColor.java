package br.com.hotforms.android.statusbarcolor;

import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

public class StatusBarColor extends CordovaPlugin {
	private static final String TAG = "StatusBarColor";

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        Log.d(TAG, "StatusBarColor: initialization");
        super.initialize(cordova, webView);

        this.cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Window window = cordova.getActivity().getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
				window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        			
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
				if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
					window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					window.setStatusBarColor(android.graphics.Color.rgb(0,0,0));
				}
            }
        });
    }
	
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "Executing action: " + action);
        final Activity activity = this.cordova.getActivity();
        final Window window = activity.getWindow();
		
		if (action.equals("setColor")) {
			this.setColor(args.getInt(0), args.getInt(1), args.getInt(2));
			return true;
		}
        return false;
    }

    public synchronized void setColor(final int r, final int g, final int b) {
		final Activity activity = this.cordova.getActivity();
        final Window window = activity.getWindow();
		
		this.cordova.getActivity().runOnUiThread(new Runnable() {
		@Override
		public void run() {
			Log.d(TAG, "Executing: R:" + r);
			Log.d(TAG, "Executing: G:" + g);
			Log.d(TAG, "Executing: B:" + b);
			
			int currentapiVersion = android.os.Build.VERSION.SDK_INT;
			if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
				window.setStatusBarColor(android.graphics.Color.rgb(r,g,b));
			}
		}});
	}
}
