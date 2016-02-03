package Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by suvp on 1/25/2016.
 */
public enum Type
{
    PUMP("Pump"),
    MOTOR("Motor"),
    SINGLE("Single"),
    BOTH("Both");

    private String displayString;
    Type(String aInDisplayString)
    {
        displayString = aInDisplayString;
    }

    public static List<String> getTypeDisplayStringList()
    {
        List<String> $RetDisplayList = new LinkedList<>();
        for(Type lType : Type.values())
        {
            $RetDisplayList.add(lType.displayString);
        }
        return $RetDisplayList;
    }

    public String getType()
    {
        return displayString;
    }
}
