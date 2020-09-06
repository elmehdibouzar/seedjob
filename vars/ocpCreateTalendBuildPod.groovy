def call(boolean dev = false, def body) {
    // working directory in the image (aka home directory)
    String workingDir = "/tmp"

    baseTalendTemplate(workingDir,  dev) {
        stage("SOFA configuration") {
            container('build') {
                sh "git config --global user.name elmehdi"
                sh "git config --global user.email mehdi.bouzar@gmail.com"
            }
        }

        body()
    }
}
