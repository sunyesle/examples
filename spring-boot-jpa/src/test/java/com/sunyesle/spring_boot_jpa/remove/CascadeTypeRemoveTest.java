package com.sunyesle.spring_boot_jpa.remove;

import com.sunyesle.spring_boot_jpa.remove.cascade_type_remove.Child;
import com.sunyesle.spring_boot_jpa.remove.cascade_type_remove.ChildRepository;
import com.sunyesle.spring_boot_jpa.remove.cascade_type_remove.Parent;
import com.sunyesle.spring_boot_jpa.remove.cascade_type_remove.ParentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CascadeTypeRemoveTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    ChildRepository childRepository;

    /*
    delete from child where id=1
    delete from child where id=2
    delete from parent where id=1
     */
    @Test
    void cascadeTypeRemove_removeParent_test() {
        // given
        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();

        parent.addChild(child1);
        parent.addChild(child2);

        parentRepository.save(parent);

        entityManager.flush();

        // when
        parentRepository.delete(parent);

        // then
        List<Parent> parents = parentRepository.findAll();
        List<Child> children = childRepository.findAll();

        assertThat(parents).isEmpty();
        assertThat(children).isEmpty();
    }

    /*
    select p1_0.id from parent p1_0
    update child set parent_id=NULL where id=1
    update child set parent_id=NULL where id=2
     */
    @Test
    void cascadeTypeRemove_removeChild_test() {
        // given
        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();

        parent.addChild(child1);
        parent.addChild(child2);

        parentRepository.save(parent);

        entityManager.flush();

        // when
        parent.removeChild(child1);
        parent.removeChild(child2);

        // then
        List<Parent> parents = parentRepository.findAll();
        List<Child> children = childRepository.findAll();

        assertThat(parents).isNotEmpty();
        assertThat(children).isNotEmpty();
    }
}
