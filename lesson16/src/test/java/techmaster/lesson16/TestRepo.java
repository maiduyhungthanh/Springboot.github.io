package techmaster.lesson16;

import lombok.extern.slf4j.Slf4j;
import techmaster.lesson16.demo.Comment;
import techmaster.lesson16.demo.Post;
import techmaster.lesson16.demo.User;
import techmaster.lesson16.repository.CommentRepository;
import techmaster.lesson16.repository.PostRepository;
import techmaster.lesson16.repository.UserRepository;
import techmaster.lesson16.service.UserDao;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestRepo {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	// @Autowired
	// private EntityManager entityManager;
    @Autowired
    private UserDao userDao;

	@Test
	@Order(1)
	public void getAllUser() {
		List<User> userList = userDao.findAll();
		Assertions.assertThat(userList.size()).isEqualTo(5);
	}

	@Test
	@Order(2)
	public void getAllPost(){
		List<Post> postList = postRepository.findAll();
		Assertions.assertThat(postList.size()).isEqualTo(10);
	}

	@Test
	@Order(3)
	public void getAllComment(){
		List<Comment> commentList = commentRepository.findAll();
		Assertions.assertThat(commentList.size()).isEqualTo(30);
	}




}
