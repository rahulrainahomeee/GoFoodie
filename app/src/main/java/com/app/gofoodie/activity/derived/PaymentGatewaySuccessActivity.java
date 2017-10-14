package com.app.gofoodie.activity.derived;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gofoodie.R;
import com.app.gofoodie.activity.base.BaseAppCompatActivity;
import com.app.gofoodie.network.callback.NetworkCallbackListener;
import com.app.gofoodie.network.handler.NetworkHandler;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.response.status.StatusResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentGatewaySuccessActivity extends BaseAppCompatActivity implements NetworkCallbackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway_success);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        StatusResponse status = (StatusResponse) intent.getParcelableExtra(WebviewActivity.PAYMENT_RESPONSE);
        TextView textView = (TextView) findViewById(R.id.text_payment_result);
        textView.setText(textView.getText() + " : " + status.getTrace());

        if (status.getAuth() != null) {
            String telrTrace = status.getTrace();
            String telrStatus = status.getAuth().getStatus();   // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.
            String telrAvs = status.getAuth().getAvs();      /* Result of the AVS check:
                                            Y = AVS matched OK
                                            P = Partial match (for example, post-code only)
                                            N = AVS not matched
                                            X = AVS not checked
                                            E = Error, unable to check AVS */
            String telrCode = status.getAuth().getCode();     // If the transaction was authorised, this contains the authorisation code from the card issuer. Otherwise it contains a code indicating why the transaction could not be processed.
            String telrMessage = status.getAuth().getMessage();  // The authorisation or processing error message.
            String telrCa_valid = status.getAuth().getCa_valid();
            String telrCardcode = status.getAuth().getCardcode(); // Code to indicate the card type used in the transaction. See the code list at the end of the document for a list of card codes.
            String telrCardlast4 = status.getAuth().getCardlast4();// The last 4 digits of the card number used in the transaction. This is supplied for all payment types (including the Hosted Payment Page method) except for PayPal.
            String telrCvv = status.getAuth().getCvv();      /* Result of the CVV check:
                                           Y = CVV matched OK
                                           N = CVV not matched
                                           X = CVV not checked
                                           E = Error, unable to check CVV */
            String telrTranref = status.getAuth().getTranref(); //The payment gateway transaction reference allocated to this request.

            JSONObject jsonRequest = new JSONObject();
            try {
                jsonRequest.put("telr_trace", telrTrace);
                jsonRequest.put("telr_status", telrStatus);
                jsonRequest.put("telr_avs", telrAvs);
                jsonRequest.put("telr_code", telrCode);
                jsonRequest.put("telr_message", telrMessage);
                jsonRequest.put("telr_ca_valid", telrCa_valid);
                jsonRequest.put("telr_cardcode", telrCardcode);
                jsonRequest.put("telr_cardlast4", telrCardlast4);
                jsonRequest.put("telr_cvv", telrCvv);
                jsonRequest.put("telr_tranref", telrTranref);

                NetworkHandler networkHandler = new NetworkHandler();
                networkHandler.httpCreate(1, this, this, jsonRequest, "url", NetworkHandler.RESPONSE_TYPE.JSON_OBJECT);
                networkHandler.executePost();

            } catch (JSONException jsonExc) {

                jsonExc.printStackTrace();
                Toast.makeText(this, "JSONException: " + jsonExc.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void closeWindow(View view) {
        this.finish();
    }

    /**
     * {@link NetworkCallbackListener} http response callback method(s).
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        Toast.makeText(this, "Http Success: " + rawObject.toString(), Toast.LENGTH_SHORT).show();
        if (requestCode == 1) {
            handleResponse(rawObject);
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        Toast.makeText(this, "Http Fail:  " + message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param json
     * @method handleResponse
     * @desc Method to handle the transaction response.
     */
    private void handleResponse(JSONObject json) {

        try {
            int statusCode = json.getInt("statusCode");
            String statusMessage = json.getString("statusMessage");

        } catch (JSONException jsonExc) {

            jsonExc.printStackTrace();
            Toast.makeText(this, "JSONException: " + jsonExc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}