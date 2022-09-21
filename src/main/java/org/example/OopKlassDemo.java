package org.example;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

import java.util.ArrayList;
import java.util.ServiceLoader;

/**
 * @author ：zsd
 * @date ：Created in 2022/9/9 11:33
 * @description：
 * @modified By：
 * @version: $
 */
//-Xms100M -Xmx100M -XX:MaxMetaspaceSize=20M
public class OopKlassDemo {

    public ArrayList arrayList;

    public ArrayList arrayList1;

    public static byte[] bytes = new byte[50 * 1024];

    public static void main(String[] args) throws InterruptedException {
        OopKlassDemo heapDemo = new OopKlassDemo();
        demo();
    }

    public static void demo() throws InterruptedException {
        OopKlassDemo heapDemo = new OopKlassDemo();
        System.out.println(ClassLayout.parseInstance(heapDemo).toPrintable());
        System.out.println(GraphLayout.parseInstance(heapDemo).toPrintable());
        System.out.println(ClassLayout.parseInstance(heapDemo.getClass()).toPrintable());
        System.out.println(GraphLayout.parseInstance(heapDemo.getClass()).toPrintable());
        pr(heapDemo.getClass());
        System.out.println(ClassLayout.parseInstance(OopKlassDemo.bytes).toPrintable());
        System.out.println(GraphLayout.parseInstance(OopKlassDemo.bytes).toPrintable());

        Thread.sleep(1000000);

    }

    /**
     * 打印object二进制数据
     * @param instance
     */
    public static void pr(Object instance) {
        VirtualMachine vm = VM.current();

        for (long off = 0; off < 200; off += 4) {
            int word = vm.getInt(instance, off);
            System.out.printf(" %6d %5d %" + 1 + "s %-" + 1 + "s %s%n", off, 4, "", "(object header)",
                    toHex((word >> 0) & 0xFF) + " " +
                            toHex((word >> 8) & 0xFF) + " " +
                            toHex((word >> 16) & 0xFF) + " " +
                            toHex((word >> 24) & 0xFF) + " " +
                            "(" +
                            toBinary((word >> 0) & 0xFF) + " " +
                            toBinary((word >> 8) & 0xFF) + " " +
                            toBinary((word >> 16) & 0xFF) + " " +
                            toBinary((word >> 24) & 0xFF) + ") " +
                            "(" + word + ")"
            );
        }
    }

    private static String toBinary(int x) {
        String s = Integer.toBinaryString(x);
        int deficit = 8 - s.length();
        for (int c = 0; c < deficit; c++) {
            s = "0" + s;
        }
        return s;
    }

    // very ineffective, so what?
    private static String toHex(int x) {
        String s = Integer.toHexString(x);
        int deficit = 2 - s.length();
        for (int c = 0; c < deficit; c++) {
            s = "0" + s;
        }
        return s;
    }
}
