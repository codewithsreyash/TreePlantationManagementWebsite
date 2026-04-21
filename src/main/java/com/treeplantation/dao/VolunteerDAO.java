package com.treeplantation.dao;

import com.treeplantation.model.Volunteer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class VolunteerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Volunteer volunteer) {
        sessionFactory.getCurrentSession().saveOrUpdate(volunteer);
    }

    @SuppressWarnings("unchecked")
    public List<Volunteer> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Volunteer")
                .list();
    }

    public Volunteer findById(Long id) {
        return sessionFactory.getCurrentSession().get(Volunteer.class, id);
    }
    
    // GAP 5: HQL query with a WHERE clause and parameter binding
    @SuppressWarnings("unchecked")
    public List<Volunteer> findVolunteersByEmailDomain(String domain) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Volunteer where email like :domain")
                .setParameter("domain", "%" + domain + "%")
                .list();
    }

    public void delete(Long id) {
        Volunteer volunteer = sessionFactory.getCurrentSession().get(Volunteer.class, id);
        if (volunteer != null) {
            sessionFactory.getCurrentSession().delete(volunteer);
        }
    }
}
