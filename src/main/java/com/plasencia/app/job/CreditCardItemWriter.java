package com.plasencia.app.job;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.plasencia.app.dao.ICreditCardRisk;
import com.plasencia.app.entity.CreditCardRisk;

import java.util.List;

/* 1.- Al igual que hicimos para el Reader, crearemos nuestro propio Writer a partir de la interfaz. 
 * El Writer recibirá la lista de CreditCardRisk que el Processor ha procesado para guardar el resultado 
 * en la base de datos.*/

public class CreditCardItemWriter implements ItemWriter<CreditCardRisk> {

    @Autowired
    private ICreditCardRisk creditCardRisk;

    @Override
    public void write(List<? extends CreditCardRisk> list) throws Exception {
    	creditCardRisk.saveAll(list);
    }
}
