package fr.d2factory.libraryapp.member;

public class Resident extends Member {

    private final float NORMAL_PRICE = (float) 0.10;
    private final float LATE_PRICE = (float) 0.20;

    @Override
    public void payBook(int numberOfDays) {
        float priceToPay = calculatePrice(numberOfDays);
        this.setWallet(this.getWallet() - priceToPay);
    }

    private float calculatePrice(int numberOfDays){
        if (numberOfDays > 60) {
            setLate(true);
            System.out.println("You are in Late situation");
            return  (NORMAL_PRICE * 60) + ((numberOfDays-60) * LATE_PRICE);
        }else{
            return NORMAL_PRICE * numberOfDays;
        }
    }
}
