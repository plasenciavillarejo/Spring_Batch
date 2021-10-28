package com.plasencia.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plasencia.app.entity.CreditCard;

@Repository
public interface ICreditCard extends JpaRepository<CreditCard, Long>{

}
