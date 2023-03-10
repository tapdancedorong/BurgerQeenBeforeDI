package app;

import app.product.Product;
import app.product.ProductRepository;
import app.product.subproduct.BurgerSet;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

import java.util.Scanner;

public class Cart {
    private Product[] items = new Product[0];
    private Scanner scanner = new Scanner(System.in);

    private Menu menu;
    private ProductRepository productRepository;



    public Cart(Menu menu, ProductRepository productRepository) {
        this.menu = menu;
        this.productRepository = productRepository;

    }

    public void printCart() {
        System.out.println("๐งบ ์ฅ๋ฐ๊ตฌ๋");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("ํฉ๊ณ : %d์\n", calculateTotalPrice() );

        System.out.println("์ด์ ์ผ๋ก ๋์๊ฐ๋ ค๋ฉด ์ํฐ๋ฅผ ๋๋ฅด์ธ์.");
        scanner.nextLine();
    }



            // ์ฅ๋ฐ๊ตฌ๋ ์ํ๋ค์ ์ต์ ์ ๋ณด //
    protected void printCartItemDetails() {
        for (Product product : items) {
            if (product instanceof BurgerSet) {                 // items ๋ Product[] ํ์์ ๋ฐฐ์ด์ด์ง๋ง ๋ณธ๋ Hamburger, Side, Drink, BurgerSet ํ์์ ์ธ์คํด์ค
                BurgerSet burgerSet = (BurgerSet) product;      // ๋ฐ๋ผ์ Product์ ์์บ์คํ ๋ ๋ฐฐ์ด์ด๋ฏ๋ก ๋ค์ด์บ์คํ
                System.out.printf(
                        " %s %6d์ (%s(์ผ์ฒฉ %d๊ฐ), %s(๋นจ๋ %s))\n",
                        product.getName(),
                        product.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().hasStraw() ? "์์" : "์์"
                );
            }
            else if (product instanceof Hamburger) {
                System.out.printf(
                        " %-8s %6d์ (๋จํ)\n",
                        product.getName(), product.getPrice()
                );
            }
            else if (product instanceof Side) {
                System.out.printf(
                        " %-8s %6d์ (์บ์ฒฉ %d๊ฐ)\n",
                        product.getName(), product.getPrice(),
                        ((Side) product).getKetchup()
                );
            }
            else if (product instanceof Drink) {
                System.out.printf(
                        " %-8s %6d์ (๋นจ๋ %s)\n",
                        product.getName(), product.getPrice(),
                        ((Drink) product).hasStraw() ? "์์" : "์์"
                );

            }

        }
    }

