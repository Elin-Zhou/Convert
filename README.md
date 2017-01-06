# Convert

用于转化两个类之间的数据

作者(Author):夕下奕林(ElinZhou)


##项目地址

https://github.com/Elin-Zhou/Convert


***

##功能简述
在java项目开发中，系统会进行分层，如service、dao等，在不同的部件之间需要传输对象，但这些对象很多都是意义相同，如代表一个订单，但其中的字段数量与类型不同。

例如一个订单在dao层中取名为OrderDO，在service中就取名为OrderModel，但是其中很多类型如订单号字段的名称和类型是相同的

但是订单的状态在数据库中使用char存储，到了dao层将会转为String，到了service需要用枚举表示

当然也会出现含义相同，但是由于某些原因导致字段名称不同的情况


根据上述种种需求，需要经常在两个类型之间进行转换，在参考市面上现有的解决方案无果后，有了本工具。


##使用方法

###简单转换

####创建对象：
	Convert<AnimalDO, AnimalModel> convert = new Convert<AnimalDO, AnimalModel>();
其中AnimalDO和AnimalModel为需要转换的类型


####转换：

	AnimalDO animalDO = new AnimalDO("aa", 2, "S");
	System.out.println(animalDO);
	AnimalModel animalModel = convert.convert2B2(animalDO, AnimalModel.class);
	System.out.println(animalModel);
	animalDO = convert.convert2B1(animalModel, AnimalDO.class);
	System.out.println(animalDO);

其中AnimalDO中拥有三个字段

	private String name;
	private int age;
	private String size;
	
AnimalModel中拥有三个字段

	private String name;
	private int age;
	private Size size;

Size为表示动物大小的枚举枚举，

	public enum Size {


    	SMALL("S", "小型"),

    	MEDIUM("M", "中型"),

    	LARGE("L", "大型"),;

    	private String code;
    	private String message;   
    }
    
上述代码输出后的结果为：


	AnimalDO{name='aa', age=2, size='S'}

	AnimalModel{name='aa', age=2, size=SMALL}

	AnimalDO{name='aa', age=2, size='S'} 
   
   


同理，List也可以使用重载方法进行转换


	Convert<AnimalDO,AnimalModel>convert = new Convert<AnimalDO, AnimalModel>();

	List<AnimalDO> list = new ArrayList<AnimalDO>();
	list.add(new AnimalDO("aa",2,"S"));
	list.add(new AnimalDO("bb",3,"M"));
	list.add(new AnimalDO("cc",2,"S"));
	list.add(new AnimalDO("dd",1,"L"));


	List<AnimalModel> models = convert.convert2B2(list,AnimalModel.class);




###别名转换
有些情况下两个bean中相同含义的字段的名字是不同的，所以需要使用别名进行转换。

大多数情况下，DO与数据库中的字段是一一对应的，甚至有且情况下DO是由工具生成的，所以一般情况不改动DO的代码，所以在此处需要改动Model的代码。

需要Model继承com.elin4it.util.convert.ConvertAlias类，然后调用其addAlias(String,String)方法，Model中的字段名称在前，DO中的字段名称在后。

为了方便自动进行别名转换，所以一般在构造方法里或静态代码块里进行别名映射，例如：

	{
	    addAlias("identity", "id");
	}


转换时跟之前一样即可进行别名映射


###父类字段拷贝

在默认情况下，只转换当前类的字段，不对父类以及祖先类字段进行转换。

如果需要转换，需要满足两点：

1. 需要转换的父类（祖先类）的字段拥有set和get方法（boolean中的get可以是is）
2. 打开拷贝父类开关

打开拷贝父类开关有两种方法，其一是在构造方法中直接打开

	Convert<AnimalDO,AnimalModel>convert = new Convert<AnimalDO, AnimalModel>(true);
	
另一种调用OpenCopySuperClassFields(boolean)方法

	convert.OpenCopySuperClassFields(true);


注意，在默认情况下，打开父类拷贝以后会将所有祖先类拥有了set和get方法的字段全部进行拷贝的，如果需要进行限制，可以调用setCopySuperClassGenerations(int)方法，设置需要拷贝几代字段，如设置1，则只拷贝当前类以及其直接父类的字段。如果要改为全部拷贝，则将其设为负数如-1即可。


###级联拷贝
如果在DO中有其他自定义类型DO，而且需要将其同样转换为对应Model，需要配置其映射关系。

在创建Convert的时候在构造方法中传入Convert.ClassMapper对象：

	Convert.ClassMapper classMapper = new Convert.ClassMapper();
	//添加时前后顺序不影响映射
	classMapper.addMapper(DetailDO.class, DetailModel.class);

	Convert<AnimalDO, AnimalModel> convert = new Convert<AnimalDO, AnimalModel>(classMapper);
	
	
###自动装箱拆箱
DO和Model中的同名字段如果为基本类型和包装类型的关系，也可以自动转换，无需配置

* byte <==> Byte
* boolean <==> Boolean
* short <==> Short
* char <==> Character
* int <==>Integer
* long <==>Long
* float <==>Float
* double <==> Double



###其他枚举
在自动枚举转换时，默认支持该枚举的构造字段为value或code，如果使用了除这两者以外的，可以调用addCreateEnumString进行添加

例如有一个表示动物类型的枚举：

	public enum Type {

    	BIRD("B"),

    	CAT("CAT"),

    	;
    	String type;


    	private Type(String type){
    	    this.type = type;
    	}


	}
	
需要调用Convert.addCreateEnumString方法设置

	convert.addCreateEnumString("type");