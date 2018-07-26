import cd.Util

def call(Map<String, String> config) {

    def util = new cd.Util(this)

    node {
        stage('Build') {
            try {

                if (config.containsKey("mavenVersion")) {
                    maven(config.mavenProfile, util)
                } else if (config.containsKey("npmVersion")) {
                    npm(config)
                } else if (config.containsKey("pythonVersion")) {
                    python()
                } else {
                    gradle()
                }

            } catch (Exception e) {
                echo '*******************************************'
                echo e.getMessage()
                echo '*******************************************'
                util.getError(e.getMessage())

            }
        }
    }
}

def gradle() {
    //Ejecuta maven con los settings especificados y define los profiles ne caso de que los hubiera
    sh "gradle -g /integracioncontinua/workspace/gradle/ clean build"
}

def maven(boolean mavenProfile, Util util) {
    //Ejecuta maven con los settings especificados y define los profiles ne caso de que los hubiera
    configFileProvider([configFile(fileId: 'Default_Maven_Settings', variable: 'MAVEN_SETTINGS')]) {
        def profile
        //	profile = mavenProfile ? util.mvnProf() : ""

        sh "mvn clean"
        sh "mvn package -Dmaven.test.skip=true -e -X"
    }
}

def npm(Map<String, String> config) {
    withCredentials([usernamePassword(
            credentialsId: 'PROXY_CREDENTIALS',
            passwordVariable: 'PASSWORD',
            usernameVariable: 'USER')]) {

        sh("export https_proxy=http://$USER:$PASSWORD@bcproxyldap.es.wcorp.carrefour.com:8080")
        sh("npm config delete registry")
        sh("npm config set https-proxy=http://$USER:$PASSWORD@bcproxyldap.es.wcorp.carrefour.com:8080/")
    }


    if (config.containsKey("senchaCmdVersion")) {

        def environmets = new HashMap<>(
                "dev": "dev",
                "cua": "test",
                "pro": "pro"
        )

        dir(config.senchaBuildPath) {
            sh("/integracioncontinua/resources/Sencha/Cmd/sencha-${config.senchaCmdVersion} app install")
            sh("/integracioncontinua/resources/Sencha/Cmd/sencha-${config.senchaCmdVersion} app build -${environmets.get(config.environment)} -des build")
            //sh("/integracioncontinua/resources/Sencha/Cmd/sencha-${config.senchaCmdVersion} app build -dev ")
            sh("tar -zcvf ../dist.tgz -C build/ .")
        }
    } else {
        sh("npm install")

        if (config.enableAngular) {
            //Builds con Angular
            //  Probamos el comando que manda Mo2o sh("ng build --pro --progress false")
            sh("ng build --progress=false --verbose=true --target=production --environment=${config.environment}")
            sh("tar -zcvf dist.tgz -C dist .")

        } else {
            sh("NODE_ENV=production npm run build")
            sh("tar -zcvf dist.tgz -C build/client .")
        }
    }
}

def python() {
    sh "tar -zcvf dist.tgz ./*"
}
