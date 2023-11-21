package com.mountblue.spring.blogApplication.dao;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppDaoImpl implements AppDao{
    private EntityManager entityManager;

    @Autowired
    public AppDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addPost() {
        Post post = new Post("World Cup 2023", "Final Match", "India vs Australia Live Score Updates, World Cup Final: Ind vs Aus, Australia's Travis Head hit a hundred in the World Cup final against India in Ahmedabad on Sunday as his team closed in on a record-extending sixth title.\n" +
                "\n" +
                "Victory for Australia is a crowning moment in what has been a phenomenal year for Pat Cummins’ side across formats, adding to their win over India in the World Test Championship final in June and their subsequent retention of the Ashes.\n" +
                "\n" +
                "Australia were wobbling at 47-3 in pursuit of a target of 241 against unbeaten tournament hosts India.\n" +
                "\n" +
                "But left-handed opener Head's 95-ball century, including 14 fours and a six, helped take them to 185-3 in the 34th over.\n" +
                "\n" +
                "Australia star batsman Steve Smith was dismissed for just four as India fought back with the ball in the World Cup final at Ahmedabad on Sunday.\n" +
                "\n" +
                "Jasprit Bumrah deceived Smith with a slower ball that had the right-hander lbw, the India paceman celebrating wildly with his team-mates as a near full house of around 130,000 fans erupted with joy.\n" +
                "\n", "Tarun Joshi");

        Tag tag = new Tag("World Cup");

        post.addTags(tag);

        entityManager.persist(post);
    }
}
