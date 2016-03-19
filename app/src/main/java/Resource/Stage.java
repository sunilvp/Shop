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

    private Integer stageValue;
    Stage(int aInStage)
    {
        stageValue = aInStage;
    }

    public static List<Integer> getAllStages()
    {
        List<Integer> $stageList = new ArrayList<>();
        for(Stage lStage: Stage.values())
        {
            $stageList.add(lStage.stageValue);
        }
        return $stageList;
    }

    public static List<String> getAllStagesString()
    {
        List<String> $stageList = new ArrayList<>();
        for(Stage lStage: Stage.values())
        {
            $stageList.add(lStage.stageValue.toString());
        }
        return $stageList;
    }

    public int getStage()
    {
        return stageValue;
    }
}
