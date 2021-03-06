package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityCRUD extends TransactionalSetup {

    @Before
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    @Test
    public void testCreateReadUpdateReadRemoveRead() {

        // create new entity
        Entity initialEntity = new Entity();
        initialEntity.setId(1);
        initialEntity.setName("name");

        // persist
        em.persist(initialEntity);
        flushAndClear();// mandatory check executed queries

        // verify persist
        Entity entity2 = em.find(Entity.class, initialEntity.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(initialEntity, entity2);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("name", objects[1]);
                Assert.assertNull(objects[2]);
            }
        }

        // update
        entity2.setName("new name");
        entity2.setValue(12);
        flushAndClear();// mandatory check executed queries

        // verify update
        Entity entity3 = em.find(Entity.class, initialEntity.getId());
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("new name", objects[1]);
                Assert.assertEquals(12, objects[2]);
            }
        }

        // remove
        Entity toBeRemovedEntity = em.find(Entity.class, initialEntity.getId());
        Assert.assertNotNull(toBeRemovedEntity);
        em.remove(toBeRemovedEntity);
        flushAndClear();// //  mandatory check executed queries

        // verify remove
        Assert.assertNull(em.find(Entity.class, initialEntity.getId()));

        // verify database state with a native query
        verifyCorrespondingTableIsEmpty(Entity.class);

    }

}
