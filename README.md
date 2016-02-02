# Convert
##功能：将一个类中的数据拷贝到另一个类中



###2016/2/1更新
  支持级联拷贝，例如Front1中有一个字段类型为Front2，需要把Front1转换为Behind1，Behind1中有个字段为Behind2，该字段由Front2转换而来
  级联拷贝不限定级联层数，但禁止循环级联，如Front1中包含Front2,Front2中包含Front1
  
#### 使用方法：
  1. 创建转换类时传入ClassMapper类，调用该类add(Class,Class)方法添加映射关系
  也可在spring中配置映射关系，例如：

  
        <bean class="cn.yumei.common.util.Convert$ClassMapper" id="classMapper">
        <constructor-arg>
        <map>
        <!--key和value可互换，顺序无影响-->
        <entry key="com.yumei.merchant.common.dal.dataobject.MerchantContact" value="com.yumei.merchant.common.service.facade.model.MerchantContactBehind"/>
        <entry key="com.yumei.merchant.common.dal.dataobject.MerchantOrderLink" value="com.yumei.merchant.common.service.facade.model.Orde </map>
        </constructor-arg>
        </bean>
    
  
  2. 通常两个类型的字段名称不同，所以Front继承ConvertAlias，调用父类add(String,String)方法设置别名

###2016/1/19更新
  支持自动拆箱装箱(之前仅支持Boolean和boolean)

* byte <==> Byte
* boolean <==> Boolean
* short <==> Short
* char <==> Character
* int <==>Integer
* long <==>Long
* float <==>Float
* double <==> Frontuble
  



###2016/1/14更新
  1. 支持从父类字段拷贝到子类字段
  2. 修复入参为null时的异常BUG
  3. 修复当两个对象同时存在某一字段，但不存在set或get方法时抛出异常的BUG
  <p/>
  <p/>
  <p/>
###2016/1/4 更新
  1. 支持直接父类字段的拷贝，要求该字段必须拥有set和get/is方法，同样支持各种类型的转换
  默认关闭拷贝功能，开启需要设置copySuperClassFields
  2. 将convert2BehindList、convert2FrontList、convert2BehindPageList、convert2FrontPageList设置为过时方法，请使用convert2Behind和convert2Front的重载方法
  <p/>
  <p/>
  1. 把两个类中同名且同类型的字段进行拷贝
  2. 两个类中同名，一方为枚举，则尝试通过其枚举创建字段进行拷贝，默认枚举创建字段为code和value，可添加
  4. 两个类中同名，一方为String，另一方为Boolean/boolean
  5. 两个类中同名，一方为Boolean，另一方为boolean
  5. 支持别名。需要在Front继承ConvertAlias，并且在调用ConvertAlias.addAlias，在其中添加别名映射
