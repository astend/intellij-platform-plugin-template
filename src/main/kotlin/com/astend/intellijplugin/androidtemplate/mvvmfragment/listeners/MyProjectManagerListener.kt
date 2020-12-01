package com.astend.intellijplugin.androidtemplate.mvvmfragment.listeners

import com.astend.intellijplugin.androidtemplate.mvvmfragment.services.MyProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener: ProjectManagerListener {

  companion object {
    var projectInstance: Project? = null
  }

  override fun projectOpened(project: Project) {
    projectInstance = project
    //project.service<MyProjectService>()
    project.getService(MyProjectService::class.java)
  }

  override fun projectClosing(project: Project) {
    projectInstance = null
    super.projectClosing(project)
  }

}
