package com.elin4it.util.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * 别名转换
 *
 * 两个Bean的转换时，如果两边的字段名不相同，但仍需要转换，可以让其中一个Bean继承该类，并且调用addAlias方法，添加一个Tuple内部类对象
 *
 * @author ElinZhou
 * @version $Id: ConvertAlias.java, v 0.1 2015年11月12日 下午5:25:50 ElinZhou Exp $
 */
public abstract class ConvertAlias {
    protected static List<Tuple> aliasList = new ArrayList<ConvertAlias.Tuple>();

    public static class Tuple {
        private String b1FieldName;
        private String b2FieldName;

        /**
         * 创建一个别名映射类
         * @param b1FieldName B1中的字段名
         * @param b2FieldName B2中的字段名
         */
        public Tuple(String b1FieldName, String b2FieldName) {
            this.b1FieldName = b1FieldName;
            this.b2FieldName = b2FieldName;
        }

        public String getB1FieldName() {
            return b1FieldName;
        }

        public String getB2FieldName() {
            return b2FieldName;
        }
    }

    /**
     * 把需要添加的别名映射加入
     *
     *
     * @param tuple
     */
    public static void addAlias(Tuple tuple) {
        aliasList.add(tuple);
    }

    public static void addAlias(String one, String two) {
        addAlias(new Tuple(one,two));
    }
    public List<Tuple> getAliasList() {
        return aliasList;
    }
}
