package com.app.gofoodie.activity.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.app.gofoodie.customview.GoFoodieProgressDialog;
import com.app.gofoodie.model.login.Data;
import com.app.gofoodie.model.login.Login;
import com.app.gofoodie.utility.SessionUtils;

import java.lang.reflect.Field;

/**
 * {@link AppCompatActivity} Base class for application customization.
 */
@SuppressWarnings("unused")
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    public final String TAG = "BaseAppCompatActivity";

    private GoFoodieProgressDialog mGoFoodieProgressDialog = null;

    @SuppressLint("RestrictedApi")
    protected void disableShiftMode(BottomNavigationView view) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

        try {

            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }

        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    /**
     * {@link AppCompatActivity} override callback method(s).
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoFoodieProgressDialog = new GoFoodieProgressDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoFoodieProgressDialog.dismiss();
    }

    /**
     * Method to get the progress dialog for the activity.
     *
     * @return GoFoodieProgressDialog
     */
    public GoFoodieProgressDialog getProgressDialog() {

        return this.mGoFoodieProgressDialog;
    }

    /**
     * Method to show full screen by hiding title, navigation and status bar.
     */
    protected void showFullScreen() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * Method to hide the navigation bar within the activity.
     */
    protected void hideNavigationBar() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.gc();
        Runtime.getRuntime().gc();
    }

    /**
     * @return reference to {@link Login} object (session).
     * Method to fetch the current session within the activity.
     */
    public Login getSession() {

        return SessionUtils.getInstance().getSession();
    }

    /**
     * Method to get the Data from session.
     *
     * @return Data from {@link Login}.{@link Data} reference.
     */
    protected Data getSessionData() {

        Login login = SessionUtils.getInstance().getSession();
        if (login == null) {

            return null;
        } else {

            return login.getData();
        }
    }

}
