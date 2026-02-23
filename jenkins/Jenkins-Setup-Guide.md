# Jenkins Setup Guide: Building a Minecraft Forge Mod in Docker

This guide explains how to connect your local Minecraft Mod project to a Jenkins instance running inside Docker and set up the provided `Jenkinsfile` to build your project.

## 1. Prerequisites

1. **Jenkins Running in Docker:** Ensure you have Jenkins up and running on your host machine via Docker.
2. **JDK 17 in Jenkins:** Minecraft Forge 1.20.1 requires Java 17 to compile. Your Jenkins instance must have JDK 17 configured.

## 2. Preparing your Project (The Host)

Jenkins uses the `Jenkinsfile` located in your project (in your case, `jenkins/Jenkinsfile`) to know how to build the mod.
For Jenkins to access your code and automatically build on changes, it needs to access your project files as a **Git Repository**.

If you haven't already initialized Git in your project folder, run these commands in your host terminal from the project root (`/run/media/aaa/FilesMediaEtc/010Projects/Github/Java-MinecraftModding/Own/Polydipsia`):

```bash
git init
git add .
git commit -m "Initial commit for Jenkins"
```

## 3. Allowing Jenkins Docker Container to Access the Host Project

There are two primary ways to let the Jenkins Docker container access your code:

**Option A: Push to a Remote Git Server (Easiest)**
Push your code to GitHub, GitLab, or Gitea, and simply configure Jenkins to clone the repository URL. This avoids tricky Docker permission issues between the container and host.

**Option B: Map your Local Folder into the Jenkins Container (Local)**
If you do not want to push your code online, you must mount your project folder into the Jenkins container.
*Warning: Running Jenkins via Docker on Linux often means the container process runs as the `jenkins` user (UID 1000). To avoid "Permission Denied" errors when Jenkins tries to write to your `build/` folder, ensure your local folder permissions allow Jenkins to read/write.*

Example docker run command to mount your project:

```bash
docker run -d -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /run/media/aaa/FilesMediaEtc/010Projects/Github/Java-MinecraftModding/Own/Polydipsia:/opt/polydipsia \
  jenkins/jenkins:lts
```

## 4. Configuring the Pipeline in Jenkins

Once Jenkins has access to the repository (either locally or remotely), set up the pipeline:

1. Open Jenkins in your web browser.
2. Click **New Item** on the left sidebar.
3. Enter a name for the project (e.g., `Polydipsia-Mod`).
4. Select **Pipeline** and click **OK**.
5. Scroll down to the **Pipeline** section at the bottom.
6. Change the **Definition** dropdown to **Pipeline script from SCM**.
7. Select **Git** from the SCM dropdown.
8. **Repository URL:**
   * *If using GitHub/GitLab (Option A):* Enter the remote URL (e.g., `https://github.com/yourname/polydipsia.git`).
   * *If using a mounted folder (Option B):* Enter the path mapped inside the container (e.g., `file:///opt/polydipsia`).
9. **Script Path:** Change this to **`jenkins/Jenkinsfile`** (this is very important since your Jenkinsfile is inside the `jenkins` folder).
10. Click **Save**.

## 5. Configuring Java 17 in Jenkins

The provided `Jenkinsfile` explicitly calls for a JDK tool named `jdk17`. You must configure this in Jenkins:

1. Go to **Manage Jenkins** -> **Tools**.
2. Scroll down to **JDK Installations**.
3. Click **Add JDK**.
4. Name the JDK exactly: **`jdk17`** (must match the name in the `Jenkinsfile`).
5. Check **Install automatically**. Select an installer like "Install from adoptium.net" and choose JDK 17.
6. Click **Save**.

## 6. Running and Automating Builds

**Manual Builds:**
You can manually run the pipeline by clicking **Build Now** on the project page.

**Auto Builds on Changes (Polling):**
To make Jenkins automatically check for changes and build:

1. Go to the Pipeline configuration (click **Configure** on the left).
2. Under **Build Triggers**, check **Poll SCM**.
3. Enter a cron schedule, for example: `H/5 * * * *` to check for new commits every 5 minutes.
4. If making changes locally through the mapped volume, you just need to `git commit` your changes. The Jenkins polling will pick up the new commit directly from the local `.git` folder within `opt/polydipsia` and trigger a build.
