package app.discount.discountPolicy;

public class FixedRateDiscountPolicy {

    // 할인비율
    private int discountRate;


    // 생성자
    public FixedRateDiscountPolicy(int discountRate) {
        this.discountRate = discountRate;
    }

    // 할인 적용 금액 계산
    public int calculateDiscountedPrice(int price) {
        return price - (price * discountRate / 100);
    }



}
