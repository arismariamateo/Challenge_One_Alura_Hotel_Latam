package com.hotelaluralatam.modelo;

public class Valor {

	private double valorDia;

	public Valor() {
		
		this.valorDia = 500.00;
	}
	
	public double valorEstadia(long dias) {
		return this.valorDia*dias;
	}
}
