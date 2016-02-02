public class InnerModel1 {
    private InnerModel2 innerModel2;
    private int         a;

    public InnerModel2 getInnerModel2() {
        return innerModel2;
    }

    public void setInnerModel2(InnerModel2 innerModel2) {
        this.innerModel2 = innerModel2;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "InnerModel1{" + "innerModel2=" + innerModel2 + ", a=" + a + '}';
    }
}