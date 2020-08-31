def call(String workingDir = "/tmp", boolean dev = false, def body) {

    def talendContainerTemplate = containerTemplate(name: 'build',
            image: "*:${dev ? 'dev' : 'prod'}",
            command: 'cat',
            ttyEnabled: true,
            workingDir: workingDir,
            alwaysPullImage: true,
            envVars: [
                    secretEnvVar(key: 'NEXUS_V2_USERNAME', secretName: 'maven-secret', secretKey: 'NEXUS_V2_USERNAME'),
                    secretEnvVar(key: 'NEXUS_V2_PASSWORD', secretName: 'maven-secret', secretKey: 'NEXUS_V2_PASSWORD'),

            ])

    def mavenVolume = persistentVolumeClaim(claimName: 'maven-repository-2', mountPath: '')
    def talendlib = persistentVolumeClaim(claimName: 'talend-lib', mountPath: '/sofa/talend-lib')

    baseTemplate(true, workingDir, [talendContainerTemplate], [talendlib,mavenVolume]) {
        body()
    }
}
