import com.elin4it.util.convert.ConvertAlias;

public class InnerDO2 extends ConvertAlias {
    private int      b;
    private InnerDO3 innerDO3;

    {
        addAlias("innerDO3", "innerModel3");
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public InnerDO3 getInnerDO3() {
        return innerDO3;
    }

    public void setInnerDO3(InnerDO3 innerDO3) {
        this.innerDO3 = innerDO3;
    }

    @Override
    public String toString() {
        return "InnerDO2{" + "b=" + b + ", innerDO3=" + innerDO3 + '}';
    }
}
