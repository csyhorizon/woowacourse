package orinnetwork.javafilter8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orinnetwork.javafilter8.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> { }
