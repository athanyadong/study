package com.athan.datasource;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @aurhor athan
 * @description 多数据源上下文
 * @date 2022/7/29 10:15
 */
public class DynamicContextHolder {

    @SuppressWarnings("unchecked")  //给线程提供局部变量  可以从头尾插入元素
    private static final ThreadLocal<Deque<String>> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> new ArrayDeque());

    /**
     * 获取当前数据源
     * @return 数据源名
     */
    public static String peek(){
        return CONTEXT_HOLDER.get().peek();
    }

    /**
     * 设置当前数据源
     * @param dataSource  当前数据源名称
     */
    public static void push(String dataSource){
    CONTEXT_HOLDER.get().push(dataSource);
    }

    /**
     * 清空当前线程数据源
     */
    public static void poll() {
        Deque<String> deque = CONTEXT_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            CONTEXT_HOLDER.remove();
        }
    }


}
