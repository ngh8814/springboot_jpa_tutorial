package com.spring.tutorial.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tutorial.dto.BoardDTO;
import com.spring.tutorial.dto.BoardListDTO;
import com.spring.tutorial.service.BoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardControllerTest {

	@Autowired private WebApplicationContext context;
	@Autowired private BoardService boardService;
	
	private MockMvc mockMvc;
	private ObjectMapper om = new ObjectMapper();
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void A_insertTest() throws Exception {
		
		BoardDTO model = new BoardDTO();
		model.setTitle("테스트 제목1");
		model.setContent("테스트 내용1");
		model.setRegUserId("admin");
		
		mockMvc.perform(post("/board")
				.content(om.writeValueAsString(model))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isCreated());
		
	}
	
	@Test
	public void B_listTest() throws Exception {
		mockMvc.perform(get("/board"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void C_viewTest() throws Exception {
		
		BoardListDTO search = new BoardListDTO();
		search.setTitle("테스트 제목1");
		
		BoardListDTO list = boardService.list(search);
		
		long id = list.getList().get(0).getId();
		
		mockMvc.perform(get("/board/" + id))
			.andExpect(status().isOk());
		
		
		mockMvc.perform(get("/board/9999"))
			.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void D_updateTest() throws Exception {
		
		BoardListDTO search = new BoardListDTO();
		search.setTitle("테스트 제목1");
		
		BoardListDTO list = boardService.list(search);
		
		long id = list.getList().get(0).getId();
		
		BoardDTO model = new BoardDTO();
		model.setTitle("테스트 제목1 수정");
		model.setContent("테스트 내용1 수정");
		model.setEditUserId("admin");
		
		mockMvc.perform(put("/board/" + id)
				.content(om.writeValueAsString(model))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void E_deleteTest() throws Exception {
		
		BoardListDTO search = new BoardListDTO();
		search.setTitle("테스트 제목1 수정");
		
		BoardListDTO list = boardService.list(search);
		
		long id = list.getList().get(0).getId();
		
		mockMvc.perform(delete("/board/" + id))
			.andExpect(status().isOk());
		
		mockMvc.perform(delete("/board/9999"))
			.andExpect(status().isNotFound());
		
	}
}
