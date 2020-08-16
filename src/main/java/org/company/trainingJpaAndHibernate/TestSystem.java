package org.company.trainingJpaAndHibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestSystem {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("trainingJpaAndHibernate");




    public static void main(String[] args) {


        ENTITY_MANAGER_FACTORY.close();


    }

    public static void addCustomer(int id, String fname, String lname) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Customer customer = new Customer();
            customer.setId(id);
            customer.setfName(fname);
            customer.setlName(lname);

            em.persist(customer);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
