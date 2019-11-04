package com.bootdo.clouddocommon.dto;

import java.io.Serializable;

/**
 * @Author : liushan
 * @Date : 2019/11/1 4:57 PM
 */
public abstract class BaseDTO implements Serializable {
    public BaseDTO() {
    }
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseDTO)) {
            return false;
        } else {
            BaseDTO other = (BaseDTO)o;
            return other.canEqual(this);
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseDTO;
    }

    public int hashCode() {
        int result = 1;
        return result;
    }

    public String toString() {
        return "BaseDTO()";
    }
}
