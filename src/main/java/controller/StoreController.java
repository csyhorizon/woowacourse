package controller;

import com.sun.tools.javac.Main;
import common.OutputMessage;
import data.ProductsData;
import data.PromotionsData;
import java.io.InputStream;
import java.util.List;
import services.StoreService;
import view.InputView;
import view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final StoreService storeService;
    private final OutputView outputView;

    public StoreController(StoreService storeService) {
        this.inputView = new InputView();
        this.storeService = storeService;
        this.outputView = new OutputView();
    }

    public void run() {
        List<ProductsData> productsData = getProductsData();
        List<PromotionsData> promotionsData = getPromotionsData();

        String menuList = menuGiveOrder(productsData);
    }

    public String menuGiveOrder(List<ProductsData> productsData) {
        outputView.showPrompt(OutputMessage.INTRO_MESSAGE);
        outputView.skipLine();
        outputView.menuListPrint(productsData);
        outputView.skipLine();

        return inputView.getInput();
    }

    private List<ProductsData> getProductsData() {
        InputStream productsLink = Main.class.getClassLoader().getResourceAsStream("products.md");
        return storeService.generateProducts(productsLink);
    }

    private List<PromotionsData> getPromotionsData() {
        InputStream promotionsLink = Main.class.getClassLoader().getResourceAsStream("promotions.md");
        return storeService.generatePromotions(promotionsLink);
    }
}
