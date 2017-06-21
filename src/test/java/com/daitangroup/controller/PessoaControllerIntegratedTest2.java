package com.daitangroup.controller;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.daitangroup.SpringRestJpaDemoApplication;
import com.daitangroup.domain.model.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRestJpaDemoApplication.class)
@WebAppConfiguration
public class PessoaControllerIntegratedTest2 {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	String baseUri = "/v1/pessoa";

	@Test
	public void mockMvcCreateTest() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Pedro");
		pessoa.setCpf("123456");
		mvc.perform(post(baseUri).content(mapper.writeValueAsString(pessoa)).contentType(APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().string(LOCATION, containsString("/v1/pessoa/")));

	}

	@Test
	public void mockMvcGetTest() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Pedro");
		pessoa.setCpf("123456");

		mvc.perform(post(baseUri).content(mapper.writeValueAsString(pessoa)).contentType(APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().string(LOCATION, containsString("/v1/pessoa/")));

		mvc.perform(get(baseUri + "/1").accept(APPLICATION_JSON)).andExpect(jsonPath("id", anything()))
				.andExpect(jsonPath("nome", equalTo("Pedro"))).andExpect(jsonPath("cpf", equalTo("123456")));

	}

}
