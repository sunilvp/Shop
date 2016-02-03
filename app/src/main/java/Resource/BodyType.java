package Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by suvp on 1/16/2016.
 */
public enum BodyType
{
    STEEL("Steel"),
    GUN_METAL("Gun Metal");

    BodyType(String aInDisplayString)
    {
        displayString = aInDisplayString;
    }

    private String displayString;

    public List<String>  getBodyTypeDisplayString()
    {
        List<String> $bodyDisplayList = new LinkedList<>();
        for(BodyType lBodyType : BodyType.values())
        {
            $bodyDisplayList.add(lBodyType.displayString);
        }
        return $bodyDisplayList;
    }

    public String getBodyType()
    {
        return displayString;
    }
}
