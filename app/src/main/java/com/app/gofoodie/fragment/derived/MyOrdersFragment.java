package com.app.gofoodie.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.gofoodie.R;
import com.app.gofoodie.adapter.listviewadapter.MyOrdersListViewAdapter;
import com.app.gofoodie.fragment.base.BaseFragment;
import com.app.gofoodie.global.constants.Network;
import com.app.gofoodie.handler.modelHandler.ModelParser;
import com.app.gofoodie.model.myorders.MyOrder;
import com.app.gofoodie.model.myorders.MyOrdersResponse;
import com.app.gofoodie.network.callback.NetworkCallbackListener;
import com.app.gofoodie.network.handler.NetworkHandler;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @class NetworkErrorFragment
 * @desc {@link BaseFragment} Fragment class to handle My Order list UI screen.
 */
public class MyOrdersFragment extends BaseFragment implements NetworkCallbackListener, View.OnClickListener {

    public static final String TAG = "MyOrdersFragment";

    ListView mListViewOrders = null;
    private ArrayList<MyOrder> mList = null;
    private MyOrdersListViewAdapter mAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_my_order, container, false);
        setHasOptionsMenu(true);
        mListViewOrders = (ListView) view.findViewById(R.id.list_view_my_orders);

        fetchMyOrders(null, null);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my_orders, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_date:

                menuItemDateSelected();
                break;
            case R.id.menu_item_long_vacation:

                menuItemLongVacationSelected();
                break;
            case R.id.menu_item_short_vacation:

                menuItemShortVacationSelected();
                break;
            case R.id.menu_item_emergency:

                menuItemEmergencySelected();
                break;

        }
        return true;
    }

    @Override
    public void fragQuitCallback() {

    }


    private void fetchMyOrders(String fromDate, String toDate) {

        String url = Network.URL_GET_MY_ORDER + "?customerid=" + getSession().getData().getCustomerId();
        if (fromDate != null && toDate != null) {

            url = url + "&from=" + fromDate.trim() + "&to=" + toDate.trim();
        }

        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.httpCreate(1, getDashboardActivity(), this, new JSONObject(), url, NetworkHandler.RESPONSE_TYPE.JSON_OBJECT);
        networkHandler.executeGet();
    }

    private void menuItemDateSelected() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year1, int month1, int day1, int year2, int month2, int day2) {

                        month1++;
                        month2++;
                        String fromDate = year1 + "-" + month1 + "-" + day1;
                        String toDate = year2 + "-" + month2 + "-" + day2;
                        fetchMyOrders(fromDate, toDate);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAutoHighlight(true);
        dpd.show(getFragmentManager(), "Select Date Range");

    }

    private void menuItemLongVacationSelected() {

    }

    private void menuItemShortVacationSelected() {

    }

    private void menuItemEmergencySelected() {

    }

    /**
     * {@link NetworkCallbackListener} http response listener callback method.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        Toast.makeText(getActivity(), "Http Success: " + rawObject.toString(), Toast.LENGTH_SHORT).show();
        if (requestCode == 1) {

            handleMyOrdersResponse(rawObject);
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        Toast.makeText(getActivity(), "Http Fail: " + message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param json
     * @method handleMyOrdersResponse
     * @desc Method to handle the MyOrder response from web api.
     */
    private void handleMyOrdersResponse(JSONObject json) {

        ModelParser parser = new ModelParser();
        MyOrdersResponse myOrdersResponse = (MyOrdersResponse) parser.getModel(json.toString(), MyOrdersResponse.class, null);

        if (myOrdersResponse.statusCode != 200 || myOrdersResponse.myOrders == null) {

            Toast.makeText(getActivity(), "" + myOrdersResponse.statusMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        mList = (ArrayList<MyOrder>) myOrdersResponse.myOrders;
        mAdapter = new MyOrdersListViewAdapter(getDashboardActivity(), this, R.layout.item_listview_my_orders, mList);
        mListViewOrders.setAdapter(mAdapter);

    }

    /**
     * {@link android.view.View.OnClickListener} click event listener callback method(s).
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.add_rating:

                addRating(view);
                break;
            case R.id.show_desc:

                showDescription(view);
                break;
            case R.id.edit:

                editCancelOrder(view);
                break;
        }
    }

    private void addRating(View view) {

        MyOrder order = (MyOrder) view.getTag();

    }

    private void showDescription(View view) {

        MyOrder order = (MyOrder) view.getTag();

    }

    private void editCancelOrder(View view) {

        MyOrder order = (MyOrder) view.getTag();

        if (!order.status.toLowerCase().trim().equals("accepted")) {

            Toast.makeText(getActivity(), "Cannot cancel this order.", Toast.LENGTH_SHORT).show();

        } else {

            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("Cancel Order");
            sweetAlertDialog.setContentText("Are you sure?");
            sweetAlertDialog.setConfirmText("Yes");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    sweetAlertDialog.dismissWithAnimation();
                }
            });

            sweetAlertDialog.setCancelText("No");
            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    sweetAlertDialog.dismissWithAnimation();
                }
            });
            sweetAlertDialog.show();
        }
    }


}
