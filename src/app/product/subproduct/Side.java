package app.product.subproduct;

import app.product.Product;
import app.product.subproduct.Hamburger;

public class Side extends Product {
    private int ketchup;

    public Side(int id, String name, int price, int kcal, int ketchup) {
        super(id, name, price, kcal);
        this.ketchup = ketchup;
    }


    // 새로운 인스턴스 생성 : 값 초기화 이슈로, 깊은 복사
    public Side (Side side) {
        super(side.getName(), side.getPrice(), side.getKcal());
        this.ketchup = side.getKetchup();
    }

    public int getKetchup() {
        return ketchup;
    }

    public void setKetchup(int ketchup) {
        this.ketchup = ketchup;
    }
}

