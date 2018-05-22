package entities.simple.projection;

/**
 * an entity projection is not a JPA entity
 */
public class Projection2OfEntityWithProjection {

    private Integer id;

    private Integer value;

    public Projection2OfEntityWithProjection(Integer id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
