package org.company.trainingJpaAndHibernate;

import javax.persistence.*;
import java.util.List;

public class TestSystem {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("trainingJpaAndHibernate");


    public static void main(String[] args) {

        addCustomer(1, "John", "Smith");
        addCustomer(2, "Tom", "Kowalski");
        addCustomer(3, "Frank", "Musk");
        addCustomer(4, "Joe", "Gates");

        getCustomer(3);
        getCustomers();
        changeFName(3, "Mark");
        deleteCustomer(4);


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

    public static void getCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.id = :custId";

        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        tq.setParameter("custId", id);
        Customer cust = null;
        try {
            cust = tq.getSingleResult();
            System.out.println("Customer: " + cust.getfName() + " " + cust.getlName());
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void getCustomers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL;";

        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> custs;
        try {
            custs = tq.getResultList();
            custs.forEach(cust -> System.out.println("Customer: " + cust.getfName() + " " + cust.getlName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void changeFName(int id, String fname) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer customer = null;

        try {
            et = em.getTransaction();
            et.begin();

            customer = em.find(Customer.class, id);
            customer.setfName(fname);

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

    public static void deleteCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer customer = null;

        try {
            et = em.getTransaction();
            et.begin();

            customer = em.find(Customer.class, id);
            em.remove(customer);


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
