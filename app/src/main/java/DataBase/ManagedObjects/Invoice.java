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

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Product product;

    @DatabaseField
    Date date;

    @ForeignCollectionField
    private Collection<Item> itemList;

    public Invoice()
    {}

    public Invoice(int aInInvoiceNumber, Product aInProduct,Date aInDate)
    {
        invoiceNumber = aInInvoiceNumber;
        product = aInProduct;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
