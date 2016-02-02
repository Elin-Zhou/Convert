import com.elin4it.util.convert.ConvertAlias;

public class InnerDO1 extends ConvertAlias {
    private InnerDO2 innerDO2;
    private int      a;

    {
        addAlias("innerDO2", "innerModel2");
    }

    public InnerDO2 getInnerDO2() {
        return innerDO2;
    }

    public void setInnerDO2(InnerDO2 innerDO2) {
        this.innerDO2 = innerDO2;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "InnerDO1{" + "innerDO2=" + innerDO2 + ", a=" + a + '}';
    }
}