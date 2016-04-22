package com.controldefault.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import com.controldefault.model.UserVO;
import com.controldefault.model.RoleVO;
import com.controldefault.util.HibernateUtil;

public class GenericDAO {
	private final Class classe;
	public static Session session;

	private static final String MESSAGE_ERRO_BANCO = "Erro relacionado a banco de dados. ";
	private static final String MESSAGE_ERRO_CAMPOS = "Erro: campos duplicados ou nulos. ";
	String messageErr;

	public GenericDAO(Class classe) {
		this.classe = classe;		
	}

	public Session getSession() {
		return session;
	}

	public boolean saveUpdate(Object pObj) throws Exception {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(pObj);
			session.getTransaction().commit();			
			return true;
		} catch (ConstraintViolationException c) {			
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_CAMPOS + c.getMessage();
			throw new Exception(c.getMessage(), c);
		} catch (JDBCConnectionException jdbc) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_BANCO + jdbc.getStackTrace();
			throw new Exception(jdbc.getMessage(), jdbc);
		}// fim catch
		finally {
			System.err.println(messageErr);
			this.getSession().close();
		}
	}

	public boolean remove(Object pObj) throws Exception {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(pObj);
			session.getTransaction().commit();
			return true;
		} catch (ConstraintViolationException c) {
			messageErr = MESSAGE_ERRO_CAMPOS + c.getMessage();
			session.getTransaction().rollback();
			throw new Exception(c.getMessage(), c);
		} catch (JDBCConnectionException jdbc) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_BANCO + jdbc.getStackTrace();
			throw new Exception(jdbc.getMessage(), jdbc);
		}// fim catch
		finally {
			System.err.println(messageErr);
			this.getSession().close();
		}
	}

	public List<Object> listAll(String pQuery) throws Exception {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			List<Object> listObject = session.createQuery(pQuery).list();
			session.getTransaction().commit();
			return listObject;
		} catch (ConstraintViolationException c) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_CAMPOS + c.getMessage();			
			throw new Exception(c.getMessage(), c);
		} catch (JDBCConnectionException jdbc) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_BANCO + jdbc.getStackTrace();
			throw new Exception(jdbc.getMessage(), jdbc);
		}// fim catch
		finally {			
			System.err.println(messageErr);
			this.getSession().close();			
		}
	}

	public Object listById(int pId) throws Exception {
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Object obj = session.get(classe, pId);
			return obj;
		} catch (ConstraintViolationException c) {
			messageErr = MESSAGE_ERRO_CAMPOS + c.getMessage();
			session.getTransaction().rollback();
			throw new Exception(c.getMessage(), c);
		} catch (JDBCConnectionException jdbc) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_BANCO + jdbc.getStackTrace();
			throw new Exception(jdbc.getMessage(), jdbc);
		}// fim catch
		finally {
			System.err.println(messageErr);
			this.getSession().close();
		}
	}

	public List<Object> listLike(String pName, String pValue) throws Exception {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(classe).add(Restrictions.like(pName, "%" + pValue + "%"));
			List<Object> obj = criteria.list();
			return obj;
		} catch (ConstraintViolationException c) {
			messageErr = MESSAGE_ERRO_CAMPOS + c.getMessage();
			session.getTransaction().rollback();
			throw new Exception(c.getMessage(), c);
		} catch (JDBCConnectionException jdbc) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_BANCO + jdbc.getStackTrace();
			throw new Exception(jdbc.getMessage(), jdbc);
		}// fim catch
		finally {
			System.err.println(messageErr);
			this.getSession().close();
		}
	}

	public List<Object> listByName(String pName, String pValue) throws Exception {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(classe).add(Restrictions.eq(pName, pValue));
			List<Object> obj = criteria.list();
			return obj;
		} catch (ConstraintViolationException c) {
			messageErr = MESSAGE_ERRO_CAMPOS + c.getMessage();
			session.getTransaction().rollback();
			throw new Exception(c.getMessage(), c);
		} catch (JDBCConnectionException jdbc) {
			session.getTransaction().rollback();
			messageErr = MESSAGE_ERRO_BANCO + jdbc.getStackTrace();
			throw new Exception(jdbc.getMessage(), jdbc);
		}// fim catch
		finally {
			System.err.println(messageErr);
			this.getSession().close();
		}
	}

}