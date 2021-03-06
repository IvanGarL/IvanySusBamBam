/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.ivanysusbambam.ejb;

import co.edu.uniandes.csw.ivanysusbambam.entities.VendedorEntity;
import co.edu.uniandes.csw.ivanysusbambam.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.ivanysusbambam.persistence.PuntoDeVentaPersistence;
import co.edu.uniandes.csw.ivanysusbambam.persistence.VendedorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Felipe Velásquez Montoya
 */
@Stateless
public class VendedorLogic {

    /**
     * Constante del logger
     */
    private static final Logger LOG = Logger.getLogger(VendedorLogic.class.getName());

    /**
     * Atributo para la persistencia del vendedor
     */
    @Inject
    private VendedorPersistence persistence;

    /**
     * Atributo para la persistencia del punto de venta
     */
    @Inject
    private PuntoDeVentaPersistence persistencePuntoVenta;

    /**
     * Método auxiliar para reducir complejidad ciclomática de createVendedor. Verificar las RN respectivas a la cédula del parámetro.
     * @param ve VendedorEntity de que se verificarán las RN.
     */
    private void verificarCedula(VendedorEntity ve) throws BusinessLogicException{
        Long cedula = ve.getCedula();
        cedulaValida(cedula);
        if (!persistence.findByCedula(cedula).isEmpty()) {
            throw new BusinessLogicException("Algún vendedor se encuentra registrado con la misma cédula");
        }
    }
    
    /**
     * Método auxiliar para reducir complejidad ciclomática de createVendedor. Verificar las RN respectivas al nombre del vendedor.
     * @param ve VendedorEntity de que se verificarán las RN.
     */
    private void verificarNombre(VendedorEntity ve) throws BusinessLogicException{
        String name = ve.getNombre();
        if (name == null || !esAlfabetica(name)) {
            throw new BusinessLogicException("Para el nombre sólo se aceptan cadenas alfabéticas con vocales con tildes o u con diéresis");
        }
    }
    
    /**
     * Método auxiliar para reducir complejidad ciclomática de createVendedor. Verificar las RN respectivas al punto de veenta del vendedor.
     * @param ve VendedorEntity de que se verificarán las RN.
     */
    private void verificarPuntoDeVenta(VendedorEntity ve)throws BusinessLogicException{
        
        if (ve.getPuntoDeVenta() == null) {
            throw new BusinessLogicException("El punto de venta no puede ser null");
        }
        Long idPuntoVenta = ve.getPuntoDeVenta().getId();
        if (idPuntoVenta == null || persistencePuntoVenta.find(idPuntoVenta) == null) {
            throw new BusinessLogicException("No existe el punto de venta al que se quiere registrar el vendedor.");
        }
        
    }
    
    /**
     * Método auxiliar para reducir complejidad ciclomática de createVendedor. Verificar las RN respectivas al carnet del vendedor.
     * @param ve VendedorEntity de que se verificarán las RN.
     */
    private void verificarCarnet(VendedorEntity ve) throws BusinessLogicException{
        Long carnet = ve.getCarnetVendedor();
        
        if (carnet == null || carnet <= 0) {
            throw new BusinessLogicException("El carnet del vendedor no puede ser negativo ni 0");
        }
        if (persistence.find(carnet) != null) {
            throw new BusinessLogicException("Ya existe un vendedor con el mismo número de carnet");
        }
        
    }
    
    /**
     * Revisa que se cumplan las reglas del negocio antes de añadir un vendedor.
     *
     * @param ve el VendedorEntity que se busca persistir.
     * @return el VendedorEntity que se persistió
     * @throws BusinessLogicException si se incumple alguna de las reglas del
     * negocio, es decir si se incumple alguna de las siguientes reglas:
     *          -La cédula es un número entero positivo único de hasta 10 dígitos y es única
     *          -El nombre es una cadena alfabética. Sólo contiene letras y espacios.
     *          -El vendedor debe estar asignado a un punto de venta que ya se encuentre registrado en la base de datos.
     *          -El carnet del vendedor es un entero positivo auto-asignado y único.
     */
    public VendedorEntity createVendedor(VendedorEntity ve) throws BusinessLogicException {
        if (ve == null) {
            throw new BusinessLogicException("El vendedor no puede ser null");
        }

        Long carnet = ve.getCarnetVendedor();
        LOG.log(Level.INFO, "Revisando si el vendedor: {0}cumple con los requisitos para ser persistido", carnet);
        
        verificarCedula(ve);
        
        verificarNombre(ve);
        
        verificarPuntoDeVenta(ve);
        
        verificarCarnet(ve);
        
        LOG.log(Level.INFO, "El vendedor: {0}cumple con los requisitos para ser persistido", carnet);

        return persistence.create(ve);
    }

    /**
     * Borra un vendedor de la BD.
     *
     * @param id carnet del vendedor que se quiere borrar
     * @return el vendedor que se borró.
     * @throws BusinessLogicException si el cedula que se pasó es null o si el
     * vendedor no existe.
     */
    public VendedorEntity deleteVendedor(Long id) throws BusinessLogicException {
        LOG.log(Level.INFO, "Borrando al vendedor: {0}", id);
        if (id == null) {
            throw new BusinessLogicException("No se puede eliminar un id null");
        }
        VendedorEntity ve = persistence.find(id);
        if (ve == null) {
            throw new BusinessLogicException("No se puede eliminar un vendedor que no existe");
        }
        return persistence.delete(id);
    }

