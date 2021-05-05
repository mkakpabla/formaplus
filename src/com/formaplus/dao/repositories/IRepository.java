package com.formaplus.dao.repositories;

import java.util.List;

public interface IRepository<T> {
	
	
	public List<T> GetAll();
	
	public T GetById(int id);
	
	public boolean Save(T obj);
	
	public boolean Delete(int id);

}
