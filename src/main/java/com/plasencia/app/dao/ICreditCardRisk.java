package com.plasencia.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plasencia.app.entity.CreditCardRisk;

@Repository
public interface ICreditCardRisk extends JpaRepository<CreditCardRisk, Long>{

}
