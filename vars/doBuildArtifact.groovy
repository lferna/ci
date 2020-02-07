import cd.Util

def call(Map<String, String> config) {

    def util = new cd.Util(this)

    node {
        stage('Build') {
            try {
			bat 'D:/Carrefour/SCA-SFC/jmeter_SFC/bin/jmeter.bat -Jjmeter.save.saveservice.output_format=xml -n -t D:/Carrefour/SCA-SFC/jmeter_SFC/backups/SCA-000001.jmx -l D:/Test_jenkins.jtl'
			
			//perfReport filterRegex: '', relativeFailedThresholdNegative: 1.2, relativeFailedThresholdPositive: 1.89, relativeUnstableThresholdNegative: 1.8, relativeUnstableThresholdPositive: 1.5, sourceDataFiles: 'D:\Test_jenkins.jtl'
               // maven(util)
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
        sh "mvn package -Dmaven.test.skip=true -e"
}
