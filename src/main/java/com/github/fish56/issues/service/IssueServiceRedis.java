package com.github.fish56.issues.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class IssueServiceRedis {

    @Autowired
    private IssueService issueService;

    /**
     * 对Redis的包装类
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *
     * @param owner
     * @param repos
     * @param sync 是否从github同步，如果为true，那么将不会使用redis缓存，直接请求github服务器
     * @return
     */
    public List<Issue> getIssues(String owner, String repos, boolean sync) {
        log.info("正在尝试获取数据");

        // 通过这个对象来操作数据库
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        // 统一key的格式
        String key = String.format("%s:%s:issues", owner, repos);

        if (!sync && redisTemplate.hasKey(key)){
            log.info("命中缓存");
            String stringIssues = operations.get(key);
            List<Issue> issues = JSONObject.parseArray(stringIssues, Issue.class);
            return issues;

        } else {
            try{
                if (redisTemplate.hasKey(key)) {
                    log.info("缓存中有数据，但是目前正在同步github并更新redis数据");
                } else {
                    log.info("没有命中缓存，正在向后台请求数据");
                }

                List<Issue> issues = issueService.getIssues(owner, repos, null);
                log.info(String.format("获取到%s-%s的issues数据，正在缓存到Redis数据库", owner, repos));
                operations.set(key, JSONObject.toJSONString(issues));
                return issues;

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
