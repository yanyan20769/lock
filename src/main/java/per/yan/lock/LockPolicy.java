package per.yan.lock;

/**
 * @author yan.gao
 * @date 2019/11/1 1:53 下午
 */
public enum LockPolicy {

    /**
     * 自定义lua脚本实现，获取锁即时失败，不支持等待
     */
    CUSTOM,

    /**
     * 内部采用redisson实现，支持获取锁等待
     */
    REDISSON;
}
