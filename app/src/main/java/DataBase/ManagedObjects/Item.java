package DataBase.ManagedObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;

/**
 * Created by suvp on 3/14/2016.
 */
public class Item implements Serializable
{
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(foreign = true)
    private Invoice invoice;

    @DatabaseField(foreign = true)
    private Bill bill;

    @DatabaseField(foreign = true)
    private Product product;

    @DatabaseField
    private String serialNumber;

    public Item() {
    }

    public Item(Product aInProduct)
    {
        product = aInProduct;
    }


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
