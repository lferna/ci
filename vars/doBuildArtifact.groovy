import cd.Util

def call(Map<String, String> config) {

    def util = new cd.Util(this)

    node {
        stage('Build') {
            try {
                maven(util)
            } catch (Exception e) {
                echo '*******************************************'
                echo e.getMessage()
                echo '*******************************************'
                util.getError(e.getMessage())

            }
        }
    }
}

def maven(Util util) {
        sh "mvn clean"
        sh "mvn package -Dmaven.test.skip=true -e -X"
}
