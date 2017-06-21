package com.daitangroup.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daitangroup.domain.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("select u from Pessoa u where u.nome like %?1%")
	List<Pessoa> findAllByNome(String nome);

}
