package view;

import common.InputMessage;
import common.OutputMessage;
import data.ProductsData;
import java.util.List;

public class OutputView {
    public void showPrompt(InputMessage message) {
        System.out.println(message.getMessage());
    }
    public void showPrompt(OutputMessage message) {
        System.out.println(message.getMessage());
    }

    public void skipLine() {
        System.out.println();
    }

    public void menuListPrint(List<ProductsData> productsData) {
        for(ProductsData productData : productsData) {
            System.out.println(productData.getMenuData());
        }
    }

    public void printErrorMessage(String message) {
        System.err.println(message);
    }
}
