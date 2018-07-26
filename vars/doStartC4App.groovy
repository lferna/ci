def call(Map<String, Object> config) {

    def util = new cd.Util(this)

    node {
        config = getConfig(config)
        doInitTools.call(config)
        doGit.call(config)		
        doBuildArtifact.call(config)        
    }
}

def getConfig(Map<String, Object> config) {
    //Add build parameters to configFile
    config.put("groovyVersion", "groovy-2.4.10")
    config.put("git_ref", git_ref)
    config.put("git_url", git_url)
    //config.put("git_url_ssh", git_url_ssh)
    //config.put("git_origin", git_origin.contains('tags') ? "tags" : "branch")
    //config.put("build_number", env.BUILD_ID)
    //config.put("environment", environment)

    //Overrride app in case set in var environment
    if (app != "jenkinsfile") {
        config.app = app
    }
    if (!config.boteqa) {
        config.put("boteqa", false)
    }
    return config
}
