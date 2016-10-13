package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by zenith on 10/6/16.
 */
public class Products extends Controller {

    static EntityManagerFactory emf;

    // creating entity manager factory
    private static EntityManagerFactory getEmf()
    {
        if (emf == null)
        {
            emf = Persistence.createEntityManagerFactory("cassandra_pu");
        }
        return emf;
    }

    // list all products
    public static Result index() {
        EntityManager em = getEmf().createEntityManager();
        List<Product> products = em.createQuery("SELECT p from Product p").getResultList();
        em.close();
        JsonNode json = Json.toJson(products);
        return ok(json);
    }

    // show a product details
    public static Result show(Integer id) {
        EntityManager em = getEmf().createEntityManager();
        Product product = em.find(Product.class, id);
        em.close();
        JsonNode json = Json.toJson(product);
        return ok(json);
    }

    // create a product
    public static Result create() {
        EntityManager em = getEmf().createEntityManager();
        JsonNode json = request().body().asJson();
        Product product = Json.fromJson(json, Product.class);
        em.persist(product);
        em.close();
        JsonNode json1 = Json.toJson(product);
        return ok(json1);
    }

    // update a product
    public static Result update(Integer id){
        EntityManager em = getEmf().createEntityManager();
        Product product = em.find(Product.class, id);
        JsonNode json = request().body().asJson();
        Product product1 = Json.fromJson(json, Product.class);
        product.setName(product1.name);
        product.setDescription(product1.description);
        em.merge(product1);
        product = em.find(Product.class, id);
        JsonNode json1 = Json.toJson(product);
        return ok(json1);
    }

    // delete a product
    public static Result delete(Integer id){
        EntityManager em = getEmf().createEntityManager();
        Product product = em.find(Product.class, id);
        JsonNode json = Json.toJson(product);
        em.remove(product);
        return ok("Record deleted" + json);
    }
}
