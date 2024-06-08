package com.example.myapp;



import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductTestJunitExample {


    private ProductRepository productRepository;


    private StockRepository stockRepository;


    private ProductServiceImpl productService;

    private Product product;
    private Stock stock;

    @BeforeEach
    public void setUp() {
        stock = new Stock();
        stock.setTitle("Test Stock");

        // Save stock to the in-memory database
        stock = stockRepository.save(stock);

        product = new Product();
        product.setTitle("Test Product");
        product.setPrice(10.0f);
        product.setQuantity(100);
        product.setStock(stock);
    }

    @Test
    @Transactional
    @Rollback
    public void testAddProduct_Success() {
        // Act
        Product result = productService.addProduct(product, stock.getIdStock());

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdProduct());
        assertEquals("Test Product", result.getTitle());
        assertEquals(10.0f, result.getPrice());
        assertEquals(100, result.getQuantity());
        assertEquals(stock, result.getStock());

        Product fetchedProduct = productRepository.findById(result.getIdProduct()).orElse(null);
        assertNotNull(fetchedProduct);
        assertEquals("Test Product", fetchedProduct.getTitle());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddProduct_StockNotFound() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            productService.addProduct(product, -1L);
        });


    }
}
