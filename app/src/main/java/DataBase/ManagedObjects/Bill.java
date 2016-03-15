package DataBase.ManagedObjects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by suvp on 3/14/2016.
 */
public class Bill implements Serializable
{
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    private String billNo;

    @DatabaseField(
            dataType = DataType.DATE_STRING,
            format = "yyyy-MM-dd")
    private Date billDate;

    @DatabaseField(foreign = true)
    private Customer customer;

    @ForeignCollectionField(eager = true)
    private Collection<Item> items;

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Bill() {
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
