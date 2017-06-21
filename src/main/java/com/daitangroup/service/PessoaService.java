package com.daitangroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daitangroup.domain.model.Pessoa;
import com.daitangroup.domain.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	public List<Pessoa> findAllByNome(String nome) {
		return pessoaRepository.findAllByNome(nome);
	}
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	public Pessoa createPessoa(Pessoa pessoa){
		return pessoaRepository.save(pessoa);
	}
	public void deletePessoa(Long id) {
		pessoaRepository.delete(id);
	}
	public Pessoa findPessoa(Long id) {
		return pessoaRepository.findOne(id);
	}

}
