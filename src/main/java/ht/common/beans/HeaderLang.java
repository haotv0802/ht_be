package ht.common.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Property of CODIX Bulgaria EAD
 * Created by vtodorov
 * Date:  02/04/2016 Time: 3:32 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface HeaderLang
{
}
