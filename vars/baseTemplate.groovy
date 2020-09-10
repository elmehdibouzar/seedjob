def call(boolean withNodeCreation = true, String workingDir = "/tmp", def additionalContainers = [], def volumes = [], def body) {

    def jnlpContainerTemplate = containerTemplate(name: 'jnlp',
            image: 'image-registry.openshift-image-registry.svc:5000/test/dockerfile-rhel-7',
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
