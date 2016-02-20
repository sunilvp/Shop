package Resource;

/**
 * Created by suvp on 1/25/2016.
 */
public enum ProductEnum
{
    RS_1_20_SS_M ("RS_1/20 ss", Type.MOTOR, BodyType.STEEL, Phase.SINGLE, HP.HP_1, Stage.STAGE_20),
    RS_1_20_SS_P ("RS_1/20 ss", Type.PUMP, BodyType.STEEL, Phase.SINGLE, HP.HP_1, Stage.STAGE_20),
    RS_1_20_GM_M ("RS_1/20 gm", Type.MOTOR, BodyType.GUN_METAL, Phase.SINGLE, HP.HP_1, Stage.STAGE_20),
    RS_1_20_GM_P ("RS_1/20 gm", Type.PUMP, BodyType.GUN_METAL, Phase.SINGLE, HP.HP_1, Stage.STAGE_20),

    RS_2_22_SS_M ("RS_2/22 ss", Type.MOTOR, BodyType.STEEL, Phase.TWO, HP.HP_2, Stage.STAGE_22),
    RS_2_22_SS_P ("RS_2/22 ss", Type.PUMP, BodyType.STEEL, Phase.TWO, HP.HP_2, Stage.STAGE_22),
    RS_2_22_GM_M ("RS_2/22 gm", Type.MOTOR, BodyType.GUN_METAL, Phase.TWO, HP.HP_2, Stage.STAGE_22),
    RS_2_22_GM_P ("RS_2/22 gm", Type.PUMP, BodyType.GUN_METAL, Phase.TWO, HP.HP_2, Stage.STAGE_22),

    RS_2_24_SS_M ("RS_1/24 ss", Type.MOTOR, BodyType.STEEL, Phase.TWO, HP.HP_2, Stage.STAGE_24),
    RS_2_24_SS_P ("RS_1/24 ss", Type.PUMP, BodyType.STEEL, Phase.TWO, HP.HP_2, Stage.STAGE_24),
    RS_2_24_GM_M ("RS_1/24 gm", Type.MOTOR, BodyType.GUN_METAL, Phase.TWO, HP.HP_2, Stage.STAGE_24),
    RS_2_24_GM_P ("RS_1/24 gm", Type.PUMP, BodyType.GUN_METAL, Phase.TWO, HP.HP_2, Stage.STAGE_24);

    public String displayedName;
    public Type type;
    public BodyType bodyType;
    public Phase phase;
    public HP hp;
    public Stage stage;

    ProductEnum(String aInDisplayedName, Type aIntype, BodyType aInbodyType,Phase aInPhase, HP aInHp, Stage aInStage )
    {
        displayedName = aInDisplayedName;
        type = aIntype;
        bodyType = aInbodyType;
        phase = aInPhase;
        hp = aInHp;
        stage = aInStage;
    }
}
