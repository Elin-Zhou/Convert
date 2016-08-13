/**
 * Yumeitech.com.cn Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.elin4it.util.convert;

/**
 * ÀàÐÍ×ª»»Æ÷
 * 
 * @author ElinZhou
 * @version $Id: ConvertInvoker.java , v 0.1 2016/7/18 18:53 ElinZhou Exp $
 */
public interface ConvertInvoker<T> {

    T invoke(T t);
}
