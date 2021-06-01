package com.systempro.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.systempro.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto (PagamentoComBoleto pagto, Date instantedoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instantedoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataPagamento(cal.getTime());
	}

}
