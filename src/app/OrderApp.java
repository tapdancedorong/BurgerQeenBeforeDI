package app;

import app.product.Product;
import app.product.ProductRepository;

import java.util.Scanner;


public class OrderApp {

    private ProductRepository productRepository;
    private Menu menu;
    private Cart cart;

    public OrderApp(ProductRepository productRepository, Menu menu, Cart cart) {
        this.productRepository = productRepository;
        this.menu = menu;
        this.cart = cart;
    }

    public OrderApp() {

    }

    public void start() {

        Scanner scanner = new Scanner(System.in);

        ProductRepository productRepository = new ProductRepository();
        Product[] products = productRepository.getAllProducts();
        Menu menu = new Menu(products);

        Cart cart = new Cart(menu, productRepository);

        Order order = new Order(cart);

        System.out.println("π BurgerQueen Order Service");

        while (true) {
            menu.printMenu();
            String input = scanner.nextLine();

            if (input.equals("+")) {
                order.makeOrder();   // new order(cart) μμ±μ μμ±
                break;
            }
            else {
                int menuNumber = Integer.parseInt(input);

                if (menuNumber == 0) cart.printCart();


//                (μ¬μ©μ μλ ₯μ΄ 1λΆν° λ©λ΄ λ§μ§λ§ λ²νΈμ ν΄λΉνλ κ²½μ°)
//                μ¬μ©μκ° κ³ λ₯Έ μνμ μ΅μμ λ³΄μ¬μ£Όκ³  κ³ λ₯΄κ² ν ν, μ₯λ°κ΅¬λμ λ΄κΈ°
                else if (1 <= menuNumber && menuNumber <= products.length) cart.addToCart(menuNumber);

            }
        }

    }
}







