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
        System.out.println("🧺 장바구니");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("합계 : %d원\n", calculateTotalPrice() );

        System.out.println("이전으로 돌아가려면 엔터를 누르세요.");
        scanner.nextLine();
    }



            // 장바구니 상품들의 옵션 정보 //
    protected void printCartItemDetails() {
        for (Product product : items) {
            if (product instanceof BurgerSet) {                 // items 는 Product[] 타입의 배열이지만 본래 Hamburger, Side, Drink, BurgerSet 타입의 인스턴스
                BurgerSet burgerSet = (BurgerSet) product;      // 따라서 Product에 업캐스팅 된 배열이므로 다운캐스팅
                System.out.printf(
                        " %s %6d원 (%s(케첩 %d개), %s(빨대 %s))\n",
                        product.getName(),
                        product.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().hasStraw() ? "있음" : "없음"
                );
            }
            else if (product instanceof Hamburger) {
                System.out.printf(
                        " %-8s %6d원 (단품)\n",
                        product.getName(), product.getPrice()
                );
            }
            else if (product instanceof Side) {
                System.out.printf(
                        " %-8s %6d원 (캐첩 %d개)\n",
                        product.getName(), product.getPrice(),
                        ((Side) product).getKetchup()
                );
            }
            else if (product instanceof Drink) {
                System.out.printf(
                        " %-8s %6d원 (빨대 %s)\n",
                        product.getName(), product.getPrice(),
                        ((Drink) product).hasStraw() ? "있음" : "없음"
                );

            }

        }
    }

            // 장바구니 상품들의 금액 합계 출력
    protected int calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : items) {
            totalPrice = totalPrice + product.getPrice();
        }
        return totalPrice;
    }


            // 장바구니 추가!  + choooseOption (productRepository 에 정의한 findbyid 메서드 호출)  - 캡슐화
    public void addToCart(int productId) {
        Product product = productRepository.findById(productId);


           // newProduct 새로운 인스턴스 생성 : 값 초기화 이슈로, 깊은 복사
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

        System.out.printf("[📣] %s를(을) 장바구니에 담았습니다.\n", product.getName());
    }


           // 상품 옵션 설정 // chooseOption()

    private void chooseOption(Product product) {
        String input;

        if (product instanceof Hamburger) {
            System.out.printf(
                    "단품으로 주문하시겠어요? (1)_단품(%d)원 (2)_세트(%d)원",
                    product.getPrice(),
                    ((Hamburger) product).getBurgerSetPrice()                      // 햄버거 타입으로 다운캐스팅 후 Hamburger 클래스의 getBurgerSetPrice 메서드 가져옴  = getter !
            );
            input = scanner.nextLine();
            if (input.equals("2"))
                ((Hamburger) product).setIsBurgerSet(true);    // 2번(세트) 선택했다면, setIsBurgerset 의 boolean 값 ture 로 변경
        }
        else if (product instanceof Side) {
            System.out.println("케첩은 몇 개가 필요신가요?");
            input = scanner.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));                  // Side 타입으로 다운캐스팅 하여 setKetchup 메서드 호출 (값 수정)
        }                                                                          // - > 입력받은(input) String 타입 숫자 -> int 타입 숫자로 변환
        else if (product instanceof Drink) {
            System.out.println("빨대가 필요하신가요? (1)_예 (2)_아니오");
            input = scanner.nextLine();
            if (input.equals("2")) ((Drink) product).setHasStraw(false);
        }
    }



    private BurgerSet composeSet(Hamburger hamburger) {

        System.out.println("사이드를 골라주세요.");
        menu.printSides();

        // 사용자 입력 받기
        // sideId 를 id 로 가지는 상품 검색
        // 사이드 옵션 선택
        String sideId = scanner.nextLine();
        Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
        Side newSide = new Side(side);  // 새로운 인스턴스 생성 : 값 초기화 이슈로, 깊은 복사
        chooseOption(newSide);

        // 음료를 골라주세요 출력
        // 음료 메뉴 출력
        System.out.println("음료를 골라주세요");
        menu.printDrinks();


        // 사용자 입력 받기
        // drinkId 를 id 로 가지는 상품 검색
        // 드링크 옵션 선택
        String drinkId = scanner.nextLine();
        Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
        Drink newDrink = new Drink(drink); // // 새로운 인스턴스 생성 : 값 초기화 이슈로, 깊은 복사
        chooseOption(newDrink);



//        String name = hamburger의 이름 + "세트";
//        int price = hamburger의 BurgerSetPrice필드의 값
//        int kcal = 햄버거, 사이드, 드링크의 칼로리 총합
//        리턴
        String name = hamburger.getName() + "세트";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();
        return new BurgerSet(name, price, kcal, hamburger, newSide, newDrink);





    }

}






//        if (product가 Hamburger 의 인스턴스이고, isBurgerSet 가 true라면) {
//            product = 세트 구성 // composeSet();
//      }
//        items 에 product 추가
//
//        "[📣] XXXX를(을) 장바구니에 담았습니다." 출력









//       장바구니 담기 → addToCart()
//
//        옵션 고르게 하기 → chooseOption()
//
//        햄버거 세트 구성하기 → composeSet()
//
//        장바구니 출력하기 → printCart()