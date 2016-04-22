package com.controldefault.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static String message;
    private static String messageErr;

    static {
        try {
        	// Criar a SessionFactory 
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            message = "INFO CONTROL: GOOD: HibernateUtil: Criado o SessionFactory";
        } catch (Throwable ex) {
        	// Make sure you log the exception, as it might be swallowed
        	message = "INFO CONTROL: ERRO: HibernateUtil: Problema ao criar o SessionFactory";
            messageErr = "Criação da SessionFactory inicial falhou - " + ex;
            throw new ExceptionInInitializerError(ex);
        } finally {
        	System.out.println(message);
        	System.err.println(messageErr);
        }
    }

    public static SessionFactory getSessionFactory() {
    	System.out.println("INFO CONTROL: INFO: HibernateUtil: Pegando o SessionFactory");
        return sessionFactory;
    }
}