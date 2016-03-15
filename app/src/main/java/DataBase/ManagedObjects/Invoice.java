package DataBase.ManagedObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by suvp on 2/23/2016.
 */
public class Invoice  implements Serializable
{
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    int invoiceNumber;

    @DatabaseField(
            dataType = DataType.DATE_STRING,
            format = "yyyy-MM-dd")
    Date date;

    @ForeignCollectionField(eager = true)
    private Collection<Item> itemList;

    public Invoice()
    {}

    public Invoice(int aInInvoiceNumber,Date aInDate)
    {
        invoiceNumber = aInInvoiceNumber;
        date = aInDate;
    }

    public Collection<Item> getItemList() {
        return itemList;
    }

    public void setItemList(Collection<Item> itemList) {
        this.itemList = itemList;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
