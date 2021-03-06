package com.app.gofoodie.activity.derived;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.gofoodie.R;
import com.app.gofoodie.global.data.GlobalData;
import com.app.gofoodie.model.rechargePlan.Subscriptionplan;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.Address;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;

import java.math.BigInteger;
import java.util.Random;

/**
 * @class PaymentGatewayActivity
 * @desc Activity Class for confirming and starting payment transaction via gateway.
 */
@SuppressWarnings("unused")
public class PaymentGatewayActivity extends AppCompatActivity {

    public static final String TAG = "PaymentGatewayActivity";

    /**
     * Payment Gateway related details, keys.
     */
    private static final String KEY = "KmgLp~QSQs@347VG";           // TODO: Insert your Key here
    private static final String STORE_ID = "18742";         // TODO: Insert your Store ID here
    private static final boolean isSecurityEnabled = false;      // Mark false to test on simulator, True to test on actual device and Production

    /**
     * Class data members.
     */
    private String amount;

    /**
     * {@link AppCompatActivity} callback method(s).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        /*
      Activity UI view object(s).
     */
        TextView txtPlanName = findViewById(R.id.txt_plan_name);
        TextView txtPlanDetail = findViewById(R.id.txt_details);
        TextView txtGetPrice = findViewById(R.id.txt_get_price);
        TextView txtDays = findViewById(R.id.txt_days);
        Button btnConform = findViewById(R.id.btn_confirm);

        Subscriptionplan subscriptionplan = GlobalData.subscriptionplan;

        amount = subscriptionplan.payAmount;
        String days = subscriptionplan.validityDays;
        String planName = subscriptionplan.name;
        String planDetail = subscriptionplan.description;

        txtPlanName.setText(planName);
        txtPlanDetail.setText(planDetail);
        txtGetPrice.setText(amount + " AED");
        txtDays.setText("Valid upto " + days + " days");

    }

    /**
     * Button Click event: Method to start the payment transaction via gateway.
     *
     * @param view reference
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, WebviewActivity.class);


        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest());

        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, PaymentGatewaySuccessActivity.class.getCanonicalName());
        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, PaymentGatewayFailActivity.class.getCanonicalName());

        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
        startActivity(intent);
        finish();
    }

    private MobileRequest getMobileRequest() {
        MobileRequest mobile = new MobileRequest();
        mobile.setStore(STORE_ID);                       // Store ID
        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
        App app = new App();
        app.setId("123456789");                          // Application installation ID
        app.setName("TelrSDK");                          // Application name
        app.setUser("123456");                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion("0.0.1");                         // Application version
        mobile.setApp(app);
        Tran tran = new Tran();
        tran.setTest("1");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("sale");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
        tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription("Test Mobile API");         // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(amount);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        //tran.setRef();                                // (Optinal) Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
        mobile.setTran(tran);
        Billing billing = new Billing();
        Address address = new Address();
        address.setCity("Dubai");                       // City : the minimum required details for a transaction to be processed
        address.setCountry("AE");                       // Country : Country must be sent as a 2 character ISO code. A list of country codes can be found at the end of this document. the minimum required details for a transaction to be processed
        address.setRegion("Dubai");                     // Region
        address.setLine1("SIT G=Towe");                 // Street address: line 1: the minimum required details for a transaction to be processed
//        address.setLine2("SIT G=Towe");               // (Optinal)
//        address.setLine3("SIT G=Towe");               // (Optinal)
//        address.setZip("SIT G=Towe");                 // (Optinal)
        billing.setAddress(address);
        Name name = new Name();
        name.setFirst("john");                          // Forename : the minimum required details for a transaction to be processed
        name.setLast("smith");                          // Surname : the minimum required details for a transaction to be processed
        name.setTitle("Mrs");                           // Title
        billing.setName(name);
        billing.setEmail("r.rahul@drushti.in"); // : the minimum required details for a transaction to be processed
        mobile.setBilling(billing);
        return mobile;

    }

}
