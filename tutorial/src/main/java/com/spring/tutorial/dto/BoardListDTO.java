package com.spring.tutorial.dto;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.spring.tutorial.domain.Board;

import lombok.Data;

@Data
public class BoardListDTO {

	private List<Board> list;
	private int page;
	private int pageSize;
	private long totalCount;
	
	private String title;
	private String content;
	
	public PageRequest toPage() {
		
		int page = 0;
		int pageSize = 10;
		
		if (this.page > 0) {
			page = this.page - 1;
		}
		
		if (this.pageSize > 0) {
			pageSize = this.pageSize;
		}
		
		return PageRequest.of(page, pageSize, new Sort(Direction.DESC, "id"));
	}
	
	public Specification<Board> toSpecification() {
		
		Specification<Board> title = null;
		Specification<Board> content = null;
		
		if (this.title != null && !this.title.isEmpty()) {
			title = Specification.where((root, query, builder) -> 
						builder.like(root.<String> get("title"), "%" + this.title + "%"));
		}
		
		if (this.content != null && !this.content.isEmpty()) {
			content = Specification.where((root, query, builder) -> 
						builder.like(root.<String> get("content"), "%" + this.content + "%"));
		}
		
		return Specifications.where(title).and(content);
	}
	
}
