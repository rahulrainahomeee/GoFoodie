package com.app.gofoodie.utility;

import android.content.Context;

import com.app.gofoodie.handler.modelHandler.ModelParser;
import com.app.gofoodie.handler.profileDataHandler.CustomerProfileHandler;
import com.app.gofoodie.model.login.Login;

import org.json.JSONObject;

/**
 * @class SessionUtils
 * @desc Singleton Class to handle the session, get session, save session, update session, load session amd remove session.
 */
@SuppressWarnings("unused")
public class SessionUtils {

    public static final String TAG = "SessionUtils";

    private static final SessionUtils ourInstance = new SessionUtils();

    /**
     * Customer's application Login session details.
     */
    private static boolean isSession = false;
    private static Login LOGIN = null;

    private SessionUtils() {

    }

    public static SessionUtils getInstance() {
        return ourInstance;
    }

    /**
     * @return boolean true = session exist and false = session not exist.
     * @method isSessionExist
     * @desc Method to check if there is any existing session or not.
     */
    public boolean isSessionExist() {

        return isSession;
    }

    /**
     * Method to remover the existing session (Login and user profile details).
     *
     * @param context reference
     */
    public void removeSession(Context context) {

        CacheUtils.getInstance().getPref(context, CacheUtils.PREF_NAME.PREF_LOGIN).edit().remove(CacheUtils.PREF_KEY).apply();
        LOGIN = null;
        isSession = false;
        CustomerProfileHandler.CUSTOMER = null;
    }

    /**
     * Method to save given parameters as the session for the application.
     *
     * @param context reference
     * @param login   reference
     */
    public void saveSession(Context context, JSONObject login) {

        CacheUtils.getInstance().getPref(context, CacheUtils.PREF_NAME.PREF_LOGIN).edit().putString(CacheUtils.PREF_KEY, login.toString()).apply();
        loadSession(context);
    }

    /**
     * Method to load the session from Preference to the globally accessible model class.
     *
     * @param context reference
     */
    public void loadSession(Context context) {

        String strLogin = CacheUtils.getInstance().getPref(context, CacheUtils.PREF_NAME.PREF_LOGIN).getString(CacheUtils.PREF_KEY, "");

        if (strLogin.isEmpty()) {
            LOGIN = null;
            isSession = false;
            removeSession(context);
            return;
        } else {
            ModelParser modelParser = new ModelParser();
            LOGIN = (Login) modelParser.getModel(strLogin, Login.class, null);
            isSession = true;
        }

        /// Check if the LOGIN session data is correct.
        if (LOGIN.getStatusCode() != 200) {

            LOGIN = null;
            isSession = false;
            removeSession(context);
        }
    }

    /**
     * Method to get the current session object {@link Login}.
     *
     * @return reference to {@link Login} object.
     */
    public Login getSession() {

        return LOGIN;
    }

}
