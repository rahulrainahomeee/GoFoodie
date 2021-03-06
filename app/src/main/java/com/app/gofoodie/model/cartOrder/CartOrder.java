package com.app.gofoodie.model.cartOrder;

import com.app.gofoodie.model.cart.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to hold from cart item, in order to proceed for the payment.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class CartOrder {

    public String date = "";
    public final String cartItemId;
    public final String restaurantName;
    public final String restaurantId;
    public final String branchId;
    public final String comboId;
    public final String comboName;
    public final String image;
    public final String type;
    public String comboPrice = "0.0";
    public final String quantity;
    public final List<Description> description = new ArrayList<>();
    public String zoneShippingCharge = "0.0";
    public String payPrice = "0.0";

    public CartOrder(Cart cart) {

        this.cartItemId = cart.getCartItemId();
        this.zoneShippingCharge = cart.getZoneShippingCharge();
        this.restaurantName = cart.getRestaurantName();
        this.branchId = cart.getBranchId();
        this.restaurantId = cart.getRestaurantId();
        this.comboId = cart.getComboId();
        this.comboName = cart.getComboName();
        this.image = cart.getImage();
        this.type = cart.getType();
        this.comboPrice = cart.getComboPrice();
        this.quantity = cart.getQuantity();
        this.payPrice = cart.getPayPrice();

        description.clear();

        for (com.app.gofoodie.model.cart.Description desc : cart.getDescription()) {

            this.description.add(new Description(desc));
        }
    }

}
