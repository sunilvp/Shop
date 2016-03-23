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
    String invoiceNumber;

    @DatabaseField(
    dataType = DataType.DATE_STRING,
    format = "dd-MM-yyyy")
    Date date;

    @ForeignCollectionField(eager = true, foreignFieldName = "invoice")
    private Collection<Item> itemList;

    public Invoice()
    {}

    public Invoice(String aInInvoiceNumber,Date aInDate)
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
