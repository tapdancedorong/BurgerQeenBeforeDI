package app.product.subproduct;

import app.product.Product;

public class BurgerSet extends Product {

    private Hamburger hamburger;
    private Side side;
    private Drink drink;

    public BurgerSet(String name, int price, int kcal, Hamburger hamburger, Side side, Drink drink) {
        super(name, price, kcal);
        this.hamburger = hamburger;
        this.side = side;
        this.drink = drink;
    }

    public Hamburger getHamburger() {           // getter 로 값을 가져오고, 포함 관계 위의 클래스 생성자를 가져와 세트 구성. 따라서 getter 만
        return hamburger;
    }

    public Side getSide() {
        return side;
    }

    public Drink getDrink() {
        return drink;
    }
}





//    햄버거 세트 : 포함관계 - 사이드, 음료