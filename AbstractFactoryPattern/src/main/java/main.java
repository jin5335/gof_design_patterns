import abst.Factory;
import abst.Laptop;
import abst.Phone;
import apple.AppleFactory;
import samsung.SamsungFactory;

public class main {
    public static void main(String[] args) {
        Factory factory = getFactory("Samsung");

        Phone phone = factory.createPhone();
        Laptop laptop = factory.createLaptop();

        System.out.println(phone.getName());
        System.out.println(laptop.getName());
    }

    static Factory getFactory(String name){
        switch(name) {
            case "Apple":
                return new AppleFactory();
            case "Samsung":
                return new SamsungFactory();
            default:
                return null;
        }
    }
}
