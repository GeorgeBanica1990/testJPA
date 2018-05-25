package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityDelete extends TransactionalSetup {

    @Before
    public void before() {

        // verify database state with a native query
        {
            Assert.assertTrue(em.createNativeQuery("select * from SimpleEntity").getResultList().isEmpty());
        }

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name");

        // persist
        em.persist(entity1);
        flushAndClear();

    }

    @Test
    public void test() {

        // remove
        Entity entity4 = em.find(Entity.class, 1);
        Assert.assertNotNull(entity4);
        em.remove(entity4);
        flushAndClear();
        // TODO mandatory check executed queries

        // verify remove
        Assert.assertNull(em.find(Entity.class, 1));

        // verify database state with a native query
        {
            Assert.assertTrue(em.createNativeQuery("select * from SimpleEntity").getResultList().isEmpty());
        }

    }

}