package relationships.orphans.onetoone.bothways;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanBothWaysA a;
    OTOOrphanBothWaysB b;

    @Before
    public void setUp() {

        a = new OTOOrphanBothWaysA();
        a.setId(1);
        a.setName("a");

        b = new OTOOrphanBothWaysB();
        b.setId(1);
        b.setName("b");

        a.setB(b);
        b.setA(a);

        persist(a, b);
        flushAndClear();

    }

    @Test
    public void testRemoveOrphanA() {

        em.find(OTOOrphanBothWaysB.class, b.getId()).setA(null);
        flushAndClear();

        Assert.assertNull(em.find(OTOOrphanBothWaysA.class, a.getId()));
        Assert.assertNull(em.find(OTOOrphanBothWaysB.class, b.getId()));

    }

    @Test
    public void testRemoveOrphanB() {

        em.find(OTOOrphanBothWaysA.class, a.getId()).setB(null);
        flushAndClear();

        Assert.assertNull(em.find(OTOOrphanBothWaysA.class, a.getId()));
        Assert.assertNull(em.find(OTOOrphanBothWaysB.class, b.getId()));

    }
}