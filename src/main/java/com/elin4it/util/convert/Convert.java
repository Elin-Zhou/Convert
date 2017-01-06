/*
 * elin4it Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.elin4it.util.convert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Bean之间数据拷贝
 *
 * @author ElinZhou
 * @version $Id: Convert.java, v 0.1 2015年10月22日 上午8:55:21 ElinZhou Exp $
 */
public class Convert<B1, B2> {

    private static final String[] initCreateEnumStrings     = { "code", "value" };
    private static final Class[]  bases                     = { byte.class, boolean.class,
                                                                short.class, char.class, int.class,
                                                                long.class, float.class,
                                                                double.class };
    private static final Class[]  packages                  = { Byte.class, Boolean.class,
                                                                Short.class, Character.class,
                                                                Integer.class, Long.class,
                                                                Float.class, Double.class };
    private List<String> createEnumStrings = null;
    /**
     * 是否拷贝父类字段，默认关闭
     */
    private boolean copySuperClassFields = false;

    /**
     * 拷贝父类字段代数，如1表示只拷贝直接父类的字段，负数表示不限,默认不限
     */
    private int copySuperClassGenerations = -1;

    /**
     * 是否级联拷贝，默认关闭
     */
    private boolean copyCascade = false;

    private ClassMapper classMapper;

    private ConvertAlias.Tuple[]  aliases;

    public Convert() {
        createEnumStrings = new ArrayList<String>();
        createEnumStrings.addAll(Arrays.asList(initCreateEnumStrings));
    }

    /**
     * @param copySuperClassFields 设置是否对进行父类（直接父类）字段也拷贝
     */
    public Convert(Boolean copySuperClassFields) {
        this();
        this.copySuperClassFields = copySuperClassFields;
    }

    /**
     * @param classMapper 级联拷贝时类映射关系
     */
    public Convert(ClassMapper classMapper) {
        this();
        copyCascade = true;
        this.classMapper = classMapper;
    }

    /**
     * @param copySuperClassFields 设置是否对进行父类（直接父类）字段也拷贝
     * @param classMapper          级联拷贝时类映射关系
     */
    public Convert(Boolean copySuperClassFields, ClassMapper classMapper) {
        this();
        this.copySuperClassFields = copySuperClassFields;
        copyCascade = true;
        this.classMapper = classMapper;
    }

    /**
     * 从orig中将与dest同名同类型的字段拷贝过去
     *
     * @param dest                目标对象（不为空）
     * @param orig                源对象（不为空）
     * @param copySuperClassField 是否对进行父类（直接父类）字段也拷贝
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void copyProperties(Object dest, Object orig, Boolean copySuperClassField)
            throws Exception {
        if (dest == null || orig == null) {
            throw new Exception("参数不能为null");
        }
        Convert convert = new Convert(copySuperClassField);
        convert.convert(orig, dest, orig.getClass(), dest.getClass());
    }

    /**
     * 从orig中将与dest同名同类型的字段拷贝过去
     *
     * @param dest 目标对象（不为空）
     * @param orig 源对象（不为空）
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void copyProperties(Object dest, Object orig) throws Exception {
        if (dest == null || orig == null) {
            throw new Exception("参数不能为null");
        }
        Convert convert = new Convert();
        convert.convert(orig, dest, orig.getClass(), dest.getClass());
    }

    /**
     * 是否进行父类（直接父类）字段也拷贝
     * @return
     */
    public boolean isCopySuperClassFields() {
        return copySuperClassFields;
    }

    /**
     * 设置为是否进行父类（直接父类）字段也拷贝
     *
     * @param copySuperClassFields
     */
    public void setCopySuperClassFields(boolean copySuperClassFields) {
        this.copySuperClassFields = copySuperClassFields;
    }

    /**
     * 拷贝父类字段代数，如1表示只拷贝直接父类的字段，负数表示不限
     *
     * @param copySuperClassGenerations
     */
    public void setCopySuperClassGenerations(int copySuperClassGenerations) {
        this.copySuperClassGenerations = copySuperClassGenerations;
    }

    /**
     * 获取拷贝父类字段代数
     * @return
     */
    public int getCopySuperClassGenerations() {
        return copySuperClassGenerations;
    }

    /**
     * 添加枚举类型转换的创建字段名，默认为code和value
     *
     * @param string
     */
    public void addCreateEnumString(String string) {
        createEnumStrings.add(string);
    }

