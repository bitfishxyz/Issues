package com.github.fish56.issues.job;

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
public class RepositorySyncJob extends QuartzJobBean {
    private static String owner = "spring-projects";
    @Autowired
    private RepositoryServiceRedis repositoryServiceRedis;

    /**
     * 每个一段时间同步一个波spring-projects前10fork的仓库的star数
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("正在执行job");
        log.info(String.format("正在同步%s的forks数前十的仓库的信息", owner));
        // 这个数据持久一点，缓存频率低一点
        List<Repository> repositories = repositoryServiceRedis.getTop10ForksRepos(owner, true);
    }
}
