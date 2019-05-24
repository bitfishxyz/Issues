package com.github.fish56.issues.github;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.junit.BeforeClass;

public class GitHubBaseTest {
    private static String token = "2eeca2860f7ea51ecb44d5a4c44b9be99b87be01";
    protected static GitHubClient gitHubClient;
    @BeforeClass
    public static void setClient(){
        gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(token);
    }
}
