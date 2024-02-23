package com.prankishor.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	@Column(name = "post_title", length = 100, nullable = false)
	private String title;
	
	@Column(length=500)
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	//Which user posted 
	
	@ManyToOne
	private User user;
	//Posted in which category
	
	@ManyToOne
	private Category category;
	
	
	//mapped by jot diya tar id to jai taate eta column bonai
	//for eg etiya post or id eta comment or table ot column bonabo
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
	
}
