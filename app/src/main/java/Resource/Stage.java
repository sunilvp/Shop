package Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suvp on 1/25/2016.
 */
public enum Stage
{
    STAGE_20 (20),
    STAGE_22 (22),
    STAGE_24 (24),
    STAGE_26 (26),
    STAGE_28 (28),
    STAGE_30 (30);

    private int stageValue;
    Stage(int aInStage)
    {
        stageValue = aInStage;
    }

    private static List<Integer> getAllStages()
    {
        List<Integer> $stageList = new ArrayList<>();
        for(Stage lStage: Stage.values())
        {
            $stageList.add(lStage.stageValue);
        }
        return $stageList;
    }

    public int getStage()
    {
        return stageValue;
    }
}
