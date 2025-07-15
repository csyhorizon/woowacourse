package services;

import model.OrderItem;
import model.ProductsData;
import model.PromotionsData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<OrderItem> generateOrders(String ordersLink) {
        return Arrays.stream(ordersLink.replace("[", "").replace("]", "").split(","))
                .map(order -> new OrderItem(order.split("-")[0], Integer.parseInt(order.split("-")[1])))
                .collect(Collectors.toList());
    }
}