    /**
     * Consulta todos los vendedores en la base de datos.
     *
     * @return una lista con todos los vendedores en la base de datos.
     */
    public List<VendedorEntity> findAllVendedores() {
        LOG.info("Recuperando todos los vendedores");
        return persistence.findAll();
    }

    /**
     * Busca un vendedor según su PK (el carnet de éste)
     *
     * @param carnet carnet del vendedor que se busca, null si no existe.
     * @return el vendedor que se buscaba.
     * @throws BusinessLogicException si carnet == null o si carnet <= 0.
     */
    public VendedorEntity findVendedor(Long carnet) throws BusinessLogicException {
        LOG.log(Level.INFO, "buscando vendedor con carnet: {0}", carnet);
        if (carnet == null) {
            throw new BusinessLogicException("No se puede buscar un id null");
        }
        if (carnet <= 0) {
            throw new BusinessLogicException("No hay carnet menores o iguales a 0");
        }
        return persistence.find(carnet);
    }

    /**
     * Encuentra un vendedor según su cédula.
     *
     * @param cedula cedula del vendedor que se busca.
     * @return el vendedor buscado, null si no exite.
     * @throws BusinessLogicException si la cédula dada por parámetro no está en
     * un formato válido o si se encontró más de un vendedor con la misma
     * cédula.
     */
    public VendedorEntity findVendedorByCedula(Long cedula) throws BusinessLogicException {
        LOG.log(Level.INFO, "buscando vendedor con cédula: {0}", cedula);
        cedulaValida(cedula);
        List<VendedorEntity> lista = persistence.findByCedula(cedula);
        if (lista.isEmpty()) {
            return null;
        }
        if (lista.size() > 1) {
            throw new BusinessLogicException("Error de consistencia en la base de datos, existen dos vendedores con la misma cedula");
        }
        return lista.get(0);
    }

    /**
     * Encuentra todos los vendedores con un nombre dado.
     *
     * @param name nombre que se busca consultar.
     * @return listo de todos los vendedores con el nombre dado, null si no hay
     * ninguno.
     * @throws BusinessLogicException si el nombre es null o no es alfabético.
     */
    public List<VendedorEntity> findVendedorByName(String name) throws BusinessLogicException {
        LOG.log(Level.INFO, "buscando vendedores con nombre: {0}", name);
        if (name == null) {
            throw new BusinessLogicException("El nombre no puede ser nulo");
        }
        if (!esAlfabetica(name)) {
            throw new BusinessLogicException("El nombre debe ser alfabético");
        }
        return persistence.findByName(name);
    }

    /**
     * Actualiza un vendedor pasado por parámetro.
     *
     * @param ve vendedor con la información actualizada.
     * @return vendedor actualizado.
     * @throws BusinessLogicException si se intentó cambiar la cédula del
     * vendedor o el nuevo nombre es null o no es una cadena alfabetica.
     */
    public VendedorEntity updateVendedor(VendedorEntity ve) throws BusinessLogicException {

        if (ve == null) {
            throw new BusinessLogicException("El vendedor no puede ser null");
        }
        LOG.log(Level.INFO, "actualizando vendedor con carnet: {0}", ve.getCarnetVendedor());
        if (ve.getCarnetVendedor() == null) {
            throw new BusinessLogicException("El carnet no puede ser igual a null");
        }
        VendedorEntity veo = persistence.find(ve.getCarnetVendedor());

        if (veo == null) {
            throw new BusinessLogicException("No existe el vendedor que se busca actualizar.");
        }
        if (!(ve.getCedula().equals(veo.getCedula()))) {
            throw new BusinessLogicException("No se puede cambiar la cedula de un vendedor.");
        }
        
        verificarNombre(ve);
        
        verificarPuntoDeVenta(ve);
        
        return persistence.update(ve);
    }

    /**
     * Verifica si una cédula es válida, es decir que tenga 10 dígitos o menos,
     * que no sea null, y que no sea negativa o cero.
     *
     * @param cedula cedula cuya validez se busca determinar.
     * @throws BusinessLogicException si la cédula no es válida.
     */
    protected static void cedulaValida(Long cedula) throws BusinessLogicException {
        if (cedula == null) {
            throw new BusinessLogicException("No se puee buscar una cédula null");
        }
        if (String.valueOf(cedula).length() > 10) {
            throw new BusinessLogicException("No se aceptan cédulas de más de 10 dígitos");
        }
        if (cedula <= 0) {
            throw new BusinessLogicException("No se acepta una cédula con valor <= 0");
        }
    }

    /**
     * Verifica que la cadena cadena pasada no tenga un caractér especial (DEL,
     * SOH, NULL, etc...)
     *
     * @param s la cadena que se busca verificar.
     * @return false si tiene un caracter especial, true de lo contrario.
     */
    protected static boolean esAlfabetica(String s) {
        char[] arreglo = s.toCharArray();
        for (char c : arreglo) {
            if ((c >= 0 && c <= 31) || (c == 127)) {
                return false;
            }
        }

        return true;

    }
}
