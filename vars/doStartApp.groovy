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
    config.put("git_ref", git_ref)
    config.put("git_url", git_url)

    //Overrride app in case set in var environment
    if (app != "jenkinsfile") {
        config.app = app
    }
    return config
}
