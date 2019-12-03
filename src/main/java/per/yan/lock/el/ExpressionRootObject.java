package per.yan.lock.el;

import lombok.Getter;

/**
 * @author yan.gao
 * @date 2019/8/30 4:55 下午
 */
@Getter
public class ExpressionRootObject {
    private final Object object;

    private final Object[] args;

    public ExpressionRootObject(Object object, Object[] args) {
        this.object = object;
        this.args = args;
    }

}