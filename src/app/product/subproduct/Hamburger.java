package app.product.subproduct;

import app.product.Product;

public class Hamburger extends Product {
    private boolean isBurgerSet;
    private int burgerSetPrice;

    public Hamburger(int id, String name, int price, int kcal, boolean isBurgerSet, int burgerSetPrice) {
        super(id, name, price, kcal);
        this.isBurgerSet = isBurgerSet;
        this.burgerSetPrice = burgerSetPrice;
    }


    // 새로운 인스턴스 생성 : 값 초기화 이슈로, 깊은 복사
    public Hamburger (Hamburger hamburger) {
        super(hamburger.getName(), hamburger.getPrice(), hamburger.getKcal());
        this.isBurgerSet = hamburger.isBurgerSet();
        this.burgerSetPrice = hamburger.getBurgerSetPrice();
    }




    public boolean isBurgerSet() {                                // 버거세트 유무 입력 메서드
        return isBurgerSet;
    }

    public void setIsBurgerSet(boolean isBurgerSet) {             // setter : (isBurgerSet 의 값을 매개변수) 로 한 setIsBurgerSet 값 (true or false) 변경 가능하게 만듦
        this.isBurgerSet = isBurgerSet;
    }

    public int getBurgerSetPrice() {
        return burgerSetPrice;
    }

    public void setBurgerSetPrice(int burgerSetPrice) {
        this.burgerSetPrice = burgerSetPrice;
    }
}




//    상품 상속 클래스
//    햄버거 : 세트 여부 , 세트 가격