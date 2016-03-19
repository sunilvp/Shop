package General;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suvp.shop.R;

import java.util.LinkedList;
import java.util.List;

import DataBase.ManagedObjects.Product;

/**
 * Created by suvp on 3/17/2016.
 */
public class CustomProductItemListAdapter extends ArrayAdapter<Product>
{
    private List<Product>  productList = new LinkedList<>();
    private Context context;

    public CustomProductItemListAdapter(Context aInContext, int resource, int textViewResourceId)
    {
        super(aInContext, resource, textViewResourceId);
        context = aInContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_product, parent, false);
        TextView firstTextView = (TextView)(rowView.findViewById(R.id.list_firstLine));
        TextView secondTextView = (TextView)(rowView.findViewById(R.id.list_secondLine));
        if(productList != null && productList.size() >0 )
        {
            Product lProduct = productList.get(position);
            firstTextView.setText(lProduct.getDisplayedName());
            secondTextView.setText(lProduct.getType().getType());
        }
        return rowView;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProducts(List<Product> aInNewProducts)
    {
        productList.addAll(aInNewProducts);
    }

    public void removeProducts(List<Product> aInDelProductList)
    {
        productList.removeAll(aInDelProductList);
    }
}
