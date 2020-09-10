def call(String workingDir = "/tmp", boolean dev = false, def body) {

    def talendContainerTemplate = containerTemplate(name: 'build',
            image: "image-registry.openshift-image-registry.svc:5000/test/openjdk8-maven:latest",
            command: 'cat',
            ttyEnabled: true,
            workingDir: workingDir,
            alwaysPullImage: true,
)

//   def mavenVolume = persistentVolumeClaim(claimName: 'maven-repository-2', mountPath: '')
//   def talendlib = persistentVolumeClaim(claimName: 'talend-lib', mountPath: '/sofa/talend-lib')

    baseTemplate(true, workingDir, talendContainerTemplate) {
        body()
    }
}
