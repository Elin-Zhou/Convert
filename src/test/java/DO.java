import com.elin4it.util.convert.ConvertAlias;

import java.math.BigDecimal;
import java.util.Date;

public class DO extends ConvertAlias {
    private int        aa;
    private String     b;
    private Date c;
    private boolean    d;
    private BigDecimal e;
    private String     f;
    private BigDecimal gg;
    private String     h;
    private int        j;
    private Character  k;

    {
        addAlias("gg", "g");
    }

    public int getAa() {
        return aa;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Date getC() {
        return c;
    }

    public void setC(Date c) {
        this.c = c;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public BigDecimal getE() {
        return e;
    }

    public void setE(BigDecimal e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public BigDecimal getGg() {
        return gg;
    }

    public void setGg(BigDecimal gg) {
        this.gg = gg;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Character getK() {
        return k;
    }

    public void setK(Character k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "DO{" + "aa=" + aa + ", b='" + b + '\'' + ", c=" + c + ", d=" + d + ", e=" + e
               + ", f='" + f + '\'' + ", gg=" + gg + ", h='" + h + '\'' + ", j=" + j + ", k=" + k
               + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DO aDo = (DO) o;

        if (aa != aDo.aa)
            return false;
        if (d != aDo.d)
            return false;
        if (j != aDo.j)
            return false;
        if (b != null ? !b.equals(aDo.b) : aDo.b != null)
            return false;
        if (c != null ? !c.equals(aDo.c) : aDo.c != null)
            return false;
        if (e != null ? !e.equals(aDo.e) : aDo.e != null)
            return false;
        if (f != null ? !f.equals(aDo.f) : aDo.f != null)
            return false;
        if (gg != null ? !gg.equals(aDo.gg) : aDo.gg != null)
            return false;
        if (h != null ? !h.equals(aDo.h) : aDo.h != null)
            return false;
        return k != null ? k.equals(aDo.k) : aDo.k == null;
    }

    @Override
    public int hashCode() {
        int result = aa;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (d ? 1 : 0);
        result = 31 * result + (e != null ? e.hashCode() : 0);
        result = 31 * result + (f != null ? f.hashCode() : 0);
        result = 31 * result + (gg != null ? gg.hashCode() : 0);
        result = 31 * result + (h != null ? h.hashCode() : 0);
        result = 31 * result + j;
        result = 31 * result + (k != null ? k.hashCode() : 0);
        return result;
    }
}