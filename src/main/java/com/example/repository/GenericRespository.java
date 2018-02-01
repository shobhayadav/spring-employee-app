package com.example.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericRespository<T, S extends Serializable> {

	/* Method to create record*/
	T create(T t) ;

	/* Method to update record*/
	T update(T t) ;

	/* Method to delete record*/
	void delete(T t) ;

	/* Method to find record using primary key*/
	T findOne(Class<T> t, Long id) ;

	/* Method to find all records */
	List<T> findAll();

}