            // ์ฅ๋ฐ๊ตฌ๋ ์ํ๋ค์ ๊ธ์ก ํฉ๊ณ ์ถ๋ ฅ
    protected int calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : items) {
            totalPrice = totalPrice + product.getPrice();
        }
        return totalPrice;
    }


            // ์ฅ๋ฐ๊ตฌ๋ ์ถ๊ฐ!  + choooseOption (productRepository ์ ์ ์ํ findbyid ๋ฉ์๋ ํธ์ถ)  - ์บก์ํ
    public void addToCart(int productId) {
        Product product = productRepository.findById(productId);


           // newProduct ์๋ก์ด ์ธ์คํด์ค ์์ฑ : ๊ฐ ์ด๊ธฐํ ์ด์๋ก, ๊น์ ๋ณต์ฌ
        Product newProduct;
        if (product instanceof Hamburger) newProduct = new Hamburger((Hamburger) product);
        else if (product instanceof Side) newProduct = new Side((Side) product);
        else newProduct = new Drink((Drink) product);


        chooseOption(newProduct);

        if (newProduct instanceof Hamburger){
            Hamburger hamburger = (Hamburger) newProduct;
            if ((hamburger.isBurgerSet())) newProduct = composeSet(hamburger);
        }


        Product[] newItems = new Product[items.length + 1];
        System.arraycopy(items, 0, newItems, 0, items.length);
        newItems[newItems.length - 1] = newProduct;
        items = newItems;

        System.out.printf("[๐ฃ] %s๋ฅผ(์) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค.\n", product.getName());
    }


           // ์ํ ์ต์ ์ค์  // chooseOption()

    private void chooseOption(Product product) {
        String input;

        if (product instanceof Hamburger) {
            System.out.printf(
                    "๋จํ์ผ๋ก ์ฃผ๋ฌธํ์๊ฒ ์ด์? (1)_๋จํ(%d)์ (2)_์ธํธ(%d)์",
                    product.getPrice(),
                    ((Hamburger) product).getBurgerSetPrice()                      // ํ๋ฒ๊ฑฐ ํ์์ผ๋ก ๋ค์ด์บ์คํ ํ Hamburger ํด๋์ค์ getBurgerSetPrice ๋ฉ์๋ ๊ฐ์ ธ์ด  = getter !
            );
            input = scanner.nextLine();
            if (input.equals("2"))
                ((Hamburger) product).setIsBurgerSet(true);    // 2๋ฒ(์ธํธ) ์ ํํ๋ค๋ฉด, setIsBurgerset ์ boolean ๊ฐ ture ๋ก ๋ณ๊ฒฝ
        }
        else if (product instanceof Side) {
            System.out.println("์ผ์ฒฉ์ ๋ช ๊ฐ๊ฐ ํ์์ ๊ฐ์?");
            input = scanner.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));                  // Side ํ์์ผ๋ก ๋ค์ด์บ์คํ ํ์ฌ setKetchup ๋ฉ์๋ ํธ์ถ (๊ฐ ์์ )
        }                                                                          // - > ์๋ ฅ๋ฐ์(input) String ํ์ ์ซ์ -> int ํ์ ์ซ์๋ก ๋ณํ
        else if (product instanceof Drink) {
            System.out.println("๋นจ๋๊ฐ ํ์ํ์ ๊ฐ์? (1)_์ (2)_์๋์ค");
            input = scanner.nextLine();
            if (input.equals("2")) ((Drink) product).setHasStraw(false);
        }
    }



    private BurgerSet composeSet(Hamburger hamburger) {

        System.out.println("์ฌ์ด๋๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์.");
        menu.printSides();

        // ์ฌ์ฉ์ ์๋ ฅ ๋ฐ๊ธฐ
        // sideId ๋ฅผ id ๋ก ๊ฐ์ง๋ ์ํ ๊ฒ์
        // ์ฌ์ด๋ ์ต์ ์ ํ
        String sideId = scanner.nextLine();
        Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
        Side newSide = new Side(side);  // ์๋ก์ด ์ธ์คํด์ค ์์ฑ : ๊ฐ ์ด๊ธฐํ ์ด์๋ก, ๊น์ ๋ณต์ฌ
        chooseOption(newSide);

        // ์๋ฃ๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์ ์ถ๋ ฅ
        // ์๋ฃ ๋ฉ๋ด ์ถ๋ ฅ
        System.out.println("์๋ฃ๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์");
        menu.printDrinks();


        // ์ฌ์ฉ์ ์๋ ฅ ๋ฐ๊ธฐ
        // drinkId ๋ฅผ id ๋ก ๊ฐ์ง๋ ์ํ ๊ฒ์
        // ๋๋งํฌ ์ต์ ์ ํ
        String drinkId = scanner.nextLine();
        Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
        Drink newDrink = new Drink(drink); // // ์๋ก์ด ์ธ์คํด์ค ์์ฑ : ๊ฐ ์ด๊ธฐํ ์ด์๋ก, ๊น์ ๋ณต์ฌ
        chooseOption(newDrink);



//        String name = hamburger์ ์ด๋ฆ + "์ธํธ";
//        int price = hamburger์ BurgerSetPriceํ๋์ ๊ฐ
//        int kcal = ํ๋ฒ๊ฑฐ, ์ฌ์ด๋, ๋๋งํฌ์ ์นผ๋ก๋ฆฌ ์ดํฉ
//        ๋ฆฌํด
        String name = hamburger.getName() + "์ธํธ";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();
        return new BurgerSet(name, price, kcal, hamburger, newSide, newDrink);





    }

}






//        if (product๊ฐ Hamburger ์ ์ธ์คํด์ค์ด๊ณ , isBurgerSet ๊ฐ true๋ผ๋ฉด) {
//            product = ์ธํธ ๊ตฌ์ฑ // composeSet();
//      }
//        items ์ product ์ถ๊ฐ
//
//        "[๐ฃ] XXXX๋ฅผ(์) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค." ์ถ๋ ฅ









//       ์ฅ๋ฐ๊ตฌ๋ ๋ด๊ธฐ โ addToCart()
//
//        ์ต์ ๊ณ ๋ฅด๊ฒ ํ๊ธฐ โ chooseOption()
//
//        ํ๋ฒ๊ฑฐ ์ธํธ ๊ตฌ์ฑํ๊ธฐ โ composeSet()
//
//        ์ฅ๋ฐ๊ตฌ๋ ์ถ๋ ฅํ๊ธฐ โ printCart()