    /**
     * 添加枚举类型转换的创建字段名，默认为code和value
     *
     * @param strings
     */
    public void addCreateEnumString(String[] strings) {
        for (String string : strings)
            addCreateEnumString(string);
    }

    public void setConvertAlias(ConvertAlias.Tuple... aliases) {
        this.aliases = aliases;
    }

    /**
     * 将B1转换为B2
     *
     * @param in         B1
     * @param b2Class B2的类类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public B2 convert2B2(B1 in, Class<B2> b2Class) {
        if (in == null) {
            return null;
        }
        return (B2) convert(in, in.getClass(), b2Class);
    }

    /**
     * 将B1 List转换为B2 List
     *
     * @param ins         B1
     * @param b2Class B2的类类型
     * @return
     */
    public List<B2> convert2B2(List<B1> ins, Class<B2> b2Class) {
        if (ins == null) {
            return null;
        }
        List<B2> outs = new ArrayList<B2>();
        for (B1 in : ins) {
            outs.add(convert2B2(in, b2Class));
        }
        return outs;
    }

    /**
     * 将B1 List转换为B2 List
     *
     * @param ins            B1 List
     * @param b2Class     B2的类类型
     * @param convertInvoker 转换执行器
     * @return
     */
    public List<B2> convert2B2(List<B1> ins, Class<B2> b2Class, ConvertInvoker<B2> convertInvoker) {
        if (ins == null) {
            return null;
        }
        List<B2> outs = new ArrayList<B2>();
        for (B1 in : ins) {
            B2 b2 = convert2B2(in, b2Class);
            b2 = convertInvoker.invoke(b2);
            outs.add(b2);
        }
        return outs;
    }

    /**
     * 将B1 PageList转换为B2 PageList
     *
     * @param ins        B1 List
     * @param b2Class B2的类类型
     * @return
     */
    public PageList<B2> convert2B2(PageList<B1> ins, Class<B2> b2Class) {
        if (ins == null) {
            return null;
        }
        PageList<B2> outs = new PageList<B2>();
        outs.setPaginator(ins.getPaginator());
        for (B1 in : ins) {
            outs.add(convert2B2(in, b2Class));
        }
        return outs;
    }

    /**
     * 将B1 PageList转换为B2 PageList
     *
     * @param ins            B1 List
     * @param b2Class     B2的类类型
     * @param convertInvoker 转换执行器
     * @return
     */
    public PageList<B2> convert2B2(PageList<B1> ins, Class<B2> b2Class,
                                   ConvertInvoker<B2> convertInvoker) {
        if (ins == null) {
            return null;
        }
        PageList<B2> outs = new PageList<B2>();
        outs.setPaginator(ins.getPaginator());
        for (B1 in : ins) {
            B2 b2 = convert2B2(in, b2Class);
            b2 = convertInvoker.invoke(b2);
            outs.add(b2);
        }
        return outs;
    }

    /**
     * 将B2转换为B2
     *
     * @param in      B2
     * @param b1Class B1的类类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public B1 convert2B1(B2 in, Class<B1> b1Class) {
        if (in == null) {
            return null;
        }
        return (B1) convert(in, in.getClass(), b1Class);
    }

    /**
     * 将B2 List转换为B1 List
     *
     * @param ins     B2 List
     * @param b1Class B1的类类型
     * @return
     */
    public List<B1> convert2B1(List<B2> ins, Class<B1> b1Class) {
        if (ins == null) {
            return null;
        }
        List<B1> outs = new ArrayList<B1>();
        for (B2 in : ins) {
            outs.add(convert2B1(in, b1Class));
        }
        return outs;
    }

    /**
     * 将B2 List转换为B1 List
     *
     * @param ins            B2 List
     * @param b1Class        B1的类类型
     * @param convertInvoker 转换执行器
     * @return
     */
    public List<B1> convert2B1(List<B2> ins, Class<B1> b1Class, ConvertInvoker<B1> convertInvoker) {
        if (ins == null) {
            return null;
        }
        List<B1> outs = new ArrayList<B1>();
        for (B2 in : ins) {
            B1 b1 = convert2B1(in, b1Class);
            b1 = convertInvoker.invoke(b1);
            outs.add(b1);
        }
        return outs;
    }

