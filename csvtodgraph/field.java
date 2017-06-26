package csvtodgraph;

import java.text.DecimalFormat;

/**
 *
 * @author raghu
 */
public class field {
    String id;
    String start_date,end_date;//For display enter date.toString();
    int no_of_days,interest_rate;
    double Principal,Paid,Percentage_paid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrincipal() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        String Prin = df.format(this.Principal);
        return Prin;
    }

    public void setPrincipal(String Principal) {
        this.Principal = Double.parseDouble(Principal);
    }

    public String getPercentage_paid() {
        return Double.toString(Percentage_paid);
    }

    public void setPercentage_paid(String Percentage_paid) {
        this.Percentage_paid = Double.parseDouble(Percentage_paid);
    }

    public String getPaid() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        String Pd = df.format(this.Paid);
        return Pd;
    }

    public void setPaid(String Paid) {
        this.Paid = Double.parseDouble(Paid);
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
            this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
            this.end_date = end_date;
    }

    public String getNo_of_days() {
        return Integer.toString(no_of_days);
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = Integer.parseInt(no_of_days);
    }

    public String getInterest_rate() {
        return Integer.toString(interest_rate);
    }

    public void setInterest_rate(String interest_rate) {
        this.interest_rate = Integer.parseInt(interest_rate);
    }
}
