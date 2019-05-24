package com.github.fish56.issues.service;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.issues.IssuesApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Slf4j
public class OrgServiceRedisTest extends IssuesApplicationTests {
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RepositoryService repositoryService;


    @Test
    public void springOrg() throws IOException {
        String org = "spring-projects";
        User spring = organizationService.getOrganization(org);
        spring.getPublicRepos();
    }

    @Test
    public void re() throws Exception {
        String org = "spring-projects";
        List<Repository> repositoryList = repositoryService.getRepositories(org);

        // 将列表排序
        Collections.sort(repositoryList, (r1, r2) -> {
            return r2.getForks() - r1.getForks();
        });

        // 获得fork数前十的repo
        List repos = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map  issueItem= new HashMap();
            issueItem.put("name", repositoryList.get(i).getName());
            issueItem.put("forks", repositoryList.get(i).getForks());
            repos.add(issueItem);
        }

        System.out.println(JSONObject.toJSONString(repos, true));
    }
}