package com.github.fish56.issues.controller;

import com.github.fish56.issues.service.IssueServiceRedis;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/repos")
public class ReposController {

    @Autowired
    private IssueServiceRedis issueServiceRedis;

    @RequestMapping("/{owner}/{repoName}/issues")
    public Object getIssues(@PathVariable String owner, @PathVariable String repoName){
        log.info(String.format("正在获取issues, owner:%s, repo:%s", owner, repoName));

        List<Issue> issues = issueServiceRedis.getIssues(owner, repoName, false);

        return issues;

    }
}
