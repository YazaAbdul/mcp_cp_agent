package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class _NewRegistrationPaymentDetailsResponse {

    /*payment_made: "Payment made",
paid_date: "2020-08-24",
payment_mode: "Cash",
received_amount: "10000.00"*/

    private String payment_made;
    private String paid_date;
    private String payment_mode;
    private String received_amount;

    public String getPayment_made() {
        return payment_made;
    }

    public void setPayment_made(String payment_made) {
        this.payment_made = payment_made;
    }

    public String getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(String paid_date) {
        this.paid_date = paid_date;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getReceived_amount() {
        return received_amount;
    }

    public void setReceived_amount(String received_amount) {
        this.received_amount = received_amount;
    }
}
