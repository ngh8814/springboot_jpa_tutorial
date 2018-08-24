package com.spring.tutorial.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column
	private String content;
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime regDatetime;
	
	@Column(nullable=false)
	private String regUserId;
	
	@UpdateTimestamp
	private LocalDateTime editDatetime;
	
	@Column
	private String editUserId;
	
	@Builder
	private Board(long id, String title, String content, 
			String regUserId, String editUserId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.regUserId = regUserId;
		this.editUserId = editUserId;
	}
	
}
