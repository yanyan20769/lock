### 使用方式
- 1 copy所有类到自己项目
- 2 yaml中加入redis相应的配置 如 spring.redis.host
- 3 springboot启动类上加上@EnableLock注解
- 4 具体需要使用分布式锁的业务代码方法上加上@Lock