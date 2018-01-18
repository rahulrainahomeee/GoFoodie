package com.app.gofoodie.model.cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.gofoodie.model.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse extends BaseModel implements Parcelable {

    public final static Creator<CartResponse> CREATOR = new Creator<CartResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CartResponse createFromParcel(Parcel in) {
            return new CartResponse(in);
        }

        public CartResponse[] newArray(int size) {
            return (new CartResponse[size]);
        }

    };
    @SerializedName("Cart")
    @Expose
    private List<Cart> cart = null;
    @SerializedName("totalPrice")
    @Expose
    private String totalPrice;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;

    protected CartResponse(Parcel in) {
        in.readList(this.cart, (Cart.class.getClassLoader()));
        this.totalPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.statusCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CartResponse() {
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cart);
        dest.writeValue(totalPrice);
        dest.writeValue(statusCode);
        dest.writeValue(statusMessage);
    }

    public int describeContents() {
        return 0;
    }

}
