package per.yan.lock;

/**
 * @author yan.gao
 * @date 2019/11/4 10:27 上午
 */
public interface LockProxy {

    boolean lock(String key, long expire);

    void release(String key);
}
