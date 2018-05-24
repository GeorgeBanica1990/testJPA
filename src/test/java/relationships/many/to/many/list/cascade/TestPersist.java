package relationships.many.to.many.list.cascade;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestPersist extends TransactionalSetup {

    private List<Object> model = buildModel();

    private static List<Object> buildModel() {
        List<Object> objects = new ArrayList<>();

        {

            CascadeM m1 = new CascadeM();
            m1.setId(1);
            m1.setName("m 1 name");
            objects.add(m1);

            CascadeN n1 = new CascadeN();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            n1.getListWithMs().add(m1);
            m1.getListWithNs().add(n1);

        }

        return objects;
    }

    @Test
    public void testPersistFromTheOwningSide() {

        // persist
        em.persist(model.get(0));
        flushAndClear();

        verifyPersistedModel();

    }

    @Test
    public void testPersistFromTheNonOwningSide() {

        // persist
        em.persist(model.get(1));
        flushAndClear();

        verifyPersistedModel();

    }

    private void verifyPersistedModel() {
        List<CascadeM> listM = em.createQuery("select t from CascadeM t", CascadeM.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listM, ReflectionComparatorMode.LENIENT_ORDER);
        List<CascadeN> listN = em.createQuery("select t from CascadeN t", CascadeN.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listN, ReflectionComparatorMode.LENIENT_ORDER);
        flushAndClear();
    }

}