package techmaster.lesson16;

import techmaster.lesson16.demo.Comment;
import techmaster.lesson16.demo.User;
import techmaster.lesson16.repository.CommentRepository;
import techmaster.lesson16.repository.PostRepository;
import techmaster.lesson16.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class TestRepo {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;


	@Test
	public void getAllUser() {
		Assertions.assertThat(userRepository.findAll().size()).isEqualTo(3);
		Assertions.assertThat(postRepository.findAll().size()).isEqualTo(3);
		Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(4);
	}

	@Test
	public void findById(){
		User userNew = new User(1, "thanh","123","th@gmail.com", null, null);
		
		Assertions.assertThat(userRepository.findById(1L).get().getName()).isEqualTo(userNew.getName());
		Assertions.assertThat(userRepository.findById(1L).get().getPosts().size()).isEqualTo(3);
	}
	
	// @Test
	// public void findByName(){
	// 	List <User> listUser = new ArrayList<>();
	// 	listUser.find
	// }

	@Test
	public void addUser(){
		User userNew = new User();	
		userNew.setName("vu");
		userNew.setPassword("111");
		userNew.setEmail("vu@gmail.com");
		Comment commentNew = new Comment();
		commentNew.setComment("my name Vu");
		commentNew.setPostId(postRepository.getById(1L));
		commentNew.setUserId(userNew);
		commentRepository.save(commentNew);
		userRepository.save(userNew);
		Assertions.assertThat(userRepository.findAll().size()).isEqualTo(4);
		Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(5);
		Assertions.assertThat(postRepository.findById(1L).get().getComments().size()).isEqualTo(5);
	}

	@Test
	public void editUser(){
		User changeUser = userRepository.getById(2L);
		String changeName = "Chinh";
		changeUser.setName(changeName);
		userRepository.save(changeUser);

		Assertions.assertThat(userRepository.findById(2L).get().getName()).isEqualTo(changeName);
	}

	@Test
	public void deleteUser(){
		userRepository.deleteById(1L);
		Assertions.assertThat(userRepository.findById(1L).isEmpty()).isTrue();		
	}

}
