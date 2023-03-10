package app;

import app.product.Product;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

public class Menu {
    private Product[] products;

    public Menu(Product[] products) {
        this.products = products;
    }

    public void printMenu() {
        System.out.println("[π»] λ©λ΄");
        System.out.println("-".repeat(60));

        printHamburgers();
        printSides();
        printDrinks();

        System.out.println();
        System.out.println("π§Ί (0) μ₯λ°κ΅¬λ");
        System.out.println("π¦ (+) μ£Όλ¬ΈνκΈ°");
        System.out.println("-".repeat(60));
        System.out.print("[π£] λ©λ΄λ₯Ό μ νν΄μ£ΌμΈμ : ");

    }



    //      printHamburgers(); printSides(); printDrinks(); λ¦¬νν λ§ - λ°λ³΅λλ λ©μλ λ¨μΆ


    protected void printDrinks() {
        System.out.println("π₯€ μλ£");
        for (Product product : products) {
            if (product instanceof Drink) {
                printEachMenu(" (%d) %s %5dKcal %5dμ\n", product);
            }
        }
        System.out.println();
    }

    protected void printSides() {
        System.out.println("π μ¬μ΄λ");
        for (Product product : products) {
            if (product instanceof Side) {
                printEachMenu(" (%d) %s %5dKcal %5dμ\n", product);
            }
        }
        System.out.println();
    }

    private void printHamburgers() {
        System.out.println("π νλ²κ±°");
        for (Product product : products) {
            if (product instanceof Hamburger) {
                printEachMenu(" (%d) %s %5dKcal %5dμ\n", product);
            }
        }

        System.out.println();
    }


    // printEachMenu λ©μλλ‘ λ°λ³΅λλ λ©μλ λ¦¬νν λ§

    private static void printEachMenu(String format, Product product) {
        System.out.printf(
                format,
                product.getId(), product.getName(), product.getKcal(), product.getPrice()
        );
    }


}

