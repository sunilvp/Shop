package DataBase.ManagedObjects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import Resource.BodyType;
import Resource.HP;
import Resource.Phase;
import Resource.Stage;
import Resource.Type;

/**
 * Created by suvp on 1/16/2016.
 */
public class Product implements Serializable
{
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    String displayedName;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Type type;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private BodyType bodyType;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Phase phase;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private HP hp;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Stage stage;

    public Product()
    {}

    public Product(String aInDisplayedName, Type aInype, BodyType aInBodyType, Phase aInPhase, HP aInHp, Stage aInStage)
    {
        displayedName = aInDisplayedName;
        type = aInype;
        bodyType = aInBodyType;
        phase = aInPhase;
        hp = aInHp;
        stage = aInStage;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String name) {
        this.displayedName = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public HP getHp() {
        return hp;
    }

    public void setHp(HP hp) {
        this.hp = hp;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}