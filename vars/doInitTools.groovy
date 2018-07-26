def call(Map<String, String> config) {

    def util = new cd.Util(this)

    node {
        stage("Clean WorkSpace") {
            try {
                echo 'Limpiar el workSpace'
                deleteDir()
            }
            catch (Exception err) {
                echo '*******************************************'
                echo err.getMessage()
                echo '*******************************************'
                util.getError(err.getMessage())
            }
        }

        stage("tools") {
            try {
                //JAVA
                def java = tool 'JAVA_HOME'
                env.PATH = "${java}/bin:${env.PATH}"
                echo env.PATH

                //MAVEN
                if (config.containsKey("mavenVersion")) {
                    def mvnHome = tool config.get("mavenVersion")
                    echo mvnHome
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                    echo env.PATH
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
