package com.github.fish56.issues.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class RepositoryServiceRedis {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 对Redis的包装类
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *
     * @param owner
     * @param sync 是否从github同步，如果为true，那么将不会使用redis缓存，直接请求github服务器
     * @return
     */
    public List<Repository> getTop10ForksRepos(String owner, boolean sync) {
        return getTopForksRepos(owner, 10, sync);
    }

    public List<Repository> getTopForksRepos(String owner, int count, boolean sync) {
        log.info("正在尝试获取数据");

        // 通过这个对象来操作数据库
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        // 统一key的格式
        String key = String.format("%s:forks:top%s", owner, count);

        if (!sync && redisTemplate.hasKey(key)){
            log.info("命中缓存");
            String topForksRepos = operations.get(key);
            List<Repository> repos = JSONObject.parseArray(topForksRepos, Repository.class);
            return repos;

        } else {
            try{
                if (redisTemplate.hasKey(key)) {
                    log.info("缓存中有数据，但是目前正在同步github并更新redis数据");
                } else {
                    log.info("没有命中缓存，正在向后台请求数据");
                }
                List<Repository> repos = repositoryService.getRepositories(owner);
                Collections.sort(repos, (r1, r2) -> {
                    return r2.getForks() - r1.getForks();
                });
                log.info(String.format("获取仓库%s的详细数据，正在缓存到Redis数据库", owner));
                operations.set(key, JSONObject.toJSONString(repos.subList(0, 10)));
                return repos;

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
