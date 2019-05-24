package com.github.fish56.issues.service;

import com.github.fish56.issues.IssuesApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class IssueServiceRedisTest extends IssuesApplicationTests {
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueServiceRedis issueServiceRedis;

    private String owner = "vuejs";
    private String repo = "vue";

    @Test
    public void myIssues(){
    }

    @Test
    public void getIssuesWithRedis() {
        long start = System.currentTimeMillis();
        List<Issue> issues = issueServiceRedis.getIssues(owner, repo, false);
        System.out.println("size of issues: " + issues.size());
        issues = issueServiceRedis.getIssues(owner, repo, false);
        issues = issueServiceRedis.getIssues(owner, repo, false);
        long timeMillis = System.currentTimeMillis() - start;
        log.info("with redis 三次请求共计耗时：" + timeMillis);
        // with redis 三次请求共计耗时：21308
    }


    @Test
    public void getIssuesWithoutRedis() throws Exception{
        long start = System.currentTimeMillis();
        List<Issue> issues = issueService.getIssues(owner, repo, null);
        issues = issueService.getIssues(owner, repo, null);
        issues = issueService.getIssues(owner, repo, null);
        long timeMillis = System.currentTimeMillis() - start;
        log.info("without redis 三次请求共计耗时：" + timeMillis);
        // without redis 三次请求共计耗时：69480
    }
}