    /**
     * 将B2 PageList转换为B1 PageList
     *
     * @param ins     B2 List
     * @param b1Class B1的类类型
     * @return
     */
    public PageList<B1> convert2B1(PageList<B2> ins, Class<B1> b1Class) {
        if (ins == null) {
            return null;
        }
        PageList<B1> outs = new PageList<B1>();
        outs.setPaginator(ins.getPaginator());
        for (B2 in : ins) {
            outs.add(convert2B1(in, b1Class));
        }
        return outs;
    }

    /**
     * 将B2 PageList转换为B1 PageList
     *
     * @param ins            B2 List
     * @param b1Class        B1的类类型
     * @param convertInvoker 类型转换器
     * @return
     */
    public PageList<B1> convert2B1(PageList<B2> ins, Class<B1> b1Class,
                                   ConvertInvoker<B1> convertInvoker) {
        if (ins == null) {
            return null;
        }
        PageList<B1> outs = new PageList<B1>();
        outs.setPaginator(ins.getPaginator());
        for (B2 in : ins) {
            B1 b1 = convert2B1(in, b1Class);
            b1 = convertInvoker.invoke(b1);
            outs.add(b1);
        }
        return outs;
    }

    public static class Builder<B1, B2> {

        private boolean              copySuperClassFields      = false;
        private int                  copySuperClassGenerations = -1;
        private boolean              copyCascade               = false;
        private ClassMapper          classMapper;
        private ConvertAlias.Tuple[] aliases;

        public Builder<B1, B2> copySuperClassFields(boolean val) {
            copySuperClassFields = val;
            return this;
        }

        public Builder<B1, B2> copySuperClassGenerations(int val) {
            copySuperClassGenerations = val;
            return this;
        }

        public Builder<B1, B2> copyCascade(boolean val) {
            copyCascade = val;
            return this;
        }

        public Builder<B1, B2> classMapper(ClassMapper val) {
            classMapper = val;
            return this;
        }

        public Builder<B1, B2> aliases(ConvertAlias.Tuple... val) {
            aliases = val;
            return this;
        }

        public Convert<B1, B2> build() {
            return new Convert<B1, B2>(this);
        }

    }

    /**
     * 通过构造器构建Convert
     * @param builder
     */
    private Convert(Builder builder) {
        this();
        copySuperClassFields = builder.copySuperClassFields;
        copySuperClassGenerations = builder.copySuperClassGenerations;
        copyCascade = builder.copyCascade;
        classMapper = builder.classMapper;
        aliases = builder.aliases;
    }

