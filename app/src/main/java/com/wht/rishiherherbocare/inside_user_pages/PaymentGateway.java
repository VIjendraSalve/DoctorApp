package com.wht.rishiherherbocare.inside_user_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/*import com.instamojo.android.Instamojo;
import com.instamojo.android.helpers.Constants;*/
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.R;

public class PaymentGateway extends BaseActivity /*implements Instamojo.InstamojoPaymentCallback*/{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

       /* Instamojo.getInstance().initialize(this, Instamojo.Environment.PRODUCTION);


        Instamojo.getInstance().initiatePayment(this, "1", this);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(Constants.ORDER_ID);
            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
               // checkPaymentStatus(transactionID, orderID);
            } else {
               /// showToast("Oops!! Payment was cancelled");
            }
        }*/
    }

  /*  @Override
    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Log.d("Payment", "Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);
    }

    @Override
    public void onPaymentCancelled() {
        Log.d("Payment","Cancelled");
    }

    @Override
    public void onInitiatePaymentFailure(String s) {
        Log.d("Payment", "Initiate payment failed");
        Toast.makeText(this, "Initiating payment failed. Error:"+s, Toast.LENGTH_SHORT).show();
    }*/
}