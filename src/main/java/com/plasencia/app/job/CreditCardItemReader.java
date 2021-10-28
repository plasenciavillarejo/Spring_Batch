package com.plasencia.app.job;

import java.util.Iterator;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;


import com.plasencia.app.dao.ICreditCard;
import com.plasencia.app.entity.CreditCard;


/* 1.- Creamos una interfaz implementando la principal ItemReader. */
public class CreditCardItemReader  implements ItemReader<CreditCard>{

	@Autowired
	private ICreditCard creditCard;
	
	private Iterator<CreditCard> usersIterator;
	
	/* 2.- Con la anotación BeforeStep realizamos una operación de lectura sobre la 
	 * base de datos previo a iniciar el Reader.*/
	@BeforeStep
    public void before(StepExecution stepExecution) {
        usersIterator = creditCard.findAll().iterator();
    }

	
	/* 3.- El método read() irá entregando cada ítem de la lista al Processor */ 
    @Override
    public CreditCard read() {
        if (usersIterator != null && usersIterator.hasNext()) {
            return usersIterator.next();
        } else {
            return null;
        }
    }
	
	
	
}
