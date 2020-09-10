def call(boolean withNodeCreation = true, String workingDir = "/tmp", def additionalContainers = [], def volumes = [], def body) {

    def jnlpContainerTemplate = containerTemplate(name: 'jnlp',
            image: 'docker-registry.default.svc:5000/bsc-dpt-a2742-global/openshift-jenkins-slave-base-rhel7:latest',
            args: '-disableHttpsCertValidation ${computer.jnlpmac} ${computer.name}',
            workingDir: workingDir,
            alwaysPullImage: false)

    def containers = [jnlpContainerTemplate]
    containers.addAll(additionalContainers)

    String nodeLabel = withNodeCreation ? "jenkins-${currentBuild.startTimeInMillis}" : "jenkins-pipeline"
    podTemplate(label: nodeLabel, containers: containers, volumes: volumes) {
        if (withNodeCreation) {
            node(nodeLabel) {
                body()
            }
        } else {
            body()
        }
    }
}