    private Object convert(Object in, Class<?> inClass, Class<?> outClass) {
        try {
            Object out = outClass.newInstance();
            return convert(in, out, inClass, outClass);
        } catch (InstantiationException instantiationException) {
            throw new RuntimeException("创建对象失败,请检查" + outClass + "不为抽象类或接口,且拥有空参构造方法");
        } catch (Exception e) {
            throw new RuntimeException("类型转换出错:" + e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object convert(Object in, Object out, Class<?> inClass, Class<?> outClass) {
        try {
            if (in == null) {
                return null;
            }

            /***************************1、查询得到in和out的父类中的字段*********************************/
            Map<String, Class> inSuperClassFieldNames = getSuperClassFieldName(inClass);
            Map<String, Class> outSuperClassFieldNames = getSuperClassFieldName(outClass);

            //1、转换所有本类中（不包括父类）中的字段
            Field[] fields = inClass.getDeclaredFields();
            // 遍历所有字段
            for (Field field : fields) {
                String inFieldName = field.getName();
                String outFieldName = getOutFieldName(in, out, inFieldName);

                if (copySuperClassFields) {
                    //判断完别名，此时outFieldName中保存了当前字段对应的out字段名
                    //判断out中是否存在outFieldName字段

                    // 把字段首字母设置为大写
                    String outFirstWord = outFieldName.substring(0, 1);
                    outFieldName = outFieldName.replaceFirst(outFirstWord,
                            outFirstWord.toUpperCase());

                    if (outSuperClassFieldNames.containsKey(outFieldName)) {
                        Class outFieldType = outSuperClassFieldNames.get(outFieldName);

                        setValue(inFieldName, outFieldName, field.getType(), outFieldType, in, out);

                        //拷贝完毕，跳出当前字段
                        continue;
                    }
                }

                Class<?> inFieldType = field.getType();
                try {
                    // 把字段首字母设置为小写
                    String outFirstWord = outFieldName.substring(0, 1);
                    outFieldName = outFieldName.replaceFirst(outFirstWord,
                            outFirstWord.toLowerCase());
                    Class<?> outFieldType = outClass.getDeclaredField(outFieldName).getType();
                    // 检查out是否存在该字段，如果没有则抛异常
                    if (outFieldType != null) {
                        setValue(inFieldName, outFieldName, inFieldType, outFieldType, in, out);
                    }
                } catch (NoSuchFieldException e) {
                    //DO NOTHING
                }

            }

            /**********************2、选择in中的父类字段开始拷贝****************************/
            if (copySuperClassFields) {

                //转换父类中有定义set、get方法的字段

                Set<Map.Entry<String, Class>> entrySet = inSuperClassFieldNames.entrySet();

                //遍历in的父类字段
                for (Map.Entry<String, Class> entry : entrySet) {
                    String outFieldName = getOutFieldName(in, out, entry.getKey());

                    //判断完别名，此时outFieldName中保存了当前字段对应的out字段名
                    //判断out中是否存在outFieldName字段
                    if (outSuperClassFieldNames.containsKey(outFieldName)) {

                        setValue(entry.getKey(), entry.getKey(), entry.getValue(),
                                outSuperClassFieldNames.get(entry.getKey()), in, out);

                        //拷贝完毕，跳出当前字段
                        continue;
                    }

                    //尝试从子类字段中查询并赋值
                    // 把字段首字母设置为小写
                    String outFirstWord = outFieldName.substring(0, 1);
                    outFieldName = outFieldName.replaceFirst(outFirstWord,
                            outFirstWord.toLowerCase());
                    try {
                        Class<?> outFieldType = outClass.getDeclaredField(outFieldName).getType();
                        // 检查out是否存在该字段，如果没有则抛异常
                        if (outFieldType != null) {
                            setValue(entry.getKey(), outFieldName, entry.getValue(), outFieldType,
                                    in, out);
                        }
                    } catch (NoSuchFieldException e) {
                        //DO NOTHING
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("类型转换出错:" + e);
        }

        return out;
    }

    /**
     * 通过判断in和out是否继承ConvertAlias来决定out的字段名称
     * @param in
     * @param out
     * @param inFieldName
     * @return
     */
    private String getOutFieldName(Object in, Object out, String inFieldName) {

        String outFieldName = inFieldName;
        List<ConvertAlias.Tuple> aliasList = new ArrayList<ConvertAlias.Tuple>();

        if (aliases != null && aliases.length != 0) {
            aliasList.addAll(Arrays.asList(aliases));
        }

        //判断是否使用了别名
        if (in instanceof ConvertAlias || out instanceof ConvertAlias) {
            Object temp;
            if (in instanceof ConvertAlias) {
                temp = in;
            } else {
                temp = out;
            }

            //获得别名列表
            List<ConvertAlias.Tuple> tempList = ((ConvertAlias) temp).getAliasList();

            if (tempList != null && !tempList.isEmpty()) {
                aliasList.addAll(tempList);
            }

        }

        for (ConvertAlias.Tuple tuple : aliasList) {
            if (tuple.getB1FieldName().equals(inFieldName)) {
                outFieldName = tuple.getB2FieldName();
                break;
            } else if (tuple.getB2FieldName().equals(inFieldName)) {
                outFieldName = tuple.getB1FieldName();
                break;
            }
        }
        return outFieldName;
    }

    /**
     * 从对应类拷贝数据
     *
     * @param inFieldName  源字段名
     * @param outFieldName 目标字段名
     * @param inFieldType  源字段类型
     * @param outFieldType 目标字段类型
     * @param in           目标对象
     * @param out          源对象
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setValue(String inFieldName, String outFieldName, Class inFieldType,
                          Class outFieldType, Object in, Object out) throws Exception {

        try {
            Class inClass = in.getClass();
            Class outClass = out.getClass();

            // 把字段首字母设置为大写
            String outFirstWord = outFieldName.substring(0, 1);
            String outSubFieldName = outFieldName.replaceFirst(outFirstWord,
                    outFirstWord.toUpperCase());

            String inFirstWord = inFieldName.substring(0, 1);
            String inSubFieldName = inFieldName
                    .replaceFirst(inFirstWord, inFirstWord.toUpperCase());

            Method getMethod;
            Method setMethod;
            // 获得out类的set方法
            try {
                setMethod = outClass.getMethod("set" + outSubFieldName, outFieldType);
            } catch (NoSuchMethodException e) {
                // 如果set方法不存在，则跳过
                return;
            }
            if (inFieldType == Boolean.class || inFieldType == boolean.class) {
                // 当字段类型为布尔时，自动生成的get方法有可能为isXxx或getXxx
                try {
                    getMethod = inClass.getMethod("is" + inSubFieldName);
                } catch (NoSuchMethodException e) {
                    try {
                        getMethod = inClass.getMethod("get" + inSubFieldName);
                    } catch (NoSuchMethodException e2) {
                        return;
                    }
                }
            } else {
                // 非布尔类型的字段查询get方法
                try {
                    getMethod = inClass.getMethod("get" + inSubFieldName);
                } catch (NoSuchMethodException e) {
                    return;
                }
            }

            Class checkClass = checkPackageType(inFieldType);
            if (checkClass != null && checkClass == outFieldType) {
                //进行拆箱或装箱操作

                //1、infiled为基本类型
                if (Arrays.asList(bases).contains(inFieldType)) {
                    //获得装箱方法，如Integer.valueOf()
                    Method convertMethod = outFieldType.getMethod("valueOf", inFieldType);
                    Object outFieldValue = convertMethod.invoke(outFieldType, getMethod.invoke(in));
                    setMethod.invoke(out, outFieldValue);
                } else if (Arrays.asList(bases).contains(outFieldType)) {
                    //2、outfield为基本类型

                    //获取拆箱方法，如Integer.intValue()
                    Method convertMethod = inFieldType.getMethod(outFieldType.getName() + "Value");
                    Object outFieldValue = convertMethod.invoke(getMethod.invoke(in));
                    setMethod.invoke(out, outFieldValue);
                }

            } else {
                if (outFieldType == inFieldType) {

                    //in和out类型相同

                    // 执行set和get
                    setMethod.invoke(out, getMethod.invoke(in));
                } else if (outFieldType.isEnum() || inFieldType.isEnum()) {

                    //in和out类型不相同，但其中一方为枚举

                    if (inFieldType.isEnum()) {
                        //如果某个同名字段在inClass和outClass中类型不相同，但in为枚举类型,则取出value赋值
                        Object enumObject = getMethod.invoke(in);
                        Object object = getValueByEnum(enumObject);
                        //如果object不为null，则表示找到了对应的枚举值
                        if (object != null) {
                            setMethod.invoke(out, object);
                        }
                    } else if (outFieldType.isEnum()) {
                        //如果某个同名字段在inClass和outClass中类型不相同，但out为枚举类型,则尝试通过value来赋值
                        Object object = createEnumByValue(outFieldType, getMethod.invoke(in));

                        //如果object不为null，则表示找到了对应的枚举值
                        if (object != null) {
                            setMethod.invoke(out, object);
                        }
                    }
                } else if ((outFieldType == String.class && (inFieldType == Boolean.class || inFieldType == boolean.class))
                        || (inFieldType == String.class && (outFieldType == Boolean.class || outFieldType == boolean.class))) {
                    //一方为String,另一方为boolean/Boolean
                    if (outFieldType == String.class) {
                        Boolean object = (Boolean) getMethod.invoke(in);
                        if (object == null)
                            return;
                        if (object) {
                            setMethod.invoke(out, "1");
                        } else {
                            setMethod.invoke(out, "2");
                        }
                    } else {
                        String object = (String) getMethod.invoke(in);
                        if (object == null)
                            return;
                        if (object.trim().equals("1")) {
                            setMethod.invoke(out, true);
                        } else {
                            setMethod.invoke(out, false);
                        }
                    }

                } else if (copyCascade && classMapper.isMapper(inFieldType, outFieldType)) {
                    //如果两个字段的类型为已设定的映射

                    Object inFieldObject = getMethod.invoke(in);
                    Convert innerConver = new Convert(classMapper);
                    Object outFieldObject = innerConver.convert2B2(inFieldObject, outFieldType);
                    setMethod.invoke(out, outFieldObject);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("数据拷贝阶段出错，源字段名：" + inFieldName + " 目标字段名：" + outFieldName
                    + " 错误信息:" + e);
        }

    }

    /**
     * 通过枚举类型和value创建一个枚举
     *
     * @param enumType 枚举类型
     * @param value    枚举值
     * @return 枚举
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object createEnumByValue(Class enumType, Object value) {
        if (!enumType.isEnum()) {
            return null;
        }
        EnumSet set = EnumSet.allOf(enumType);
        Object object = null;
        //遍历该枚举类型中的所有枚举值
        for (Object e : set) {
            Field field = null;
            //遍历所设定的创建枚举的字符串，以尝试寻找需要的字段
            for (String s : createEnumStrings) {
                try {
                    field = e.getClass().getDeclaredField(s);
                } catch (NoSuchFieldException exception) {
                    //DO NOTHING
                }
            }

            if (field != null) {
                //找到对应的创建字段后，判断当前遍历到的枚举的创建字段是否为传入的value
                try {
                    field.setAccessible(true);
                    if (field.get(e).equals(value)) {
                        object = e;
                        break;
                    }
                } catch (Exception exception) {
                    //DO NOTHING
                }
            }

        }
        return object;
    }

    /**
     * 通过创建字段来创建枚举
     *
     * @param enumObject
     * @return
     */
    private Object getValueByEnum(Object enumObject) {
        if (enumObject == null) {
            return null;
        }
        Object object = null;
        Field field = null;
        //遍历所设定的创建枚举的字符串，以尝试寻找需要的字段
        for (String s : createEnumStrings) {
            try {
                field = enumObject.getClass().getDeclaredField(s);
            } catch (NoSuchFieldException exception) {
                //DO NOTHING
            }
        }

        if (field != null) {
            //找到对应的创建字段后，取出对应的值
            try {
                field.setAccessible(true);
                object = field.get(enumObject);
            } catch (Exception e) {
                return null;
            }
        }
        return object;

    }

    @SuppressWarnings("rawtypes")
    private Map<String, Class> getSuperClassFieldName(Class c) {
        Class superClass = c.getSuperclass();

        Map<String, Class> fieldMap = new HashMap<String, Class>();
        Map<String, Class> confirmMap = new HashMap<String, Class>();

        int count = 0;

        while (superClass != null) {
            count++;
            //判断是否到了设定的拷贝父类代数
            if (copySuperClassGenerations > 0 && count > copySuperClassGenerations) {
                break;
            }
            Method[] allMethods = superClass.getMethods();
            for (Method method : allMethods) {
                if (method.getDeclaringClass() == superClass) {
                    String methodName = method.getName();
                    String fieldName;
                    if (methodName.startsWith("set") || methodName.startsWith("get")
                            || methodName.startsWith("is")) {

                        //获得其字段名
                        if (methodName.startsWith("is")) {
                            fieldName = methodName.substring(2, methodName.length());
                        } else {
                            fieldName = methodName.substring(3, methodName.length());
                        }
                        //该方法的标记类型，set方法为其入参，get为其返回值
                        Class flagClass;
                        if (methodName.startsWith("set")) {
                            //由于该方法为set或get/is方法，所以有且只有一个参数
                            Class[] parameterTypesmethod = method.getParameterTypes();
                            if (parameterTypesmethod.length != 1) {
                                continue;
                            }
                            flagClass = parameterTypesmethod[0];
                        } else {
                            Class resultClass = method.getReturnType();
                            if (resultClass == null) {
                                continue;
                            } else {
                                flagClass = resultClass;
                            }
                        }

                        //此处判断是否用成对的set和get方法，而且需要保证其类型相同
                        //判断是否有记录该字段，如果有则判断记录中的字段标记类型和当前的标记类型是否相同
                        //如果相同则把value置为null做标记
                        //如果不存在，则添加到记录中
                        if (fieldMap.containsKey(fieldName)) {
                            Class fieldClass = fieldMap.get(fieldName);
                            if (fieldClass != null && flagClass != null) {

                                if (fieldClass == flagClass) {
                                    //此时表示有成对的set或get方法出现,则value设置为null作为标记
                                    fieldMap.put(fieldName, null);
                                    confirmMap.put(fieldName, fieldClass);
                                }
                            }
                        } else {
                            fieldMap.put(fieldName, flagClass);
                        }
                    }
                }
            }
            superClass = superClass.getSuperclass();

        }

        //对所有的记录进行判断
        //        Set<Map.Entry<String, Class>> entrySet = fieldMap.entrySet();
        //
        //        Iterator<Map.Entry<String, Class>> iterator = entrySet.iterator();
        //        if (iterator.hasNext()) {
        //            if (iterator.next().getValue() != null) {
        //                //删除必须使用迭代器删除，否则会报ConcurrentModificationException
        //                iterator.remove();
        //            }
        //        }

        return confirmMap;

    }

    /**
     * 查询是否为可装箱转换类型，如果是则返回装箱/拆箱后的类型，否则返回null
     *
     * @param c
     * @return
     */
    private Class checkPackageType(Class c) {
        int index = -1;
        for (int i = 0; i < bases.length; i++) {
            if (bases[i] == c) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            //未查询到
            for (int i = 0; i < packages.length; i++) {
                if (packages[i] == c) {
                    index = i;
                    break;
                }
            }
        } else {
            //查询到
            return packages[index];
        }

        if (index == -1) {
            return null;
        } else {
            return bases[index];
        }
    }

    public static class ClassMapper {
        private Set<Tuple> tuples = new HashSet<Tuple>();

        public ClassMapper() {
        }

        public ClassMapper(Tuple[] tuples) {
            this.tuples.addAll(Arrays.asList(tuples));
        }

        public ClassMapper(List<Tuple> tuples) {
            this.tuples.addAll(tuples);
        }

        public ClassMapper(Map<String, String> tuples) {
            for (Map.Entry<String, String> entry : tuples.entrySet()) {
                this.tuples.add(new Tuple(entry.getKey(), entry.getValue()));
            }
        }

        public Set<Tuple> getTuples() {
            return tuples;
        }

        public void setTuples(Set<Tuple> tuples) {
            this.tuples = tuples;
        }

        /**
         * 添加映射
         *
         * @param classOne
         * @param classTwo
         * @return
         */
        public boolean addMapper(Class classOne, Class classTwo) {
            if (getMapper(classOne) != null || getMapper(classTwo) != null) {
                return false;
            }
            return this.tuples.add(new Tuple(classOne, classTwo));
        }

        /**
         * @param clazz 返回映射类型
         * @return 映射类型，如果不存在则返回null
         */
        public Class getMapper(Class clazz) {
            for (Tuple tuple : tuples) {
                Class c = tuple.getMapper(clazz);
                if (c != null) {
                    return c;
                }
            }
            return null;
        }

        /**
         * 判断两个输入类型是否为映射关系
         *
         * @param classOne
         * @param classTwo
         * @return
         */
        public boolean isMapper(Class classOne, Class classTwo) {
            return getMapper(classOne) == classTwo;
        }

        class Tuple {
            private Class classOne;
            private Class classTwo;

            public Tuple(Class classOne, Class classTwo) {
                this.classOne = classOne;
                this.classTwo = classTwo;
            }

            public Tuple(String classOne, String classTwo) {
                try {
                    this.classOne = Class.forName(classOne);
                } catch (ClassNotFoundException e) {
                    classOne = null;
                }

                try {
                    this.classTwo = Class.forName(classTwo);
                } catch (ClassNotFoundException e) {
                    classTwo = null;
                }
            }

            /**
             * 判断两个输入类型是否为映射关系
             *
             * @param classOne
             * @param classTwo
             * @return
             */
            public boolean isMapper(Class classOne, Class classTwo) {
                return this.classOne == classOne && this.classTwo == classTwo
                        || this.classOne == classTwo && this.classTwo == classOne;
            }

            /**
             * 返回映射类型
             *
             * @param clazz
             * @return
             */
            public Class getMapper(Class clazz) {
                if (classOne == clazz) {
                    return classTwo;
                } else if (classTwo == clazz) {
                    return classOne;
                } else {
                    return null;
                }
            }

            @Override
            public boolean equals(Object o) {
                if (this == o)
                    return true;
                if (o == null || getClass() != o.getClass())
                    return false;

                Tuple that = (Tuple) o;

                if (!classOne.equals(that.classOne))
                    return false;
                return classTwo.equals(that.classTwo);

            }

            @Override
            public int hashCode() {
                int result = classOne.hashCode();
                result = 31 * result + classTwo.hashCode();
                return result;
            }
        }

    }

}
