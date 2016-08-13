package com.elin4it.util.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * DO和Model的转换时，如果两边的字段名不相同，但仍需要转换，可以让DO继承该类，并且调用addAlias方法，添加一个Tuple内部类对象
 *
 * @author ElinZhou
 * @version $Id: ConvertAlias.java, v 0.1 2015年11月12日 下午5:25:50 ElinZhou Exp $
 */
public abstract class ConvertAlias {
    private List<Tuple> aliasList = new ArrayList<ConvertAlias.Tuple>();
    class Tuple {
        private String doFieldName;
        private String modelFieldName;

        /**
         * 创建一个别名映射类
         * @param doFieldName DO中的字段名
         * @param modelFieldName Model中的字段名
         */
        public Tuple(String doFieldName, String modelFieldName) {
            this.doFieldName = doFieldName;
            this.modelFieldName = modelFieldName;
        }
        public String getDoFieldName() {
            return doFieldName;
        }

        public String getModelFieldName() {
            return modelFieldName;
        }
    }

    /**
     * 把需要添加的别名映射加入
     *
     *
     * @param tuple
     */
    protected void addAlias(Tuple tuple) {
        aliasList.add(tuple);
    }
    protected void addAlias(String one,String two){
        addAlias(new Tuple(one,two));
    }
    public List<Tuple> getAliasList() {
        return aliasList;
    }
}
