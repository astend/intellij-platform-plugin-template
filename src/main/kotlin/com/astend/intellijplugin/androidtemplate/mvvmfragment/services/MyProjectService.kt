package com.astend.intellijplugin.androidtemplate.mvvmfragment.services

import com.astend.intellijplugin.androidtemplate.mvvmfragment.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

  init {
    println(MyBundle.message("projectService", project.name))
  }
}
