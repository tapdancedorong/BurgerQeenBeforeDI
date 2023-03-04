package app;

import app.discount.discountCondition.CozDiscountCondition;
import app.discount.discountCondition.KidDiscountCondition;

public class Order {
    private Cart cart;

    public Order(Cart cart) {
        this.cart = cart;
    }

    public void makeOrder() {

        CozDiscountCondition cozDiscountCondition = new CozDiscountCondition();
        KidDiscountCondition kidDiscountCondition = new KidDiscountCondition();

        cozDiscountCondition.checkDiscountCondition();
        kidDiscountCondition.checkDiscountCondition();
        // 할인 조건 생성자 및 호출 메서드

        int totalPrice = cart.calculateTotalPrice();

        int finalPrice = totalPrice;

        if (cozDiscountCondition.isSatisfied()) finalPrice = cozDiscountCondition.applyDiscount(finalPrice);
        if (kidDiscountCondition.isSatisfied()) finalPrice = kidDiscountCondition.applyDiscount(finalPrice);
        // 할인 계산식 적용


        System.out.println("[📣] 주문이 완료되었습니다. ");
        System.out.println("[📣] 주문내역은 다음과 같습니다. ");
        System.out.println("-".repeat(60));

        cart.printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("금액 합계          : %d원\n", totalPrice);

        //
        System.out.printf("할인 적용 금액 : %d원\n", finalPrice);

    }



}
