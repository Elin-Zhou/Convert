package com.elin4it.util.convert;

import java.util.ArrayList;
import java.util.Collection;


/**
 *  分页List
 *
 * @author ElinZhou
 * @version $Id: Convert.java, v 0.1 2015年10月22日 上午8:55:21 ElinZhou Exp $
 */
public class PageList<E> extends ArrayList<E> {

    /**  */
    private static final long serialVersionUID = -5725605078351614003L;
    
    private Paginator paginator;

    /**
     * 创建一个<code>PageList</code>。
     */
    public PageList() {
        paginator = new Paginator(0, 0);
    }

    /**
     * 创建<code>PageList</code>，并将指定<code>Collection</code>中的内容复制到新的list中。
     *
     * @param c 要复制的<code>Collection</code>
     */
    public PageList(Collection<E> c) {
        this(c, null);
    }

    /**
     * 创建<code>PageList</code>，并将指定<code>Collection</code>中的内容复制到新的list中。
     *
     * @param c 要复制的<code>Collection</code>
     */
    public PageList(Collection<E> c, Paginator paginator) {
        super(c);
        this.paginator = (paginator == null) ? new Paginator()
                                             : paginator;
    }

    /**
     * 取得分页器，可从中取得有关分页和页码的所有信息。
     *
     * @return 分页器对象
     */
    public Paginator getPaginator() {
        return paginator;
    }

    /**
     * 设置分页器。
     *
     * @param paginator 要设置的分页器对象
     */
    public void setPaginator(Paginator paginator) {
        if (paginator != null) {
            this.paginator = paginator;
        }
    }
    
}
