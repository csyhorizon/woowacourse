package services;

import data.ProductsData;
import data.PromotionsData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class StoreService {
    public List<ProductsData> generateProducts(InputStream productsLink) {
        try (BufferedReader productsFile = new BufferedReader(new InputStreamReader(productsLink))) {
            productsFile.readLine();
            return productsFile.lines()
                    .map(ProductsData::of)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PromotionsData> generatePromotions(InputStream promotionsLink) {
        try (BufferedReader promotionsFile = new BufferedReader(new InputStreamReader(promotionsLink))) {
            promotionsFile.readLine();
            return promotionsFile.lines()
                    .map(PromotionsData::of)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
