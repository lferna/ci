def call(Map<String, String> config) {

    def util = new cd.Util(this)

    node {
        stage("Git") {
            try {
                if (config.git_origin == "tags") {
                    git([url: config.git_url, credentialsId: config.gitCredentials])
                    sh('git checkout -b temp')
                    sh('git branch -D master')
                    sh("git checkout -b master tags/${config.git_ref}")
                    sh('git branch -D temp')
                } else {
                    git([url: config.git_url, branch: config.git_ref, credentialsId: config.gitCredentials])
                }
            }
            catch (Exception err) {
                echo '*******************************************'
                echo err.getMessage()
                echo '*******************************************'
                util.getError(err.getMessage())
            }
        }
    }
}
