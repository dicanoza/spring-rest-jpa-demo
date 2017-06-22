package com.daitangroup.controller;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daitangroup.domain.model.Pessoa;
import com.daitangroup.service.PessoaService;

@RestController
@RequestMapping("/v1/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	/**
	 *
	 * @param nome
	 *            used to filter {@link Pessoa#getNome()}
	 * @return List of {@link Pessoa}
	 */
	@RequestMapping(path = "", method = GET)
	public ResponseEntity<List<Pessoa>> getPessoasByName(@RequestParam(value = "nome", required = false) String nome) {
		if (nome == null || nome.isEmpty()) {
			return ok(pessoaService.findAll());
		}
		return ok(pessoaService.findAllByNome(nome));
	}

	/**
	 *
	 * @param id
	 *            of the {@link Pessoa} entity to be find
	 * @return an {@link Pessoa} or NotFound(404)
	 */
	@RequestMapping(path = "/{id}", method = GET)
	public ResponseEntity<Pessoa> getPessoa(@PathVariable("id") long id) {
		Pessoa pessoa = pessoaService.findPessoa(id);
		if (pessoa == null) {
			return ResponseEntity.notFound().build();

		}
		return ok(pessoa);
	}

	/**
	 *
	 * @param pessoa
	 *            Entity to be inserted into the database
	 * @return Headers with Created 201 if success
	 */
	@RequestMapping(method = POST)
	public ResponseEntity<Void> createPessoa(@RequestBody(required = true) Pessoa pessoa) {
		pessoa = pessoaService.createPessoa(pessoa);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
		return created(location).build();
	}

	/**
	 *
	 * @param id
	 *            of the {@link Person} to be deleted
	 * @return NoCotent 204 if success
	 */
	@RequestMapping(value = "/{id}", method = DELETE)
	public ResponseEntity<Void> deletePessoa(@PathVariable("id") Long id) {
		pessoaService.deletePessoa(id);
		return noContent().build();
	}

}
