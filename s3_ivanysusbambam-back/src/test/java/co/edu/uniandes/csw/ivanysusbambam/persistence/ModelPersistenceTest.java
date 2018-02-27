/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.ivanysusbambam.persistence;

import co.edu.uniandes.csw.ivanysusbambam.entities.ModelEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Joseph Ortíz Moreno
 */
@RunWith(Arquillian.class)
public class ModelPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ModelEntity.class.getPackage())
                .addPackage(ModelPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Inject
    private ModelPersistence modelPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas de la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ModelEntity").executeUpdate();
    }

    /**
     * Representa las primeras instancias de los datos a probar
     */
    private List<ModelEntity> data = new ArrayList<ModelEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ModelEntity entity = factory.manufacturePojo(ModelEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Modelo.
     */
    @Test
    public void createModelTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ModelEntity newEntity = factory.manufacturePojo(ModelEntity.class);
        ModelEntity result = modelPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ModelEntity entity = em.find(ModelEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Modelos
     */
    @Test
    public void getModelsTest() {
        List<ModelEntity> list = modelPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ModelEntity ent : list) {
            boolean found = false;
            for (ModelEntity entity : data) {
                if (ent.getCilindraje().equals(entity.getCilindraje())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Modelo segun su cilindraje
     */
    @Test
    public void getModelTest() {
        ModelEntity entity = data.get(0);
        ModelEntity newEntity = modelPersistence.find(entity.getCilindraje());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroPuertas(), newEntity.getNumeroPuertas());
        Assert.assertEquals(entity.getCentCubicos(), newEntity.getCentCubicos());
        Assert.assertEquals(entity.getTransmision(), newEntity.getTransmision());
        Assert.assertEquals(entity.getAutomoviles(), newEntity.getAutomoviles());
    }
    
       /**
     * Prueba para consultar la lista de Modelos segun el numero de puertas
    
    @Test
    public void getModelsPuertasTest() {
        List<ModelEntity> list = modelPersistence.findByPuertas(Integer.SIZE);
        Assert.assertEquals(data.size(), list.size());
        for (ModelEntity ent : list) {
            boolean found = false;
            for (ModelEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    *  */
    
     /**
     * Prueba para eliminar un Modelo
     */
    @Test
    public void deleteModelTest() {
        ModelEntity entity = data.get(0);
        modelPersistence.delete(entity.getCilindraje());
        ModelEntity deleted = em.find(ModelEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Author.
     */
    @Test
    public void updateModelTest() {
        ModelEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ModelEntity newEntity = factory.manufacturePojo(ModelEntity.class);

        newEntity.setCilindraje(entity.getCilindraje());

        modelPersistence.update(newEntity);

        ModelEntity resp = em.find(ModelEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCilindraje(),entity.getCilindraje());
    }
    
    
}
