package com.hotelaluralatam.modelo;

import java.time.LocalDate;

public class Reserva {
    private Integer id;
    private LocalDate fechaEntrada;
    private LocalDate  fechaSalida;
    private String valor;
    private String FormadePago; 
    private Huesped huesped;

    public Reserva(Integer id, LocalDate  fechaEntrada, LocalDate  fechaSalida, String valor, String FormadePago) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valor = valor;
        this.FormadePago = FormadePago;
    }

    public Reserva(Integer id, LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formadePago,
			Huesped huesped) {
		super();
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.FormadePago = formadePago;
		this.huesped = huesped;
	}

	public Reserva(LocalDate  fechaEntrada, LocalDate  fechaSalida, String valor, String FormadePago) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valor = valor;
        this.FormadePago = FormadePago;
    }
    

  

	public Reserva(int id, LocalDate fechaEntrada, LocalDate fechaSalida, String FormadePago, Huesped huesped2,
			double valor) {
		
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.FormadePago = FormadePago;
		this.huesped = huesped;
		this.valor = String.valueOf(valor);
		
		// TODO Auto-generatedvalor constructor stub
	}

	
	public Huesped getHuesped() {
		return huesped;
	}


	public void setHuesped(Huesped huesped) {
		this.huesped = huesped;
	}


	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate  getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate  fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate  getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate  fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFormaPago() {
        return FormadePago;
    }

    public void setFormaPago(String FormadePago) {
        this.FormadePago = FormadePago;
    }

    @Override
    public String toString() {
        return "Reserva [FormadePago=" + FormadePago + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida
                + ", id=" + id + ", valor=" + valor + "]";
    }
}
