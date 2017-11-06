
package com.app.gofoodie.model.restaurantBranch;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.gofoodie.model.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantBranchResponse extends BaseModel implements Parcelable {

    @SerializedName("restaurant_branch")
    @Expose
    public RestaurantBranch restaurantBranch;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;
    public final static Creator<RestaurantBranchResponse> CREATOR = new Creator<RestaurantBranchResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RestaurantBranchResponse createFromParcel(Parcel in) {
            return new RestaurantBranchResponse(in);
        }

        public RestaurantBranchResponse[] newArray(int size) {
            return (new RestaurantBranchResponse[size]);
        }

    };

    protected RestaurantBranchResponse(Parcel in) {
        this.restaurantBranch = ((RestaurantBranch) in.readValue((RestaurantBranch.class.getClassLoader())));
        this.statusCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RestaurantBranchResponse() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(restaurantBranch);
        dest.writeValue(statusCode);
        dest.writeValue(statusMessage);
    }

    public int describeContents() {
        return 0;
    }

}