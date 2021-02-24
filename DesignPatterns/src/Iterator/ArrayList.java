package Iterator;

import java.util.Arrays;

/**
 * @author:李罡毛
 * @date:2021/2/23 14:37
 */
public class ArrayList implements List{
    private int tail = 0;
    private Object[] objects = new Object[11];

    @Override
    public ArrayList add(Object o) {
        if (tail>=objects.length-1){
            //扩容
            objects = Arrays.copyOf(objects,objects.length/2+ objects.length);
        }
        objects[tail] = o;
        tail++;
        return this;
    }

    @Override
    public Object get(int i) {
        if (i>tail) throw new IndexOutOfBoundsException();
        return objects[i];
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "objects=" + Arrays.toString(objects) +
                '}';
    }
}
