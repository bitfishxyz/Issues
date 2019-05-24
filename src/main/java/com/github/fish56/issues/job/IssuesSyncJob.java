package com.github.fish56.issues.job;

import com.github.fish56.issues.service.IssueServiceRedis;
import com.github.fish56.issues.service.RepositoryServiceRedis;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.Repository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 定时同步spring-projects top10 的厂库的issue；
 */
@Slf4j
public class IssuesSyncJob extends QuartzJobBean {
    private static String owner = "spring-projects";
    @Autowired
    private IssueServiceRedis issueServiceRedis;
    @Autowired
    private RepositoryServiceRedis repositoryServiceRedis;

    /**
     * 每个一段时间同步一个波spring-projects前10fork的厂库的issues
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("正在执行同步" + owner + "的issues 的 job");
        log.info("正在获取repos的数据");
        // 这个数据持久一点，缓存频率低一点，如果需要更新，有单独的job
        List<Repository> repositories = repositoryServiceRedis.getTop10ForksRepos(owner, false);

        repositories.forEach(repository -> {
            log.info("正在同步repo " + repository.getName() + " 的issues");
            issueServiceRedis.getIssues(owner, repository.getName(), true);
        });
    }
}
