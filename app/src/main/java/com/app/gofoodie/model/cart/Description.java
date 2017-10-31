
package com.app.gofoodie.model.cart;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Description implements Parcelable
{

    @SerializedName("item_id")
    @Expose
    public String itemId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("options")
    @Expose
    public List<String> options = null;
    public final static Creator<Description> CREATOR = new Creator<Description>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Description createFromParcel(Parcel in) {
            return new Description(in);
        }

        public Description[] newArray(int size) {
            return (new Description[size]);
        }

    }
    ;

    protected Description(Parcel in) {
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.options, (String.class.getClassLoader()));
    }

    public Description() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(itemId);
        dest.writeValue(name);
        dest.writeValue(value);
        dest.writeList(options);
    }

    public int describeContents() {
        return  0;
    }

}
