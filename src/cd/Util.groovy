package cd

import java.io.IOException;

class Util implements Serializable {

	def script

	Util(script) {
        this.script = script
    }

	def mvnProf(){
		if (script.env.BRANCH_NAME != "master"){    	
  			return '-P desa'    						
    	}
    	else {
    		return '-P prod'
    	}
	} 

    def getError (String err) {
        script.currentBuild.result = 'FAILURE'
        throw new hudson.AbortException(err)
    }    

}



