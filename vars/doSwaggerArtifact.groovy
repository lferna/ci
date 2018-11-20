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
		def exists = fileExists "${workspace}/generated/swagger-ui/swagger.json"
		if (exists) {
			echo 'Yes'
		} else {
			echo 'No'
		}
}
