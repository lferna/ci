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
        def workspace = pwd()
		def version = fileExists "${workspace}/generated/swagger-ui/swagger.json"
		echo 'Workspace & file is: '
		echo version
}
