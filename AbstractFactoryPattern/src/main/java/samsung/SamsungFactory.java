package samsung;

import abst.Factory;
import abst.Laptop;
import abst.Phone;

public class SamsungFactory implements Factory {
    @Override
    public Phone createPhone() {
        return new GalaxyS20();
    }

    @Override
    public Laptop createLaptop() {
        return new GalaxyBook();
    }
}
