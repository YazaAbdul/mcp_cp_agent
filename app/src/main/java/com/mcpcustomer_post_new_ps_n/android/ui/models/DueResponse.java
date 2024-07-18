package com.mcpcustomer_post_new_ps_n.android.ui.models;

public class DueResponse {
    /*customer_id: "12",
milestone_title: "Final Amount",
milestone_amount: "166666668",
schedule_date: "0000-00-00",
schedule_amount: "",
status: 1,
msg: ""


 milestone_title: "milett1",
    daysfrom_booking_date: "2020-09-15",
    milestone_amount: "499500",
    schedule_date: null,
    schedule_amount: null*/



    private String milestone_title;
    private String daysfrom_booking_date;
    private String milestone_amount;
    private String schedule_date;
    private String schedule_amount;
    private String payment_id;

    public String getPayment_id() {
        return payment_id;
    }

    public String getDaysfrom_booking_date() {
        return daysfrom_booking_date;
    }

    public void setDaysfrom_booking_date(String daysfrom_booking_date) {
        this.daysfrom_booking_date = daysfrom_booking_date;
    }

    public String getMilestone_title() {
        return milestone_title;
    }

    public void setMilestone_title(String milestone_title) {
        this.milestone_title = milestone_title;
    }

    public String getMilestone_amount() {
        return milestone_amount;
    }

    public void setMilestone_amount(String milestone_amount) {
        this.milestone_amount = milestone_amount;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getSchedule_amount() {
        return schedule_amount;
    }

    public void setSchedule_amount(String schedule_amount) {
        this.schedule_amount = schedule_amount;
    }
}
