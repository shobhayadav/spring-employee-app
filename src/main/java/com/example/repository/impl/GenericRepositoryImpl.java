package com.example.repository.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericRepositoryImpl<T , S extends Serializable> {

	@PersistenceContext
	protected EntityManager em;
	
	/* Method to create record*/
	public T create(T t) {
		em.persist(t);
		em.flush();
		return t;
	}

	/* Method to update record*/
	public void delete(T t) {
		em.remove(t);
		em.flush();
		
	}

	/* Method to delete record*/
	public T update(T t) {
		 em.merge(t);
		 em.flush();
		 return t;
	}

	
}
