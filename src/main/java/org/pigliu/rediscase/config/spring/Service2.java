package org.pigliu.rediscase.config.spring;

import org.assertj.core.util.Lists;

import javax.management.relation.RoleList;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/10
 */
public class Service2 {


    public static void main(String[] args) {

    }

    private static int test() {
        try {
            ArrayList<Object> objects = new ArrayList<>();
            return 1;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("finallt");
            return 2;
        }
    }
}
