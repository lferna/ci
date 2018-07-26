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
                //NodeJS+Sencha Client
                def nodeHome = tool 'NodeJS_6.11'
                env.PATH = "${nodeHome}:${env.PATH}"

                //JAVA
                def java = tool 'jdk1.8.0_92'
                env.PATH = "${java}/bin:${env.PATH}"
                echo env.PATH

                //MAVEN
                if (config.containsKey("mavenVersion")) {
                    def mvnHome = tool config.get("mavenVersion")
                    echo mvnHome
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                    echo env.PATH
                }

                //GRADLE
                if (config.containsKey("gradleVersion")) {
                    def gradleHome = tool config.get("gradleVersion")
                    env.PATH = "${gradleHome}/bin:${env.PATH}"
                }
                if (config.containsKey("groovyVersion")) {
                    def groovyHome = tool config.get("groovyVersion")
                    env.PATH = "${groovyHome}/bin:${env.PATH}"
                }
                if (config.containsKey("senchaCmdVersion")) {
                def senchaCmdHome = tool config.get("senchaCmdVersion")
                env.PATH = "${senchaCmdHome}:${env.PATH}"
                }

                if (config.containsKey("npmVersion")) {
                    def npmHome = tool config.get("npmVersion")
                    env.PATH = "${npmHome}/bin:${env.PATH}"
                    //Se necesita para el upload
                    def mvnHome = tool "MAVEN_3.0.3"
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                }
                if (config.containsKey("pythonVersion")) {
                    //Se necesita para el upload
                    def mvnHome = tool "MAVEN_3.0.3"
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                }


                echo env.PATH

                //SONARQUBE
                def scannerHome = tool 'SonarQube_Scanner_2.8'
                env.PATH = "${scannerHome}/bin:${env.PATH}"

                //Openshift Client
                def ocHome = tool 'openshift-origin-client-tools-v1.5.0'
                env.PATH = "${ocHome}:${env.PATH}"
                echo env.PATH

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
