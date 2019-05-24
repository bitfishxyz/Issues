package com.github.fish56.issues.config;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    /**
     * 从配置文件中读取token
     */
    @Value("${github.token}")
    private String token;

    /**
     * 配置github客户端
     * @return
     */
    @Bean
    public GitHubClient gitHubClient(){
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(token);
        return gitHubClient;
    }

    /**
     * 配置IssueService
     * @param gitHubClient 自动注入
     * @return
     */
    @Bean
    public IssueService issueService(GitHubClient gitHubClient){
        return new IssueService(gitHubClient);
    }

    @Bean
    public OrganizationService organizationService(GitHubClient gitHubClient){
        return new OrganizationService(gitHubClient);
    }

    @Bean
    public RepositoryService repositoryService(GitHubClient gitHubClient){
        return new RepositoryService(gitHubClient);
    }
}
