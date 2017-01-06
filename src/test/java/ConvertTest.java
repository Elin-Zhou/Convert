
/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */

import com.elin4it.util.convert.Convert;
import com.elin4it.util.convert.ConvertAlias;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ElinZhou
 * @version $Id: ConvertTest.java , v 0.1 2016/2/2 9:41 ElinZhou Exp $
 */
public class ConvertTest {

    @Test
    public void baseConvert() {
        System.out.println(" /**测试转换与别名**/");
        DO do1 = new DO();

        do1.setAa(1);
        do1.setB("2");
        do1.setC(new Date());
        do1.setD(true);
        do1.setE(new BigDecimal(3.2));
        do1.setF("1");
        do1.setGg(new BigDecimal(1.5));
        do1.setH("1");
        do1.setJ(111);
        do1.setK('a');

        System.out.println(do1);
        Convert<DO, Model> convert = new Convert<DO, Model>();
        convert.setConvertAlias(new ConvertAlias.Tuple[] { new ConvertAlias.Tuple("a", "aa") });
        Model model = convert.convert2B2(do1, Model.class);
        System.out.println(model);

        DO do2 = convert.convert2B1(model, DO.class);
        System.out.println(do2);

        assert do2.equals(do1);
    }

    @Test
    public void parentFieldConvert() {
        System.out.println("\n\n/**测试父类转换**/");
        /**测试父类转换**/

        SubDo subDo = new SubDo();
        subDo.setA(1);
        subDo.setB("2");
        subDo.setC(new Date());
        subDo.setD(true);
        subDo.setOne(5);
        subDo.setTwo("two");
        subDo.setThree(new Date());
        subDo.setFour(true);
        subDo.setFive(new BigDecimal("123321123321"));
        subDo.setSix("1");
        subDo.setSeven(new BigDecimal(1.5));
        subDo.setEight("   1  ");
        System.out.println(subDo);
        //        Convert<SubDo, SubModel> extendConvert = new Convert<SubDo, SubModel>(true);
        Convert<SubDo, SubModel> extendConvert = new Convert.Builder<SubDo, SubModel>()
            .copySuperClassFields(true).build();
        SubModel subModel = extendConvert.convert2B2(subDo, SubModel.class);
        System.out.println(subModel);

        System.out.println(extendConvert.convert2B1(subModel, SubDo.class));

    }

    @Test
    public void subFieldFromParentConvert() {
        System.out.println("\n\n/******测试子类拷父类********/");
        /******测试子类拷父类********/
        SubDo subDo = new SubDo();
        subDo.setA(1);
        subDo.setB("2");
        subDo.setC(new Date());
        subDo.setD(true);
        subDo.setOne(5);
        subDo.setTwo("two");
        subDo.setThree(new Date());
        subDo.setFour(true);
        subDo.setFive(new BigDecimal("123321123321"));
        subDo.setSix("1");
        subDo.setSeven(new BigDecimal(1.5));
        subDo.setEight("   1  ");
        System.out.println(subDo);
        Convert<SubDo, SuperModel> superSubConvert = new Convert<SubDo, SuperModel>(true);
        SuperModel superModel = superSubConvert.convert2B2(subDo, SuperModel.class);
        System.out.println(superModel);
        System.out.println(superSubConvert.convert2B1(superModel, SubDo.class));
    }

    @Test
    public void casadeConvert() {
        /*****************测试级联***********************/
        System.out.println("\n\n\n\n/*****************测试级联***********************/");
        InnerDO1 innerDO1 = new InnerDO1();
        innerDO1.setA(10);
        InnerDO2 innerDO2 = new InnerDO2();
        innerDO2.setB(5);
        innerDO1.setInnerDO2(innerDO2);
        InnerDO3 innerDO3 = new InnerDO3();
        innerDO3.setC(15);
        innerDO2.setInnerDO3(innerDO3);
        System.out.println(innerDO1);
        Convert.ClassMapper classMapper = new Convert.ClassMapper();
        classMapper.addMapper(InnerDO2.class, InnerModel2.class);
        classMapper.addMapper(InnerDO3.class, InnerModel3.class);
        Convert<InnerDO1, InnerModel1> casadeConvet = new Convert<InnerDO1, InnerModel1>(
                classMapper);
        System.out.println(casadeConvet.convert2B2(innerDO1, InnerModel1.class));
    }
}

/*********************************************************
 * 测试类
 *****************************************************************************/

enum BlendingStatusEnum {
    BLENDING_AWAIT("1", "待对账"),

    BLENDING_FINISH("3", "已挂账"),

    BLENDING_SUCCESS("2", "已对账");

    private String value = null;

    private String message = null;

    private BlendingStatusEnum(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过枚举<code>value</code>获得枚举
     *
     * @param value
     * @return
     */
    public static BlendingStatusEnum getByValue(String value) {
        for (BlendingStatusEnum test : values()) {
            if (test.getValue().equals(value)) {
                return test;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value + "|" + message;
    }
}
