package relationships.manytoone.notstrict;

import javax.persistence.*;

/**
 * This class represents the MANY part of a MANY TO ONE relationship
 * The children knows about their parents<br>
 * The parents do not know about their children<br>
 * The child is the owner of the relationship<br>
 * No cascade options must be used in this case since parents are fully independent entities<br>
 * It is allowed for a child not to have a parent<br>
 * This relationship is similar to ONE TO ONE
 */
@Entity
public class MTOONotStrictChild {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private MTOONotStrictParent parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MTOONotStrictParent getParent() {
        return parent;
    }

    public void setParent(MTOONotStrictParent parent) {
        this.parent = parent;
    }

}