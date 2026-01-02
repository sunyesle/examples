package com.sunyesle.spring_boot_jpa.remove;

import com.sunyesle.spring_boot_jpa.remove.orphan_removal.Child2;
import com.sunyesle.spring_boot_jpa.remove.orphan_removal.Child2Repository;
import com.sunyesle.spring_boot_jpa.remove.orphan_removal.Parent2;
import com.sunyesle.spring_boot_jpa.remove.orphan_removal.Parent2Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrphanRemovalTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    Parent2Repository parentRepository;

    @Autowired
    Child2Repository childRepository;

    /*
    delete from child2 where id=1
    delete from child2 where id=2
    delete from parent2 where id=1
     */
    @Test
    void orphanRemovalTrue_removeParent_test() {
        // given
        Child2 child1 = new Child2();
        Child2 child2 = new Child2();

        Parent2 parent = new Parent2();

        parent.addChild(child1);
        parent.addChild(child2);

        parentRepository.save(parent);

        entityManager.flush();

        // when
        parentRepository.delete(parent);

        // then
        List<Parent2> parents = parentRepository.findAll();
        List<Child2> children = childRepository.findAll();

        assertThat(parents).isEmpty();
        assertThat(children).isEmpty();
    }

    /*
    select p1_0.id from parent2 p1_0
    delete from child2 where id=1
    delete from child2 where id=2
     */
    @Test
    void orphanRemovalTrue_removeChild_test() {
        // given
        Child2 child1 = new Child2();
        Child2 child2 = new Child2();

        Parent2 parent = new Parent2();

        parent.addChild(child1);
        parent.addChild(child2);

        parentRepository.save(parent);

        entityManager.flush();

        // when
        parent.removeChild(child1);
        parent.removeChild(child2);

        // then
        List<Parent2> parents = parentRepository.findAll();
        List<Child2> children = childRepository.findAll();

        assertThat(parents).isNotEmpty();
        assertThat(children).isEmpty();
    }
}
