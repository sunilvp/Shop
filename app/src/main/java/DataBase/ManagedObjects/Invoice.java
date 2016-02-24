package DataBase.ManagedObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

/**
 * Created by suvp on 2/23/2016.
 */
public class Invoice {
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    int invoiceNumber;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Product product;

    Invoice()
    {}

    public Invoice(int aInInvoiceNumber, Product aInProduct)
    {
        invoiceNumber = aInInvoiceNumber;
        product = aInProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
