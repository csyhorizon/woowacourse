package controller;

import com.sun.tools.javac.Main;
import common.InputMessage;
import common.OutputMessage;
import model.OrderItem;
import model.ProductsData;
import model.PromotionsData;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import services.StoreService;
import validator.Inputvalidator;
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

        while (true) {
            List<OrderItem> menuList = menuGiveOrder(productsData);



            if (menuBuyServiceEnd()) break;
        }

        // 프로모션 확인
        // 멤버십 할인
        // 출력 안내
    }

    private boolean menuBuyServiceEnd() {
        return getCheckInput();
    }

    private void menuGive(List<ProductsData> productsData) {
        outputView.showPrompt(OutputMessage.INTRO_MESSAGE);
        outputView.skipLine();
        outputView.menuListPrint(productsData);
        outputView.skipLine();
    }

    private List<OrderItem> menuGiveOrder(List<ProductsData> productsData) {
        menuGive(productsData);
        outputView.showPrompt(InputMessage.USER_PURCHASE_PRODUCT);
        return inputOrderItem();
    }

    private List<OrderItem> inputOrderItem() {
        String inputOrderItems = getInput(Inputvalidator::validateProductsCheck);
        return storeService.generateOrders(inputOrderItems);
    }

    private boolean getCheckInput() {
        while (true) {
            try {
                return inputView.getCheckInput();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String getInput(Consumer<String> validator) {
        while(true) {
            try {
                String input = inputView.getInput();
                validator.accept(input);

                return input;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
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
