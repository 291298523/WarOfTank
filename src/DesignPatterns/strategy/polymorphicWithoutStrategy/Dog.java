package DesignPatterns.strategy.polymorphicWithoutStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author:李罡毛
 * @date:2021/2/12 11:07
 */
public class Dog implements Serializable,Comparable<Dog> {
    @Serial
    private static final long serialVersionUID = -8741750107645037313L;
    private int appetite;//狗狗饭量

    public Dog() {
    }

    public Dog(int appetite) {
        this.appetite = appetite;
    }

    public int getAppetite() {
        return appetite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return appetite == dog.appetite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appetite);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "appetite=" + appetite +
                '}';
    }

    @Override
    public int compareTo(Dog o) {
        if (this.getAppetite()>o.getAppetite())return -1;
        else if (this.getAppetite()<o.getAppetite())return 1;
        return 0;
    }
}
