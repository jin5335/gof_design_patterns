package apple;

import abst.Factory;
import abst.Laptop;
import abst.Phone;

public class AppleFactory implements Factory {
    @Override
    public Phone createPhone() {
        return new IPhone();
    }

    @Override
    public Laptop createLaptop() {
        return new MacBook();
    }
}
