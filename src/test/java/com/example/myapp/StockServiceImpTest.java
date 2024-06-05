import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.devops_project.entities.Stock;

import java.util.ArrayList;
import java.util.List;

class StockServiceImplTest {

    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        // Initialisez votre service avant chaque test
        stockService = new StockServiceImpl(new MockStockRepository());
    }

    @Test
    void testSearchStockByName() {
        // Ajoutez quelques stocks à la base de données
        Stock stock1 = new Stock(1L, "Product A", 10);
        Stock stock2 = new Stock(2L, "Product B", 15);
        Stock stock3 = new Stock(3L, "Another Product", 20);
        stockService.addStock(stock1);
        stockService.addStock(stock2);
        stockService.addStock(stock3);

        // Recherchez les stocks par nom
        List<Stock> foundStocks = stockService.searchStockByName("Product");

        // Assurez-vous que les stocks contenant "Product" dans leur nom sont retournés
        Assertions.assertEquals(2, foundStocks.size());
        Assertions.assertTrue(foundStocks.contains(stock1));
        Assertions.assertTrue(foundStocks.contains(stock2));
    }

    // Implémentez une classe MockStockRepository pour simuler le comportement du repository
    private static class MockStockRepository implements StockRepository {
        private List<Stock> stocks = new ArrayList<>();

        @Override
        public <S extends Stock> S save(S entity) {
            stocks.add(entity);
            return entity;
        }

        @Override
        public Optional<Stock> findById(Long aLong) {
            return stocks.stream()
                    .filter(stock -> stock.getId().equals(aLong))
                    .findFirst();
        }

        @Override
        public List<Stock> findAll() {
            return stocks;
        }

        @Override
        public List<Stock> findByNameContainingIgnoreCase(String name) {
            return stocks.stream()
                    .filter(stock -> stock.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }
}
