package fr.d2factory.libraryapp.member;

public class Student extends Member{

    private boolean isFirstYearStudent;
    private final float PRICE = (float) 0.10;

    @Override
    public void payBook(int numberOfDays) {
        float priceToPay = calculatePrice(numberOfDays);
        this.setWallet(this.getWallet() - priceToPay);
    }

    public boolean isFirstYearStudent() {
        return isFirstYearStudent;
    }

    public void setFirstYearStudent(boolean firstYearStudent) {
        isFirstYearStudent = firstYearStudent;
    }

    private float calculatePrice(int numberOfDays){
        float price = 0 ;
        if (isFirstYearStudent){
            if (numberOfDays > 15){
                price = (numberOfDays - 15) * PRICE;
            }
            if (numberOfDays > 45) {
                System.out.println("You are in Late situation");
                setLate(true);
            }
        }else {
            if (numberOfDays > 30){
                System.out.println("You are in Late situation");
                setLate(true);
            }
            price = (numberOfDays) * PRICE;
        }
        return price;
    }

}
