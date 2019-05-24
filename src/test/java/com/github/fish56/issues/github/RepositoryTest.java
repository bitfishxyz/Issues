package com.github.fish56.issues.github;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Test;

@Slf4j
public class RepositoryTest extends GitHubBaseTest {

    @Test
    public void issues() throws Exception{
        RepositoryService repositoryService = new RepositoryService(gitHubClient);
        Repository vue = repositoryService.getRepository("vuejs", "vue");
        log.info("forks: " + vue.getForks());
        log.info("watchers: " + vue.getWatchers());
    }
}
