package per.yan.lock;

/**
 * @author yan.gao
 * @date 2019/11/1 10:06 上午
 */
public class LockException extends RuntimeException {

    private static final long serialVersionUID = -8502385996266165615L;

    public LockException() {
        super();
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable throwable) {
        super(throwable);
    }

    public LockException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
