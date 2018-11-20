import cd.Util

def call(Map<String, String> config) {

    def util = new cd.Util(this)

    node {
        stage('Swagger') {
            try {
                swagger(util)
            } catch (Exception e) {
                echo '*******************************************'
                echo e.getMessage()
                echo '*******************************************'
                util.getError(e.getMessage())

            }
        }
    }
}

def swagger(Util util) {
        def env.WORKSPACE = pwd()
		def version = readFile "${env.WORKSPACE}/README.md"
		echo 'Workspace & file is: '
		echo version
}
