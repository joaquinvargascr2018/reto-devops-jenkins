import jenkins.model.*

def jobName = "SeedJob"

def configXml = new File("/var/jenkins_home/init.groovy.d/config.xml")

if (!Jenkins.instance.getItem(jobName)) {
    def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
    try {
        def seedJob = Jenkins.instance.createProjectFromXML(jobName, xmlStream)
        seedJob.scheduleBuild(0, null)
    } catch (ex) {
        println "ERROR: ${ex}"
        println configXml.stripIndent()
    }
}