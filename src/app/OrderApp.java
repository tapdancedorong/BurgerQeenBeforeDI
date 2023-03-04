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

        System.out.println("🍔 BurgerQueen Order Service");

        while (true) {
            menu.printMenu();
            String input = scanner.nextLine();

            if (input.equals("+")) {
                order.makeOrder();   // new order(cart) 생성자 생성
                break;
            }
            else {
                int menuNumber = Integer.parseInt(input);

                if (menuNumber == 0) cart.printCart();


//                (사용자 입력이 1부터 메뉴 마지막 번호에 해당하는 경우)
//                사용자가 고른 상품의 옵션을 보여주고 고르게 한 후, 장바구니에 담기
                else if (1 <= menuNumber && menuNumber <= products.length) cart.addToCart(menuNumber);

            }
        }

    }
}







