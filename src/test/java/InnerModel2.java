public class InnerModel2 {
    private int         b;
    private InnerModel3 innerModel3;

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public InnerModel3 getInnerModel3() {
        return innerModel3;
    }

    public void setInnerModel3(InnerModel3 innerModel3) {
        this.innerModel3 = innerModel3;
    }

    @Override
    public String toString() {
        return "InnerModel2{" + "b=" + b + ", innerModel3=" + innerModel3 + '}';
    }
}
