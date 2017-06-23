package com.daitangroup.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.daitangroup.domain.model.Pessoa;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PessoaControllerIntegratedTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void restTemplateTest() {
		// test data
		Pessoa p = new Pessoa();
		p.setNome("Pedro");
		p.setCpf("123456");

		// creating a person
		ResponseEntity<Void> createPessoa = this.restTemplate.postForEntity("/v1/pessoa", p, Void.class);
		assertEquals(CREATED, createPessoa.getStatusCode());
		assertNotNull(createPessoa.getHeaders().getLocation());
		assertTrue(createPessoa.getHeaders().getLocation().toString().contains("/v1/pessoa"));

		// checking for the person that was just created
		ResponseEntity<Pessoa> findPessoa = this.restTemplate.getForEntity(createPessoa.getHeaders().getLocation(),
				Pessoa.class);
		assertEquals(OK, findPessoa.getStatusCode());
		assertEquals(p.getNome(), findPessoa.getBody().getNome());
	}

}
