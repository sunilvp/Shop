package Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by suvp on 1/16/2016.
 */
public enum HP
{
    HP_NONE("NONE",0.0),
    HP_0_5("0.5", 0.5),
    HP_1("1" , 1.0),
    HP_2("2", 2.0),
    HP_2_5("2.5", 2.5),
    HP_3("3", 3.0);

    HP(String aInDisplayedName, Double aInFloatValue)
    {
        displayedName = aInDisplayedName;
        numericValue = aInFloatValue;
    }

    private String displayedName;
    private Double numericValue;

    public List<String> getHPStringList()
    {
        List<String> $HpStringList = new LinkedList<>();
        for(HP lHp : HP.values())
        {
            $HpStringList.add(lHp.displayedName);
        }
        return $HpStringList;
    }

    public List<Double> getHpDoubleList()
    {
        List<Double> $HpDoubleList = new LinkedList<>();
        for(HP lHp : HP.values())
        {
            $HpDoubleList.add(lHp.numericValue);
        }
        return $HpDoubleList;
    }

    public String getString()
    {
        return displayedName;
    }

    public double getHpFloat()
    {
        return numericValue;
    }

}
