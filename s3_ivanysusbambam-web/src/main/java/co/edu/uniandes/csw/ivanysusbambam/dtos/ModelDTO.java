package co.edu.uniandes.csw.ivanysusbambam.dtos;

import co.edu.uniandes.csw.ivanysusbambam.entities.ModelEntity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Joseph Ortíz Moreno
 */
public class ModelDTO {

    /**
     * id del modelo
     */
    private Long id;

    /**
     * nombre del modelo
     */
    private String name;

    /**
     * Representa el numero de puertas del vehículo
     */
    private Integer numeroPuertas;
    /**
     * Representa la transmisión del vehículo
     */
    private String transmision;
    /**
     * Representa el cilindraje del vehículo
     */
    private Integer cilindraje;
    /**
     * Representa los centímetros cúbicos
     */
    private Double centCubicos;

    /**
     * Método constructor de la clase
     */
    public ModelDTO() {
        //Constructor utilizado por JAX
    }

    /**
     * Método constructor que recibe una entidad de modelo
     *
     * @param enti Entidad de modelo recibida
     */
    public ModelDTO(ModelEntity enti) {
        if (enti != null) {
            this.centCubicos = enti.getCentCubicos();
            this.cilindraje = enti.getCilindraje();
            this.numeroPuertas = enti.getNumeroPuertas();
            this.transmision = enti.getTransmision();
            this.id = enti.getId();
            this.name = enti.getName();
        }
    }

    /**
     * Convierte el DTO en una entity
     *
     * @return Retorna la entity creada
     */
    public ModelEntity toEntity() {
        ModelEntity nueva = new ModelEntity();
        nueva.setCentCubicos(this.getCentCubicos());
        nueva.setCilindraje(this.getCilindraje());
        nueva.setNumeroPuertas(this.getNumeroPuertas());
        nueva.setTransmision(this.getTransmision());
        nueva.setId(this.getId());
        nueva.setName(this.getName());
        return nueva;
    }

    /**
     * @return el id del modelo 
     */
    public Long getId() {
        return id;
    }

    /**
     * Seta el id del modelo al dado por parametro
     * @param id id del modelo
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el numero de puertas
     *
     * @return Numero de puertas
     */
    public Integer getNumeroPuertas() {
        return numeroPuertas;
    }

    /**
     * Retorna la transmision
     *
     * @return Tipo de transmision
     */
    public String getTransmision() {
        return transmision;
    }

    /**
     * Retorna el cilindraje
     *
     * @return Cilindraje
     */
    public Integer getCilindraje() {
        return cilindraje;
    }

    /**
     * Retorna los centimetros Cubicos
     *
     * @return Centimetros Cubicos
     */
    public Double getCentCubicos() {
        return centCubicos;
    }

    /**
     * Actualiza el número de puertas
     *
     * @param pu Nuevo número de puertas
     */
    public void setNumeroPuertas(Integer pu) {
        numeroPuertas = pu;
    }

    /**
     * Actualiza la transmisión
     *
     * @param nueva Nuevo tipo de transmisión
     */
    public void setTransmision(String nueva) {
        transmision = nueva;
    }

    /**
     * Actualiza el cilindraje
     *
     * @param nCil Nuevo cilindraje
     */
    public void setCilindraje(Integer nCil) {
        cilindraje = nCil;
    }

    /**
     * Actualiza los centimetros cubicos
     *
     * @param nueCent Nuevos Centimetros Cubicos
     */
    public void setCentCubicos(Double nueCent) {
        centCubicos = nueCent;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
