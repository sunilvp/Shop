package Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by suvp on 1/25/2016.
 */
public enum Phase
{
    SINGLE (1),
    TWO (2),
    THREE (3);

    private int phaseIntValue;

    Phase(int aInPhase)
    {
        phaseIntValue = aInPhase;
    }

    public static List<Integer> getPhaseValues()
    {
        List<Integer> $phaseList = new LinkedList<>();
        for(Phase lPhase : Phase.values())
        {
            $phaseList.add(lPhase.phaseIntValue);
        }
        return $phaseList;
    }

    public int getPhase()
    {
        return phaseIntValue;
    }
}
