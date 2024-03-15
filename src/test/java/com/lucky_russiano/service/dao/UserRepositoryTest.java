package com.lucky_russiano.service.dao;

import com.lucky_russiano.entity.MyUser;
import lombok.Cleanup;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {
    @Test
    void saveUserTest(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(MyUser.class);
        configuration.configure();
        @Cleanup SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        MyUser user1 = MyUser.builder().id(2L)
                .username("avraam112")
                .password("123")
                .name("Russo")
                .lastName("Zaripov")
                .salary(1000)
                .build();
        session.beginTransaction();
        session.persist(user1);
        session.flush();
        MyUser myUser = session.get(MyUser.class, user1.getId());
        Assertions.assertThat(myUser).isNotNull();
        Assertions.assertThat(myUser.getId()).isEqualTo(2L);
        session.getTransaction().commit();

    }

}
